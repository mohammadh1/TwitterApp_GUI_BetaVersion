package com.twitter.server;

import com.implementation.SessionServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.twitter.server.LoadingFiles.usernameList;

/**
 * server is handling multithreading
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public class Server {
    public static boolean endSession=false;
    public static HashMap<String, Boolean> loginState;
    private static ArrayList<SessionServiceImpl> clients = new ArrayList<>();
    public static ExecutorService pool = Executors.newFixedThreadPool(10);
    public Server(int port) {
        loginState = new HashMap<>();
        for (String str : usernameList) {
            loginState.put(str, false);
        }
        try (ServerSocket server = new ServerSocket(port)){
            while (true) {
                Socket socket = server.accept();
                System.out.println("New client connected : " + socket.getInetAddress().getHostAddress());
                SessionServiceImpl clientHandler = new SessionServiceImpl(socket);    // client handler
                clients.add(clientHandler);
                //pool.execute(clientHandler);
                new Thread(clientHandler).start();              // start a thread for every client
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }


    public static void main(String args[]) {
      int port = 0;
      try (FileReader fileReader = new FileReader("./src/com/resources/server-application.properties")) {
          Properties properties = new Properties();
          properties.load(fileReader);
          port = Integer.parseInt(properties.getProperty("server.port"));
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      Server server = new Server(port); // start server
    }
}
