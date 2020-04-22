package model;

public abstract class Account {
    protected String firstName;
    protected String lastName;
    protected String emailAddress;
    protected int phoneNumber;
    protected String username;
    protected String password;

    public Account(String firstName, String lastName, String emailAddress, int phoneNumber, String username,
                   String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        DateBase.getDateBase().setAllAccounts(this);
    }
}
