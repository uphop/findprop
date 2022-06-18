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

            StringBuilder utilityDescription = new StringBuilder();
            utilityDescription.append("Your expected monthly utility costs: ");
            utilityDescription.append(System.getProperty("line.separator"));

            int totalCost = 0;
            List<UtilityPriceDetails> utilityPriceDetails = utilityCosts.getPrice();
            for(int i = 0; i < utilityPriceDetails.size(); i++) {
                UtilityPriceDetails utilityPriceDetail = utilityPriceDetails.get(i);
                int pcmCost = RentPriceValueFormatter.getUtilityCostPCM(utilityPriceDetail);

                // exclude council tax into separate cost category
                if(utilityPriceDetail.getUtilityType() == UtilityFeeEnum.council_tax) {
                    StringBuilder councilTaxDescription = new StringBuilder();
                    councilTaxDescription.append("This is your council tax");

                    RentCostEntry councilTaxCostEntry = new RentCostEntry();
                    councilTaxCostEntry.setPriceMean(pcmCost);
                    councilTaxCostEntry.setLabel(RentCostEntryType.council_tax.getDisplayName());
                    councilTaxCostEntry.setType(RentCostEntryType.council_tax);
                    councilTaxCostEntry.setDescription(councilTaxDescription.toString());
                    rentCosts.add(councilTaxCostEntry);
                } else {
                    totalCost += pcmCost;
                    utilityDescription.append(" - ");
                    utilityDescription.append(utilityPriceDetail.getUtilityType().getDisplayName());
                    utilityDescription.append(" (");
                    utilityDescription.append(RentPriceValueFormatter.getPriceWithPeriodAsString(pcmCost, RentPricePeriodEnum.month));
                    utilityDescription.append(")");
                    if(i < utilityPriceDetails.size() - 1) {
                        utilityDescription.append(", ");
                        utilityDescription.append(System.getProperty("line.separator"));
                    }
                }
            }

            RentCostEntry utilityCostEntry = new RentCostEntry();
            utilityCostEntry.setPriceMean(totalCost);
            utilityCostEntry.setLabel(RentCostEntryType.utility.getDisplayName());
            utilityCostEntry.setType(RentCostEntryType.utility);
            utilityCostEntry.setDescription(utilityDescription.toString());
            rentCosts.add(utilityCostEntry);
        }

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

        // set upfront costs
        UpfrontPriceLocalAuthorityDetails upfrontCosts = currentPriceDetails.getUpfrontDetails();
        if (upfrontCosts != null) {
            for (UpfrontPriceDetails upfrontCost : upfrontCosts.getPrice()) {
                StringBuilder upfrontDescription = new StringBuilder();
                upfrontDescription.append("Put aside up to ");
                upfrontDescription.append(RentPriceValueFormatter.getPriceWithPeriodAsString(upfrontCost.getPriceMean(), RentPricePeriodEnum.month));

                rentCosts.add(new RentCostEntry(
                        upfrontCost.getPriceMean(),
                        RentCostEntryType.upfront.getDisplayName(),
                        RentCostEntryType.upfront, upfrontDescription.toString()));
            }
        }

        return rentCosts;
    }
}
