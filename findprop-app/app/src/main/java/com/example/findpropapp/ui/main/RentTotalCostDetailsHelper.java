package com.example.findpropapp.ui.main;

import com.example.findpropapp.model.RentPriceLocalAuthorityDetails;
import com.example.findpropapp.model.RentPricePostcodeAreaDetails;
import com.example.findpropapp.model.RentPriceRegionDetails;
import com.example.findpropapp.model.RentPriceResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RentTotalCostDetailsHelper {
    public static ArrayList<RentPriceEntry> getRentPriceEntries(RentPriceResponse currentPriceDetails) {
        ArrayList<RentPriceEntry> rentPrices = new ArrayList<RentPriceEntry>();
        if (currentPriceDetails == null) {
            return rentPrices;
        }

        // set average postcode area price
        RentPricePostcodeAreaDetails postcodeAreaDetails = currentPriceDetails.getPostcodeAreaDetails();
        if (postcodeAreaDetails != null) {
            rentPrices.add(new RentPriceEntry(postcodeAreaDetails.getPrice().getPriceMean(),
                    postcodeAreaDetails.getPrice().getPriceLow(),
                    postcodeAreaDetails.getPrice().getPriceHigh(),
                    postcodeAreaDetails.getPostcodeArea(),
                    RentPriceEntryType.postcode_area));
        }

        // set average anchor local authority price
        RentPriceLocalAuthorityDetails localAuthorityDetails = currentPriceDetails.getLocalAuthorityDetails();
        if (localAuthorityDetails != null) {
            rentPrices.add(new RentPriceEntry(localAuthorityDetails.getPrice().getPriceMean(),
                    localAuthorityDetails.getPrice().getPriceLow(),
                    localAuthorityDetails.getPrice().getPriceHigh(),
                    localAuthorityDetails.getLocalAuthority(),
                    RentPriceEntryType.local_authority));
        }

        // set average related local authority prices
        if (currentPriceDetails.getRelatedLocalAuthorityDetails() != null) {
            for (RentPriceLocalAuthorityDetails relatedLocalAuthorityPrice : currentPriceDetails.getRelatedLocalAuthorityDetails()) {
                if (relatedLocalAuthorityPrice != null) {
                    rentPrices.add(new RentPriceEntry(relatedLocalAuthorityPrice.getPrice().getPriceMean(),
                            relatedLocalAuthorityPrice.getPrice().getPriceLow(),
                            relatedLocalAuthorityPrice.getPrice().getPriceHigh(),
                            relatedLocalAuthorityPrice.getLocalAuthority(),
                            RentPriceEntryType.related_local_authority));
                }
            }
        }

        // set average region price
        RentPriceRegionDetails regionDetails = currentPriceDetails.getRegionDetails();
        if (regionDetails != null) {
            rentPrices.add(new RentPriceEntry(regionDetails.getPrice().getPriceMean(),
                    regionDetails.getPrice().getPriceLow(),
                    regionDetails.getPrice().getPriceHigh(),
                    regionDetails.getRegion(),
                    RentPriceEntryType.region));
        }

        return rentPrices;
    }
}
