package services;

import dtu.ws.fastmoney.*;


import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.AccountRegistrationException;
import exceptions.account.BankAccountException;
import infrastructure.repositories.MerchantsList;
import infrastructure.repositories.interfaces.IMerchants;
import models.Merchant;
import services.interfaces.IMerchantService;
import models.MerchInt;
import java.math.BigDecimal;
import java.util.UUID;

@jakarta.enterprise.context.ApplicationScoped
public class MerchantService implements IMerchantService {

    private final BankService bankService = new BankServiceService().getBankServicePort();

    private final IMerchants repo = MerchantsList.getInstance(); 

    public void clear() {
        repo.clear();
    }

    @Override
    public String register(Merchant merchant) throws BankServiceException_Exception,AccountExistsException, BankAccountException {

        if (isRegistered(merchant)) {
            throw new AccountExistsException("Account with cpr (" + merchant.getCprNumber() + ") already exists!");
        }
        merchant.setId("MERC-"+ UUID.randomUUID());
        repo.add(merchant);
        return merchant.getId();

        
    }

    @Override
    public Merchant get(String cpr) throws AccountNotFoundException {
        Merchant merchant = repo.getByCpr(cpr);

        if (merchant == null) {
            throw new AccountNotFoundException("Account with Cpr (" + cpr + ") is not found!");
        }

       return merchant;
    }

    @Override
    public Merchant getMerchantById(String merchantId) throws AccountNotFoundException {
        Merchant merchant = repo.getById(merchantId);

        if (merchant == null) {
            throw new AccountNotFoundException("Account with Cpr (" + merchantId + ") is not found!");
        }

       return merchant;
    }


    @Override
    public void retireAccountByCpr(String cpr) throws BankAccountException {
        Merchant merchant = repo.getByCpr(cpr);

        if (merchant == null) {
            return;
        }

        retireAccountFromInfo(merchant);
    }




    // HELPERS

    private boolean isRegistered(Merchant merchant) {
        Merchant potentialMerchant = repo.getByCpr(merchant.getCprNumber());
        return potentialMerchant != null;
    }

    private String registerBankAccount(Merchant merchant, int money)
            throws BankAccountException {

        User user = new User();
        user.setCprNumber(merchant.getCprNumber());
        user.setFirstName(merchant.getFirstName());
        user.setLastName(merchant.getLastName());

        // try to create a bank account for the user
        String bankId;
        try {
            bankId = bankService.createAccountWithBalance(user, BigDecimal.valueOf(money));
        } catch (BankServiceException_Exception e) {
            throw new BankAccountException("Failed to create bank account" +
                    " for account with cpr (" + merchant.getCprNumber() + ")");
        }

        return bankId;
    }



    private void retireAccountFromInfo(Merchant merchant)
            throws BankAccountException {
        try {
            bankService.retireAccount(merchant.getBankAccount());
            repo.remove(merchant.getId());
        } catch (BankServiceException_Exception e) {
            throw new BankAccountException(e.getMessage());
        }
    }
}
