package services;
import com.google.gson.Gson;


import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.TokenInt;
import models.BankPay;
import models.Token;
import services.interfaces.ITokenService;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.core.Response;

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
            case "RequestTokens":
                try {
                    System.out.println("Hello from RequestTokens");
                    TokenInt tokenInt = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), TokenInt.class);
                    
                    System.out.println(tokenInt.getCustomerId());

                    Response response = service.requestTokens(tokenInt);

                    Event eventOut = new Event("RequestTokensSuccessfull", new Object[]{response.getStatus()});
                    System.out.println("Ready to send back");
                    eventSender.sendEvent(eventOut);
                    
                } catch (Exception e) {
                    Event eventOut = new Event("RequestTokensFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "RequestGetToken":
                try {
                    System.out.println("Hello from RequestGetToken");
                    String customerId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                    System.out.println(customerId);
                    Token Token = service.getFirstToken(customerId);
                    Event eventOut = new Event("GetTokenSuccessfull", new Object[]{Token});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("GetTokenFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "GetCustomerIdFromTokenId":
                try {
                    System.out.println("Hello form GetCustomerIdFromTokenId");
                    BankPay BankPay = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), BankPay.class);
                    String customerId = service.getCustomerIdByTokenIdForPayment(BankPay.getTokenId());
                    Event eventOut = new Event("SuccessfullGotTheCustomerID", new Object[]{customerId});
                    System.out.println("I am contacting Customer Service ...");
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("FailureGotTheCustomerID", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
