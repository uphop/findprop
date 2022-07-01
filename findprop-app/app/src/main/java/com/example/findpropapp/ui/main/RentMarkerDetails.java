package com.example.findpropapp.ui.main;

import com.example.findpropapp.model.RentPriceResponse;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class RentMarkerDetails {
    RentPriceResponse rentPrices;
    Marker anchorMarker;
    List<Marker> referenceMarkers;
    Circle circle;

    public RentPriceResponse getRentPrices() {
        return rentPrices;
    }

    public void setRentPrices(RentPriceResponse rentPrices) {
        this.rentPrices = rentPrices;
    }

    public Marker getAnchorMarker() {
        return anchorMarker;
    }

    public void setAnchorMarker(Marker anchorMarker) {
        this.anchorMarker = anchorMarker;
    }

    public List<Marker> getReferenceMarkers() {
        return referenceMarkers;
    }

    public void setReferenceMarkers(List<Marker> referenceMarkers) {
        this.referenceMarkers = referenceMarkers;
    }

    public void addReferenceMarker(Marker referenceMarker) {
        if(this.referenceMarkers == null) {
            this.referenceMarkers = new ArrayList<>();
        }
        this.referenceMarkers.add(referenceMarker);
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
