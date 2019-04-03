import com.rabbitmq.client.*;

abstract class SystemWorker {

    Channel channel;
    String clinicExchange;
    String callbackQueue;
    static final boolean AUTO_ACK = true;

    SystemWorker() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        clinicExchange = "clinic_exchange";
        channel.exchangeDeclare(clinicExchange, BuiltinExchangeType.TOPIC);
        callbackQueue = channel.queueDeclare().getQueue();
    }

    void registerCallback() throws Exception {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body);

                System.out.println("Received: " + message);
            }
        };

        channel.basicConsume(callbackQueue, AUTO_ACK, consumer);
    }

}
