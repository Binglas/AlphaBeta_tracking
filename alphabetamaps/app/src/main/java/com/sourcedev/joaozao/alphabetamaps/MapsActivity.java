package com.sourcedev.joaozao.alphabetamaps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AlphaBetaGen alphaBetaGen;
    private ArrayList<Double> arrayX;
    private ArrayList<Double> arrayY;
    //private LatLng feup1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //alphaBetaGen = new AlphaBetaGen(this, mMap, 1, 99);
        //alphaBetaGen.executeAlphaBetaGen();

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

        alphaBetaGen = new AlphaBetaGen(this, mMap, 1, 99);
        alphaBetaGen.executeAlphaBetaGen();

        arrayX = alphaBetaGen.getArrayListX();
        arrayY = alphaBetaGen.getArrayListY();

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //  FEUP 41º10'41.00" N 8º35'54.00" W
        /*for(int i = 0 ; i < arrayY.size() ; i++) {
            Log.d("_OLA","lat = " + arrayX.get(i) + "    long = " + arrayY.get(i));
            LatLng feup1 = new LatLng(arrayX.get(i), arrayY.get(i));
            //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.addMarker(new MarkerOptions().position(feup1).title("Marker in feup"));
        }*/

        //feup1 = new LatLng(41.17866546024098, -8.597631000000002);
        //mMap.addMarker(new MarkerOptions().position(feup1).title("Marker in feup"));
        //mMap.setMaxZoomPreference(mMap.getMaxZoomLevel());
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(feup1));
    }
}
