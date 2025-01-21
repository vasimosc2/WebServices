package services;

import infrastructure.repositories.PaymentMap;
import infrastructure.repositories.interfaces.IPayments;
import jakarta.ws.rs.core.Response;
import models.BankPay;
import services.interfaces.IPaymentService;


@jakarta.enterprise.context.ApplicationScoped
public class PaymentService implements IPaymentService {

    private final IPayments paymentmap = PaymentMap.getInstance();

    public void clear() {
        paymentmap.clear();
    }

    @Override
    public Response requestPayment(BankPay bankPay) {

        int money = bankPay.money();

        return Response.status(Response.Status.OK).build();
    }




//        if (tokenInt.getAmount() < 1 || tokenInt.getAmount() > 5) {
//            throw new TokenException("You can only request 1 to 5 tokens");
//        }
//
//        List<Token> unusedTokens = getUnusedTokens(tokenInt.getCustomerId()); // It does not matter
//        if (unusedTokens.size() > 6) {
//            throw new TokenException("You can only have a maximum of 6 tokens");
//        }
//
//        List<Token> generatedTokens = new ArrayList<>();
//
//        for(int i = 0; i < tokenInt.getAmount(); i++){
//
//            Token token = new Token(UUID.randomUUID().toString(), false);
//            System.out.println(tokenInt.getCustomerId());
//            paymentmap.update(tokenInt.getCustomerId(),token);
//            generatedTokens.add(token);
//        }
//
//        System.out.println(paymentmap.get(tokenInt.getCustomerId()));
//        return generatedTokens.isEmpty() ? Response.status(Response.Status.BAD_REQUEST).build() : Response.status(Response.Status.OK).entity(generatedTokens).build();





}









