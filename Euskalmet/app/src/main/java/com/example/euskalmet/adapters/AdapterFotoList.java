package com.example.euskalmet.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.euskalmet.R;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class AdapterFotoList extends ArrayAdapter <String> {

    private ImageView imgV;
    private Context context;
    private String str;

    public AdapterFotoList(Context context, int activity_adapter_list, ArrayList<String> placeList) {
        super(context, 0, placeList);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // super(itemView);
        // Get the data item for this position
        str = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_photo_adapter_list, parent, false);
        }
        // Lookup view for data population
        imgV= (ImageView) convertView.findViewById(R.id.imgV_galeriaAdapter);

        // Populate the data into the template view using the data object

        byte[] img = str.getBytes(StandardCharsets.UTF_8);
        byte[] decodedString = Base64.decode(img, Base64.NO_WRAP);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgV.setImageBitmap(bmp);

        // Return the completed view to render on screen
        return convertView;
    }

}
