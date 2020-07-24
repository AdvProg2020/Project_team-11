package bank;

import Client.view.Validation;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

public class Account {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private long balance;
    private long accountId;
    private static ArrayList<Account> allAccounts = new ArrayList<>();

    public Account(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.balance = 0;
        allAccounts.add(this);
    }

    public long getBalance() {
        return balance;
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public static void setAllAccounts(ArrayList<Account> allAccounts) {
        Account.allAccounts = allAccounts;
    }

    public static Account getAccountByUsername(String username) {
        for (Account account : allAccounts) {
            if (account.username.equals(username))
                return account;
        }
        return null;
    }

    private static Account getAccountById(int accountId) {
        for (Account account : allAccounts) {
            if (account.accountId == accountId)
                return account;
        }
        return null;
    }

    public static String createAccount(String firstName, String lastName, String username, String password, String repeatPass) {
        if (!password.equals(repeatPass)) {
            return "passwords do not match";
        } else if (getAccountByUsername(username) != null) {
            return "username is not available";
        } else {
            Account account = new Account(firstName, lastName, username, password);
            int accountId;
            do {
                accountId = new Random().nextInt(99999) + 100000;
            } while (getAccountById(accountId) != null);
            account.accountId = accountId;
            return String.valueOf(accountId);
        }
    }

    public static String getToken(String username, String password) {
        if (getAccountByUsername(username) == null) {
            return "invalid username";
        } else {
            Account account = getAccountByUsername(username);
            if (account.password.equals(password)) {
                return createToken();
            } else {
                return "invalid password";
            }
        }
    }

    private static String createToken() {
        byte[] randomBytes = new byte[24];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }

    public static String createReceipt(String username, String type, String money, String sourceId, String destId, String description) {
        if (!Validation.validateLong(money)) {
            return "invalid money";
        }

        if (!description.matches("[A-Za-z0-9 ]*")) {
            return "your input contains invalid characters";
        }

        if (!sourceId.matches("-?\\d{1,9}") || !destId.matches("-?\\d{1,9}")) {
            return "invalid parameters passed";
        }

        if (type.equalsIgnoreCase("deposit")) {
            if (!sourceId.equals("-1")) {
                return "invalid parameters passed";
            } else if (getAccountById(Integer.parseInt(destId)) == null) {
                return "dest account is is invalid";
            } else {
                return String.valueOf(new Receipt(type,
                        Long.parseLong(money),
                        Integer.parseInt(sourceId),
                        Integer.parseInt(destId),
                        description).getReceiptId());
            }
        } else if (type.equalsIgnoreCase("withdraw")) {
            if (!destId.equals("-1")) {
                return "invalid parameters passed";
            } else if (getAccountByUsername(username).accountId != Integer.parseInt(sourceId)) {
                return "token is invalid";
            } else {
                return String.valueOf(new Receipt(type,
                        Long.parseLong(money),
                        Integer.parseInt(sourceId),
                        Integer.parseInt(destId),
                        description).getReceiptId());
            }
        } else if (type.equalsIgnoreCase("move")) {
            if (getAccountByUsername(username).accountId != Integer.parseInt(sourceId)) {
                return "token is invalid";
            } else if (getAccountById(Integer.parseInt(destId)) == null) {
                return "dest account is is invalid";
            } else if (sourceId.equals(destId)) {
                return "equal source and dest account";
            } else {
                return String.valueOf(new Receipt(type,
                        Long.parseLong(money),
                        Integer.parseInt(sourceId),
                        Integer.parseInt(destId),
                        description).getReceiptId());
            }
        } else {
            return "invalid receipt type";
        }
    }

    public static String getTransactions(String username, String type) {
        ArrayList<Receipt> allReceipts = new ArrayList<>(Receipt.getAllReceipts());
        ArrayList<Receipt> receipts = new ArrayList<>();
        Account account = getAccountByUsername(username);
        switch (type) {
            case "+":
                for (Receipt receipt : allReceipts) {
                    if (receipt.getDestAccountId() == account.accountId) {
                        receipts.add(receipt);
                    }
                }
                break;
            case "-":
                for (Receipt receipt : allReceipts) {
                    if (receipt.getSourceAccountId() == account.accountId) {
                        receipts.add(receipt);
                    }
                }
                break;
            case "*":
                for (Receipt receipt : allReceipts) {
                    if (receipt.getSourceAccountId() == account.accountId || receipt.getDestAccountId() == account.accountId) {
                        receipts.add(receipt);
                    }
                }
                break;
            default:
                Receipt receipt = Receipt.getReceiptById(Integer.parseInt(type));
                if (receipt == null) {
                    return "invalid receipt ID";
                } else if (receipt.getSourceAccountId() != account.accountId && receipt.getDestAccountId() != account.accountId) {
                    return "invalid receipt ID";
                } else {
                    receipts.add(receipt);
                }
        }
        return new Gson().toJson(receipts);
    }

    public static String payReceipt(String receiptId) {
        if (!Validation.validateInteger(receiptId)) {
            return "invalid receipt id";
        } else if (Receipt.getReceiptById(Integer.parseInt(receiptId)) == null) {
            return "invalid receipt id";
        } else {
            Receipt receipt = Receipt.getReceiptById(Integer.parseInt(receiptId));
            if (receipt.isPaid()) {
                return "receipt is paid before";
            } else {
                switch (receipt.getReceiptType()) {
                    case "deposit":
                        getAccountById(receipt.getDestAccountId()).balance += receipt.getMoney();
                        receipt.setPaid(true);
                        break;
                    case "withdraw":
                        if (getAccountById(receipt.getSourceAccountId()).balance < receipt.getMoney()) {
                            return "source account does not have enough money";
                        } else {
                            getAccountById(receipt.getSourceAccountId()).balance -= receipt.getMoney();
                            receipt.setPaid(true);
                        }
                        break;
                    case "move":
                        if (getAccountById(receipt.getSourceAccountId()).balance < receipt.getMoney()) {
                            return "source account does not have enough money";
                        } else {
                            getAccountById(receipt.getSourceAccountId()).balance -= receipt.getMoney();
                            getAccountById(receipt.getDestAccountId()).balance += receipt.getMoney();
                            receipt.setPaid(true);
                        }
                }
            }
        }
        return "done successfully";
    }
}
