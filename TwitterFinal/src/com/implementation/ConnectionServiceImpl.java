package com.implementation;


import com.interfaces.ConnectionService;
import com.interfaces.ConsoleViewService;
import com.twitter.client.Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * connection service is connected to session , also session is communicating with server and server is handling multithreading
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public class ConnectionServiceImpl implements ConnectionService {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    /**
     * description of first operation (sending request)
     * 1- Client class gives a json file as a request to ConnectionService class
     * 2- ConnectionService reads json file and sends strings of json request file over dataOutputStream
     * 3- flush dataOutputStream for making sure of everything is written on stream
     * after server does some operation on request : (receiving response)
     * 4- ConnectionService receives some data over dataInputStream
     * 5- ConnectionService writes received data on a json file as response
     * 6- print a "done" string to make sure none of streams are stuck or whatever that stops the process
     *
     * @param address address of socket
     * @param port    port of socket
     */
    public ConnectionServiceImpl(String address, int port, File file) {
        try (Socket socket = new Socket(address, port)) {
            new Client();
            socket.setReuseAddress(true);
            File requestFile = new File(String.valueOf(Client.getPath()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./files/Response/ResponseServerCopy.json"));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(requestFile));
            //---------------------------------
            //first operation
            String str = bufferedReader.readLine();
            dataOutputStream.writeUTF(str);
            //---------------------------------
            dataOutputStream.flush();
            bufferedReader.close();
            //---------------------------------
            // write code
            //---------------------------------
            //second operation
            String str1 = dataInputStream.readUTF();
            bufferedWriter.write(str1);
            //---------------------------------
            bufferedWriter.close();

            dataOutputStream.close();
            dataInputStream.close();
            //---------------------------------
            ConsoleViewServiceImpl consoleViewService = new ConsoleViewServiceImpl();
            consoleViewService.terminalStart(new File("./files/Response/ResponseServerCopy.json"));
            System.out.println("connection service process is done");
            System.out.println(ANSI_YELLOW +
                    "###########################################\n" +
                    "              END OF THIS PROCESS          \n" +
                    "###########################################\n" +
                    ANSI_RESET);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}