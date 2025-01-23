package services;

import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import models.Token;
import models.TokenInt;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import static utils.EventTypes.*;

public class TokenService implements EventReceiver {
    
    private CompletableFuture<Boolean> requestTokensResult;
    private CompletableFuture<Token> getOneTokenResult;
    private CompletableFuture<Boolean> retireCustomerTokensResult;


    private EventSender eventSender;
    private final Gson gson = new Gson();

    public TokenService(EventSender eventSender)  {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event eventIn) {
        switch (eventIn.getEventType()) {
            case TOKENS_GENERATED:
                System.out.println("I got a successfull Generation of Token");
                requestTokensResult.complete(true);
                break;
            case TOKENS_REQUEST_FAILED:
                requestTokensResult.complete(false);
                break;
            case GET_FIRST_TOKEN_RETRIEVED:
                System.out.println("I got a successfull Get ONE Token");
                Token token = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Token.class);
                System.out.println(token.getId());
                getOneTokenResult.complete(token);
                break;
            case GET_FIRST_TOKEN_REQUEST_FAILED:
                getOneTokenResult.complete(null);
                break;
            case "CustomerRetirementSuccessful":
                retireCustomerTokensResult.complete(true);
                break;
            case "CustomerRetirementFailed":
                retireCustomerTokensResult.complete(false);
                break;
            default:
            System.out.println("Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }


    public boolean sendGenerateTokensEvent(TokenInt tokenInt) throws Exception{
        String eventType = TOKENS_GENERATION_REQUESTED;
        System.out.println("we are sending request of token event");
        Object[] arguments = new Object[]{tokenInt};
        Event event = new Event(eventType, arguments);
        requestTokensResult = new CompletableFuture<>();
        eventSender.sendEvent(event);
        System.out.println("almost requestTokenResults join");
        return requestTokensResult.join();

    }

    public Token sendGetOneTokenEvent(String customerId) throws Exception{
        System.out.println("I reached RequestGetToken");
        String eventType = GET_FIRST_TOKEN_REQUESTED;
        Object[] arguments = new Object[]{customerId};
        Event event = new Event(eventType, arguments);
        getOneTokenResult = new CompletableFuture<>();
        eventSender.sendEvent(event);

        System.out.println("We are here in GET_FIRST_TOKEN_REQUESTED");
        return getOneTokenResult.join();
    }
}
