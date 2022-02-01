package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class DatosMeteorologicosActivity extends AppCompatActivity {

    private Button btnVolverDatosMeteorologicos;
    private ListView listViewDatosMetereologicos;
    private String origen;
    private String areaName;
    private String user;
    private int pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_meteorologicos);

        Bundle extras = getIntent().getExtras();
        origen = extras.getString("origen");
        areaName = extras.getString("place");
        user = extras.getString("user");
        pass = Integer.parseInt(extras.getString("pass"));

        btnVolverDatosMeteorologicos = findViewById(R.id.btn_volverDatosMetereologicos);
        listViewDatosMetereologicos = findViewById(R.id.listView_datosMetereologicos);

        btnVolverDatosMeteorologicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(DatosMeteorologicosActivity.this, DetailEspacioNaturalActivity.class);
                intento.putExtra("place", areaName );
                intento.putExtra("origen", origen);
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);
            }
        });

    }
}