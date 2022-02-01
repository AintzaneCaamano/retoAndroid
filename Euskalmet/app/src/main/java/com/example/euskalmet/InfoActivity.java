package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.euskalmet.adapters.AdapterTownList;
import com.example.euskalmet.cliente.ClientThread;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    private TextView textView_InfoTitle;
    private ListView listViewInfo;
    private Button btnVolverInfo;
    private RadioButton rbtnVizcaya;
    private RadioButton rbtnGuip;
    private RadioButton rbtnAlaba;
    private RadioGroup radioGroup;
    private ArrayList<String> places;
    private AdapterTownList adapterTownList;
    private static final String SEPARADOR = "/////";
    private String user;
    private int pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        textView_InfoTitle = findViewById(R.id.textView_InfoTitle);
        listViewInfo = findViewById(R.id.listView_favoritos);
        btnVolverInfo = findViewById(R.id.btn_volverInfo);
        rbtnAlaba = findViewById(R.id.rBtn_Info_Alaba);
        rbtnGuip = findViewById(R.id.rBtn_Info_Gui);
        rbtnVizcaya=findViewById(R.id.rBtn_info_Viz);
        radioGroup = findViewById(R.id.rGroupInfo);


        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        pass = Integer.parseInt(extras.getString("pass"));

        loadListFromServer("1");
        adapterTownList= new AdapterTownList(InfoActivity.this, R.layout.activity_adapter_list, places);
        listViewInfo.setAdapter(adapterTownList);


        btnVolverInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(InfoActivity.this, ActivityOperaciones.class);
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);
            }
        });

        listViewInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intento = new Intent(getApplicationContext(), DetailMunicipioActivity.class);
                    String place =listViewInfo.getItemAtPosition(position).toString();
                    intento.putExtra("place", place );
                intento.putExtra("user", user);
                intento.putExtra("pass", String.valueOf(pass));
                startActivity(intento);

            }
        });


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rBtn_Info_Alaba:
                if (checked)
                    loadListFromServer("Araba/Ã�lava");
                    rearrageAdapter();
                    break;
            case R.id.rBtn_Info_Gui:
                if (checked)
                    loadListFromServer("Gipuzkoa");
                     rearrageAdapter();
                    break;
            case R.id.rBtn_info_Viz:
                if (checked)
                    loadListFromServer("Bizkaia");
                    rearrageAdapter();
                    break;
        }
    }

    private void rearrageAdapter(){
        adapterTownList= new AdapterTownList(InfoActivity.this, R.layout.activity_adapter_list, places);
        listViewInfo.setAdapter(adapterTownList);
        adapterTownList.notifyDataSetChanged();
    }
    private void loadListFromServer(String territory){
        // get the text message on the user and password
        String messageSent = "4"+SEPARADOR + territory ;

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