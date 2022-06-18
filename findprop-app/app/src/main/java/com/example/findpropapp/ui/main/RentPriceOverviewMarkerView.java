package com.example.findpropapp.ui.main;

import android.content.Context;
import android.widget.TextView;

import com.example.findpropapp.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class RentPriceOverviewMarkerView extends MarkerView {
    private static final String TAG = RentPriceOverviewMarkerView.class.getSimpleName();
    private TextView markerContent;
    private MPPointF mOffset;
    private ArrayList<RentPriceEntry> rentPrices;

    public RentPriceOverviewMarkerView(Context context, int layoutResource, ArrayList<RentPriceEntry> rentPrices) {
        super(context, layoutResource);
        markerContent = (TextView) findViewById(R.id.marker_content);
        this.rentPrices = rentPrices;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        // set marker description from price entry by label
        String description = getDescription(e);
        markerContent.setText(description);
        super.refreshContent(e, highlight);
    }

    private String getDescription(Entry entry) {
        if (entry != null && this.rentPrices != null) {
            RentPriceEntry priceEntry = rentPrices.get((int) entry.getX());
            return priceEntry.getDescription();
        }
        return "";
    }

    @Override
    public MPPointF getOffset() {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }
        return mOffset;
    }
}
