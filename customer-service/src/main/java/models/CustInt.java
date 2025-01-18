package models;

public class CustInt {
    private Customer customer;
    private int money;
    public CustInt() {}

    
    public CustInt(Customer customer, int money) {
        this.customer = customer;
        this.money = money;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}