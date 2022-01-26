package com.twitter.client;


import com.implementation.CommandParserServiceImpl;
import com.implementation.ConnectionServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

/**
 * client class handle client thread and communicate with connection service
 * connection service is connected to session , also session is communicating with server and server is handling multithreading
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public class Client {
    public static String username;
    public static int port;
    public static String ip;

    // path of file if user give that :
    //private static Path path;


    /**
     * instantiate a new client
     * user can decide to write a json on their own or give a location of json file
     */
    public Client() {
        // first implementation for terminal
        /*boolean flag = true;
        while (flag) {
            System.out.println("welcome to twitter, you are able to give a file as a request or create a file for request");
            System.out.println("1-requestFile location \n2-create requestFile \nexit");
            String userInput = scanner.nextLine();
            // a switch case to print the first lines of program
            switch (userInput) {
                case "1":
                    System.out.println("name :");
                    String location = scanner.nextLine().trim();
                    path = Paths.get(location).toAbsolutePath();
                    flag = false;
                    break;
                case "2":
                    CommandParserServiceImpl commandParserService = new CommandParserServiceImpl();
                    path = Paths.get(commandParserService.commandParser().trim()).toAbsolutePath();
                    flag = false;
                    break;
                case "3":
                    flag = false;
                    break;
            }
        }*/
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
    }
    /*/**
     * when we want the path of file that user enter for json request
     *
     * @return path of json file
     */
    /*public static Path getPath() {
        return path;
    }*/


    public static void setUsername(String username) {
        Client.username = username;
    }

    /*public static void main(String args[])
    {

        // start connection
        //ConnectionServiceImpl connectionService = new ConnectionServiceImpl(ip, port, new File(String.valueOf(path)));
    }*/
}

