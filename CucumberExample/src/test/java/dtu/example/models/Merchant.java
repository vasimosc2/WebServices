/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package dtu.example.models;

public class Merchant {

   private String merchantId;
    private String firstName;
    private String lastName;
    private String cprNumber;
    private String bankAccount;

    
    public Merchant(){}

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getCprNumber() {
        return cprNumber;
    }
    
    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }

    public String getBankAccount(){
        return bankAccount;
    }

    public void setBankAccount(String bankAccount){
        this.bankAccount = bankAccount;
    }

    public String getMerchantId(){
        return merchantId;
    }

    public void setMerchantId(String merchantId){
        this.merchantId = merchantId;
    }


}