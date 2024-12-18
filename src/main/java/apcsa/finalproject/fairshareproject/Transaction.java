package apcsa.finalproject.fairshareproject;

public class Transaction {
    private final String name; // debit or credit
    private final double amount;
    private final String description;
    private final String status;

    public Transaction(String name, double amount, String description, String status) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
