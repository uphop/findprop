package com.example.findpropapp.ui.main;

import com.example.findpropapp.model.RentPriceResponse;
import com.example.findpropapp.model.UpfrontPriceDetails;
import com.example.findpropapp.model.UpfrontPriceLocalAuthorityDetails;
import com.example.findpropapp.model.UtilityPriceDetails;
import com.example.findpropapp.model.UtilityPriceLocalAuthorityDetails;

import java.util.ArrayList;

public class RentTotalCostHelper {
    public static ArrayList<RentCostEntry> getRentTotalCostEntries(RentPriceResponse currentPriceDetails) {
        ArrayList<RentCostEntry> rentCosts = new ArrayList<RentCostEntry>();
        if (currentPriceDetails == null) {
            return rentCosts;
        }

        // set utility costs
        UtilityPriceLocalAuthorityDetails utilityCosts = currentPriceDetails.getUtilityDetails();
        if(utilityCosts != null) {
            for(UtilityPriceDetails utilityCost : utilityCosts.getPrice()) {
                rentCosts.add(new RentCostEntry(utilityCost.getPriceMean(), utilityCost.getUtilityType().getDisplayName(), RentCostEntryType.utility, utilityCost.getUtilityType()));
            }
        }

        // set upfront costs
        UpfrontPriceLocalAuthorityDetails upfrontCosts = currentPriceDetails.getUpfrontDetails();
        if(upfrontCosts != null) {
            for(UpfrontPriceDetails upfrontCost : upfrontCosts.getPrice()) {
                rentCosts.add(new RentCostEntry(upfrontCost.getPriceMean(), upfrontCost.getUpfrontFeeType().getDisplayName(), RentCostEntryType.upfront, upfrontCost.getUpfrontFeeType()));
            }
        }

        return rentCosts;
    }
}
