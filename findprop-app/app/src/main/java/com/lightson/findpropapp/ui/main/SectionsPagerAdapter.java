package com.lightson.findpropapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lightson.findpropapp.R;
import com.lightson.findpropapp.model.RentPriceResponse;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private RentPriceResponse currentPriceDetails;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public void setRentDetails(RentPriceResponse currentPriceDetails) {
        this.currentPriceDetails = currentPriceDetails;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RentPriceOverviewFragment.newInstance(currentPriceDetails);
            case 1:
                return RentTotalCostOverviewFragment.newInstance(currentPriceDetails);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}