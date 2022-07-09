package com.lightson.findpropapp.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.lightson.findpropapp.R;
import com.lightson.findpropapp.model.RentPricePeriodEnum;
import com.lightson.findpropapp.model.RentPriceResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RentTotalCostOverviewFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = RentTotalCostOverviewFragment.class.getSimpleName();
    private static final String ARG_CURRENT_PRICE_DETAILS = "CURRENT_PRICE_DETAILS";
    private static final Map<RentCostEntryType, Integer> costColorMap = new HashMap<RentCostEntryType, Integer>() {
        {
            {
                put(RentCostEntryType.utility,
                        android.graphics.Color.rgb(153, 0, 0));
                put(RentCostEntryType.rent,
                        android.graphics.Color.rgb(0, 0, 153));
                put(RentCostEntryType.upfront,
                        android.graphics.Color.rgb(0, 102, 0));
            }
        }
    };
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_HOLE_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_HOLE_TEXT_SIZE = 30;
    private static final int DEFAULT_HOLE_COLOR = Color.BLACK;
    private RentPriceResponse currentPriceDetails;
    private PieChart chart;

    public RentTotalCostOverviewFragment() {
        // Required empty public constructor
    }

    public static RentTotalCostOverviewFragment newInstance(RentPriceResponse currentPriceDetails) {
        RentTotalCostOverviewFragment fragment = new RentTotalCostOverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_PRICE_DETAILS, currentPriceDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentPriceDetails = (RentPriceResponse) getArguments().getSerializable(ARG_CURRENT_PRICE_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_rent_total_cost_overview, container, false);

        SwitchMaterial firstMonthSwitch = mView.findViewById(R.id.first_month_switch);
        firstMonthSwitch.setOnCheckedChangeListener(this);

        this.chart = mView.findViewById(R.id.rent_total_cost_overview_chart);
        updateChart(chart, firstMonthSwitch.isChecked());

        return mView;
    }

    private void styleChartDataset(PieChart chart, ArrayList<RentCostEntry> rentCosts) {
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        ArrayList<Integer> pieColors = new ArrayList<Integer>();

        for (RentCostEntry rentCost : rentCosts) {
            // create a new pie entry
            PieEntry pieEntry = new PieEntry(rentCost.getPriceMean(), rentCost.getLabel());
            pieEntries.add(pieEntry);
            pieColors.add(costColorMap.get(rentCost.getType()));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, ""); // add entries to dataset
        pieDataSet.setDrawValues(true);
        pieDataSet.setValueTextColor(DEFAULT_TEXT_COLOR);
        pieDataSet.setValueTextSize(DEFAULT_TEXT_SIZE);
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return RentPriceValueFormatter.getPriceWithPeriodAsString((int) value, RentPricePeriodEnum.month);
            }
        });
        pieDataSet.setColors(pieColors);

        PieData pieData = new PieData(pieDataSet);
        chart.setData(pieData);
    }

    private void styleDescription(PieChart chart, ArrayList<RentCostEntry> rentCosts) {
        Description description = chart.getDescription();
        description.setEnabled(true);
        description.setTextColor(DEFAULT_TEXT_COLOR);
        description.setTextSize(DEFAULT_TEXT_SIZE);

        StringBuilder descriptionText = new StringBuilder();
        descriptionText.append(getString(R.string.total_rent_cost_for));
        descriptionText.append(" ");
        descriptionText.append(currentPriceDetails.getPropertyType());
        if (currentPriceDetails.getBedrooms() > 0) {
            descriptionText.append(" ");
            descriptionText.append(getString(R.string.with));
            descriptionText.append(" ");
            descriptionText.append(String.valueOf(currentPriceDetails.getBedrooms()));
            descriptionText.append(" ");
            if (currentPriceDetails.getBedrooms() > 1) {
                descriptionText.append(getString(R.string.bedrooms));
            } else {

                descriptionText.append(getString(R.string.bedroom));
            }

        }
        description.setText(descriptionText.toString());
    }

    private void styleHole(PieChart chart, ArrayList<RentCostEntry> rentCosts) {
        int totalCost = rentCosts.stream().mapToInt(o -> o.getPriceMean()).sum();
        chart.setCenterTextColor(DEFAULT_HOLE_TEXT_COLOR);
        chart.setCenterTextSize(DEFAULT_HOLE_TEXT_SIZE);
        chart.setHoleColor(DEFAULT_HOLE_COLOR);
        chart.setCenterText(String.valueOf(RentPriceValueFormatter.getPriceWithPeriodAsString(totalCost, RentPricePeriodEnum.month)));
    }

    private void styleLegend(PieChart chart, ArrayList<RentCostEntry> rentCosts) {
        Legend legend = chart.getLegend();
        legend.setTextColor(DEFAULT_TEXT_COLOR);
        legend.setTextSize(DEFAULT_TEXT_SIZE);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setYEntrySpace(10f);
        legend.setWordWrapEnabled(true);
        legend.setEnabled(true);

        // create list of distinct legend entries
        Map<String, LegendEntry> legendEntryMap = new HashMap<>();

        rentCosts.forEach((rentCost) -> {
            if (!legendEntryMap.containsKey(rentCost.getType().getDisplayName())) {
                legendEntryMap.put(rentCost.getType().getDisplayName(),
                        new LegendEntry(rentCost.getType().getDisplayName(),
                                Legend.LegendForm.DEFAULT, DEFAULT_TEXT_SIZE,
                                2f,
                                null,
                                costColorMap.get(rentCost.getType())));
            }
        });
        legend.setCustom(legendEntryMap.values().toArray(new LegendEntry[0]));
    }

    private void styleMarker(PieChart chart, ArrayList<RentCostEntry> rentCosts) {
        RentTotalCostMarkerView mv = new RentTotalCostMarkerView(this.getContext(), R.layout.layout_custom_marker_view, rentCosts);
        mv.setChartView(chart);
        chart.setMarker(mv);
    }

    private void styleChart(PieChart chart, ArrayList<RentCostEntry> rentCosts) {
        chart.setDrawHoleEnabled(true);
        chart.setDrawCenterText(true);
        chart.setDrawEntryLabels(false);
        chart.setDrawRoundedSlices(false);
        chart.setHighlightPerTapEnabled(true);
    }

    private void updateChart(PieChart chart, boolean withUpfrontCosts) {
        ArrayList<RentCostEntry> rentCosts = RentTotalCostPreprocessor.getRentTotalCostEntries(this.currentPriceDetails, withUpfrontCosts, this.getContext());

        styleChartDataset(chart, rentCosts);
        styleMarker(chart, rentCosts);
        styleHole(chart, rentCosts);
        styleLegend(chart, rentCosts);
        styleDescription(chart, rentCosts);
        styleChart(chart, rentCosts);
        chart.notifyDataSetChanged();
        chart.invalidate(); // refresh
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // hide visible marker, if any
        this.chart.highlightValue(null);

        // update chart with new upfront cost visibility
        updateChart(this.chart, isChecked);
    }
}