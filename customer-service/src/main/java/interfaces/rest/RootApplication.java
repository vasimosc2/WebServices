package interfaces.rest;

import interfaces.rabbitmq.CustomerFactory;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;


import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.logging.Logger;

@ApplicationPath("/")
public class RootApplication extends jakarta.ws.rs.core.Application {

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    public RootApplication() {
        super();
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting... RabbitMQ host: " + System.getenv("RABBITMQ_HOST"));
        try {
            LOGGER.info("Starting RabbitMQ AccountEventService...");
            new CustomerFactory().getService();
        } catch (Exception e) {
            LOGGER.severe("Failed to start RabbitMQ AccountEventService...");
            e.printStackTrace();
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

    // New REST endpoint for "Hello World"
    @Path("/hello")
    public static class HelloWorldResource {

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String sayHello() {
            return "Hello World from Customer Service";
        }
    }
}
