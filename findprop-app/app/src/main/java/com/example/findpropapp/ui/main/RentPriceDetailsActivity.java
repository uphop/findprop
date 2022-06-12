package com.example.findpropapp.ui.main;

import android.os.Bundle;

import com.example.findpropapp.R;
import com.example.findpropapp.model.RentPriceResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import com.example.findpropapp.databinding.ActivityRentPriceDetailsBinding;

public class RentPriceDetailsActivity extends AppCompatActivity {

    private ActivityRentPriceDetailsBinding binding;

    private static final String ARG_CURRENT_PRICE_DETAILS = "CURRENT_PRICE_DETAILS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRentPriceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RentPriceResponse currentPriceDetails = (RentPriceResponse) extras.getSerializable(ARG_CURRENT_PRICE_DETAILS);
            updateTitle(currentPriceDetails);
            sectionsPagerAdapter.setRentDetails(currentPriceDetails);
        }
    }

    private void updateTitle(RentPriceResponse currentPriceDetails) {
        if (currentPriceDetails != null) {
            String titleText = "Renting near " + currentPriceDetails.getPostcodeDetails().getPostcode() + ", " + currentPriceDetails.getLocalAuthorityDetails().getLocalAuthority();
            TextView titleView = (TextView) findViewById(R.id.title);
            titleView.setText(titleText);
        }
    }
}