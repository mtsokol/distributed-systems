package zooclient;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import java.io.IOException;
import java.util.List;

public class ZooClient {

    private final ZooKeeper zk;
    public static final String NODE = "/z";
    private Process proc;

    public ZooClient(String hosts) throws IOException {
        zk = new ZooKeeper(hosts, 3000, event -> {

        });
    }

    public void close() throws InterruptedException {
        zk.close();
    }

    public void printTree(String node) throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren(node, false);

        for (String childNode : children) {
            System.out.print(childNode + " ");
        }
        System.out.println("\n-----------");

        if (!children.isEmpty()) {
            for (String childNode : children) {
                printTree(node + "/" + childNode);
            }
        }
    }

    public void zNodeExists() throws KeeperException, InterruptedException {
        Stat stat = zNodeHandle();

        if (stat != null) {
            runProc();

            try {
                watchChildren();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**INTERNAL API**/

    private Stat zNodeHandle() throws KeeperException, InterruptedException {
        return zk.exists(NODE, event -> {

            if (event.getType() == Watcher.Event.EventType.NodeCreated) {
                runProc();

                try {
                    watchChildren();
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }

            } else if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
                stopProc();
            }

            try {
                zNodeHandle();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

    private void watchChildren() throws KeeperException, InterruptedException {

        zk.getChildren(NODE, event -> {
            if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {

                try {
                    List<String> children = zk.getChildren(NODE, false);

                    System.out.println("Node has children: ");

                    for (String node : children) {
                        System.out.println(node);
                    }

                    watchChildren();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void runProc() {
        Runtime run = Runtime.getRuntime();
        try {
            proc = run.exec("gnome-calculator");
            System.out.println("Started external app");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopProc() {
        proc.destroy();
        System.out.println("Stopped external app");
    }

}
