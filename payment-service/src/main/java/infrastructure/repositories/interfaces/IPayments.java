package infrastructure.repositories.interfaces;

import java.util.List;
import java.util.Map;

import models.Payment;
import models.Token;

public interface IPayments extends IRepository<Map<String, Payment>> {
    void clear();

    Payment get(String paymentId);

}
