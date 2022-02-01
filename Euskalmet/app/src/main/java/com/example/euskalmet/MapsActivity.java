package com.example.euskalmet;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.euskalmet.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private double latitud ;
    private double longitud ;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String user;
    private int pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadChords();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        loadChords();
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng position = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(position).title("Estacion metereologica"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(9));
    }

    public void loadChords(){
        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        pass = Integer.parseInt(extras.getString("pass"));
        if (extras.getDouble("lat")!=0.0000){
            latitud = (extras.getDouble("lat"));
        }else {
             latitud = 43.245553729819484;
        }

        if (extras.getDouble("long")!=0.0000){
            longitud =(extras.getDouble("long"));
        }else {
            longitud = -2.960475856567045;
        }
    }
}