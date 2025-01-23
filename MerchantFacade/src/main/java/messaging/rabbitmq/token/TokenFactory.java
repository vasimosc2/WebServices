package messaging.rabbitmq.token;
import messaging.EventSender;


public class TokenFactory {

    static TokenService TokenService = null;

    public static TokenService getService() {
        if (TokenService  != null) {
            return TokenService ;
        }

        EventSender sender = new TokenSender();
        TokenService  = new TokenService(sender);
        TokenListener r = new TokenListener(TokenService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return TokenService;
    }
}
