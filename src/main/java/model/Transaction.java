package model;

public class Transaction {
    private Account sender;
    private Account recipient;
    private double amount;

    public Transaction(Account sender, Account recipient, double amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public Account getSender() {
        return sender;
    }

    public Account getRecipient() {
        return recipient;
    }

    public double getAmount(){return amount;}
}
