import com.rabbitmq.client.*;

import java.io.IOException;

public class Technician extends SystemWorker {

    private Technician() throws Exception{
        super();
    }

    public static void main(String[] args) throws Exception {

        Technician tech = new Technician();

        tech.registerLogger();

        String hipQueue = tech.channel.queueDeclare().getQueue();
        String kneeQueue = tech.channel.queueDeclare().getQueue();
        //String elbowQueue = tech.channel.queueDeclare().getQueue();

        tech.channel.queueBind(hipQueue, tech.clinicExchange, "hip");
        tech.channel.queueBind(kneeQueue, tech.clinicExchange, "knee");
        //tech.channel.queueBind(elbowQueue, tech.clinicExchange, "elbow");

        Consumer consumer = new DefaultConsumer(tech.channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tech.channel.basicPublish("", properties.getReplyTo(), null, (message + " done").getBytes());

                tech.channel.basicPublish(tech.clinicExchange, "admin",
                        null, "copy done".getBytes());

                System.out.println("Received: " + message + " from " + properties.getReplyTo());
            }
        };

        tech.channel.basicConsume(hipQueue, AUTO_ACK, consumer);
        tech.channel.basicConsume(kneeQueue, AUTO_ACK, consumer);
        //tech.channel.basicConsume(elbowQueue, AUTO_ACK, consumer);
    }

}