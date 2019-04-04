import com.rabbitmq.client.*;

abstract class SystemWorker {

    Channel channel;
    final String clinicExchange = "injury_exchange";
    final String loggerExchange = "logger_exchange";
    String callbackQueue;
    String loggerQueue;
    static final boolean AUTO_ACK = true;

    SystemWorker() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(clinicExchange, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(loggerExchange, BuiltinExchangeType.FANOUT);
        callbackQueue = channel.queueDeclare().getQueue();
        loggerQueue = channel.queueDeclare().getQueue();
        channel.queueBind(loggerQueue, loggerExchange, "");
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

    void registerLogger() throws Exception {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body);

                System.out.println("ADMIN MESSAGE: " + message);
            }
        };

        channel.basicConsume(loggerQueue, AUTO_ACK, consumer);
    }

}
