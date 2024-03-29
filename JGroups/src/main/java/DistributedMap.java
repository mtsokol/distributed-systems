import org.jgroups.*;
import org.jgroups.util.Util;
import protos.DistributedMapProtos.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static protos.DistributedMapProtos.MapOperation.OperationType;

public class DistributedMap extends ReceiverAdapter implements SimpleStringMap {

    private final Map<String, Integer> hashMap = new HashMap<>();

    private JChannel channel = new JChannel("src/main/resources/udp_stack.xml");

    DistributedMap() throws Exception {
        channel.setReceiver(this);
        channel.setDiscardOwnMessages(true);
        channel.connect("MapGroup");
        channel.getState(null, 10000);
    }

    @Override
    public void viewAccepted(View view) {
        if (view instanceof MergeView) {
            MergeView tmp = (MergeView) view;
            ViewHandler handler = new ViewHandler(channel, tmp);
            handler.start();
        }
        System.out.println("view: " + view);
    }

    @Override
    public void receive(Message msg) {

        try {
            MapOperation operation = MapOperation.parseFrom(msg.getBuffer());

            System.out.println(operation.getType() + " " + operation.getKey());

            switch (operation.getType()) {
                case PUT:
                    hashMap.put(operation.getKey(), operation.getValue());
                    break;
                case REMOVE:
                    hashMap.remove(operation.getKey());
                    break;
                default:
                    System.out.println("Not handled");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean containsKey(String key) {
        return hashMap.containsKey(key);
    }

    @Override
    public Integer get(String key) {
        return hashMap.get(key);
    }

    @Override
    public void put(String key, Integer value) {
        hashMap.put(key, value);
        sendMessage(OperationType.PUT, key, value);
    }

    @Override
    public Integer remove(String key) {
        int result = hashMap.remove(key);
        sendMessage(OperationType.REMOVE, key);
        return result;
    }

    private void sendMessage(OperationType type, String key) {
        sendMessage(type, key, 0);
    }

    private void sendMessage(OperationType type, String key, Integer value) {
        MapOperation operation;
        operation = MapOperation.newBuilder()
                .setType(type)
                .setKey(key)
                .setValue(value).build();

        Message msg = new Message(null, operation.toByteArray());

        try {
            channel.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        synchronized (hashMap) {
            Util.objectToStream(hashMap, new DataOutputStream(output));
        }
    }

    @Override
    public void setState(InputStream input) throws Exception {
        Map<String, Integer> receivedMap;
        receivedMap = Util.objectFromStream(new DataInputStream(input));
        synchronized (hashMap) {
            hashMap.clear();
            hashMap.putAll(receivedMap);
        }
        System.out.println("Set received state: " + hashMap.keySet().toString());
    }


    private static class ViewHandler extends Thread {
        JChannel ch;
        MergeView view;

        private ViewHandler(JChannel ch, MergeView view) {
            this.ch = ch;
            this.view = view;
        }

        public void run() {
            List<View> subgroups = view.getSubgroups();
            View tmp_view = subgroups.get(0); // picks the first
            Address local_addr = ch.getAddress();
            if (!tmp_view.getMembers().contains(local_addr)) {
                System.out.println("Not member of the new primary partition ("
                        + tmp_view + "), will re-acquire the state");
                try {
                    ch.getState(null, 30000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Not member of the new primary partition ("
                        + tmp_view + "), will do nothing");
            }
        }
    }
}
