package com.example.findpropapp.ui.main;

import android.content.Context;
import android.widget.TextView;

import com.example.findpropapp.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class RentTotalCostMarkerView extends MarkerView {
    private static final String TAG = RentTotalCostMarkerView.class.getSimpleName();
    private TextView markerContent;
    private MPPointF mOffset;
    private ArrayList<RentCostEntry> rentCosts;

    public RentTotalCostMarkerView(Context context, int layoutResource, ArrayList<RentCostEntry> rentCosts) {
        super(context, layoutResource);
        markerContent = (TextView) findViewById(R.id.marker_content);
        this.rentCosts = rentCosts;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        // set marker description from price entry by label
        PieEntry pieEntry = (PieEntry) e;
        String description = getDescription(pieEntry.getLabel());
        markerContent.setText(description);
        super.refreshContent(e, highlight);
    }

    private String getDescription(String label) {
        if (label != null && this.rentCosts != null) {
            for (RentCostEntry entry : rentCosts) {
                if (label.equalsIgnoreCase(entry.getType().getDisplayName())) {
                    return entry.getDescription();
                }
            }
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
