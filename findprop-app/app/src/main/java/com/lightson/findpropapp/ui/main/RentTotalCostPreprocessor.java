package com.lightson.findpropapp.ui.main;

import android.content.Context;
import android.content.res.Resources;

import com.lightson.findpropapp.R;
import com.lightson.findpropapp.model.RentPricePeriodEnum;
import com.lightson.findpropapp.model.RentPriceResponse;
import com.lightson.findpropapp.model.UpfrontPriceCompositeDetails;
import com.lightson.findpropapp.model.UpfrontPriceDetails;
import com.lightson.findpropapp.model.UtilityPriceDetails;
import com.lightson.findpropapp.model.UtilityPriceLocalAuthorityDetails;

import java.util.ArrayList;
import java.util.List;

public class RentTotalCostPreprocessor {
    private static final String TAG = RentTotalCostOverviewFragment.class.getSimpleName();

    public static ArrayList<RentCostEntry> getRentTotalCostEntries(RentPriceResponse currentPriceDetails, boolean withUpfrontCosts, Context context) {
        ArrayList<RentCostEntry> rentCosts = new ArrayList<RentCostEntry>();
        if (currentPriceDetails == null) {
            return rentCosts;
        }

        addUtilityCosts(currentPriceDetails, rentCosts, context);
        addRentCosts(currentPriceDetails, rentCosts, context);
        if (withUpfrontCosts) {
            addUpfrontCosts(currentPriceDetails, rentCosts, context);
        }

        return rentCosts;
    }

    private static void addUtilityCosts(RentPriceResponse currentPriceDetails, ArrayList<RentCostEntry> rentCosts, Context context) {
        // set utility costs
        UtilityPriceLocalAuthorityDetails utilityCosts = currentPriceDetails.getUtilityDetails();
        if (utilityCosts != null) {
            // aggregate all utility costs except council tax under single cost entry
            Resources resources = context.getResources();
            StringBuilder utilityDescription = new StringBuilder();
            utilityDescription.append(resources.getString(R.string.your_expected_utility_costs));
            utilityDescription.append(" ");
            utilityDescription.append(System.getProperty("line.separator"));

            int totalCost = 0;
            List<UtilityPriceDetails> utilityPriceDetails = utilityCosts.getPrice();
            for (int i = 0; i < utilityPriceDetails.size(); i++) {
                UtilityPriceDetails utilityPriceDetail = utilityPriceDetails.get(i);
                int pcmCost = RentPriceValueFormatter.getUtilityCostPCM(utilityPriceDetail);

                totalCost += pcmCost;
                utilityDescription.append(" - ");
                utilityDescription.append(utilityPriceDetail.getUtilityType().getDisplayName());
                utilityDescription.append(" (");
                utilityDescription.append(RentPriceValueFormatter.getPriceWithPeriodAsString(pcmCost, RentPricePeriodEnum.month));
                utilityDescription.append(")");
                if (i < utilityPriceDetails.size() - 1) {
                    utilityDescription.append(", ");
                    utilityDescription.append(System.getProperty("line.separator"));
                }
            }

            RentCostEntry utilityCostEntry = new RentCostEntry();
            utilityCostEntry.setPriceMean(totalCost);
            utilityCostEntry.setLabel(RentCostEntryType.utility.getDisplayName());
            utilityCostEntry.setType(RentCostEntryType.utility);
            utilityCostEntry.setDescription(utilityDescription.toString());
            rentCosts.add(utilityCostEntry);
        }
    }

    private static void addRentCosts(RentPriceResponse currentPriceDetails, ArrayList<RentCostEntry> rentCosts, Context context) {
        // set rent price - take postcode area rent, if available; otherwise, local authority rent
        int rentPrice = currentPriceDetails.getPostcodeAreaDetails() != null ?
                currentPriceDetails.getPostcodeAreaDetails().getPrice().getPriceMean() :
                currentPriceDetails.getLocalAuthorityDetails().getPrice().getPriceMean();

        Resources resources = context.getResources();
        StringBuilder rentDescription = new StringBuilder();
        rentDescription.append(resources.getString(R.string.typical_rent_is));
        rentDescription.append(" ");
        rentDescription.append(RentPriceValueFormatter.getPriceWithPeriodAsString(rentPrice, RentPricePeriodEnum.month));

        rentCosts.add(new RentCostEntry(
                rentPrice,
                RentCostEntryType.rent.getDisplayName(),
                RentCostEntryType.rent, rentDescription.toString()));
    }

    private static void addUpfrontCosts(RentPriceResponse currentPriceDetails, ArrayList<RentCostEntry> rentCosts, Context context) {
        // set upfront costs
        UpfrontPriceCompositeDetails upfrontCosts = currentPriceDetails.getUpfrontDetails();
        if (upfrontCosts != null) {
            Resources resources = context.getResources();
            StringBuilder upfrontDescription = new StringBuilder();
            upfrontDescription.append(resources.getString(R.string.your_expected_upfront_costs));
            upfrontDescription.append(" ");
            upfrontDescription.append(System.getProperty("line.separator"));

            int totalCost = 0;
            List<UpfrontPriceDetails> upfrontPriceDetails = upfrontCosts.getPrice();
            for (int i = 0; i < upfrontPriceDetails.size(); i++) {
                UpfrontPriceDetails upfrontPriceDetail = upfrontPriceDetails.get(i);
                totalCost += upfrontPriceDetail.getPriceMean();

                upfrontDescription.append(" - ");
                upfrontDescription.append(upfrontPriceDetail.getUpfrontFeeType().getDisplayName());
                upfrontDescription.append(" (");
                upfrontDescription.append(RentPriceValueFormatter.getPriceWithPeriodAsString(upfrontPriceDetail.getPriceMean(), RentPricePeriodEnum.one_off));
                upfrontDescription.append(")");
                if (i < upfrontPriceDetails.size() - 1) {
                    upfrontDescription.append(", ");
                    upfrontDescription.append(System.getProperty("line.separator"));
                }
            }

            RentCostEntry upfrontCostEntry = new RentCostEntry();
            upfrontCostEntry.setPriceMean(totalCost);
            upfrontCostEntry.setLabel(RentCostEntryType.upfront.getDisplayName());
            upfrontCostEntry.setType(RentCostEntryType.upfront);
            upfrontCostEntry.setDescription(upfrontDescription.toString());
            rentCosts.add(upfrontCostEntry);
        }
    }

}
