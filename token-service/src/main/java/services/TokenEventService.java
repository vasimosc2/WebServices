package services;
import com.google.gson.Gson;


import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.TokenInt;
import models.Token;
import services.interfaces.ITokenService;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.core.Response;

import static utils.EventTypes.*;

public class TokenEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(TokenEventService.class.getName());

    private final EventSender eventSender;
    
    private final ITokenService service;
    private final Gson gson = new Gson();

    public TokenEventService(EventSender eventSender, TokenService service) {
        this.eventSender = eventSender;
        this.service = service;
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {
        switch (eventIn.getEventType()) {
            case TOKENS_REQUEST:
                try {
                    System.out.println("Hello from RequestTokens");
                    TokenInt tokenInt = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), TokenInt.class);
                    
                    System.out.println(tokenInt.getCustomerId());

                    Response response = service.requestTokens(tokenInt);

                    Event eventOut = new Event(TOKENS_REQUEST_SUCCESS, new Object[]{response.getStatus()});
                    System.out.println("Ready to send back");
                    eventSender.sendEvent(eventOut);
                    
                } catch (Exception e) {
                    Event eventOut = new Event(TOKENS_REQUEST_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case GET_FIRST_TOKEN_REQUEST:
                try {
                    System.out.println("Hello from RequestGetToken");
                    String customerId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                    System.out.println(customerId);
                    Token Token = service.getFirstToken(customerId);
                    Event eventOut = new Event(GET_FIRST_TOKEN_REQUEST_SUCCESS, new Object[]{Token});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event(GET_FIRST_TOKEN_REQUEST_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
