package com.twitter;

import com.twitter.server.Account;
import com.twitter.server.LoadingFiles;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static com.twitter.server.LoadingFiles.individualTweets;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // print accounts
        try (FileInputStream fileInputStream = new FileInputStream("./files/data/accounts.ap")) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            LinkedHashSet<Account> accounts = (LinkedHashSet<Account>) objectInputStream.readObject();
            for (Account account : accounts) {
                System.out.println(account.getUsername() + "   " + account.getPassword());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*new LoadingFiles();
        LoadingFiles.loadingTweets();
        for (String name: individualTweets.keySet()) {
            String key = name;
            String value = individualTweets.get(name).toString();
            System.out.println(key + " " + value);
        }*/
        /*try (FileInputStream fileInputStream = new FileInputStream("./files/data/followingList.ap")) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            LinkedHashMap<String, ArrayList<String>> list = (LinkedHashMap<String, ArrayList<String>>) objectInputStream.readObject();
            for (String str : list.keySet()) {
                System.out.println("user : " + str);
                for (String str1 : list.get(str)) {
                    System.out.println(str1);
                }
            }
            //list.get("mohammadh1").remove(0);
            try (FileOutputStream fileOutputStream = new FileOutputStream("./files/data/followingList.ap")){
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(list);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/


    }
}
