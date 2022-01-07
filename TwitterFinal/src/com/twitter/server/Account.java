package com.twitter.server;


import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import static com.interfaces.EncryptAlgorithmService.getSHA;
import static com.interfaces.EncryptAlgorithmService.toHexString;
import static com.twitter.server.LoadingFiles.usernameList;

/**
 * Account class defines every account that going to be created in app
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public class Account implements Serializable {

    /**
     * fields that must get values in constructor
     */
    private String firstName;
    private String lastName;
    private String bio;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    private String username;
    private String password;
    private int following;

    /**
     * constructor of Account class
     * instantiate an object of Account class
     * create a new Account
     *  @param firstName firstname of user
     * @param lastName lastname of user
     * @param bio bio of user
     * @param birthDate birthdate of user
     * @param registrationDate registration date of user
     * @param username username of user
     * @param password password of user
     */
    public Account(String firstName, String lastName, String bio, LocalDate birthDate, LocalDate registrationDate, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.username = username;
        usernameList.add(username);
        this.password = password;
        encryptingPassword();
        this.following = 0;
    }
    public Account(String firstName, String lastName, LocalDate birthDate, LocalDate registrationDate, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = null;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.username = username;
        usernameList.add(username);
        this.password = password;
        encryptingPassword();
        this.following = 0;
    }

    /**
     * getter methods fot all fields
     */
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getBio() {
        return bio;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getFollowing() {
        return following;
    }

    /**
     * setter methods fot all fields except username , registration date ( once these fields are assigned , no one will be able to change them )
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFollowing(int following) {
        this.following = following;
    }

    //check methods start from here : ->

    /**
     * check username that new client want to submit for availability
     *
     * @param username username of new client
     * @return a boolean to tell us username is available or not
     */
    public boolean checkUsername(String username) {
        for (String strUser : usernameList) {
            try {
                if (strUser.equals(username)) {
                    return false;
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * check bio that new client want to submit for being less that 256
     *
     * @return a boolean to tell us bio's length is acceptable or not
     */
    public boolean checkBiography() {
        if (bio.length() > 256) {
            return false;
        }
        return true;
    }

    /**
     * encrypting password to hash so if password files would be stolen no one can figure out correct password
     *
     */
    public void encryptingPassword() {
        try
        {
            this.password = toHexString(getSHA(this.password));
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
    }

}

