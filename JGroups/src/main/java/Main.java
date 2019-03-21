public class Main {

    public static void main(String[] args) throws Exception {

        DistributedMap distributedMap = new DistributedMap();

        distributedMap.put("CAR1", 43);

        distributedMap.put("HOME2", 23);

        Thread.sleep(1000);

        int result = distributedMap.get("CAR1");

        System.out.println(result);
    }

}
