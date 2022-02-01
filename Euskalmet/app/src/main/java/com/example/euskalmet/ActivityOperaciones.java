package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityOperaciones extends AppCompatActivity {

   private Button btnMunicipios;
    private Button btnEspaciosNaturales;
    private  Button btnFavoritos;
    private Button btnRanking;

    private String user;
    private int pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones);

        btnMunicipios = findViewById(R.id.Operac_btnMunicipios);
        btnEspaciosNaturales = findViewById(R.id.Operac_btnEspacios);
        btnFavoritos = findViewById(R.id.Operac_btnFavs);
        btnRanking = findViewById(R.id.Operac_btnTop);



        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        pass = Integer.parseInt(extras.getString("pass"));

        //falta la funcionalidad de cambiar de idioma

        //Cada intent va a mandar informacion diferente, solo que por ahora no tenemos esa info
        btnMunicipios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(ActivityOperaciones.this, InfoActivity.class);
                intento.putExtra("origen","municipio");
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);
            }
        });

        btnEspaciosNaturales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(ActivityOperaciones.this, EspaciosNaturalesActivity.class);
                intento.putExtra("origen","natural");
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(ActivityOperaciones.this, FavoritosActivity.class);
                intento.putExtra("origen","fav");
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(ActivityOperaciones.this, TopRankingActivity.class);
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);
            }
        });
    }
}