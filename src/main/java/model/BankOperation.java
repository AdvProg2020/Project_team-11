package model;

import server.Server;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class BankOperation {
    private int commission;
    private long minimumMoney;
    private String password;
    private long accountId;
    private HashMap<Long, Long> sellerWithdrawRequest;// id & money

    public BankOperation(int commission, long minimumMoney, String password) {
        this.commission = commission;
        this.minimumMoney = minimumMoney;
        this.password = password;
        try {
            Socket socket = new Socket("127.0.0.1", Server.getBankServerPort());
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("create_account admin admin admin " + password + " " + password);
            dataOutputStream.flush();
            System.out.println(dataInputStream.readUTF()); // TODO : debug mode
            this.accountId = Long.parseLong(dataInputStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.sellerWithdrawRequest = new HashMap<>();
        DataBase.getDataBase().setBankOperation(this);
    }

    public int getCommission() {
        return commission;
    }

    public long getMinimumMoney() {
        return minimumMoney;
    }

    public String getPassword() {
        return password;
    }

    public long getAccountId() {
        return accountId;
    }

    public HashMap<Long, Long> getSellerWithdrawRequest() {
        return sellerWithdrawRequest;
    }

    public void addSellerWithdrawRequest(long money, long sellerId) {
        this.sellerWithdrawRequest.put(sellerId, money);
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public void setMinimumMoney(long minimumMoney) {
        this.minimumMoney = minimumMoney;
    }
}
