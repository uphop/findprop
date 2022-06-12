package com.example.findpropapp.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findpropapp.R;
import com.example.findpropapp.model.RentPriceResponse;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RentMarketOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RentMarketOverviewFragment extends Fragment {
    private static final String ARG_CURRENT_PRICE_DETAILS = "CURRENT_PRICE_DETAILS";
    private RentPriceResponse currentPriceDetails;

    public RentMarketOverviewFragment() {
        // Required empty public constructor
    }

    public static RentMarketOverviewFragment newInstance(RentPriceResponse currentPriceDetails) {
        RentMarketOverviewFragment fragment = new RentMarketOverviewFragment();
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

        View mView = inflater.inflate(R.layout.fragment_rent_market_overview, container, false);
        // updatePriceRange(mView);
        updateChart(mView);
        return mView;
    }

    private void updateChart(View mView) {

    }
}