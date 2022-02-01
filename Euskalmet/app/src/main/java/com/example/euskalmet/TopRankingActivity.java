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

public class TopRankingActivity extends AppCompatActivity {

    private ListView listViewTopRanking;
    private Button btnVolverFromRanking;
    private AdapterTownList adapterTownList;
    private ArrayList<String> places;
    private static final String SEPARADOR = "/////";
    private String user;
    private int pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ranking);

        loadListFromServer();

        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        pass = Integer.parseInt(extras.getString("pass"));

        listViewTopRanking = findViewById(R.id.listView_topRanking);
        btnVolverFromRanking = findViewById(R.id.btn_volverOperacionesFromRanking);

        adapterTownList= new AdapterTownList(TopRankingActivity.this, R.layout.activity_adapter_list, places);
        listViewTopRanking.setAdapter(adapterTownList);

        rearrageAdapter();

        btnVolverFromRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(TopRankingActivity.this, ActivityOperaciones.class);
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);
            }
        });

        listViewTopRanking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intento = new Intent(getApplicationContext(), DetailEspacioNaturalActivity.class);
                String place =listViewTopRanking.getItemAtPosition(position).toString();
                intento.putExtra("place", place );
                intento.putExtra("origen", "Espacio");
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);
            }
        });

    }

    private void rearrageAdapter(){
        adapterTownList= new AdapterTownList(TopRankingActivity.this, R.layout.activity_adapter_list, places);
        listViewTopRanking.setAdapter(adapterTownList);
        adapterTownList.notifyDataSetChanged();
    }

    private void loadListFromServer(){
        // get the text message on the user and password
        String messageSent = "35"+SEPARADOR;

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