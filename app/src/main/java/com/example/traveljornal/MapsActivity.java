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

import com.example.traveljornal.databaseclasses.AppDatabase;
import com.example.traveljornal.databaseclasses.User;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public MainActivity mainAct;
    List<String> fileNames = new ArrayList<String>();
    File directory;


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
        try {
            SavePreferences();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SavePreferences() throws IOException {
        //check which user is logged in
        // create a file with the name being set to username of user currently logged in
//        String currentUser = mainAct.getCurrentUser();

        String currentUser = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        String fileNameString = currentUser; //sets file name to be username
        if (fileNameString.isEmpty()) {
            directory = getFilesDir();
        }
        else {
            directory = getDir(fileNameString, MODE_PRIVATE);
        }
        File[] files = directory.listFiles();
        FileOutputStream fos = openFileOutput(fileNameString, Context.MODE_PRIVATE);
        fos.write(internalStorageBinding.saveFileEditText.getText().toString().getBytes());
        fos.close();


        fileNames.add(fileNameString);
        SharedPreferences sharedPreferences1 = getSharedPreferences(fileNameString, MODE_PRIVATE);

//        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();

        editor.putInt("listSize", markerList.size());
        for(int i = 0; i <markerList.size(); i++){
            editor.putFloat("lat"+i, (float) markerList.get(i).getPosition().latitude);
            editor.putFloat("lng"+i, (float) markerList.get(i).getPosition().longitude);
            editor.putString("title"+i, markerList.get(i).getTitle());
        }

        editor.commit();
    }

    private void LoadPreferences(){
//        SharedPreferences sharedPreferences1 = getSharedPreferences(fileNameString, MODE_PRIVATE);

        //loop thru all files created in internal storage, find the one that equals current user then...
        String currentUser = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> userList = db.DatabaseAccessInterface().getAllUsers();

        //go thru sp files, find file with same name as current user, then get data from that file

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).username == currentUser) {
                //        String fileNameString = sharedPreferencesBinding.fileNameEditView.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences(currentUser, MODE_PRIVATE);

//            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

                int size = sharedPreferences.getInt("listSize", 0);
                for(int j = 0; j < size; j++){
                    double lat = (double) sharedPreferences.getFloat("lat"+i,0);
                    double longit = (double) sharedPreferences.getFloat("lng"+i,0);
                    String title = sharedPreferences.getString("title"+i,"NULL");

                    markerList.add(mMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(title)));
                }
            }
                SharedPreferences sharedPreferences1 = getPreferences(MODE_PRIVATE);
                int size = sharedPreferences1.getInt("listSize", 0);
                for(int j = 0; j < size; j++){
                    double lat = (double) sharedPreferences1.getFloat("lat"+i,0);
                    double longit = (double) sharedPreferences1.getFloat("lng"+i,0);
                    String title = sharedPreferences1.getString("title"+i,"NULL");

                    markerList.add(mMap.addMarker(new MarkerOptions().position(new LatLng(lat, longit)).title(title)));
                }
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