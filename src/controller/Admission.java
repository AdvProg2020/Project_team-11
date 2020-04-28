package controller;

import model.Account;
import model.DataBase;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Admission {
    public Boolean isThereAccountWhithUsername(String username){
        for (Account account: DataBase.getDataBase().getAllAccounts()) {
            if (account.getUsername() == username)
                return true;
        }
        return false;
    }
    public Boolean isPasswordValid(String password){
        String validPassword = "[A-Z].*";
        Pattern pattern = Pattern.compile(validPassword);
        Matcher matcher = pattern.matcher(password);
        if (matcher.matches())
            return true;
        else
            return false;
    }
    public Boolean isPasswordCorrect(String username, String password){
        for (Account account: DataBase.getDataBase().getAllAccounts()) {
            if (account.getUsername() == username) {
                if (account.getPassword() == password)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }
}
