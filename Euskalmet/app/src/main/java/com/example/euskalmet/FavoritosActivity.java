package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.euskalmet.adapters.AdapterTownList;
import com.example.euskalmet.cliente.ClientThread;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity {

    private ListView listViewFavoritos;
    private Button btnVolverFavoritos;
    private AdapterTownList adapterTownList;
    private ArrayList<String> places;
    private static final String SEPARADOR = "/////";
    private String user;
    private int pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);


        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        pass = Integer.parseInt(extras.getString("pass"));

        loadListFromServer(user, pass);
        btnVolverFavoritos = findViewById(R.id.btn_volverFavoritos);
        listViewFavoritos = findViewById(R.id.listView_favoritos);

        adapterTownList= new AdapterTownList(FavoritosActivity.this, R.layout.activity_adapter_list, places);
        listViewFavoritos.setAdapter(adapterTownList);

        rearrageAdapter();

        btnVolverFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(FavoritosActivity.this, ActivityOperaciones.class);
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);
            }
        });

        listViewFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intento = new Intent(getApplicationContext(), DetailEspacioNaturalActivity.class);
                String place =listViewFavoritos.getItemAtPosition(position).toString();
                intento.putExtra("place", place );
                intento.putExtra("origen", "Espacio");
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);
            }
        });
    }

    private void rearrageAdapter(){
        adapterTownList= new AdapterTownList(FavoritosActivity.this, R.layout.activity_adapter_list, places);
        listViewFavoritos.setAdapter(adapterTownList);
        adapterTownList.notifyDataSetChanged();
    }

    private void loadListFromServer(String user, int contrasenia){
        // get the text message on the user and password
        String messageSent = "30"+SEPARADOR + user + SEPARADOR + contrasenia;

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
        places = clientThread.getArrayStringResponse();
    }
}