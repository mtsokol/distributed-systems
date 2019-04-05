import com.rabbitmq.client.AMQP;

public class Doctor extends SystemWorker {

    private AMQP.BasicProperties props;

    private Doctor() throws Exception {
        super();

        props = new AMQP.BasicProperties
                .Builder()
                .replyTo(callbackQueue)
                .build();
    }

    public static void main(String[] args) throws Exception {

        Doctor doctor = new Doctor();
        doctor.registerCallback();
        doctor.registerLogger();

        while (true) {

            Thread.sleep(3000);

            doctor.injuryPublish("hip", "maria, hip");
            doctor.injuryPublish("knee", "maria, knee");
            doctor.injuryPublish("elbow", "maria, elbow");
        }

    }

    private void injuryPublish(String key, String message) throws Exception {
        System.out.println("Sending requests");
        channel.basicPublish(clinicExchange, key,
                props, message.getBytes());
        channel.basicPublish(clinicExchange, "admin",
                null, (message + " " + hashCode()).getBytes());
    }

}
