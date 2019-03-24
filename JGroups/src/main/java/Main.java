public class Main {

    public static void main(String[] args) throws Exception {

        DistributedMap distributedMap = new DistributedMap();

        while (true) {
            Thread.sleep(1000);

            distributedMap.put("CAR1", (int) (Math.random() * 100));

            distributedMap.put("HOME2", 23);

            int result = distributedMap.get("CAR1");

            System.out.println(result);

        }

    }

}
