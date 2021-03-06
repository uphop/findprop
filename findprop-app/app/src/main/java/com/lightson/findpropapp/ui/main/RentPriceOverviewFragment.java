package com.lightson.findpropapp.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.lightson.findpropapp.R;
import com.lightson.findpropapp.model.RentPriceResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentPriceOverviewFragment extends Fragment {
    private static final String TAG = RentPriceOverviewFragment.class.getSimpleName();
    private LogHelper logHelper;

    private static final String ARG_CURRENT_PRICE_DETAILS = "CURRENT_PRICE_DETAILS";
    private static final int DEFAULT_LOW_BAR_COLOR = Color.valueOf(0, 0, 0, 0).toArgb();
    private static final Map<RentPriceEntryType, int[]> priceEntryColorMap = new HashMap<RentPriceEntryType, int[]>() {
        {
            {
                put(RentPriceEntryType.region, new int[]{
                        DEFAULT_LOW_BAR_COLOR,
                        android.graphics.Color.rgb(153, 0, 153)
                });
                put(RentPriceEntryType.local_authority, new int[]{
                        DEFAULT_LOW_BAR_COLOR,
                        android.graphics.Color.rgb(0, 0, 153)
                });
                put(RentPriceEntryType.related_local_authority, new int[]{
                        DEFAULT_LOW_BAR_COLOR,
                        android.graphics.Color.rgb(0, 153, 153)
                });
                put(RentPriceEntryType.similar_local_authority, new int[]{
                        DEFAULT_LOW_BAR_COLOR,
                        android.graphics.Color.rgb(0, 153, 0)
                });
                put(RentPriceEntryType.postcode_area, new int[]{
                        DEFAULT_LOW_BAR_COLOR,
                        android.graphics.Color.rgb(153, 153, 0)
                });
                put(RentPriceEntryType.postcode, new int[]{
                        DEFAULT_LOW_BAR_COLOR,
                        android.graphics.Color.rgb(153, 0, 0)
                });
            }
        }
    };
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final float DEFAULT_LEGEND_LINE_WIDTH = 2f;
    private static final float DEFAULT_PRICE_OFFSET = 250f;
    private static final float DEFAULT_SPACE_OFFSET = 0.85f;
    private static final float DEFAULT_LABEL_ROTATION = -30f;
    private RentPriceResponse currentPriceDetails;

    public RentPriceOverviewFragment() {
        // Required empty public constructor
    }

    public static RentPriceOverviewFragment newInstance(RentPriceResponse currentAnchorDetails) {
        RentPriceOverviewFragment fragment = new RentPriceOverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_PRICE_DETAILS, currentAnchorDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentPriceDetails = (RentPriceResponse) getArguments().getSerializable(ARG_CURRENT_PRICE_DETAILS);
        }

        logHelper = new LogHelper(TAG, this.getContext());
        logHelper.logEvent(UsageEventEnum.rent_prices_overview_started);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_rent_price_overview, container, false);
        updateChart(mView);
        return mView;
    }

    private void styleChart(CombinedChart chart) {
        // general chart look&feel
        chart.setTouchEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDrawValueAboveBar(true);
        chart.setDrawBorders(false);
        chart.setAutoScaleMinMaxEnabled(true);
    }

    private void styleDescription(CombinedChart chart, ArrayList<RentPriceEntry> rentPrices) {
        Description description = chart.getDescription();
        description.setEnabled(true);
        description.setTextColor(DEFAULT_TEXT_COLOR);
        description.setTextSize(DEFAULT_TEXT_SIZE);

        StringBuilder descriptionText = new StringBuilder();
        descriptionText.append(getString(R.string.rent_price_for));
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

    private void styleMarker(CombinedChart chart, ArrayList<RentPriceEntry> rentPrices) {
        RentPriceOverviewMarkerView mv = new RentPriceOverviewMarkerView(this.getContext(), R.layout.layout_custom_marker_view, rentPrices);
        mv.setChartView(chart);
        chart.setMarker(mv);
    }

    private void styleLegend(CombinedChart chart, ArrayList<RentPriceEntry> rentPrices) {
        Legend legend = chart.getLegend();
        legend.setYEntrySpace(10f);
        legend.setWordWrapEnabled(true);
        legend.setTextColor(DEFAULT_TEXT_COLOR);
        legend.setTextSize(DEFAULT_TEXT_SIZE);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);

        // create list of distinct legend entries
        LegendEntry[] legendEntries = new LegendEntry[5];
        legendEntries[0] = new LegendEntry(RentPriceEntryType.postcode.getDisplayName(), Legend.LegendForm.DEFAULT, DEFAULT_TEXT_SIZE, DEFAULT_LEGEND_LINE_WIDTH, null, priceEntryColorMap.get(RentPriceEntryType.postcode)[1]);
        legendEntries[1] = new LegendEntry(RentPriceEntryType.postcode_area.getDisplayName(), Legend.LegendForm.DEFAULT, DEFAULT_TEXT_SIZE, DEFAULT_LEGEND_LINE_WIDTH, null, priceEntryColorMap.get(RentPriceEntryType.postcode_area)[1]);
        legendEntries[2] = new LegendEntry(RentPriceEntryType.local_authority.getDisplayName(), Legend.LegendForm.DEFAULT, DEFAULT_TEXT_SIZE, DEFAULT_LEGEND_LINE_WIDTH, null, priceEntryColorMap.get(RentPriceEntryType.local_authority)[1]);
        legendEntries[3] = new LegendEntry(RentPriceEntryType.similar_local_authority.getDisplayName(), Legend.LegendForm.DEFAULT, DEFAULT_TEXT_SIZE, DEFAULT_LEGEND_LINE_WIDTH, null, priceEntryColorMap.get(RentPriceEntryType.similar_local_authority)[1]);
        legendEntries[4] = new LegendEntry(RentPriceEntryType.region.getDisplayName(), Legend.LegendForm.DEFAULT, DEFAULT_TEXT_SIZE, DEFAULT_LEGEND_LINE_WIDTH, null, priceEntryColorMap.get(RentPriceEntryType.region)[1]);

        legend.setCustom(legendEntries);
    }

    private void styleXAxis(CombinedChart chart, ArrayList<RentPriceEntry> rentPrices) {
        // style X axis / reference rent price categories
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(DEFAULT_TEXT_SIZE);
        xAxis.setTextColor(DEFAULT_TEXT_COLOR);
        xAxis.setLabelRotationAngle(DEFAULT_LABEL_ROTATION);

        List<String> valueLabels = new ArrayList<>();
        rentPrices.forEach((rentPrice) -> {
            valueLabels.add(rentPrice.getLabel());
        });
        xAxis.setValueFormatter(new IndexAxisValueFormatter(valueLabels));

        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);

        xAxis.setSpaceMin(chart.getBarData().getBarWidth() / DEFAULT_SPACE_OFFSET);
        xAxis.setSpaceMax(chart.getBarData().getBarWidth() / DEFAULT_SPACE_OFFSET);
    }

    private void styleYAxis(CombinedChart chart, ArrayList<RentPriceEntry> rentPrices) {
        // style Y axis / rent price
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawZeroLine(false);
        yAxisLeft.setDrawTopYLabelEntry(false);
        yAxisLeft.setDrawLabels(false);
        yAxisLeft.setDrawAxisLine(false);

        // set Y axis min/max
        ArrayList<RentPriceEntry> rentPricesClone = (ArrayList<RentPriceEntry>) rentPrices.clone();
        Collections.sort(rentPricesClone, Comparator.comparingInt(RentPriceEntry::getPriceLow));
        float minY = (float) rentPricesClone.get(0).getPriceLow() - DEFAULT_PRICE_OFFSET;

        Collections.sort(rentPricesClone, Comparator.comparingInt(RentPriceEntry::getPriceHigh));
        float maxY = (float) rentPricesClone.get(rentPricesClone.size() - 1).getPriceHigh() + DEFAULT_PRICE_OFFSET;

        yAxisLeft.setAxisMinimum(minY);
        yAxisLeft.setAxisMaximum(maxY);
    }

    private void styleChartDataset(CombinedChart chart, ArrayList<RentPriceEntry> rentPrices) {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        ArrayList<Integer> barColors = new ArrayList<Integer>();
        ArrayList<Entry> lineEntries = new ArrayList<Entry>();

        // convert rent prices to bar / line chart entries
        for (int i = 0; i < rentPrices.size(); i++) {
            // create a new stacked bar entry
            BarEntry barEntry = new BarEntry(i,
                    new float[]{rentPrices.get(i).getPriceLow(),
                            rentPrices.get(i).getPriceHigh() - rentPrices.get(i).getPriceLow()});
            barEntries.add(barEntry);


            // add two color instances for each stacked bar entry
            barColors.add(priceEntryColorMap.get(rentPrices.get(i).getType())[0]);
            barColors.add(priceEntryColorMap.get(rentPrices.get(i).getType())[1]);

            Entry lineEntry = new Entry(i, (float) rentPrices.get(i).getPriceMean());
            lineEntries.add(lineEntry);
        }

        // prepare stacked bar chart for min / max values
        BarDataSet barDataSet = new BarDataSet(barEntries, "Bar DataSet"); // add entries to dataset
        barDataSet.setValueTextColor(DEFAULT_TEXT_COLOR); // styling, ...
        barDataSet.setValueTextSize(DEFAULT_TEXT_SIZE);
        barDataSet.setColors(barColors);
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarStackedLabel(float value, BarEntry stackedEntry) {
                float[] barEntryVals = stackedEntry.getYVals();
                float adjustedBarEntryValue = value == barEntryVals[0] ? barEntryVals[0] : barEntryVals[0] + barEntryVals[1];
                return RentPriceValueFormatter.getPriceWithPeriodAsString((int) adjustedBarEntryValue,
                        currentPriceDetails.getLocalAuthorityDetails().getPrice().getPeriod());
            }
        });
        BarData barData = new BarData(barDataSet);

        // prepare lien chart for average values
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Line DataSet");
        lineDataSet.setColor(DEFAULT_TEXT_COLOR);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleColor(DEFAULT_TEXT_COLOR);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setFillColor(DEFAULT_TEXT_COLOR);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(DEFAULT_TEXT_SIZE);
        lineDataSet.setValueTextColor(DEFAULT_TEXT_COLOR);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return RentPriceValueFormatter.getPriceWithPeriodAsString((int) value,
                        currentPriceDetails.getLocalAuthorityDetails().getPrice().getPeriod());
            }
        });
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawHorizontalHighlightIndicator(true);
        LineData lineData = new LineData(lineDataSet);

        // compile hybrid chart of bar / line charts, to show both min / max and average rent prices
        CombinedData data = new CombinedData();
        data.setData(barData);
        data.setData(lineData);
        chart.setData(data);
    }

    private void setListeners(CombinedChart chart, ArrayList<RentPriceEntry> rentPrices) {
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void updateChart(View mView) {
        ArrayList<RentPriceEntry> rentPrices = RentPriceOverviewPreprocessor.getRentPriceEntries(this.currentPriceDetails, this.getContext());

        CombinedChart chart = mView.findViewById(R.id.rent_price_overview_chart);
        styleChartDataset(chart, rentPrices);
        styleMarker(chart, rentPrices);
        styleXAxis(chart, rentPrices);
        styleYAxis(chart, rentPrices);
        styleLegend(chart, rentPrices);
        styleDescription(chart, rentPrices);
        styleChart(chart);
        setListeners(chart, rentPrices);
        chart.invalidate(); // refresh


    }
}