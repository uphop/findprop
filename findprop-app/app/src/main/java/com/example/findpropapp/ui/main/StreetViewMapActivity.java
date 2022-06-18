package com.example.findpropapp.ui.main;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.findpropapp.R;
import com.example.findpropapp.model.RentPriceResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.findpropapp.databinding.ActivityStreetViewMapBinding;

public class StreetViewMapActivity extends FragmentActivity implements OnMapReadyCallback, OnStreetViewPanoramaReadyCallback {

    private GoogleMap mMap;
    private static final String ARG_CURRENT_LAT = "ARG_CURRENT_LAT";
    private static final String ARG_CURRENT_LON = "ARG_CURRENT_LON";
    private StreetViewPanorama streetViewPanorama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view_map);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        this.streetViewPanorama = streetViewPanorama;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double latitude = extras.getDouble(ARG_CURRENT_LAT);
            double longitude = extras.getDouble(ARG_CURRENT_LON);
            LatLng position = new LatLng(latitude, longitude);

            /*MarkerOptions markerOptions = new MarkerOptions()
                    .position(position)
                    .title("Hello there!");
            Marker marker = mMap.addMarker(markerOptions);*/

            this.streetViewPanorama.setPosition(position);
        }

    }

    private void updatePosition() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double latitude = extras.getDouble(ARG_CURRENT_LAT);
            double longitude = extras.getDouble(ARG_CURRENT_LON);
            LatLng position = new LatLng(latitude, longitude);

            /*MarkerOptions markerOptions = new MarkerOptions()
                    .position(position)
                    .title("Hello there!");
            Marker marker = mMap.addMarker(markerOptions);*/

            this.streetViewPanorama.setPosition(position);
        }
    }
}