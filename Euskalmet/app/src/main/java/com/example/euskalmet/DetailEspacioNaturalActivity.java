package com.example.euskalmet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class DetailEspacioNaturalActivity extends AppCompatActivity {

    TextView textViewNombreEspacioNatural;
    Button btnVolverDetailEspacioNatural;
    Button btnHacerFoto;
    ImageView imageViewFoto;
    private static final int CAMERA_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

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

        btnHacerFoto.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
            //Uri uri = (Uri) data.getData();
        }
    }
}