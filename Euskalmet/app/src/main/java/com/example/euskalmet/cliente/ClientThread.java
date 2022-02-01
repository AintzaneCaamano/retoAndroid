package com.example.euskalmet.cliente;

import android.util.Log;

import com.example.euskalmet.Envio;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread implements Runnable{
    // IP para el emulador de android : 10.0.2.2
    //Ip para usar el movil como emulador : la del server
    private static String HOST = "10.0.2.2";
    private static int PORT = 5000;
    private String messageSent;
    private ArrayList<String> receivedArray = new ArrayList();
    private Envio receivedEnvio;
    private String receivedString;
    private int option = 0 ;
    private ArrayList<Double> receivedArrayDouble;

    @Override
    public void run() {
        Socket socket = null;
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            InetAddress serverAddr = InetAddress.getByName(HOST);
            socket = new Socket(serverAddr, PORT);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            objectOutputStream.writeObject(messageSent);
            objectOutputStream.reset();
            objectOutputStream.flush();


            // object = objectInputStream.readObject();
            switch (option) {
                case 1:
                    //recibir un envio
                    receivedEnvio = (Envio) objectInputStream.readObject();
                    break;
                case 2 :
                    //Recibir array de Strings
                    receivedArray = (ArrayList<String>) objectInputStream.readObject();
                    break;
                case 3:
                    //Recibir String
                    receivedString = (String) objectInputStream.readObject();
                    Log.i("***********RECIBIDO :",receivedString);
                    break;
                case 4:
                    //Recibir array de Double
                    receivedArrayDouble = (ArrayList<Double>) objectInputStream.readObject();
                    break;
                case 5:
                    receivedArray = (ArrayList<String>) objectInputStream.readObject();
                    // receivedString = (String) objectInputStream.readObject();
                    // receivedString = receivedString.replaceAll("\n","");
                    // Log.i("***********RECIBIDO :",receivedString);
                    break;

            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            option = 0 ;
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
        return receivedEnvio;
    }

    public String getStringResponse(){
        return receivedString;
    }

    public ArrayList<String> getArrayStringResponse(){
        return receivedArray;
    }

    public ArrayList<Double> getArrayDouble(){
        return receivedArrayDouble;
    }
    public void setOption(int op){
        option =op;
    }
}

