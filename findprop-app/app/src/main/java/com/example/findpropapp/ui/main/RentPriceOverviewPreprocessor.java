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

        // set average postcode area price
        updatePostcodeAreaPrice(currentPriceDetails, context, rentPrices);

        // set average anchor local authority price
        updateLocalAuthorityPrice(currentPriceDetails, context, rentPrices);

        // set average related local authority prices
        // updateRelatedLocalAuthorityPrice(currentPriceDetails, context, rentPrices);

        // set average similar local authority prices
        updateSimilarLocalAuthorityPrice(currentPriceDetails, context, rentPrices);

        // set average region price
        updateRegionPrice(currentPriceDetails, context, rentPrices);

        return rentPrices;
    }

    private static void updatePostcodeAreaPrice(RentPriceResponse currentPriceDetails, Context context, ArrayList<RentPriceEntry> rentPrices) {
        RentPricePostcodeAreaDetails postcodeAreaDetails = currentPriceDetails.getPostcodeAreaDetails();
        if (postcodeAreaDetails != null) {
            Resources resources = context.getResources();

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
    }

    private static void updateLocalAuthorityPrice(RentPriceResponse currentPriceDetails, Context context, ArrayList<RentPriceEntry> rentPrices) {
        RentPriceLocalAuthorityDetails localAuthorityDetails = currentPriceDetails.getLocalAuthorityDetails();
        if (localAuthorityDetails != null) {
            Resources resources = context.getResources();

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
    }

    private static void updateRelatedLocalAuthorityPrice(RentPriceResponse currentPriceDetails, Context context, ArrayList<RentPriceEntry> rentPrices) {
        if (currentPriceDetails.getRelatedLocalAuthorityDetails() != null) {
            Resources resources = context.getResources();

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
        }
    }

    private static void updateSimilarLocalAuthorityPrice(RentPriceResponse currentPriceDetails, Context context, ArrayList<RentPriceEntry> rentPrices) {
        if (currentPriceDetails.getSimilarLocalAuthorityDetails() != null) {
            Resources resources = context.getResources();
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
    }

    private static void updateRegionPrice(RentPriceResponse currentPriceDetails, Context context, ArrayList<RentPriceEntry> rentPrices) {
        RentPriceRegionDetails regionDetails = currentPriceDetails.getRegionDetails();
        if (regionDetails != null) {
            Resources resources = context.getResources();

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
    }
}
