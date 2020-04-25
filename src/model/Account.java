package model;

public abstract class Account {
    protected String firstName;
    protected String lastName;
    protected String emailAddress;
    protected long phoneNumber;
    protected String username;
    protected String password;

    public Account( String username,String firstName, String lastName, String emailAddress, int phoneNumber,
                   String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        DataBase.getDataBase().setAllAccounts(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
