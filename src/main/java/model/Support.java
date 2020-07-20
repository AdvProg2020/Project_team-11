package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Support extends Account {
    private HashMap<String, ArrayList<HashMap<String, String>>> messages;

    public Support(String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password) {
        super(firstName, lastName, emailAddress, phoneNumber, username, password);
        this.messages = new HashMap<>();
    }

    public HashMap<String, ArrayList<HashMap<String, String>>> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, ArrayList<HashMap<String, String>>> messages) {
        this.messages = messages;
    }
}
