package com.example.euskalmet;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread implements Runnable{
    // IP para el emulador de android : 10.0.2.2
    //Ip para usar el movil como emulador : la del server
    private static String HOST = "10.0.2.2";
    private static int PORT = 5000;
    private String messageSent;
    private Envio messageResponse;
    private String messageRecieved;
    private int opcion = 0 ;

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
            //objectOutputStream.reset();
            objectOutputStream.flush();

            objectInputStream = new ObjectInputStream(socket.getInputStream());
// object = objectInputStream.readObject();
           switch (opcion) {
               case 1:
                   messageResponse = (Envio) objectInputStream.readObject();
                   break;
               case 10:
                   messageRecieved = (String) objectInputStream.readObject();
                   break;
           }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (null != objectInputStream)
                    objectInputStream.close();
                if (null != objectOutputStream)
                    objectOutputStream.close();
                if (null != socket)
                    socket.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void setMessageSent(String messageSent) {
        this.messageSent = messageSent;
    }

    public Envio getMessageResponse() {
        return messageResponse;
    }
    public String getStringResponse(){
        return messageRecieved;
    }
    public void setOpcion(int op){
      opcion=op;
    }
}

