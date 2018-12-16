package model;

public class Transaction {
    private long senderId;
    private long recipientId;
    private double amount;

    public Transaction(long sender, long recipient, double amount) {
        this.senderId = sender;
        this.recipientId = recipient;
        this.amount = amount;
    }

    public long getSenderId() {
        return senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public double getAmount(){return amount;}
}
