import com.rabbitmq.client.*;

import java.io.IOException;

public class Admin extends SystemWorker {

    Admin() throws Exception {
        super();
    }

    public static void main(String[] args) throws Exception {

        Admin admin = new Admin();

        String adminQueue = admin.channel.queueDeclare().getQueue();

        admin.channel.queueBind(adminQueue, admin.clinicExchange, "admin");

        Consumer consumer = new DefaultConsumer(admin.channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("ADMIN: " + message);
            }
        };

        admin.channel.basicConsume(adminQueue, AUTO_ACK, consumer);

        while (true) {

            Thread.sleep(4000);

            admin.channel.basicPublish(admin.loggerExchange, "", null,
                    "MESSAGE FROM ADMIN".getBytes());
        }


    }

}
