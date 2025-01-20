package dtu.example.models;

public class MerchInt {
    private Merchant merchant;
    private int money;
    public MerchInt() {}

    
    public MerchInt(Merchant merchant, int money) {
        this.merchant = merchant;
        this.money = money;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
