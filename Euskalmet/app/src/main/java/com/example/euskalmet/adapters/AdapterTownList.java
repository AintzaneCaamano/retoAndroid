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

public class AdapterTownList extends ArrayAdapter <String> {

    private  TextView txtViewName;
    private Context context;
    private String place;

    public AdapterTownList(Context context, int activity_adapter_list, ArrayList<String> placeList) {
        super(context, 0, placeList);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // super(itemView);
        // Get the data item for this position
        place = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter_list, parent, false);
        }
        // Lookup view for data population
        txtViewName= (TextView) convertView.findViewById(R.id.txtV_listviewlyo_name);

        // Populate the data into the template view using the data object
        txtViewName.setText(place);
        // Return the completed view to render on screen
        return convertView;
    }
}
