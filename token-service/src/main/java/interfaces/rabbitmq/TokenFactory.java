package interfaces.rabbitmq;

import messaging.EventSender;
import services.TokenEventService;
import services.TokenService;


public class TokenFactory {

    static TokenEventService TokenEventService = null;

    public TokenEventService getService() {

        if (TokenEventService != null) {
            return TokenEventService;
        }

        
        EventSender b = new TokenSender();
        TokenEventService = new TokenEventService(b, new TokenService());
        TokenListener r = new TokenListener(TokenEventService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return TokenEventService;
    }
}
