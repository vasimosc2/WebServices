/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */

package services;
import com.google.gson.Gson;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import models.BankPay;
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
            case TOKENS_GENERATION_REQUESTED:
                try {
                    System.out.println("Hello from TOKENS_GENERATION_REQUESTED");
                    TokenInt tokenInt = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), TokenInt.class);
                    
                    System.out.println(tokenInt.getCustomerId());

                    Response response = service.requestTokens(tokenInt);

                    Event eventOut = new Event(TOKENS_GENERATED, new Object[]{response.getStatus()});
                    System.out.println("Ready to send back");
                    eventSender.sendEvent(eventOut);
                    
                } catch (Exception e) {
                    Event eventOut = new Event(TOKENS_REQUEST_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case GET_FIRST_TOKEN_REQUESTED:
                try {
                    System.out.println("Hello from RequestGetOne Token");
                    String customerId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                    System.out.println(customerId);
                    Token Token = service.getFirstToken(customerId);
                    Event eventOut = new Event(GET_FIRST_TOKEN_RETRIEVED, new Object[]{Token});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event(GET_FIRST_TOKEN_FAILED, new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;

            case PAYMENT_REQUESTED:
                try {
                    System.out.println("Hello from PAYMENT_REQUESTED in Token_Service");
                    BankPay bankPay = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), BankPay.class);
                    String correlationId = gson.fromJson(gson.toJson(eventIn.getArguments()[1]), String.class);
                    if (service.isTokenValid(bankPay.getTokenId())) {
                        String customerId = service.getCustomerIdByTokenIdForPayment(bankPay.getTokenId());
                        Event eventOut = new Event(GET_CUSTOMER_BY_CUSTOMER_ID_REQUESTED, new Object[]{customerId,correlationId});
                        System.out.println("I am contacting Customer Service ...");
                        eventSender.sendEvent(eventOut);
                    } else {
                        System.out.println("Token Not Valid");
                        Event eventOut = new Event(GET_CUSTOMER_ID_BY_TOKEN_ID_REQUEST_FAILED, new Object[]{correlationId});
                        eventSender.sendEvent(eventOut);
                    }
                } catch (Exception e) {
                    Event eventOut = new Event("FailureGotTheCustomerID", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case RETIRE_CUSTOMER_REQUEST_SUCCESS:
                System.out.println(" I got a Retire Customer so I have to delete his tokens From DtuPay");
                String customerId = gson.fromJson(gson.toJson(eventIn.getArguments()[0]), String.class);
                service.deleteToken(customerId);
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
