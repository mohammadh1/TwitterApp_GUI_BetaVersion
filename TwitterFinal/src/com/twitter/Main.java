package com.twitter;

import com.twitter.server.Account;

import java.io.*;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
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

        /*int port = 0;
        String ip = "";
        try (FileReader fileReader = new FileReader("./src/com/resources/client-application.properties")) {
            Properties properties = new Properties();
            properties.load(fileReader);
            port = Integer.parseInt(properties.getProperty("server.port"));
            ip = properties.getProperty("server.ip");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger logger = Logger.getLogger("log");*/

    }
}
