package com.example.euskalmet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable{

    private final String message;
    private String envio;
    private Socket client;
    private PrintWriter printwriter;
    private ObjectInputStream objectInputStream;


    ClientThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {

        try {
            // the IP and port should be correct to have a connection established
            // Creates a stream socket and connects it to the specified port number on the named host.
            client = new Socket("10.5.7.33", 4444);  // connect to server
            printwriter = new PrintWriter(client.getOutputStream(),true);
            objectInputStream = new ObjectInputStream(client.getInputStream());
            printwriter.write(message);  // write the message to output stream


            printwriter.flush();
            envio = objectInputStream.readObject();
            printwriter.close();
            objectInputStream.close();
            // closing the connection
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    }

