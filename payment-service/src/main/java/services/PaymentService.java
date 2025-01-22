package services;

import java.math.BigDecimal;

import dtu.ws.fastmoney.BankService;
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

        BigDecimal money = bankPay.getMoney();
        String tokenId = bankPay.getTokenId();
        String merchantId = bankPay.getMerchantId();


        //TODO CreateEVENT on RABBITMQ to retrieve the customerID from the token service   <custID, list<Token>>
        //subsequent TODO CreateEVENT on RABBITMQ that given the customerID to retrieve the customer from the customer service  < List <Customers>>
        //
        // in parallel TODO CreateEVENT on RABBITMQ that given the merchantID to retrieve the merchant from the merchant service  < List <Merchants>>
        //
        // subsequent TODO Customer.getBankAcc, Merhcant.getBankAcc


        //BankService bankService = new BankService();
        //boolean success = bankService.transferMoneyFromTo(Customer.getBankAccount, merchant.getBankACcount, money);

        return Response.status(Response.Status.OK).build();

        //subsequent TODO CreateEVENT on RABBITMQ to update the token service with the new token status




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


}









