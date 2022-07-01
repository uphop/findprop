package com.example.findpropapp.ui.main;

import android.content.Context;
import android.content.res.Resources;

import com.example.findpropapp.R;
import com.example.findpropapp.model.RentPriceDetails;
import com.example.findpropapp.model.RentPriceLocalAuthorityDetails;
import com.example.findpropapp.model.RentPricePostcodeAreaDetails;
import com.example.findpropapp.model.RentPriceRegionDetails;
import com.example.findpropapp.model.RentPriceResponse;

import java.util.ArrayList;

public class RentPriceOverviewPreprocessor {
    public static ArrayList<RentPriceEntry> getRentPriceEntries(RentPriceResponse currentPriceDetails, Context context) {
        ArrayList<RentPriceEntry> rentPrices = new ArrayList<RentPriceEntry>();
        if (currentPriceDetails == null) {
            return rentPrices;
        }
        Resources resources = context.getResources();

        // set average postcode area price
        RentPricePostcodeAreaDetails postcodeAreaDetails = currentPriceDetails.getPostcodeAreaDetails();
        if (postcodeAreaDetails != null) {
            StringBuilder postcodeAreaDetailsDescription = new StringBuilder();
            postcodeAreaDetailsDescription.append(resources.getString(R.string.average_price_for));
            postcodeAreaDetailsDescription.append(" ");
            postcodeAreaDetailsDescription.append(resources.getString(R.string.postcode_area));
            postcodeAreaDetailsDescription.append(" ");
            postcodeAreaDetailsDescription.append(postcodeAreaDetails.getPostcodeArea());

            RentPriceDetails postcodeAreaPriceDetails = RentPriceValueFormatter.getPriceDetailsPCM(postcodeAreaDetails.getPrice());
            rentPrices.add(new RentPriceEntry(postcodeAreaPriceDetails.getPriceMean(),
                    postcodeAreaPriceDetails.getPriceLow(),
                    postcodeAreaPriceDetails.getPriceHigh(),
                    postcodeAreaDetails.getPostcodeArea(),
                    RentPriceEntryType.postcode_area,
                    postcodeAreaDetailsDescription.toString()));
        }

        // set average anchor local authority price
        RentPriceLocalAuthorityDetails localAuthorityDetails = currentPriceDetails.getLocalAuthorityDetails();
        if (localAuthorityDetails != null) {
            StringBuilder localAuthorityDetailsDescription = new StringBuilder();
            localAuthorityDetailsDescription.append(resources.getString(R.string.average_price_for));
            localAuthorityDetailsDescription.append(" ");
            localAuthorityDetailsDescription.append(resources.getString(R.string.borough));
            localAuthorityDetailsDescription.append(" ");
            localAuthorityDetailsDescription.append(localAuthorityDetails.getLocalAuthority());

            RentPriceDetails localAuthorityPriceDetails = RentPriceValueFormatter.getPriceDetailsPCM(localAuthorityDetails.getPrice());
            rentPrices.add(new RentPriceEntry(localAuthorityPriceDetails.getPriceMean(),
                    localAuthorityPriceDetails.getPriceLow(),
                    localAuthorityPriceDetails.getPriceHigh(),
                    localAuthorityDetails.getLocalAuthority(),
                    RentPriceEntryType.local_authority,
                    localAuthorityDetailsDescription.toString()));
        }

        /* // set average related local authority prices
        if (currentPriceDetails.getRelatedLocalAuthorityDetails() != null) {
            for (RentPriceLocalAuthorityDetails relatedLocalAuthorityPrice : currentPriceDetails.getRelatedLocalAuthorityDetails()) {
                if (relatedLocalAuthorityPrice != null) {
                    StringBuilder localAuthorityDetailsDescription = new StringBuilder();
                    localAuthorityDetailsDescription.append(resources.getString(R.string.average_price_for));
                    localAuthorityDetailsDescription.append(" ");
                    localAuthorityDetailsDescription.append(relatedLocalAuthorityPrice.getLocalAuthority());
                    localAuthorityDetailsDescription.append(" ");
                    localAuthorityDetailsDescription.append(resources.getString(R.string.nearby_borough));

                    rentPrices.add(new RentPriceEntry(relatedLocalAuthorityPrice.getPrice().getPriceMean(),
                            relatedLocalAuthorityPrice.getPrice().getPriceLow(),
                            relatedLocalAuthorityPrice.getPrice().getPriceHigh(),
                            relatedLocalAuthorityPrice.getLocalAuthority(),
                            RentPriceEntryType.related_local_authority,
                            localAuthorityDetailsDescription.toString()));
                }
            }
        }*/

        // set average similar local authority prices
        if (currentPriceDetails.getSimilarLocalAuthorityDetails() != null) {
            for (RentPriceLocalAuthorityDetails similarLocalAuthorityPrice : currentPriceDetails.getSimilarLocalAuthorityDetails()) {
                if (similarLocalAuthorityPrice != null) {
                    StringBuilder localAuthorityDetailsDescription = new StringBuilder();
                    localAuthorityDetailsDescription.append(resources.getString(R.string.average_price_for));
                    localAuthorityDetailsDescription.append(" ");
                    localAuthorityDetailsDescription.append(resources.getString(R.string.similar_borough));
                    localAuthorityDetailsDescription.append(" ");
                    localAuthorityDetailsDescription.append(similarLocalAuthorityPrice.getLocalAuthority());

                    RentPriceDetails similarLocalAuthorityPriceDetails = RentPriceValueFormatter.getPriceDetailsPCM(similarLocalAuthorityPrice.getPrice());
                    rentPrices.add(new RentPriceEntry(similarLocalAuthorityPriceDetails.getPriceMean(),
                            similarLocalAuthorityPriceDetails.getPriceLow(),
                            similarLocalAuthorityPriceDetails.getPriceHigh(),
                            similarLocalAuthorityPrice.getLocalAuthority(),
                            RentPriceEntryType.similar_local_authority,
                            localAuthorityDetailsDescription.toString()));
                }
            }
        }

        // set average region price
        RentPriceRegionDetails regionDetails = currentPriceDetails.getRegionDetails();
        if (regionDetails != null) {
            StringBuilder regionDetailsDescription = new StringBuilder();
            regionDetailsDescription.append(resources.getString(R.string.average_price_for));
            regionDetailsDescription.append(" ");
            regionDetailsDescription.append(regionDetails.getRegion());
            regionDetailsDescription.append(" ");
            regionDetailsDescription.append(resources.getString(R.string.region));

            RentPriceDetails regionPriceDetails = RentPriceValueFormatter.getPriceDetailsPCM(regionDetails.getPrice());
            rentPrices.add(new RentPriceEntry(regionPriceDetails.getPriceMean(),
                    regionPriceDetails.getPriceLow(),
                    regionPriceDetails.getPriceHigh(),
                    regionDetails.getRegion(),
                    RentPriceEntryType.region,
                    regionDetailsDescription.toString()));
        }

        return rentPrices;
    }
}
