package com.example.findpropapp.ui.main;

import com.example.findpropapp.model.RentPriceLocalAuthorityDetails;
import com.example.findpropapp.model.RentPricePeriodEnum;
import com.example.findpropapp.model.RentPricePostcodeAreaDetails;
import com.example.findpropapp.model.RentPriceResponse;
import com.example.findpropapp.model.UpfrontPriceDetails;
import com.example.findpropapp.model.UpfrontPriceLocalAuthorityDetails;
import com.example.findpropapp.model.UtilityFeeEnum;
import com.example.findpropapp.model.UtilityPriceDetails;
import com.example.findpropapp.model.UtilityPriceLocalAuthorityDetails;

import java.util.ArrayList;
import java.util.List;

public class RentTotalCostPreprocessor {
    private static final String DEFAULT_RENT_PRICE_DISPLAY_NAME = "Monthly rent";

    public static ArrayList<RentCostEntry> getRentTotalCostEntries(RentPriceResponse currentPriceDetails) {
        ArrayList<RentCostEntry> rentCosts = new ArrayList<RentCostEntry>();
        if (currentPriceDetails == null) {
            return rentCosts;
        }

        // set utility costs
        UtilityPriceLocalAuthorityDetails utilityCosts = currentPriceDetails.getUtilityDetails();
        if (utilityCosts != null) {
            // aggregate all utility costs except council tax under single cost entry
            int totalCost = 0;
            StringBuilder label = new StringBuilder();
            List<UtilityPriceDetails> utilityPriceDetails = utilityCosts.getPrice();
            for(int i = 0; i < utilityPriceDetails.size(); i++) {
                UtilityPriceDetails utilityPriceDetail = utilityPriceDetails.get(i);
                int pcmCost = RentPriceValueFormatter.getUtilityCostPCM(utilityPriceDetail);

                // exclude council tax into separate cost category
                if(utilityPriceDetail.getUtilityType() == UtilityFeeEnum.council_tax) {
                    RentCostEntry councilTaxCostEntry = new RentCostEntry();
                    councilTaxCostEntry.setPriceMean(pcmCost);
                    councilTaxCostEntry.setLabel(RentCostEntryType.council_tax.getDisplayName());
                    councilTaxCostEntry.setType(RentCostEntryType.council_tax);
                    rentCosts.add(councilTaxCostEntry);
                } else {
                    totalCost += pcmCost;
                    label.append(utilityPriceDetail.getUtilityType().getDisplayName());
                    label.append(" (");
                    label.append(RentPriceValueFormatter.getPriceWithPeriodAsString(pcmCost, RentPricePeriodEnum.month));
                    label.append(") ");
                    if(i < utilityPriceDetails.size() - 1) {
                        label.append(",");
                    }
                }
            }

            RentCostEntry utilityCostEntry = new RentCostEntry();
            utilityCostEntry.setPriceMean(totalCost);
            utilityCostEntry.setLabel(RentCostEntryType.utility.getDisplayName());
            utilityCostEntry.setType(RentCostEntryType.utility);
            rentCosts.add(utilityCostEntry);
        }

        // set rent price - take postcode area rent, if available; otherwise, local authority rent
        int rentPrice = currentPriceDetails.getPostcodeAreaDetails() != null ?
                currentPriceDetails.getPostcodeAreaDetails().getPrice().getPriceMean() :
                currentPriceDetails.getLocalAuthorityDetails().getPrice().getPriceMean();
        rentCosts.add(new RentCostEntry(
                rentPrice,
                RentCostEntryType.rent.getDisplayName(),
                RentCostEntryType.rent));

        // set upfront costs
        UpfrontPriceLocalAuthorityDetails upfrontCosts = currentPriceDetails.getUpfrontDetails();
        if (upfrontCosts != null) {
            for (UpfrontPriceDetails upfrontCost : upfrontCosts.getPrice()) {
                rentCosts.add(new RentCostEntry(
                        upfrontCost.getPriceMean(),
                        upfrontCost.getUpfrontFeeType().getDisplayName(),
                        RentCostEntryType.upfront));
            }
        }

        return rentCosts;
    }
}
