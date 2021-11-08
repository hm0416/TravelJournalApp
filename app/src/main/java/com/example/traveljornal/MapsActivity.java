package com.example.traveljornal;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.traveljornal.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    //https://stackoverflow.com/questions/44165099/saving-map-marker-location-onmapclick-using-shared-preferences/44166482
    public static final String SHARED_PREF_NAME = "plot";
    public static final String LONGTITUDE = "long";
    public static final String LATITUDE = "lat";
    public static final String PLOTTED = "plotted";
    private boolean plotted = false;
    public static final String LOGGEDIN_SHARED_PREF = "";
    public int markerCount = 0;

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
        ArrayList<LatLng> locationArrayList = new ArrayList<>();

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                locationArrayList.add(latLng);

//                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                markerCount++;

                String lat = Double.toString(latLng.latitude);
                String lon = Double.toString(latLng.longitude);

                SharedPreferences sharedPreferences = MapsActivity.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                editor.putString(LONGTITUDE, lon);
                editor.putString(LATITUDE, lat);
                editor.commit();

//                prefs.edit().putString("Lat",String.valueOf(latLng.latitude)).commit();
//                prefs.edit().putString("Lng",String.valueOf(latLng.longitude)).commit();

//                    mMap.clear();
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                    mMap.addMarker(markerOptions);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Boolean checkedout = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);

        if (checkedout) {
            for(int i = 0; i < markerCount; i++) {
                SharedPreferences sharedPreferencess = getSharedPreferences(MapsActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String lon = sharedPreferencess.getString(MapsActivity.LONGTITUDE, "Not Available");
                String lat = sharedPreferencess.getString(MapsActivity.LATITUDE, "Not Available");

                double lo = Double.parseDouble(lon);
                double la = Double.parseDouble(lat);

                LatLng resumedPosition = new LatLng(lo, la);

//
//            mMap.addMarker(new MarkerOptions().position(resumedPosition).title("Marker"));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(resumedPosition));

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(resumedPosition).draggable(false));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(resumedPosition));
            }


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}