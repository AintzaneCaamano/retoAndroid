package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView textView_InfoTitle;
    TextView textView_buscar;
    EditText editTextBusqueda;
    ListView listViewInfo;
    Button btnVolverInfo;
    Button btnProvisionalDetailMunicipios;
    Button btnProvisionalDetailEspacioNatural;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        textView_InfoTitle = findViewById(R.id.textView_InfoTitle);
        textView_buscar = findViewById(R.id.textView_buscar);
        editTextBusqueda = findViewById(R.id.editText_busqueda);
        listViewInfo = findViewById(R.id.listView_Info);
        btnVolverInfo = findViewById(R.id.btn_volverInfo);
        btnProvisionalDetailMunicipios = findViewById(R.id.btn_provisionalDetailMunicipios);
        btnProvisionalDetailEspacioNatural = findViewById(R.id.btn_provisionalDetailEspacioNatural);

        btnVolverInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToOperaciones = new Intent(InfoActivity.this, ActivityOperaciones.class);
                startActivity(goToOperaciones);
            }
        });

        btnProvisionalDetailMunicipios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailMunicipio = new Intent(InfoActivity.this, DetailMunicipioActivity.class);
                startActivity(goToDetailMunicipio);
            }
        });

        btnProvisionalDetailEspacioNatural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailEspacioNatural = new Intent(InfoActivity.this, DetailEspacioNaturalActivity.class);
                startActivity(goToDetailEspacioNatural);
            }
        });


    }
}