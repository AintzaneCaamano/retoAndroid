package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.euskalmet.cliente.ClientThread;

import java.nio.charset.StandardCharsets;
//import java.util.Base64;
import java.util.ArrayList;
import java.util.Base64.Decoder;

import android.util.Base64;


public class GaleriaActivity extends AppCompatActivity {
    private ImageView foto1;
    private static final String SEPARADOR = "/////";
    private  ArrayList<String> imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        foto1 = findViewById(R.id.imgV_Galeria1);

        Bundle extras = getIntent().getExtras();
        String place = extras.getString("place");

        getImagefromServer(place);

        for (int i = 0 ; i<imgs.size();i++){
            convertirImg(foto1,imgs.get(i).toString());
        }
    }

    private void getImagefromServer(String place){

        String messageSent = "10"+ SEPARADOR + place;
        Envio messageResponse;
        ClientThread clientThread = new ClientThread();
        clientThread.setMessageSent(messageSent);
        clientThread.setOption(5);
        Thread thread = new Thread(clientThread);

        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // The Answer

        imgs  = clientThread.getArrayStringResponse();
        // String imgs  = clientThread.getStringResponse();

       /* byte[] img = imgs.getBytes(StandardCharsets.UTF_8);
        byte[] decodedString = Base64.decode(img, Base64.NO_WRAP);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
*/

        //Log.i("***********byte length :", String.valueOf(img.length));
        //Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);


        //Base64.decodeBase64(bytesEncoded);
        //byte[] img = imgs.getBytes(StandardCharsets.UTF_8);
        //byte [] img = new byte[1];
       /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            img = Base64.getDecoder().decode(imgs);

        }*/

        //Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
        //ImageView image = findViewById(R.id.imgview_fotoDetalle);
        //foto1.setImageBitmap(Bitmap.createScaledBitmap(bmp, foto1.getWidth(), foto1.getHeight(),  false));
        /*foto1.setImageBitmap(bmp);*/
    }

    private void convertirImg(ImageView foto1, String imgs){
        byte[] img = imgs.getBytes(StandardCharsets.UTF_8);
        byte[] decodedString = Base64.decode(img, Base64.NO_WRAP);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        foto1.setImageBitmap(bmp);
    }
}