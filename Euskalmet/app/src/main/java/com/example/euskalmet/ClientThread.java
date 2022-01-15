package com.example.euskalmet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable{

    private final String message;
    private Socket client;
    private PrintWriter printwriter;

    ClientThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {

        try {
            // the IP and port should be correct to have a connection established
            // Creates a stream socket and connects it to the specified port number on the named host.
            client = new Socket("192.168.43.114", 4444);  // connect to server
            printwriter = new PrintWriter(client.getOutputStream(),true);
            printwriter.write(message);  // write the message to output stream

            printwriter.flush();
            printwriter.close();

            // closing the connection
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textField.setText("");
           }
        });*/
    }
    }

