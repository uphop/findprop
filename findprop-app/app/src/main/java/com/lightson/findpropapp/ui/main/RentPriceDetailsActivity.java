package com.lightson.findpropapp.ui.main;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lightson.findpropapp.DefaultMapsActivity;
import com.lightson.findpropapp.R;
import com.lightson.findpropapp.databinding.ActivityRentPriceDetailsBinding;
import com.lightson.findpropapp.model.RentPriceResponse;

public class RentPriceDetailsActivity extends AppCompatActivity {

    private static final String ARG_CURRENT_PRICE_DETAILS = "CURRENT_PRICE_DETAILS";
    private ActivityRentPriceDetailsBinding binding;

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
            StringBuilder titleText = new StringBuilder();
            titleText.append(getString(R.string.renting_near));
            
            titleText.append(" ");
            if (currentPriceDetails.getPostcodeDetails() != null) {
                titleText.append(currentPriceDetails.getPostcodeDetails().getPostcode());
                titleText.append(", ");
            }
            titleText.append(currentPriceDetails.getLocalAuthorityDetails().getLocalAuthority());

            TextView titleView = (TextView) findViewById(R.id.title);
            titleView.setText(titleText.toString());
        }
    }
}