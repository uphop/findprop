package com.example.findpropapp.ui.main;

import com.example.findpropapp.model.RentPriceLocalAuthorityDetails;
import com.example.findpropapp.model.RentPricePostcodeAreaDetails;
import com.example.findpropapp.model.RentPriceRegionDetails;
import com.example.findpropapp.model.RentPriceResponse;

import java.util.ArrayList;

public class RentPriceOverviewPreprocessor {
    public static ArrayList<RentPriceEntry> getRentPriceEntries(RentPriceResponse currentPriceDetails) {
        ArrayList<RentPriceEntry> rentPrices = new ArrayList<RentPriceEntry>();
        if (currentPriceDetails == null) {
            return rentPrices;
        }

        // set average postcode area price
        RentPricePostcodeAreaDetails postcodeAreaDetails = currentPriceDetails.getPostcodeAreaDetails();
        if (postcodeAreaDetails != null) {
            StringBuilder postcodeAreaDetailsDescription = new StringBuilder();
            postcodeAreaDetailsDescription.append("Average price for ");
            postcodeAreaDetailsDescription.append(postcodeAreaDetails.getPostcodeArea());
            postcodeAreaDetailsDescription.append(" postcode area");

            rentPrices.add(new RentPriceEntry(postcodeAreaDetails.getPrice().getPriceMean(),
                    postcodeAreaDetails.getPrice().getPriceLow(),
                    postcodeAreaDetails.getPrice().getPriceHigh(),
                    postcodeAreaDetails.getPostcodeArea(),
                    RentPriceEntryType.postcode_area,
                    postcodeAreaDetailsDescription.toString()));
        }

        // set average anchor local authority price
        RentPriceLocalAuthorityDetails localAuthorityDetails = currentPriceDetails.getLocalAuthorityDetails();
        if (localAuthorityDetails != null) {
            StringBuilder localAuthorityDetailsDescription = new StringBuilder();
            localAuthorityDetailsDescription.append("Average price for  ");
            localAuthorityDetailsDescription.append(localAuthorityDetails.getLocalAuthority());
            localAuthorityDetailsDescription.append(" borough");

            rentPrices.add(new RentPriceEntry(localAuthorityDetails.getPrice().getPriceMean(),
                    localAuthorityDetails.getPrice().getPriceLow(),
                    localAuthorityDetails.getPrice().getPriceHigh(),
                    localAuthorityDetails.getLocalAuthority(),
                    RentPriceEntryType.local_authority,
                    localAuthorityDetailsDescription.toString()));
        }

        // set average related local authority prices
        if (currentPriceDetails.getRelatedLocalAuthorityDetails() != null) {
            for (RentPriceLocalAuthorityDetails relatedLocalAuthorityPrice : currentPriceDetails.getRelatedLocalAuthorityDetails()) {
                if (relatedLocalAuthorityPrice != null) {
                    StringBuilder localAuthorityDetailsDescription = new StringBuilder();
                    localAuthorityDetailsDescription.append("Average price for near-by ");
                    localAuthorityDetailsDescription.append(relatedLocalAuthorityPrice.getLocalAuthority());
                    localAuthorityDetailsDescription.append(" borough");

                    rentPrices.add(new RentPriceEntry(relatedLocalAuthorityPrice.getPrice().getPriceMean(),
                            relatedLocalAuthorityPrice.getPrice().getPriceLow(),
                            relatedLocalAuthorityPrice.getPrice().getPriceHigh(),
                            relatedLocalAuthorityPrice.getLocalAuthority(),
                            RentPriceEntryType.related_local_authority,
                            localAuthorityDetailsDescription.toString()));
                }
            }
        }

        // set average region price
        RentPriceRegionDetails regionDetails = currentPriceDetails.getRegionDetails();
        if (regionDetails != null) {
            StringBuilder regionDetailsDescription = new StringBuilder();
            regionDetailsDescription.append("Average price for  ");
            regionDetailsDescription.append(regionDetails.getRegion());
            regionDetailsDescription.append(" region");

            rentPrices.add(new RentPriceEntry(regionDetails.getPrice().getPriceMean(),
                    regionDetails.getPrice().getPriceLow(),
                    regionDetails.getPrice().getPriceHigh(),
                    regionDetails.getRegion(),
                    RentPriceEntryType.region,
                    regionDetailsDescription.toString()));
        }

        return rentPrices;
    }
}
