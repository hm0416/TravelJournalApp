package com.example.traveljornal;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
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

    //https://stackoverflow.com/questions/44165099/saving-map-marker-location-onmapclick-using-shared-preferences/44166482
//    public static final String SHARED_PREF_NAME = "plot";
//    public static final String LONGTITUDE = "long";
//    public static final String LATITUDE = "lat";
//    public static final String PLOTTED = "plotted";
//    private boolean plotted = false;
//    public static final String LOGGEDIN_SHARED_PREF = "";
//    public int markerCount = 0;
//    ArrayList<LatLng> locationArrayList = new ArrayList<>();
    List<LatLng> locationArrayList = new ArrayList<LatLng>();
    SharedPreferences prefs = null;
    MarkerOptions markerOptions = new MarkerOptions();
    Marker marker = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




//        prefs = this.getSharedPreferences("LatLng",MODE_PRIVATE);
//
//        if((prefs.contains("Lat")) && (prefs.contains("Lng"))) {
//            String lat = prefs.getString("Lat", "");
//            String lng = prefs.getString("Lng", "");
//            LatLng l = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//            mMap.addMarker(new MarkerOptions().position(l));
//        }
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//
//        savedInstanceState.
//    }

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

        prefs = MapsActivity.this.getSharedPreferences("LatLng",MODE_PRIVATE);

        if((prefs.contains("Lat")) && (prefs.contains("Lng")))
        {

            String lat = prefs.getString("Lat","");
            String lng = prefs.getString("Lng","");
            LatLng l = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
            mMap.addMarker(new MarkerOptions().position(l));

        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                MarkerOptions markerOptions = new MarkerOptions();
//                prefs.edit().putString("Lat",String.valueOf(latLng.latitude)).apply();
//                prefs.edit().putString("Lng",String.valueOf(latLng.longitude)).apply();

                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                locationArrayList.add(latLng);

//                mMap.clear();

                marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                prefs.edit().putString("Lat",String.valueOf(latLng.latitude)).apply();
                prefs.edit().putString("Lng",String.valueOf(latLng.longitude)).apply();
//                markerCount++;
//
//                String lat = Double.toString(latLng.latitude);
//                String lon = Double.toString(latLng.longitude);
//
//                SharedPreferences sharedPreferences = MapsActivity.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
//                editor.putString(LONGTITUDE, lon);
//                editor.putString(LATITUDE, lat);
//                editor.commit();

//                prefs.edit().putString("Lat",String.valueOf(latLng.latitude)).commit();
//                prefs.edit().putString("Lng",String.valueOf(latLng.longitude)).commit();

//                    mMap.clear();
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                    mMap.addMarker(markerOptions);
            }
        });


//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//
//            @Override
//            public boolean onMarkerClick(Marker arg0) {
////Your marker removed
//                marker.remove();
//                return true;
//            }
//        });

//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        Boolean checkedout = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
//
//        if (checkedout) {
//            for(int i = 0; i < markerCount; i++) {
//                SharedPreferences sharedPreferencess = getSharedPreferences(MapsActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                String lon = sharedPreferencess.getString(MapsActivity.LONGTITUDE, "Not Available");
//                String lat = sharedPreferencess.getString(MapsActivity.LATITUDE, "Not Available");
//
//                double lo = Double.parseDouble(lon);
//                double la = Double.parseDouble(lat);
//
//                LatLng resumedPosition = new LatLng(lo, la);
//
////
////            mMap.addMarker(new MarkerOptions().position(resumedPosition).title("Marker"));
////            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
////            mMap.moveCamera(CameraUpdateFactory.newLatLng(resumedPosition));
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(resumedPosition).draggable(false));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(resumedPosition));
//            }


//        }


    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        try {
//            // Modes: MODE_PRIVATE, MODE_WORLD_READABLE, MODE_WORLD_WRITABLE
//            FileOutputStream output = openFileOutput("latlngpoints.txt",
//                    Context.MODE_APPEND);
//            DataOutputStream dout = new DataOutputStream(output);
//            dout.writeInt(locationArrayList.size()); // Save line count
//            for (LatLng point : locationArrayList) {
//                dout.writeUTF(point.latitude + "," + point.longitude);
//                Log.v("write", point.latitude + "," + point.longitude);
//            }
//            dout.flush(); // Flush stream ...
//            dout.close(); // ... and close.
//        } catch (IOException exc) {
//            exc.printStackTrace();
//        }
//    }
//
//    private void loadMarkers(List<LatLng> listOfPoints) {
//        int i=listOfPoints.size();
//        while(i>0){
//            i--;
//            double Lat=listOfPoints.get(i).latitude;
//            double Lon=listOfPoints.get(i).longitude;
//            MarkerOptions mp = new MarkerOptions();
//
//            LatLng point = new LatLng(Lat, Lon);
//            mp.position(point);
//
//            mp.title("my previous position");
//
//
//            mMap.addMarker(new MarkerOptions().position(point).title(point.toString()));
//
////            mMap.addMarker(mp);
//
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                    point, 16));
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        try {
//            FileInputStream input = openFileInput("latlngpoints.txt");
//            DataInputStream din = new DataInputStream(input);
//            int sz = din.readInt(); // Read line count
//            for (int i = 0; i < sz; i++) {
//                String str = din.readUTF();
//                Log.v("read", str);
//                String[] stringArray = str.split(",");
//                double latitude = Double.parseDouble(stringArray[0]);
//                double longitude = Double.parseDouble(stringArray[1]);
//                locationArrayList.add(new LatLng(latitude, longitude));
//            }
//            din.close();
//            loadMarkers(locationArrayList);
//        } catch (IOException exc) {
//            exc.printStackTrace();
//        }
//    }
}