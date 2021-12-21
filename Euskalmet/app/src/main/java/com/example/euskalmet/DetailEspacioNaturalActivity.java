package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailEspacioNaturalActivity extends AppCompatActivity {

    TextView textViewNombreEspacioNatural;
    Button btnVolverDetailEspacioNatural;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_espacio_natural);

        textViewNombreEspacioNatural = findViewById(R.id.textView_NombreEspacioNatural);
        btnVolverDetailEspacioNatural = findViewById(R.id.btn_volverDetailEspacioNatural);

        btnVolverDetailEspacioNatural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToInfo = new Intent(DetailEspacioNaturalActivity.this, InfoActivity.class);
                startActivity(goToInfo);
            }
        });
    }
}