package model;

import server.Server;

import java.io.*;
import java.net.Socket;

public class BankOperation {
    private int commission;
    private long minimumMoney;
    private String password;
    private long accountId;

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

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public void setMinimumMoney(long minimumMoney) {
        this.minimumMoney = minimumMoney;
    }
}
