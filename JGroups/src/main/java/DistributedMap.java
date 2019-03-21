import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import protos.DistributedMapProtos.*;

import java.util.HashMap;
import java.util.Map;

import static protos.DistributedMapProtos.MapOperation.OperationType;

//TODO passing states []
//TODO merging separate regions []
//TODO randomize messages []

public class DistributedMap extends ReceiverAdapter implements SimpleStringMap {

    private Map<String, Integer> hashMap = new HashMap<>();

    private JChannel channel;

    DistributedMap() throws Exception {

        channel = new JChannel();
        channel.connect("MapGroup");
        channel.setReceiver(this);

    }

    @Override
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    @Override
    public void receive(Message msg) {

        try {

            MapOperation operation = MapOperation.parseFrom(msg.getBuffer());

            System.out.println(operation.getKey());

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

}
