package bank;

import java.util.ArrayList;

public class Receipt {
    private String receiptType;
    private long money;
    private int sourceAccountId;
    private int destAccountId;
    private String description;
    private int receiptId;
    private boolean paid;
    private static ArrayList<Receipt> allReceipts = new ArrayList<>();

    public Receipt(String receiptType, long money, int sourceAccountId, int destAccountId, String description) {
        this.receiptType = receiptType;
        this.money = money;
        this.sourceAccountId = sourceAccountId;
        this.destAccountId = destAccountId;
        this.description = description;
        this.receiptId = allReceipts.size() + 1;
        this.paid = false;
        allReceipts.add(this);
    }

    public String getReceiptType() {
        return receiptType;
    }

    public long getMoney() {
        return money;
    }

    public int getSourceAccountId() {
        return sourceAccountId;
    }

    public int getDestAccountId() {
        return destAccountId;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public static ArrayList<Receipt> getAllReceipts() {
        return allReceipts;
    }

    public static Receipt getReceiptById(int receiptId) {
        for (Receipt receipt : allReceipts) {
            if (receipt.receiptId == receiptId)
                return receipt;
        }
        return null;
    }
}
