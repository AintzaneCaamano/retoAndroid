package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailMunicipioActivity extends AppCompatActivity {

    TextView textViewNombreMunicipio;
    Button btnVolverDetailMunicipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_municipio);

        textViewNombreMunicipio = findViewById(R.id.textView_NombreMunicipio);
        btnVolverDetailMunicipio = findViewById(R.id.btn_volverDetailMunicipio);

        btnVolverDetailMunicipio .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToInfo = new Intent(DetailMunicipioActivity.this, InfoActivity.class);
                startActivity(goToInfo);
            }
        });


    }
}