package com.example.findpropapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.findpropapp.adapter.FindPropApiAdapter;
import com.example.findpropapp.adapter.RentPriceCallback;
import com.example.findpropapp.databinding.ActivityMapsBinding;
import com.example.findpropapp.model.RentPriceDetails;
import com.example.findpropapp.model.RentPriceLocalAuthorityDetails;
import com.example.findpropapp.model.RentPricePeriodEnum;
import com.example.findpropapp.model.RentPricePostcodeAreaDetails;
import com.example.findpropapp.model.RentPricePostcodeDetails;
import com.example.findpropapp.model.RentPricePropertyTypeEnum;
import com.example.findpropapp.model.RentPriceResponse;
import com.example.findpropapp.ui.main.MapDistanceHelper;
import com.example.findpropapp.ui.main.RentMarkerDetails;
import com.example.findpropapp.ui.main.RentPriceDetailsActivity;
import com.example.findpropapp.ui.main.RentPriceValueFormatter;
import com.example.findpropapp.ui.main.StreetViewMapActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DefaultMapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnInfoWindowLongClickListener,
        SearchView.OnQueryTextListener,
        GoogleMap.OnMarkerClickListener {

    private static final String TAG = DefaultMapsActivity.class.getSimpleName();
    // Default map params
    private final float DEFAULT_REFERENCE_MARKER_COLOR = BitmapDescriptorFactory.HUE_AZURE;
    private final int DEFAULT_CIRCLE_COLOR = android.graphics.Color.argb(50, 204, 204, 204);
    private final float DEFAULT_CIRCLE_BORDER_WIDTH = 1f;
    private final double DEFAULT_CIRCLE_RADIUS_OFFSET = 50.0;

    private final String ARG_CURRENT_PRICE_DETAILS = "CURRENT_PRICE_DETAILS";
    private final String ARG_CURRENT_LAT = "ARG_CURRENT_LAT";
    private final String ARG_CURRENT_LON = "ARG_CURRENT_LON";

    private final double DEFAULT_MAX_RANGE = 1000.0;
    private Map<String, RentPricePropertyTypeEnum> propertyTypeMap;
    private RentPricePropertyTypeEnum currentPropertyType = RentPricePropertyTypeEnum.flat;
    private Map<String, Integer> bedroomCountMap;
    private int currentBedroomCount = 2;

    private GoogleMap mMap;
    private SupportMapFragment mFragment;
    private SearchView searchView;
    private ActivityMapsBinding binding;

    // Location-related attributes
    private boolean locationPermissionGranted;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final int MAP_DEFAULT_ZOOM_LEVEL = 15;
    private Location lastKnownLocation;
    private CameraPosition cameraPosition;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Marker map
    private Map<Marker, RentMarkerDetails> priceDetailsMap = new HashMap<Marker, RentMarkerDetails>();

    // Adapters
    private FusedLocationProviderClient fusedLocationClient;
    private FindPropApiAdapter findPropApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Prepare location adapter
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Prepare FindProp API adapter
        findPropApiClient = new FindPropApiAdapter(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mFragment = mapFragment;

        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    // Init after map is loaded
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable dark mode
        updateMapUI();

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        getDeviceLocation();
        updateLocationUI();

        // Set tooltip view for markers
        updateInfoWindowUI();

        // Prepare search box
        updateSearchUI();

        // Prepare bedroom count filter
        updateBedroomCountFilterUI();

        // Init reset button
        updateResetButtonUI();

        // Add a single map click handler for setting new markers
        mMap.setOnMapClickListener(this);

        // Add a single marker click for showing reference markers and radius
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        // check if marker is in the marker map already
        if (priceDetailsMap.containsKey(marker)) {
            // Start new activity with property details
            RentMarkerDetails rentMarkerDetails = priceDetailsMap.get(marker);
            Intent intent = new Intent(this, RentPriceDetailsActivity.class);
            intent.putExtra(ARG_CURRENT_PRICE_DETAILS, rentMarkerDetails.getRentPrices());
            startActivity(intent);
        }
    }

    private void updateSearchUI() {
        searchView = findViewById(R.id.idSearchView);
        searchView.setOnQueryTextListener(this);
    }

    private void updateBedroomCountFilterUI() {
        // Set values for bedroom count list
        bedroomCountMap = new LinkedHashMap<String, Integer>() {
            {
                {
                    put(getString(R.string.one_bedroom), 1);
                    put(getString(R.string.two_bedroom), 2);
                    put(getString(R.string.three_bedroom), 3);
                    put(getString(R.string.four_and_more_bedroom), 4);
                }
            }
        };

        Spinner spinner = (Spinner) findViewById(R.id.bedroomCountSpinner);
        String[] bedroomCounts = bedroomCountMap.keySet().stream().toArray(String[]::new);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DefaultMapsActivity.this,
                android.R.layout.simple_spinner_item, bedroomCounts);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set default bedroom count
        int spinnerPosition = adapter.getPosition(getString(R.string.two_bedroom));
        spinner.setSelection(spinnerPosition);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentBedroomCount = bedroomCountMap.get(bedroomCounts[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateResetButtonUI() {
        ImageButton resetButton = (ImageButton) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetUI();
            }
        });
    }

    private void resetUI() {
        // remove markers from map
        mMap.clear();

        // remove marker details
        priceDetailsMap.clear();

        // reset camera to current location
        getDeviceLocation();
    }

    private void updateMapUI() {
        // Set map style to dark mode
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle_night));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
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

        // Set a listener for info window events
        mMap.setOnInfoWindowClickListener(this);
        // Add a long info window click handler for starting street view
        mMap.setOnInfoWindowLongClickListener(this);
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
            Log.e(TAG, "Cannot update location, map is not set.");
            return;
        }
        try {
            // If current location permission is granted, update UI with My Location dot and button
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

                // Move My Location button to the bottom right corner
                if (mFragment != null &&
                        mFragment.getView().findViewById(Integer.parseInt("1")) != null) {
                    View locationButton = ((View) mFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                            locationButton.getLayoutParams();
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                    layoutParams.setMargins(0, 0, 30, 30);
                }
            } else {
                // Otherwise hide My Location elements
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
                Log.e(TAG, "Cannot set current location, permission not granted.");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Cannot update location, " + e.getMessage());
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
                            Log.e(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, MAP_DEFAULT_ZOOM_LEVEL));
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
            // Get rent prices around that anchor postcode
            findPropApiClient.getRentPrices(latLng.longitude, latLng.latitude, DEFAULT_MAX_RANGE, currentPropertyType, currentBedroomCount,
                    new RentPriceCallback() {
                        @Override
                        public void onSuccess(RentPriceResponse rentPrices) {
                            if (rentPrices != null) {
                                updateCurrentAnchor(rentPrices);
                            } else {
                                showMessage(
                                        "Cannot find rent prices for this location try another one."
                                );
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {
                            // TODO: handle postcode retrieval failure
                            Log.e(TAG, e.toString());
                            showMessage(
                                    "Cannot get rent prices for this location."
                            );
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
        RentMarkerDetails markerDetails = new RentMarkerDetails();

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
        // Set rent price - take postcode area rent, if available; otherwise, local authority rent
        RentPriceDetails price = (postcodeAreaDetails != null) ? postcodeAreaDetails.getPrice() : localAuthorityDetails.getPrice();
        marker.setSnippet(getAnchorSnippetText(price));
        marker.showInfoWindow();

        // Save new marker and its associated rent price details
        markerDetails.setAnchorMarker(marker);
        markerDetails.setRentPrices(rentPrices);

        // Create reference markers
        double maxDistance = 0.0;
        List<RentPricePostcodeDetails> referencePostcodes = rentPrices.getRelatedPostcodeDetails();
        if (referencePostcodes != null) {
            for (RentPricePostcodeDetails referencePostcode : referencePostcodes) {
                // Configure marker position and title
                LatLng referenceMarkerPosition = new LatLng(referencePostcode.getLatitude(), referencePostcode.getLongitude());
                String referenceTitle = String.format("%s, %s", referencePostcode.getPostcode(), localAuthorityDetails.getLocalAuthority());
                MarkerOptions referenceMarkerOptions = new MarkerOptions()
                        .position(referenceMarkerPosition)
                        .title(referenceTitle)
                        .icon(BitmapDescriptorFactory.defaultMarker(DEFAULT_REFERENCE_MARKER_COLOR));

                // Add reference marker details
                Marker referenceMarker = mMap.addMarker(referenceMarkerOptions);
                markerDetails.addReferenceMarker(referenceMarker);

                // Update reference marker snippet text
                referenceMarker.setSnippet(getReferenceSnippetText(referencePostcode.getPrice()));

                // calculate distance between anchor and reference
                double distance = MapDistanceHelper.getDistance(postcodeDetails.getLatitude(), referencePostcode.getLatitude(),
                        postcodeDetails.getLongitude(), referencePostcode.getLongitude(), 0, 0);
                maxDistance = (distance > maxDistance) ? distance : maxDistance;
            }

            // Create new circle around reference markers
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(marker.getPosition())
                    .radius(maxDistance + DEFAULT_CIRCLE_RADIUS_OFFSET)
                    .strokeWidth(DEFAULT_CIRCLE_BORDER_WIDTH)
                    .strokeColor(DEFAULT_CIRCLE_COLOR)
                    .fillColor(DEFAULT_CIRCLE_COLOR)
            );
            markerDetails.setCircle(circle);
        }

        // Save new marker details in the marker map
        priceDetailsMap.put(marker, markerDetails);

        // update reference markers
        updateReferences(marker);
    }

    private String getAnchorSnippetText(RentPriceDetails price) {
        StringBuilder snippetText = new StringBuilder();
        snippetText.append(getString(R.string.typical_rent_is));
        snippetText.append(" ");
        snippetText.append(getString(R.string.between));
        snippetText.append(" ");
        // Set rent price - take postcode area rent, if available; otherwise, local authority rent
        RentPriceDetails pricePCM = RentPriceValueFormatter.getPriceDetailsPCM(price);
        snippetText.append(
                RentPriceValueFormatter.getPriceWithPeriodAsString(
                        pricePCM.getPriceLow(),
                        RentPricePeriodEnum.month));
        snippetText.append(" ");
        snippetText.append(getString(R.string.and));
        snippetText.append(" ");
        snippetText.append(
                RentPriceValueFormatter.getPriceWithPeriodAsString(
                        pricePCM.getPriceHigh(),
                        RentPricePeriodEnum.month));

        return snippetText.toString();
    }

    private String getReferenceSnippetText(RentPriceDetails price) {
        StringBuilder referenceSnippetText = new StringBuilder();
        referenceSnippetText.append(getString(R.string.recent_rent_is));
        referenceSnippetText.append(" ");
        // Set rent price - take postcode area rent, if available; otherwise, local authority rent
        referenceSnippetText.append(
                RentPriceValueFormatter.getPriceWithPeriodAsString(
                        RentPriceValueFormatter.getRentPricePCM(price),
                        RentPricePeriodEnum.month));

        return referenceSnippetText.toString();
    }

    private void updateReferences(Marker selectedMarker) {
        // check if this is an anchor marker; if not - no updates needed
        if (!priceDetailsMap.containsKey(selectedMarker)) return;

        // go through all anchor markers
        for (Marker anchorMarker : priceDetailsMap.keySet()) {
            // get reference markers
            RentMarkerDetails anchorMarkerDetails = priceDetailsMap.get(anchorMarker);
            if (anchorMarkerDetails != null) {
                // check if this is selected anchor marker
                boolean isAnchor = anchorMarker.equals(selectedMarker);

                // show reference markers for the selected anchor; hide reference markers for all other anchors
                List<Marker> referenceMarkers = anchorMarkerDetails.getReferenceMarkers();
                if (referenceMarkers != null) {
                    for (Marker referenceMarker : referenceMarkers) {
                        referenceMarker.setVisible(isAnchor);
                    }
                }

                // show circle around reference markers for the selected anchor; hide circle for all other anchors
                Circle circle = anchorMarkerDetails.getCircle();
                if (circle != null) circle.setVisible(isAnchor);
            }
        }
    }

    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {
        Intent intent = new Intent(this, StreetViewMapActivity.class);
        intent.putExtra(ARG_CURRENT_LAT, marker.getPosition().latitude);
        intent.putExtra(ARG_CURRENT_LON, marker.getPosition().longitude);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Get location text from search view
        String location = searchView.getQuery().toString();
        searchView.setQuery("", false);
        searchView.setIconified(true);

        // Get address from location text
        if (location != null || location.equals("")) {
            Geocoder geocoder = new Geocoder(DefaultMapsActivity.this);
            try {
                List<Address> addressList = geocoder.getFromLocationName(location, 1);
                if (addressList.size() > 0) {
                    // Add new marker by the first address coordinates
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    captureCurrentAnchor(latLng);
                } else {
                    Log.e(TAG, "Failed to get addresses from location, no results for " + location);
                    Toast.makeText(getApplicationContext(),
                            "No results for " + location,
                            Toast.LENGTH_LONG)
                            .show();
                }
            } catch (IOException e) {
                Log.e(TAG, "Failed to get addresses from location: " + e.toString());
            }
        } else {
            Log.e(TAG, "Failed to get addresses from location, location text is empty.");
            Toast.makeText(getApplicationContext(),
                    "Location is empty",
                    Toast.LENGTH_LONG)
                    .show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void showMessage(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        // show details of current marker / hide details of other anchors
        updateReferences(marker);

        // trigger default behavior
        return false;
    }


}