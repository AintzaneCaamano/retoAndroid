package com.example.euskalmet.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.euskalmet.InfoActivity;
import com.example.euskalmet.R;

import java.util.ArrayList;


public class AdapterDataList extends ArrayAdapter<String> {
    private TextView txtV1;
    private TextView txtV2;
    private TextView txtV3;
    private Context context;
    private String data;
    private String SEPARADOR = ";";
    public AdapterDataList(Context context, int activity_data_adapter_list, ArrayList<String> placeList) {
        super(context, 0, placeList);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // super(itemView);
        // Get the data item for this position
        data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_data_adapter_list, parent, false);
        }
        // Lookup view for data population
        txtV1= (TextView) convertView.findViewById(R.id.txtV1);
        txtV2= (TextView) convertView.findViewById(R.id.txtV2);
        txtV3= (TextView) convertView.findViewById(R.id.txtV3);

        String [] info = data.split(SEPARADOR);
         if (info.length>2){
        // Populate the data into the template view using the data object
        txtV1.setText(data.split(SEPARADOR)[0]);
        txtV2.setText(data.split(SEPARADOR)[1]);
        txtV3.setText(data.split(SEPARADOR)[2]);
         }else {
             txtV1.setText(data);
         }


        // Return the completed view to render on screen
        return convertView;
    }

}
