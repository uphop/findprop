package com.example.findpropapp.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.findpropapp.R;
import com.example.findpropapp.model.RentPriceResponse;
import com.example.findpropapp.model.UpfrontFeeEnum;
import com.example.findpropapp.model.UtilityFeeEnum;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RentTotalCostOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RentTotalCostOverviewFragment extends Fragment {
    private static final String TAG = RentTotalCostOverviewFragment.class.getSimpleName();
    private static final String ARG_CURRENT_PRICE_DETAILS = "CURRENT_PRICE_DETAILS";
    private static final Map<UtilityFeeEnum, Integer> priceUtilityCostColorMap = new HashMap<UtilityFeeEnum, Integer>() {
        {
            {
                put(UtilityFeeEnum.energy,
                        android.graphics.Color.rgb(153, 0, 0));
                put(UtilityFeeEnum.council_tax,
                        android.graphics.Color.rgb(0, 0, 153));
                put(UtilityFeeEnum.internet,
                        android.graphics.Color.rgb(0, 102, 0));
                put(UtilityFeeEnum.tv_license,
                        android.graphics.Color.rgb(102, 102, 102));
                put(UtilityFeeEnum.water,
                        android.graphics.Color.rgb(255, 204, 0));
            }
        }
    };
    private static final Map<UpfrontFeeEnum, Integer> priceUpfrontCostColorMap = new HashMap<UpfrontFeeEnum, Integer>() {
        {
            {
                put(UpfrontFeeEnum.holding_deposit,
                        android.graphics.Color.rgb(255, 102, 0));
                put(UpfrontFeeEnum.tenancy_deposit,
                        android.graphics.Color.rgb(102, 0, 153));

            }
        }
    };
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_SIZE = 10;
    private RentPriceResponse currentPriceDetails;

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
        updateChart(mView);
        return mView;
    }

    private void styleChartDataset(PieChart chart, ArrayList<RentCostEntry> rentCosts) {
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        ArrayList<Integer> pieColors = new ArrayList<Integer>();

        for (RentCostEntry rentCost : rentCosts) {
            // create a new pie entry
            Log.e(TAG, "Pie Entry: " + rentCost.toString());
            PieEntry pieEntry = new PieEntry(rentCost.getPriceMean(), rentCost.getLabel());
            pieEntries.add(pieEntry);

            switch (rentCost.getType()) {
                case upfront:
                    pieColors.add(priceUpfrontCostColorMap.get(rentCost.getUpfrontFee()));
                    break;
                case utility:
                    pieColors.add(priceUtilityCostColorMap.get(rentCost.getUtilityFee()));
                    break;
            }
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Pie DataSet"); // add entries to dataset
        pieDataSet.setValueTextColor(DEFAULT_TEXT_COLOR); // styling, ...
        pieDataSet.setValueTextSize(DEFAULT_TEXT_SIZE);
        pieDataSet.setColors(pieColors);

        PieData pieData = new PieData(pieDataSet);
        chart.setData(pieData);
    }

    private void updateChart(View mView) {
        ArrayList<RentCostEntry> rentPrices = RentTotalCostHelper.getRentTotalCostEntries(this.currentPriceDetails);

        PieChart chart = mView.findViewById(R.id.rent_total_cost_overview_chart);
        styleChartDataset(chart, rentPrices);

        chart.invalidate(); // refresh
    }
}