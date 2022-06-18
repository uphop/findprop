package com.example.findpropapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.findpropapp.adapter.FIndPropApiAdapter;
import com.example.findpropapp.adapter.RentPriceCallback;
import com.example.findpropapp.databinding.ActivityMapsBinding;
import com.example.findpropapp.model.RentPriceDetails;
import com.example.findpropapp.model.RentPriceLocalAuthorityDetails;
import com.example.findpropapp.model.RentPricePostcodeAreaDetails;
import com.example.findpropapp.model.RentPricePostcodeDetails;
import com.example.findpropapp.model.RentPriceResponse;
import com.example.findpropapp.ui.main.RentPriceDetailsActivity;
import com.example.findpropapp.ui.main.RentPriceValueFormatter;
import com.example.findpropapp.ui.main.StreetViewMapActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class DefaultMapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnInfoWindowLongClickListener {

    private static final String TAG = DefaultMapsActivity.class.getSimpleName();
    // Default map params
    private static final int MAP_DEFAULT_ZOOM_LEVEL = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String ARG_CURRENT_PRICE_DETAILS = "CURRENT_PRICE_DETAILS";
    private static final String ARG_CURRENT_LAT = "ARG_CURRENT_LAT";
    private static final String ARG_CURRENT_LON = "ARG_CURRENT_LON";

    private static final String DEFAULT_PROPERTY_TYPE = "flat";
    private static final int DEFAULT_BEDROOM_COUNT = 2;
    private static final double DEFAULT_MAX_RANGE = 250.0;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    // Location-related attributes
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;

    // Marker map
    private Map<Marker, RentPriceResponse> priceDetailsMap = new HashMap<Marker, RentPriceResponse>();

    // Adapters
    private FusedLocationProviderClient fusedLocationClient;
    private FIndPropApiAdapter findPropApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Prepare location adapter
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Prepare FindProp API adapter
        findPropApiClient = new FIndPropApiAdapter(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Init after map is loaded
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        getDeviceLocation();
        updateInfoWindowUI();

        // Add a single map click handler for setting new markers
        mMap.setOnMapClickListener(this);

        // Add a long info window click handler for starting street view
        mMap.setOnInfoWindowLongClickListener(this);

        // Set a listener for info window events
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        // check if marker is in the marker map already
        if (priceDetailsMap.containsKey(marker)) {
            // Start new activity with property details
            RentPriceResponse currentPriceDetails = priceDetailsMap.get(marker);
            Intent intent = new Intent(this, RentPriceDetailsActivity.class);
            intent.putExtra(ARG_CURRENT_PRICE_DETAILS, currentPriceDetails);
            startActivity(intent);
        }
    }

    private void updateInfoWindowUI() {
        Context ctx = this.getApplicationContext();
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(ctx);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(ctx);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(ctx);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);
                return info;
            }
        });
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                Log.e(TAG, "Setting current location");
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
                Log.e(TAG, "Cannot set current location.");
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), MAP_DEFAULT_ZOOM_LEVEL));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    // Clean up all prior markers, get location of a new anchor marker and create reference markers around with rent price details
    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        captureCurrentAnchor(latLng);
    }

    private void captureCurrentAnchor(LatLng latLng) {
        // Got current location. In some rare situations this can be null.
        if (latLng != null) {
            Log.i(TAG, "Capturing new anchor: " + latLng.toString());
            // Get rent prices around that anchor postcode
            findPropApiClient.getRentPrices(latLng.longitude, latLng.latitude, DEFAULT_MAX_RANGE, DEFAULT_PROPERTY_TYPE, DEFAULT_BEDROOM_COUNT,
                    new RentPriceCallback() {
                        @Override
                        public void onSuccess(RentPriceResponse rentPrices) {
                            updateCurrentAnchor(rentPrices);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            // TODO: handle postcode retrieval failure
                            Log.e(TAG, e.toString());
                        }
                    });
        } else {
            Log.e(TAG, "Cannot capture new anchor, LatLng is null.");
        }
    }

    private void updateCurrentAnchor(RentPriceResponse rentPrices) {
        RentPricePostcodeDetails postcodeDetails = rentPrices.getPostcodeDetails();
        RentPricePostcodeAreaDetails postcodeAreaDetails = rentPrices.getPostcodeAreaDetails();
        RentPriceLocalAuthorityDetails localAuthorityDetails = rentPrices.getLocalAuthorityDetails();

        // Configure marker position and title
        LatLng markerPosition = new LatLng(postcodeDetails.getLatitude(), postcodeDetails.getLongitude());
        String markerTitle = String.format("%s, %s", postcodeDetails.getPostcode(), localAuthorityDetails.getLocalAuthority());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(markerPosition)
                .title(markerTitle);

        // Add marker to map and move camera to make that in the center
        Marker marker = mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(markerPosition));

        // Update marker snippet text and show snippet
        StringBuilder snippetText = new StringBuilder();
        snippetText.append("Typical rent here is ");
        // Set rent price - take postcode area rent, if available; otherwise, local authority rent
        RentPriceDetails price = (postcodeAreaDetails != null) ? postcodeAreaDetails.getPrice() : localAuthorityDetails.getPrice();
        snippetText.append(RentPriceValueFormatter.getPriceWithPeriodAsString(price));
        marker.setSnippet(snippetText.toString());
        marker.showInfoWindow();

        // Save new marker and its associated rent price details
        priceDetailsMap.put(marker, rentPrices);
    }

    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {
        Intent intent = new Intent(this, StreetViewMapActivity.class);
        intent.putExtra(ARG_CURRENT_LAT, marker.getPosition().latitude);
        intent.putExtra(ARG_CURRENT_LON, marker.getPosition().longitude);
        startActivity(intent);
    }
}