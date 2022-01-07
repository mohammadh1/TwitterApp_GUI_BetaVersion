package com.implementation;

import com.twitter.server.Account;

import java.io.*;
import java.net.Socket;

import static com.twitter.server.LoadingFiles.currentAccount;

/**
 * session is communicating with server and server is handling multithreading
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public class SessionServiceImpl implements Runnable{
    private final Socket socket;
    private Account currentAcc;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    /**
     * constructor of Session class
     * @param socket socket for every client
     */
    public SessionServiceImpl(Socket socket)
    {
        this.socket = socket;
        currentAcc = currentAccount;
    }

    /**
     * description of first operation (receiving request)
     * 1- ConnectionService class sends strings of json file as a request to Session class
     * 2- Session reads strings and writes them on a file as request of client
     * 3- and point it for others classes
     * after server does some operation on request : (sending response)
     * 4- Session reads strings of json response file
     * 5- Session writes processed data over dataOutputStream so ConnectionService can get data
     * 6- print a "done" string to make sure none of streams are stuck or whatever that stops the process
     *
     */
    public void run()
    {
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;
        try {
                bufferedWriter = new BufferedWriter(new FileWriter("./files/Request/RequestClientCopy.json"));
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                //---------------------------------
                //first operation
                String str = dataInputStream.readUTF();
                bufferedWriter.write(str);
                System.out.println(str);
                bufferedWriter.close();
                //---------------------------------
                RequestParserServiceImpl requestParserService = new RequestParserServiceImpl();
                File fileResponse = requestParserService.requestParse(new File("./files/Request/RequestClientCopy.json"));
                bufferedReader = new BufferedReader(new FileReader(fileResponse));
                //---------------------------------
                //second operation
                String str1 = bufferedReader.readLine();
                dataOutputStream.writeUTF(str1);
                //---------------------------------
                dataOutputStream.flush();
                System.out.println("session process is done");

                bufferedReader.close();
            dataOutputStream.close();
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}