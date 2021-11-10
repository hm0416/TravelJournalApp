package com.example.traveljornal;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.traveljornal.databinding.ActivityMapsBinding;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    List<LatLng> locationArrayList = new ArrayList<LatLng>();
    MarkerOptions markerOptions = new MarkerOptions();
    Marker marker = null;
    private List<Marker> markerList = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    //https://stackoverflow.com/questions/25438043/store-google-maps-markers-in-sharedpreferences
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        SavePreferences();
    }

    private void SavePreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("listSize", markerList.size());

        for(int i = 0; i <markerList.size(); i++){
            editor.putFloat("lat"+i, (float) markerList.get(i).getPosition().latitude);
            editor.putFloat("lng"+i, (float) markerList.get(i).getPosition().longitude);
            editor.putString("title"+i, markerList.get(i).getTitle());
        }

        editor.commit();
    }

    private void LoadPreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        int size = sharedPreferences.getInt("listSize", 0);
        for(int i = 0; i < size; i++){
            double lat = (double) sharedPreferences.getFloat("lat"+i,0);
            double longit = (double) sharedPreferences.getFloat("lng"+i,0);
            String title = sharedPreferences.getString("title"+i,"NULL");

            markerList.add(mMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(title)));
        }
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
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        LoadPreferences();

//        prefs = MapsActivity.this.getSharedPreferences("LatLng",MODE_PRIVATE);
//
//        if((prefs.contains("Lat")) && (prefs.contains("Lng")))
//        {
//
//            String lat = prefs.getString("Lat","");
//            String lng = prefs.getString("Lng","");
//            LatLng l = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
//
//            for(int i = 0; i < locationArrayList.size(); i++) {
//                LatLng latLng = locationArrayList.get(i);
//                Double lat2 = latLng.latitude;
//                Double lang2 = latLng.longitude;
//
//                LatLng l2 = new LatLng(lat2, lang2);
//                mMap.addMarker(new MarkerOptions().position(l2));
//
//            }
//
////            mMap.addMarker(new MarkerOptions().position(l));
//
//        }



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                locationArrayList.add(latLng);

                marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                markerList.add(mMap.addMarker(new MarkerOptions().position(latLng).title("Marker")));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

//                prefs.edit().putString("Lat",String.valueOf(latLng.latitude)).apply(); //saving last clicked country
//                prefs.edit().putString("Lng",String.valueOf(latLng.longitude)).apply();
//                markerCount++;
//
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                //Your marker removed
                marker.remove();
                return true;
            }
        });
    }
}