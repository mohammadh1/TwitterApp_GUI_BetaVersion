package com.implementation;

import com.interfaces.AuthenticationService;
import com.twitter.server.Account;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Iterator;

import static com.interfaces.EncryptAlgorithmService.getSHA;
import static com.interfaces.EncryptAlgorithmService.toHexString;
import static com.twitter.server.LoadingFiles.accounts;
import static com.twitter.server.LoadingFiles.usernameList;

/**
 * authentication class contains method for login and signup
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    /**
     * login method that must be executed early moments of program
     *
     * @param username username of client
     * @param password password of client
     * @return a boolean to tell us login is successful or not
     */
    public Boolean login(String username, String password) {
        boolean flag = false;
        Iterator<Account> it = accounts.iterator();
        while (it.hasNext()) {
            Account account = it.next();
            if (account.getUsername().equals(username)) {
                try {
                    if (account.getPassword().equals(toHexString(getSHA(password)))) {
                        flag = true;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }
        if (flag) {
            System.out.println("Login Successful");
            return true;
        }
        else {
            System.out.println("Login Failed\n if you don't have an account yet , please sign up first");
            return false;
        }
    }

    /**
     * login method that must be executed early moments of program if client does not have an account
     *
     * @param username username of client
     * @param password password of client
     * @param bio bio of client
     * @param firstName firstname of client
     * @param lastName lastname of client
     * @param birthDate birthdate of client
     * @return a boolean to tell us signup is successful or not
     */
    public Boolean signup(String username, String password, String bio, String firstName, String lastName, LocalDate birthDate, LocalDate registrationDate) {
        boolean flag = true;
        for (String str : usernameList) {
            if (str.equals(username)) {
                System.err.println("The username already exists, try again");
                flag = false;
                break;
            }
        }
        if (flag) {
            Account newAccount = new Account(firstName, lastName, bio, birthDate, registrationDate, username, password);
            accounts.add(newAccount);
            return true;
        }
        else
            return false;
    }

    /**
     * login method that must be executed early moments of program if client does not have an account
     * overload of submit method without bio argument
     *
     * @param username username of client
     * @param password password of client
     * @param firstName firstname of client
     * @param lastName lastname of client
     * @param birthDate birthdate of client
     * @return a boolean to tell us signup is successful or not
     */
    public Boolean signup(String username, String password, String firstName, String lastName, LocalDate birthDate) {
        boolean flag = false;
        for (String str : usernameList) {
            if (str.equals(username)) {
                System.err.println("The username already exists, try again");
            }
            else {
                LocalDate registrationDate = LocalDate.now();
                Account newAccount = new Account(firstName, lastName, birthDate, registrationDate, username, password);
                accounts.add(newAccount);
                flag = true;
            }
        }
        if (flag)
            return true;
        else
            return false;
    }

}
