import com.rabbitmq.client.*;

import java.io.IOException;

public class Technician extends SystemWorker {

    private Technician() throws Exception{
        super();
        channel.basicQos(1);
    }

    public static void main(String[] args) throws Exception {

        Technician tech = new Technician();

        String firstSpec = args[0];
        String secondSpec = args[1];

        tech.registerLogger();

        String firstQueue = tech.channel.queueDeclare().getQueue();
        String secondQueue = tech.channel.queueDeclare().getQueue();

        tech.channel.queueBind(firstQueue, tech.clinicExchange, firstSpec);
        tech.channel.queueBind(secondQueue, tech.clinicExchange, secondSpec);

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

                tech.channel.basicPublish("", properties.getReplyTo(),
                        null, (message + " done").getBytes());

                tech.channel.basicPublish(tech.clinicExchange, "admin",
                        null, (message + " done").getBytes());

                System.out.println("Received: " + message + " from " + properties.getReplyTo());
            }
        };

        tech.channel.basicConsume(firstQueue, AUTO_ACK, consumer);
        tech.channel.basicConsume(secondQueue, AUTO_ACK, consumer);
    }

}
