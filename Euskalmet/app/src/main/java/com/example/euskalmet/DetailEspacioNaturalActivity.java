package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.euskalmet.cliente.ClientThread;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class DetailEspacioNaturalActivity extends AppCompatActivity {

    private TextView textViewNombreEspacioNatural;
    private TextView textVDescription;
    private Button btnVolverDetailEspacioNatural;
    private Button btnHacerFoto;
    private String messageResponse;
    private ImageView imageViewFoto;
    private Uri uri;
    private static final int CAMERA_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final String SEPARADOR = "/////";
    private String areaName;
    private String areaDesc;
    /*
      @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_espacio_natural);

            textViewNombreEspacioNatural = findViewById(R.id.textView_NombreEspacioNatural);
            btnVolverDetailEspacioNatural = findViewById(R.id.btn_volverDetailEspacioNatural);
            btnHacerFoto = findViewById(R.id.btn_hacerFoto);
            imageViewFoto = findViewById(R.id.imageViewFoto);

            btnVolverDetailEspacioNatural.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToInfo = new Intent(DetailEspacioNaturalActivity.this, InfoActivity.class);
                    startActivity(goToInfo);
                }
            });


            */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_espacio_natural);

        textViewNombreEspacioNatural = findViewById(R.id.textView_NombreEspacioNatural);
        textVDescription = findViewById(R.id.txtV_EspacioDetail_Desc);
        btnVolverDetailEspacioNatural = findViewById(R.id.btn_volverDetailEspacioNatural);
        Button btnSacar = findViewById(R.id.btn_hacerFoto);
        btnHacerFoto = findViewById(R.id.btn_hacerFoto);
        imageViewFoto = findViewById(R.id.imageViewFoto);
        Bundle extras = getIntent().getExtras();
        loadInfoFromServer(extras.getString("place"));
        textViewNombreEspacioNatural.setText(areaName);
        textVDescription.setText(areaDesc);


        btnHacerFoto.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
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



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageViewFoto.setImageBitmap(photo);
            //para extraer la URI(ruta) de la foto
             uri = (Uri) data.getData();
        }
    }

    private void getImagefromServer(){

        String messageSent = "10"+ SEPARADOR + "1";
        Envio messageResponse;
        ClientThread clientThread = new ClientThread();
        clientThread.setMessageSent(messageSent);
        clientThread.setOption(3);
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
        //ImageView image = findViewById(R.id.imgview_fotoDetalle);
        //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));
    }

    private void SubirFotoAlServer(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.playas);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


        // get the text message on the user and password
        String messageSent = "9"+SEPARADOR + encodedImage;

        ClientThread clientThread = new ClientThread();
        clientThread.setOption(1);
        clientThread.setMessageSent(messageSent);

        Thread thread = new Thread(clientThread);
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {

        }

        // The Answer
        messageResponse = clientThread.getStringResponse();
    }
    private void loadInfoFromServer(String territory){
        // get the text message on the user and password
        String messageSent = "22"+ SEPARADOR + territory ;

        ClientThread clientThread = new ClientThread();
        clientThread.setMessageSent(messageSent);
        clientThread.setOption(2);
        Thread thread = new Thread(clientThread);
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {

        }

        // The Answer
        ArrayList<String> arr = clientThread.getArrayStringResponse();
        areaDesc = arr.get(1).toString();
        areaName = arr.get(0).toString();
    }
}