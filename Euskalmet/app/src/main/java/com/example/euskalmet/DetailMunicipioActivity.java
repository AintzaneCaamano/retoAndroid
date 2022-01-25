package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.euskalmet.cliente.ClientThread;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailMunicipioActivity extends AppCompatActivity {
    private ArrayList<Double>  choords;
    private  TextView textViewNombreMunicipio;
    private  TextView textViewDesc;
    private Button btnVolverDetailMunicipio;
    private Button btnMapa;
    private Button btnEspacio;
    private String info;
    private static final String SEPARADOR = "/////";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_municipio);

        textViewNombreMunicipio = findViewById(R.id.textView_NombreMunicipio);
        btnVolverDetailMunicipio = findViewById(R.id.btn_volverDetailMunicipio);
        btnMapa = findViewById(R.id.btn_mapaDetailMunicipio2);
        btnEspacio = findViewById(R.id.btn_espaciosDetailMunicipio);
        textViewDesc= findViewById(R.id.txtVw_DetailMunicipio);
        Bundle extras = getIntent().getExtras();
        textViewNombreMunicipio.setText(extras.getString("place"));
        loadChoordInfoFromServer(extras.getString("place"));
        loadInfoFromServer(extras.getString("place"));



        btnVolverDetailMunicipio .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(DetailMunicipioActivity.this, InfoActivity.class);
                startActivity(intento);
            }
        });

        btnEspacio .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(DetailMunicipioActivity.this, DetailEspacioNaturalActivity.class);
                intento.putExtra("place", extras.getString("place") );
                startActivity(intento);
            }
        });
        btnMapa .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(DetailMunicipioActivity.this, MapsActivity.class);
                intento.putExtra("lat", choords.get(0) );
                intento.putExtra("long", choords.get(1) );
                startActivity(intento);
            }
        });

        textViewDesc.setText(info);
    }
    private void loadChoordInfoFromServer(String territory){
        // get the text message on the user and password
        String messageSent = "20"+SEPARADOR + territory ;

        ClientThread clientThread = new ClientThread();
        clientThread.setMessageSent(messageSent);
        clientThread.setOption(4);
        Thread thread = new Thread(clientThread);
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {

        }

        // The Answer
       choords = clientThread.getArrayDouble();

    }
    private void loadInfoFromServer(String territory){
        // get the text message on the user and password
        String messageSent = "21"+ SEPARADOR + territory ;

        ClientThread clientThread = new ClientThread();
        clientThread.setMessageSent(messageSent);
        clientThread.setOption(3);
        Thread thread = new Thread(clientThread);
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {

        }

        // The Answer
        info = clientThread.getStringResponse();

    }
}