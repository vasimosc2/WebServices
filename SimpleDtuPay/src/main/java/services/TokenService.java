package services;

import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import models.Token;
import models.TokenInt;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

public class TokenService implements EventReceiver {
    
    private CompletableFuture<Boolean> requestTokensResult;
    private CompletableFuture<Token> getTokenResult;
    private CompletableFuture<Boolean> retireCustomerTokensResult;


    private EventSender eventSender;
    private final Gson gson = new Gson();

    public TokenService(EventSender eventSender)  {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event eventIn) {
        switch (eventIn.getEventType()) {
            case "RequestTokensSuccessfull":
                System.out.println("I got a successfull Token request");
                requestTokensResult.complete(true);
                break;
            case "RequestTokensFailed":
                requestTokensResult.complete(false);
                break;
            case "GetTokenSuccessfull":
                System.out.println("I got a successfull GetToken");
                Token token = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), Token.class);
                getTokenResult.complete(token);
                break;
            case "GetTokenFailed":
                getTokenResult.complete(null);
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


    public boolean sendRequestTokensEvent(TokenInt tokenInt) throws Exception{
        String eventType = "RequestTokens";
        Object[] arguments = new Object[]{tokenInt};
        Event event = new Event(eventType, arguments);
        requestTokensResult = new CompletableFuture<>();
        eventSender.sendEvent(event);
        
        return requestTokensResult.join();

    }

    public Token sendGetTokenRequest(String customerId) throws Exception{
        System.out.println("I reached RequestGetToken");
        String eventType = "RequestGetToken";
        Object[] arguments = new Object[]{customerId};
        Event event = new Event(eventType, arguments);
        getTokenResult = new CompletableFuture<>();
        eventSender.sendEvent(event);

        return getTokenResult.join();
    }
}
