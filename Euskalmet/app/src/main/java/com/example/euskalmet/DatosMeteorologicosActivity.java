package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class DatosMeteorologicosActivity extends AppCompatActivity {

    private Button btnVolverDatosMetereologicos;
    private ListView listViewDatosMetereologicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_meteorologicos);

        btnVolverDatosMetereologicos = findViewById(R.id.btn_volverDatosMetereologicos);
        listViewDatosMetereologicos = findViewById(R.id.listView_datosMetereologicos);

        btnVolverDatosMetereologicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDetailEspacios = new Intent(DatosMeteorologicosActivity.this, DetailEspacioNaturalActivity.class);
                startActivity(goToDetailEspacios);
            }
        });

    }
}