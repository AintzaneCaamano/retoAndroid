package com.example.euskalmet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread implements Runnable{

    private static String HOST = "10.5.7.33";
    private static int PORT = 5000;
    private String messageSent;
    private Envio messageResponse;

    @Override
    public void run() {
        Socket socket = null;
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            InetAddress serverAddr = InetAddress.getByName(HOST);
            socket = new Socket(serverAddr, PORT);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeObject(messageSent);
            objectOutputStream.reset();
            objectOutputStream.flush();

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            messageResponse = (Envio) objectInputStream.readObject();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (null != objectInputStream)
                    objectInputStream.close();
                if (null != objectOutputStream)
                    objectOutputStream.close();
                if (null != socket)
                    socket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setMessageSent(String messageSent) {
        this.messageSent = messageSent;
    }

    public Envio getMessageResponse() {
        return messageResponse;
    }
}

