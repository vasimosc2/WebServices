package org.acme;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtil {
    private static final String QUEUE_NAME = "customerQueue";

    public static Channel getChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // RabbitMQ server address
        factory.setUsername("vasimosc"); // Default username
        factory.setPassword("bncvcxff3"); // Default password
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

    public static String getQueueName() {
        return QUEUE_NAME;
    }
}