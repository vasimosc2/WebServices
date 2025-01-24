/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */

package interfaces.rabbitmq;
import messaging.EventSender;
import services.MerchantEventService;
import services.MerchantService;


public class MerchnatFactory {

    static MerchantEventService merchantEventService = null;

    public MerchantEventService getService() {

        if (merchantEventService != null) {
            return merchantEventService;
        }

        
        EventSender b = new MerchantSender();
        merchantEventService = new MerchantEventService(b, new MerchantService());
        MerchantListener r = new MerchantListener(merchantEventService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return merchantEventService;
    }
}
