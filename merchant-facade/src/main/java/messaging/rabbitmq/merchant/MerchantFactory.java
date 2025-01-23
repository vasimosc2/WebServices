package messaging.rabbitmq.merchant;
import messaging.EventSender;
import services.MerchantService;


public class MerchantFactory {

    static MerchantService merchantService = null;

    public static MerchantService getService() {
        if (merchantService  != null) {
            return merchantService ;
        }

        EventSender sender = new MerchantSender();
        merchantService  = new MerchantService(sender);
        MerchantListener r = new MerchantListener(merchantService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return merchantService;
    }
}
