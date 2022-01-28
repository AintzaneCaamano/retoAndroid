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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private CheckBox fav;
    private Envio messageResponse;
    private ImageView imageViewFoto;
    private Bitmap photo;
    // private Uri uri;
    private static final int CAMERA_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final String SEPARADOR = "/////";
    private String areaName;
    private String areaDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_espacio_natural);

        textViewNombreEspacioNatural = findViewById(R.id.textView_NombreEspacioNatural);
        textVDescription = findViewById(R.id.txtV_EspacioDetail_Desc);
        btnVolverDetailEspacioNatural = findViewById(R.id.btn_volverDetailEspacioNatural);
        fav = findViewById(R.id.check_Detail_SaveFav);
        btnHacerFoto = findViewById(R.id.btn_hacerFoto);
        imageViewFoto = findViewById(R.id.imageViewFoto);


        Bundle extras = getIntent().getExtras();
        if(extras.getString("origen").equals("Espacio")){
            loadInfoFromServer2(extras.getString("place"));
        }else{
            loadInfoFromServer(extras.getString("place"));}


        textViewNombreEspacioNatural.setText(areaName);
        textVDescription.setText(areaDesc);
        comprobarFavs();

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
                if (extras.getString("origen").equals("Espacio")){
                    Intent goToInfo = new Intent(DetailEspacioNaturalActivity.this, EspaciosNaturalesActivity.class);
                    startActivity(goToInfo);
                }else{
                    Intent goToInfo = new Intent(DetailEspacioNaturalActivity.this, InfoActivity.class);
                    startActivity(goToInfo);
                }
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fav.isChecked()){
                    guardarEnFavs();
                }else{
                    borrarDeFavs();
                }
            }
        });

        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(DetailEspacioNaturalActivity.this, GaleriaActivity.class);
                startActivity(intento);
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
            photo = (Bitmap) data.getExtras().get("data");
            imageViewFoto.setImageBitmap(photo);
            //para extraer la URI(ruta) de la foto
            // uri = (Uri) data.getData();
            SubirFotoAlServer();
        }
    }



    private void SubirFotoAlServer(){
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.playas);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), photo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 50, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


        // get the text message on the user and password
        //TODO
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
        messageResponse = clientThread.getMessageResponse();
        if(messageResponse.getLogin()){
            Toast toastOk = Toast.makeText(getApplicationContext(), "Fotografía subida correctamente", Toast.LENGTH_LONG);
            toastOk.show();
        }else{
            Toast toastOk = Toast.makeText(getApplicationContext(), "La fotografía no se ha subido", Toast.LENGTH_LONG);
            toastOk.show();
        }
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
        areaDesc = arr.get(0).toString();
        areaName = arr.get(1).toString();
    }
    private void loadInfoFromServer2(String territory){
        // get the text message on the user and password
        String messageSent = "26"+ SEPARADOR + territory ;

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
        areaDesc = arr.get(0).toString();
        areaName = territory;
    }

    private void comprobarFavs(){
        // get the text message on the user and password
        String messageSent = "25"+ SEPARADOR + LoginActivity.nombre+ SEPARADOR + LoginActivity.contrasenia + SEPARADOR + areaName;

        ClientThread clientThread = new ClientThread();
        clientThread.setMessageSent(messageSent);
        clientThread.setOption(1);
        Thread thread = new Thread(clientThread);

        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {

        }

        // The Answer
        messageResponse = clientThread.getMessageResponse();
        if(messageResponse.getLogin()){
            fav.setChecked(true);
        }else {
            fav.setChecked(false);
        }

    }

    private void guardarEnFavs(){
        // get the text message on the user and password
        String messageSent = "23"+ SEPARADOR + LoginActivity.nombre+ SEPARADOR + LoginActivity.contrasenia + SEPARADOR + areaName;

        ClientThread clientThread = new ClientThread();
        clientThread.setMessageSent(messageSent);
        clientThread.setOption(1);
        Thread thread = new Thread(clientThread);

        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {

        }

        // The Answer
        messageResponse = clientThread.getMessageResponse();
        if(messageResponse.getLogin()){
            Toast toastOk = Toast.makeText(getApplicationContext(), "Favorito guardado correctamente", Toast.LENGTH_LONG);
            toastOk.show();
        }else{
            Toast toastOk = Toast.makeText(getApplicationContext(), "Favorito no se ha podido guardar", Toast.LENGTH_LONG);
            toastOk.show();
        }

    }

    private void borrarDeFavs(){
        // get the text message on the user and password
        String messageSent = "24"+ SEPARADOR + LoginActivity.nombre + SEPARADOR + LoginActivity.contrasenia + SEPARADOR + areaName;

        ClientThread clientThread = new ClientThread();
        clientThread.setMessageSent(messageSent);
        clientThread.setOption(1);
        Thread thread = new Thread(clientThread);

        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {

        }

        // The Answer
        messageResponse = clientThread.getMessageResponse();
        if(messageResponse.getLogin()){
            Toast toastOk = Toast.makeText(getApplicationContext(), "Favorito borrado correctamente", Toast.LENGTH_LONG);
            toastOk.show();
        }else{
            Toast toastOk = Toast.makeText(getApplicationContext(), "Favorito no se ha podido borrar", Toast.LENGTH_LONG);
            toastOk.show();
    }
}


}