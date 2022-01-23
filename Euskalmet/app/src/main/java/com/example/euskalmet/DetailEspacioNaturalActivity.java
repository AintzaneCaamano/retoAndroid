package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class DetailEspacioNaturalActivity extends AppCompatActivity {

    TextView textViewNombreEspacioNatural;
    Button btnVolverDetailEspacioNatural;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_espacio_natural);

        textViewNombreEspacioNatural = findViewById(R.id.textView_NombreEspacioNatural);
        btnVolverDetailEspacioNatural = findViewById(R.id.btn_volverDetailEspacioNatural);
        Button btnSacar = findViewById(R.id.btn_hacerFoto);

    //getImage();
        btnSacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.playas);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


                // get the text message on the user and password
                String messageSent = "9-" + encodedImage;
                Envio messageResponse;
                ClientThread clientThread = new ClientThread();
                clientThread.setMessageSent(messageSent);

                Thread thread = new Thread(clientThread);
                try {
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {

                }

                // The Answer
                messageResponse = clientThread.getMessageResponse();

            }
        });

        btnVolverDetailEspacioNatural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToInfo = new Intent(DetailEspacioNaturalActivity.this, InfoActivity.class);
                startActivity(goToInfo);
            }
        });
    }

    private void getImage(){

        String messageSent = "10-" + "1";
        Envio messageResponse;
        ClientThread clientThread = new ClientThread();
        clientThread.setMessageSent(messageSent);
        clientThread.setOpcion(10);
        Thread thread = new Thread(clientThread);

        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
e.printStackTrace();
        }

        // The Answer
        String imgs  = clientThread.getStringResponse();

        byte[] img = imgs.getBytes(StandardCharsets.UTF_8);
        Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
        ImageView image = findViewById(R.id.imgview_fotoDetalle);
        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));
    }
}