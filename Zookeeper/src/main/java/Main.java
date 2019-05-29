import org.apache.zookeeper.KeeperException;
import zooclient.ZooClient;
import java.util.Scanner;

public class Main {

    private static final String HOSTS = "localhost:2181,localhost:2182,localhost:2183";

    public static void main(String[] argv) throws Exception {

        ZooClient zooClient = new ZooClient(HOSTS);

        zooClient.zNodeExists();

        while (true) {
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            if (str.contains("tree")) {
                try {
                    zooClient.printTree(ZooClient.NODE);
                } catch (KeeperException | InterruptedException e) {
                    System.out.println("No child found");
                }
            } else if (str.contains("exit")) {
                zooClient.close();
                System.exit(0);
            }
        }
    }

}
