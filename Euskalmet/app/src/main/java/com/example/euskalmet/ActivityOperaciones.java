package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityOperaciones extends AppCompatActivity {

    Button btnMunicipios;
    Button btnEspaciosNaturales;
    Button btnFavoritos;
    Button btnRanking;
    Button btnCambiarIdioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones);

        btnMunicipios = findViewById(R.id.Operac_btnMunicipios);
        btnEspaciosNaturales = findViewById(R.id.Operac_btnEspacios);
        btnFavoritos = findViewById(R.id.Operac_btnFavs);
        btnRanking = findViewById(R.id.Operac_btnTop);
        btnCambiarIdioma = findViewById(R.id.Operac_changeLang);

        //falta la funcionalidad de cambiar de idioma

        //Cada intent va a mandar informacion diferente, solo que por ahora no tenemos esa info
        btnMunicipios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MunicipiosToInfoActivity = new Intent(ActivityOperaciones.this, InfoActivity.class);
                startActivity(MunicipiosToInfoActivity);
            }
        });

        btnEspaciosNaturales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EspaciosNatToInfoActivity = new Intent(ActivityOperaciones.this, InfoActivity.class);
                startActivity(EspaciosNatToInfoActivity);
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FavoritosToInfoActivity = new Intent(ActivityOperaciones.this, InfoActivity.class);
                startActivity(FavoritosToInfoActivity);
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RankingToInfoActivity = new Intent(ActivityOperaciones.this, InfoActivity.class);
                startActivity(RankingToInfoActivity);
            }
        });

    }
}