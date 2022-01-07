package com.twitter.server;

import java.io.*;
import java.util.*;

/**
 * load from files and fill catch of program
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class LoadingFiles {
    // current account that logged in
    public static Account currentAccount;
    // all usernames store here
    public static LinkedHashSet<String> usernameList = new LinkedHashSet<>();
    // all accounts store here
    public static LinkedHashSet<Account> accounts = new LinkedHashSet<>();
    // all individualTweets store here
    public static LinkedHashMap<String, ArrayList<Tweet>> individualTweets = new LinkedHashMap<>();
    // all following relations store here
    public static LinkedHashMap<String, ArrayList<String>> followingList = new LinkedHashMap<>();
    private static String tweetsFilePath;
    private static String accountsFilePath;
    private static String followingFilePath;
    public LoadingFiles() {
        usernameList = new LinkedHashSet<>();
        accounts = new LinkedHashSet<>();
        individualTweets = new LinkedHashMap<>();
        followingList = new LinkedHashMap<>();
        try (FileReader fileReader = new FileReader("./src/com/resources/server-application.properties")) {
            Properties properties = new Properties();
            properties.load(fileReader);
            tweetsFilePath = properties.getProperty("server.tweets.file");
            accountsFilePath = properties.getProperty("server.users.file");
            followingFilePath = properties.getProperty("server.follow.file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * getter of current account
     * @return current account
     */
    public static Account getCurrentAccount() {
        return currentAccount;
    }

    /**
     * accounts information
     * load username and password of each account from hard drive and store it in an arraylist to use
     */
    public static void loadingAccounts() {
        if (new File(accountsFilePath).length() != 0) {
            //Path path = Paths.get("accounts.ap");
            File file = new File(accountsFilePath);
            if (file.exists()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    accounts = (LinkedHashSet<Account>) objectInputStream.readObject();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("File is not existed");
            }
            for (Account account : accounts) {
                usernameList.add(account.getUsername());
            }
        }
    }
    /**
     * loading tweets.ap from hard drive and store them in a linkedHashmap that the keys are usernames and the values
     * are tweets.ap that every individual texted on platform
     */
    public static void loadingTweets() {
        if (new File(tweetsFilePath).length() != 0) {
            //Path path = Paths.get("tweets.ap");
            File file = new File(tweetsFilePath);
            if (file.exists()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    individualTweets = (LinkedHashMap<String, ArrayList<Tweet>>) objectInputStream.readObject();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("File is not existed");
            }
        }
    }
    /**
     * load following list of each account and put it into a hashmap that the keys are account owner and the values are
     * the accounts that owner is following them
     */
    public static void loadingFollowingList() {
        if (new File(followingFilePath).length() != 0) {
            //Path path = Paths.get("followingList.ap");
            File file = new File(followingFilePath);
            if (file.exists()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    followingList = (LinkedHashMap<String, ArrayList<String>>) objectInputStream.readObject();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("File is not existed");
            }
        }
    }

    /*public static void updateFollowingList(Account doerAccount) {
        for (String username : followingList.get(doerAccount.getUsername())) {
            // write new following list of doer account on file ( note that file name is doer's username )
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(doerAccount.getUsername()))) {
                bufferedWriter.write(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/ // this method is not necessary yet (to update all lists of following of every account)


    /**
     * storingFollowingList : storing followingList.ap on file
     */
    public static void storingFollowingList() {

        try (FileOutputStream fileOutputStream = new FileOutputStream(followingFilePath)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(followingList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * storingTweets : storing individualTweets on file
     */
    public static void storingTweets() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(tweetsFilePath)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(individualTweets);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * storingAccounts : storing Accounts on file
     */
    public static void storingAccounts() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(accountsFilePath)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(accounts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * find account using username
     *
     * @param username username of account
     * @return found account
     */
    public static Account findAccount(String username) {
        Iterator<Account> iterator = accounts.iterator();
        Account account = null;
        while (iterator.hasNext()) {
            account = iterator.next();
            if (account.getUsername().equals(username)) {
                break;
            }
        }
        return account;
    }

    /**
     * find tweet using username and text
     *
     * @param username username of Account
     * @param text text of Tweet
     * @return found Tweet
     */
    public static Tweet findTweet(String username, String text) {
        Tweet tweet = null;
        text = text.trim();
        Iterator<Tweet> iterator = individualTweets.get(username).iterator();
        while (iterator.hasNext()) {
            tweet = iterator.next();
            if (tweet.getText().equals(text)) {
                break;
            }
        }
        return tweet;
    }
}

