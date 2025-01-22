package interfaces.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import messaging.Event;
import messaging.EventSender;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MerchantSender implements EventSender {

    private final static Logger LOGGER = Logger.getLogger(MerchantSender.class.getName());

	private static final String EXCHANGE_NAME = "eventsExchange";
	private static final String QUEUE_TYPE = "topic";
	private static final String TOPIC = "events";

    @Override
    public void sendEvent(Event event) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");
        factory.setUsername("guest");
        factory.setPassword("guest");
		
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, QUEUE_TYPE);
            String message = new Gson().toJson(event);
            LOGGER.log(Level.INFO, "RABBITMQ: Sending message: " + message);
            channel.basicPublish(EXCHANGE_NAME, TOPIC, null, message.getBytes(StandardCharsets.UTF_8));
        }
    }

}
