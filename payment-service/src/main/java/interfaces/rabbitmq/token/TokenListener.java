package interfaces.rabbitmq.token;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import messaging.Event;
import messaging.EventReceiver;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenListener {
    private final static Logger LOGGER = Logger.getLogger(TokenListener.class.getName());
	private static final String EXCHANGE_NAME = "eventsExchange";
	private static final String QUEUE_TYPE = "topic";
	private static final String TOPIC = "Tokens";

    EventReceiver receiver;

    public TokenListener(EventReceiver receiver) {
        this.receiver = receiver;
    }

    public void listen() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        
        
        factory.setHost("172.20.0.3");
        factory.setUsername("vasimosc");
        factory.setPassword("bncvcxff3");


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, QUEUE_TYPE);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, TOPIC);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            LOGGER.log(Level.INFO, "RABBITMQ: Received raw message: " + message);

            Event event;

            try {
                event = new Gson().fromJson(message, Event.class);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "RABBITMQ: Failed to parse message to JSON: " + message);
                return;
            }

            try {
                receiver.receiveEvent(event);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "RABBITMQ: Receive event error: " + e.getMessage());
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

}