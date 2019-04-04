import com.rabbitmq.client.AMQP;

public class Doctor extends SystemWorker {

    private Doctor() throws Exception {
        super();
    }

    public static void main(String[] args) throws Exception {

        Doctor doctor = new Doctor();
        doctor.registerCallback();
        doctor.registerLogger();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .replyTo(doctor.callbackQueue)
                .build();

        while (true) {

            Thread.sleep(3000);

            System.out.println("Sending requests");
            doctor.channel.basicPublish(doctor.clinicExchange, "hip",
                    props, "maria, hip".getBytes());

            doctor.channel.basicPublish(doctor.clinicExchange, "knee",
                    props, "maria, knee".getBytes());

            doctor.channel.basicPublish(doctor.clinicExchange, "elbow",
                    props, "maria, elbow".getBytes());

            doctor.channel.basicPublish(doctor.clinicExchange, "admin",
                    null, "copy".getBytes());

        }

    }

}
