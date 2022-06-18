package com.example.findpropapp.ui.main;

import android.util.Log;

import com.example.findpropapp.model.RentPricePeriodEnum;
import com.example.findpropapp.model.RentPriceResponse;
import com.example.findpropapp.model.UpfrontPriceDetails;
import com.example.findpropapp.model.UpfrontPriceLocalAuthorityDetails;
import com.example.findpropapp.model.UtilityPriceDetails;
import com.example.findpropapp.model.UtilityPriceLocalAuthorityDetails;

import java.util.ArrayList;
import java.util.List;

public class RentTotalCostPreprocessor {
    private static final String TAG = RentTotalCostOverviewFragment.class.getSimpleName();

    public static ArrayList<RentCostEntry> getRentTotalCostEntries(RentPriceResponse currentPriceDetails, boolean withUpfrontCosts) {
        ArrayList<RentCostEntry> rentCosts = new ArrayList<RentCostEntry>();
        if (currentPriceDetails == null) {
            return rentCosts;
        }

        addUtilityCosts(currentPriceDetails, rentCosts);
        addRentCosts(currentPriceDetails, rentCosts);
        if (withUpfrontCosts) {
            addUpfrontCosts(currentPriceDetails, rentCosts);
        }

        return rentCosts;
    }

    private static void addUtilityCosts(RentPriceResponse currentPriceDetails, ArrayList<RentCostEntry> rentCosts) {
        // set utility costs
        UtilityPriceLocalAuthorityDetails utilityCosts = currentPriceDetails.getUtilityDetails();
        if (utilityCosts != null) {
            // aggregate all utility costs except council tax under single cost entry

            StringBuilder utilityDescription = new StringBuilder();
            utilityDescription.append("Your expected monthly utility costs: ");
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

    private static void addRentCosts(RentPriceResponse currentPriceDetails, ArrayList<RentCostEntry> rentCosts) {
        // set rent price - take postcode area rent, if available; otherwise, local authority rent
        int rentPrice = currentPriceDetails.getPostcodeAreaDetails() != null ?
                currentPriceDetails.getPostcodeAreaDetails().getPrice().getPriceMean() :
                currentPriceDetails.getLocalAuthorityDetails().getPrice().getPriceMean();

        StringBuilder rentDescription = new StringBuilder();
        rentDescription.append("Typical rent is ");
        rentDescription.append(RentPriceValueFormatter.getPriceWithPeriodAsString(rentPrice, RentPricePeriodEnum.month));

        rentCosts.add(new RentCostEntry(
                rentPrice,
                RentCostEntryType.rent.getDisplayName(),
                RentCostEntryType.rent, rentDescription.toString()));
    }

    private static void addUpfrontCosts(RentPriceResponse currentPriceDetails, ArrayList<RentCostEntry> rentCosts) {
        // set upfront costs
        UpfrontPriceLocalAuthorityDetails upfrontCosts = currentPriceDetails.getUpfrontDetails();
        if (upfrontCosts != null) {
            StringBuilder upfrontDescription = new StringBuilder();
            upfrontDescription.append("Your expected one-off upfront costs: ");
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
