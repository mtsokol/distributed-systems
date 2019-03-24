public class Main {

    public static void main(String[] args) throws Exception {

        DistributedMap distributedMap = new DistributedMap();

        while (true) {
            Thread.sleep(1000);

            String randLabel = "Entry" + (int) (Math.random() * 100);

            distributedMap.put(randLabel, (int) (Math.random() * 100));

            int result = distributedMap.get(randLabel);

            System.out.println(result);

            if ((int) (Math.random() * 100) % 3 == 0) {
                distributedMap.remove(randLabel);
            }

        }

    }

}
