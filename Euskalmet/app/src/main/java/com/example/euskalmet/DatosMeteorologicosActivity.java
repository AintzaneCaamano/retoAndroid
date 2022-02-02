package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.euskalmet.adapters.AdapterDataList;
import com.example.euskalmet.cliente.ClientThread;

import java.util.ArrayList;

public class DatosMeteorologicosActivity extends AppCompatActivity {

    private Button btnVolverDatosMeteorologicos;
    private ListView listViewDatosMetereologicos;
    private AdapterDataList adapter;
    private ArrayList<String> places;
    private static final String SEPARADOR = "/////";
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

        loadListFromServer();

        adapter = new AdapterDataList(DatosMeteorologicosActivity.this, R.layout.activity_data_adapter_list, places);
        listViewDatosMetereologicos.setAdapter(adapter);

        rearrageAdapter();

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

        listViewDatosMetereologicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intento = new Intent(getApplicationContext(), DetailEspacioNaturalActivity.class);
                String place =listViewDatosMetereologicos.getItemAtPosition(position).toString();
                intento.putExtra("place", place );
                intento.putExtra("origen", "Espacio");
                startActivity(intento);
            }
        });
    }

    private void rearrageAdapter(){
        adapter = new AdapterDataList(DatosMeteorologicosActivity.this, R.layout.activity_data_adapter_list, places);
        listViewDatosMetereologicos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadListFromServer(){
        // get the text message on the user and password
        String messageSent = "36" + SEPARADOR + areaName;

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