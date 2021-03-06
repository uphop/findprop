package com.lightson.findpropapp;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lightson.findpropapp.adapter.FindPropApiAdapter;
import com.lightson.findpropapp.adapter.RentPriceCallback;
import com.lightson.findpropapp.databinding.ActivityMapsBinding;
import com.lightson.findpropapp.model.RentPriceDetails;
import com.lightson.findpropapp.model.RentPriceLocalAuthorityDetails;
import com.lightson.findpropapp.model.RentPricePeriodEnum;
import com.lightson.findpropapp.model.RentPricePostcodeAreaDetails;
import com.lightson.findpropapp.model.RentPricePostcodeDetails;
import com.lightson.findpropapp.model.RentPricePropertyTypeEnum;
import com.lightson.findpropapp.model.RentPriceResponse;
import com.lightson.findpropapp.ui.main.LogHelper;
import com.lightson.findpropapp.ui.main.MapDistanceHelper;
import com.lightson.findpropapp.ui.main.RentMarkerDetails;
import com.lightson.findpropapp.ui.main.RentPriceDetailsActivity;
import com.lightson.findpropapp.ui.main.RentPriceValueFormatter;
import com.lightson.findpropapp.ui.main.StreetViewMapActivity;
import com.lightson.findpropapp.ui.main.UsageEventEnum;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
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
    private LogHelper logHelper;

    // Default map params
    private final float DEFAULT_REFERENCE_MARKER_COLOR = BitmapDescriptorFactory.HUE_AZURE;
    private final int DEFAULT_CIRCLE_COLOR = android.graphics.Color.argb(50, 204, 204, 204);
    private final float DEFAULT_CIRCLE_BORDER_WIDTH = 1f;
    private final double DEFAULT_CIRCLE_RADIUS_OFFSET = 50.0;
    private final String ARG_CURRENT_PRICE_DETAILS = "CURRENT_PRICE_DETAILS";
    private final String ARG_CURRENT_LAT = "ARG_CURRENT_LAT";
    private final String ARG_CURRENT_LON = "ARG_CURRENT_LON";
    private final double DEFAULT_MAX_RANGE = 500.0;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final int MAP_DEFAULT_ZOOM_LEVEL = 15;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

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
    private Location lastKnownLocation;
    private CameraPosition cameraPosition;

    // Marker map
    private Map<Marker, RentMarkerDetails> priceDetailsMap = new HashMap<Marker, RentMarkerDetails>();

    // Adapters
    private FusedLocationProviderClient fusedLocationClient;
    private FindPropApiAdapter findPropApiClient;

    @SuppressLint("MissingPermission")
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

        logHelper = new LogHelper(TAG, this);
        logHelper.logEvent(UsageEventEnum.session_start, null);
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

        logHelper.logEvent(UsageEventEnum.map_initialised);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        logHelper.logEvent(UsageEventEnum.marker_info_window_clicked, new HashMap<String, String>() {
            {
                put("marker_title", marker.getTitle());
                put("marker_snippet", marker.getSnippet());
                put("longitude", String.valueOf(marker.getPosition().longitude));
                put("latitude", String.valueOf(marker.getPosition().latitude));

                if (priceDetailsMap.containsKey(marker)) {
                    RentMarkerDetails markerDetails = priceDetailsMap.get(marker);
                    put("property_type", markerDetails.getRentPrices().getPropertyType().toString());
                    put("bedrooms", String.valueOf(markerDetails.getRentPrices().getBedrooms()));
                    put("postcode", markerDetails.getRentPrices().getPostcodeDetails().getPostcode());
                }
            }
        });

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
                logHelper.logEvent(UsageEventEnum.bedroom_filter_selection_changed, new HashMap<String, String>() {
                    {
                        put("bedrooms", String.valueOf(currentBedroomCount));
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateResetButtonUI() {
        ImageButton resetButton = (ImageButton) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> {
            resetUI();
            logHelper.logEvent(UsageEventEnum.reset_button_clicked);
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
        Map<String, String> logParams = new HashMap<String, String>() {
            {
                put("style", String.valueOf(R.raw.mapstyle_night));
            }
        };

        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle_night));

            if (!success) {
                logHelper.logEvent(UsageEventEnum.map_styling_failed, logParams, TAG, "Can't apply map style.", Log.ERROR);
            }
        } catch (Resources.NotFoundException e) {
            logHelper.logEvent(UsageEventEnum.map_styling_failed, logParams, TAG, "Can't find map style. Error: " + e.toString(), Log.ERROR);
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

                logHelper.logEvent(UsageEventEnum.marker_info_content_shown, new HashMap<String, String>() {
                    {
                        put("marker_title", marker.getTitle());
                        put("marker_snippet", marker.getSnippet());
                    }
                });

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

            logHelper.logEvent(UsageEventEnum.location_permission_granted);
        } else {
            logHelper.logEvent(UsageEventEnum.location_permission_requested);

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

                logHelper.logEvent(UsageEventEnum.location_permission_granted);
            } else {
                logHelper.logEvent(UsageEventEnum.location_permission_rejected, null, TAG, null, Log.ERROR);
                logHelper.showMessage(R.string.cannot_get_location_permission);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        updateLocationUI();
    }

    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        if (mMap == null) {
            logHelper.logEvent(UsageEventEnum.map_setup_failed, null, TAG, "Cannot update location, map is not set.", Log.ERROR);
            logHelper.showMessage(R.string.cannot_load_map);
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

                logHelper.logEvent(UsageEventEnum.my_location_enabled);
            } else {
                // Otherwise hide My Location elements
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();

                logHelper.logEvent(UsageEventEnum.my_location_disabled, null, TAG, "Cannot set current location, permission not granted.", Log.ERROR);
                logHelper.showMessage(R.string.cannot_set_current_location);
            }
        } catch (SecurityException e) {
            logHelper.logEvent(UsageEventEnum.my_location_failed, null, TAG, "Cannot update location, " + e.getMessage(), Log.ERROR);
            logHelper.showMessage(R.string.cannot_set_current_location);
        }
    }

    private void getDeviceLocation() {
        try {
            if (!locationPermissionGranted) {
                logHelper.logEvent(UsageEventEnum.current_location_permission_failed, null, TAG, null, Log.ERROR);
                logHelper.showMessage(R.string.cannot_set_current_location);
                return;
            }

            @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationClient.getLastLocation();
            locationResult.addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.
                    lastKnownLocation = task.getResult();
                    if (lastKnownLocation == null) {
                        logHelper.logEvent(UsageEventEnum.current_location_undetermined, null, TAG, null, Log.ERROR);
                        logHelper.showMessage(R.string.cannot_determine_current_location);
                        return;
                    }

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(lastKnownLocation.getLatitude(),
                                    lastKnownLocation.getLongitude()), MAP_DEFAULT_ZOOM_LEVEL));

                    logHelper.logEvent(UsageEventEnum.current_location_updated);

                } else {
                    mMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, MAP_DEFAULT_ZOOM_LEVEL));
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);

                    logHelper.logEvent(UsageEventEnum.current_location_failed, null, TAG, "Current location is null. Using defaults. Error: " + task.getException(), Log.ERROR);
                    logHelper.showMessage(R.string.cannot_determine_current_location);
                }
            });

        } catch (SecurityException e) {
            logHelper.logEvent(UsageEventEnum.current_location_security_failed, null, TAG, "Getting current location failed. Error: " + e.getMessage(), Log.ERROR);
            logHelper.showMessage(R.string.cannot_set_current_location);
        }
    }

    // Clean up all prior markers, get location of a new anchor marker and create reference markers around with rent price details
    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        logHelper.logEvent(UsageEventEnum.map_clicked, new HashMap<String, String>() {
            {
                put("longitude", String.valueOf(latLng.longitude));
                put("latitude", String.valueOf(latLng.latitude));
            }
        });

        captureCurrentAnchor(latLng);
    }

    private void captureCurrentAnchor(LatLng latLng) {
        // Got current location. In some rare situations this can be null.
        if (latLng != null) {
            // Get rent prices around that anchor postcode
            Map<String, String> logParams = new HashMap<String, String>() {
                {
                    put("longitude", String.valueOf(latLng.longitude));
                    put("latitude", String.valueOf(latLng.latitude));
                    put("max_range", String.valueOf(DEFAULT_MAX_RANGE));
                    put("property_type", currentPropertyType.toString());
                    put("bedrooms", String.valueOf(currentBedroomCount));
                }
            };
            logHelper.logEvent(UsageEventEnum.rent_prices_requested, logParams);

            Instant processingStart = Instant.now();
            findPropApiClient.getRentPrices(latLng.longitude, latLng.latitude, DEFAULT_MAX_RANGE, currentPropertyType, currentBedroomCount,
                    new RentPriceCallback() {
                        @Override
                        public void onSuccess(RentPriceResponse rentPrices) {
                            Instant processingEnd = Instant.now();
                            logParams.put("app_duration",String.valueOf(Duration.between(processingStart, processingEnd)));

                            if (rentPrices != null) {
                                logParams.put("status", rentPrices.getStatus().toString());
                                logParams.put("api_duration", String.valueOf(rentPrices.getDuration()));
                                logParams.put("postcode", rentPrices.getPostcodeDetails().getPostcode());

                                logHelper.logEvent(UsageEventEnum.rent_prices_received, logParams);
                                updateCurrentAnchor(rentPrices);
                            } else {
                                logHelper.logEvent(UsageEventEnum.rent_prices_failed, logParams, TAG, "Cannot find rent prices for this location try another one.", Log.ERROR);
                                logHelper.showMessage(R.string.cannot_find_rent_prices);
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Instant processingEnd = Instant.now();
                            logParams.put("app_duration",String.valueOf(Duration.between(processingStart, processingEnd)));
                            logHelper.logEvent(UsageEventEnum.rent_prices_failed, logParams, TAG, "Cannot find rent prices for this location. Error: " + e.getMessage(), Log.ERROR);
                            logHelper.showMessage(R.string.cannot_find_rent_prices);
                        }
                    });
        } else {
            logHelper.logEvent(UsageEventEnum.rent_prices_failed, null, TAG, "Cannot capture new anchor, LatLng is null.", Log.ERROR);
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
        logHelper.logEvent(UsageEventEnum.rent_prices_camera_moved_to_anchor);

        // Update marker snippet text and show snippet
        // Set rent price - take postcode area rent, if available; otherwise, local authority rent
        RentPriceDetails price = (postcodeAreaDetails != null) ? postcodeAreaDetails.getPrice() : localAuthorityDetails.getPrice();
        marker.setSnippet(getAnchorSnippetText(price));
        marker.showInfoWindow();

        // Save new marker and its associated rent price details
        markerDetails.setAnchorMarker(marker);
        markerDetails.setRentPrices(rentPrices);
        logHelper.logEvent(UsageEventEnum.rent_prices_anchor_set);

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

                logHelper.logEvent(UsageEventEnum.rent_prices_references_set, new HashMap<String, String>() {
                    {
                        put("longitude", String.valueOf(referencePostcode.getLongitude()));
                        put("latitude", String.valueOf(referencePostcode.getLatitude()));
                        put("distance", String.valueOf(distance));
                        put("property_type", currentPropertyType.toString());
                        put("bedrooms", String.valueOf(currentBedroomCount));
                        put("postcode", referencePostcode.getPostcode());
                    }
                });
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

            logHelper.logEvent(UsageEventEnum.rent_prices_references_radius_set, new HashMap<String, String>() {
                {
                    put("radius", String.valueOf(circle.getRadius()));
                }
            });
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

        logHelper.logEvent(UsageEventEnum.rent_prices_references_updated);
    }

    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {
        logHelper.logEvent(UsageEventEnum.marker_info_window_long_clicked, new HashMap<String, String>() {
            {
                put("marker_title", marker.getTitle());
                put("marker_snippet", marker.getSnippet());
                put("longitude", String.valueOf(marker.getPosition().longitude));
                put("latitude", String.valueOf(marker.getPosition().latitude));

                if (priceDetailsMap.containsKey(marker)) {
                    RentMarkerDetails markerDetails = priceDetailsMap.get(marker);
                    put("property_type", markerDetails.getRentPrices().getPropertyType().toString());
                    put("bedrooms", String.valueOf(markerDetails.getRentPrices().getBedrooms()));
                    put("postcode", markerDetails.getRentPrices().getPostcodeDetails().getPostcode());
                }
            }
        });

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
            Instant processingStart = Instant.now();
            Map<String, String> logParams = new HashMap<String, String>() {
                {
                    put("location", location);
                }
            };
            logHelper.logEvent(UsageEventEnum.location_search_started, logParams);

            Geocoder geocoder = new Geocoder(DefaultMapsActivity.this);
            try {
                List<Address> addressList = geocoder.getFromLocationName(location, 1);
                if (addressList.size() > 0) {
                    // Add new marker by the first address coordinates
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    Instant processingEnd = Instant.now();
                    logParams.put("app_duration",String.valueOf(Duration.between(processingStart, processingEnd)));
                    logParams.put("longitude", String.valueOf(latLng.longitude));
                    logParams.put("latitude", String.valueOf(latLng.latitude));
                    logParams.put("address", address.toString());
                    logHelper.logEvent(UsageEventEnum.location_search_completed, logParams);

                    captureCurrentAnchor(latLng);
                } else {
                    logHelper.logEvent(UsageEventEnum.location_search_failed, logParams, TAG, "Failed to get addresses from location, no results for " + location, Log.ERROR);
                    logHelper.showMessage(R.string.cannot_find_location_address);
                }
            } catch (IOException e) {
                logHelper.logEvent(UsageEventEnum.location_search_failed, logParams, TAG, "Failed to get addresses from location, no results for " + location + ". Error: " + e.getMessage(), Log.ERROR);
                logHelper.showMessage(R.string.cannot_find_location_address);
            }
        } else {
            logHelper.logEvent(UsageEventEnum.location_search_not_specified, null, TAG, "Failed to get addresses from location, location text is empty.", Log.ERROR);
            logHelper.showMessage(R.string.cannot_find_location_address_empty);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        // show details of current marker / hide details of other anchors
        updateReferences(marker);

        logHelper.logEvent(UsageEventEnum.marker_clicked, new HashMap<String, String>() {
            {
                put("marker_title", marker.getTitle());
                put("marker_snippet", marker.getSnippet());
                put("longitude", String.valueOf(marker.getPosition().longitude));
                put("latitude", String.valueOf(marker.getPosition().latitude));

                if (priceDetailsMap.containsKey(marker)) {
                    RentMarkerDetails markerDetails = priceDetailsMap.get(marker);
                    put("property_type", markerDetails.getRentPrices().getPropertyType().toString());
                    put("bedrooms", String.valueOf(markerDetails.getRentPrices().getBedrooms()));
                    put("postcode", markerDetails.getRentPrices().getPostcodeDetails().getPostcode());
                }
            }
        });

        // trigger default behavior
        return false;
    }
}