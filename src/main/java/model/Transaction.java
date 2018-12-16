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

    public long getSender() {
        return senderId;
    }

    public long getRecipient() {
        return recipientId;
    }

    public double getAmount(){return amount;}
}
