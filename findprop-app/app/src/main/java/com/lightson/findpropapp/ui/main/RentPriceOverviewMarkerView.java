package com.lightson.findpropapp.ui.main;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.lightson.findpropapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RentPriceOverviewMarkerView extends MarkerView {
    private static final String TAG = RentPriceOverviewMarkerView.class.getSimpleName();
    private LogHelper logHelper;

    private TextView markerContent;
    private MPPointF mOffset;
    private ArrayList<RentPriceEntry> rentPrices;

    public RentPriceOverviewMarkerView(Context context, int layoutResource, ArrayList<RentPriceEntry> rentPrices) {
        super(context, layoutResource);
        markerContent = (TextView) findViewById(R.id.marker_content);
        this.rentPrices = rentPrices;
        logHelper = new LogHelper(TAG, context);
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

            logHelper.logEvent(UsageEventEnum.rent_prices_overview_marker_shown, new HashMap<String, String>() {
                {
                    put("label", priceEntry.getLabel());
                }
            });

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
