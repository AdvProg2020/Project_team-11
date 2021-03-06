package Client.view.tableViewData;

import Client.view.ClientHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Account {
    private String accountType;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private long wallet;
    private String companyName;
    private String onlineStatus;

    public Account(String accountType, String firstName, String lastName, String email, String phoneNumber,
                   String username, String password, long wallet, String companyName, String onlineStatus) {
        this.accountType = accountType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.companyName = companyName;
        this.wallet = wallet;
        this.onlineStatus = onlineStatus;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getWallet() {
        return wallet;
    }

    public void setWallet(long wallet) {
        this.wallet = wallet;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public static ObservableList<Account> getAccounts() {
        ObservableList<Account> list = FXCollections.observableArrayList();
        try {
            ClientHandler.getDataOutputStream().writeUTF("get users");
            ClientHandler.getDataOutputStream().flush();
            String data = ClientHandler.getDataInputStream().readUTF();
            Type foundListType = new TypeToken<ArrayList<Account>>() {}.getType();
            Gson gson = new Gson();
            list.addAll(new ArrayList<>(gson.fromJson(data, foundListType)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
