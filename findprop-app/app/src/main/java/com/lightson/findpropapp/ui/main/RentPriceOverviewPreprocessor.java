package com.lightson.findpropapp.ui.main;

import android.content.Context;
import android.content.res.Resources;

import com.lightson.findpropapp.R;
import com.lightson.findpropapp.model.RentPriceDetails;
import com.lightson.findpropapp.model.RentPriceLocalAuthorityDetails;
import com.lightson.findpropapp.model.RentPricePostcodeAreaDetails;
import com.lightson.findpropapp.model.RentPricePostcodeDetails;
import com.lightson.findpropapp.model.RentPriceRegionDetails;
import com.lightson.findpropapp.model.RentPriceResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RentPriceOverviewPreprocessor {
    public static ArrayList<RentPriceEntry> getRentPriceEntries(RentPriceResponse currentPriceDetails, Context context) {
        ArrayList<RentPriceEntry> rentPrices = new ArrayList<RentPriceEntry>();
        if (currentPriceDetails == null) {
            return rentPrices;
        }

        // set postcode  price
        updatePostcodePrice(currentPriceDetails, context, rentPrices);

        // set postcode area price
        updatePostcodeAreaPrice(currentPriceDetails, context, rentPrices);

        // set anchor local authority price
        updateLocalAuthorityPrice(currentPriceDetails, context, rentPrices);

        // set related local authority prices
        // updateRelatedLocalAuthorityPrice(currentPriceDetails, context, rentPrices);

        // set similar local authority prices
        updateSimilarLocalAuthorityPrice(currentPriceDetails, context, rentPrices);

        // set region price
        updateRegionPrice(currentPriceDetails, context, rentPrices);

        return rentPrices;
    }

    private static void updatePostcodePrice(RentPriceResponse currentPriceDetails, Context context, ArrayList<RentPriceEntry> rentPrices) {
        RentPricePostcodeDetails postcodeDetails = currentPriceDetails.getPostcodeDetails();
        ArrayList<RentPricePostcodeDetails> relatedPostcodeDetails = (ArrayList) currentPriceDetails.getRelatedPostcodeDetails();
        if (postcodeDetails != null && relatedPostcodeDetails != null) {
            Resources resources = context.getResources();

            // create a copy of reference postcodes
            ArrayList<RentPricePostcodeDetails> sortedPostcodeDetails = (ArrayList) relatedPostcodeDetails.clone();

            // add anchor to the same list, if that has price
            if (postcodeDetails.getPrice() != null) sortedPostcodeDetails.add(postcodeDetails);

            // if there are less than 2 elements, let's skip showing postcode prices at all
            if (sortedPostcodeDetails.size() < 2) return;

            // sort postcodes by mean price, to find low/high prices
            Collections.sort(sortedPostcodeDetails, new Comparator<RentPricePostcodeDetails>() {
                @Override
                public int compare(RentPricePostcodeDetails o1, RentPricePostcodeDetails o2) {
                    RentPriceDetails price1 = RentPriceValueFormatter.getPriceDetailsPCM(o1.getPrice());
                    RentPriceDetails price2 = RentPriceValueFormatter.getPriceDetailsPCM(o2.getPrice());
                    return price1.getPriceMean() - price2.getPriceMean();
                }
            });
            int referencePostcodePriceLow = RentPriceValueFormatter.getPriceDetailsPCM(sortedPostcodeDetails.get(0).getPrice()).getPriceMean();
            int referencePostcodePriceHigh = RentPriceValueFormatter.getPriceDetailsPCM(sortedPostcodeDetails.get(relatedPostcodeDetails.size() - 1).getPrice()).getPriceMean();
            int anchorPostcodePriceMean = (referencePostcodePriceHigh + referencePostcodePriceLow) / 2;

            StringBuilder postcodeDetailsDescription = new StringBuilder();
            postcodeDetailsDescription.append(resources.getString(R.string.average_price_for));
            postcodeDetailsDescription.append(" ");
            postcodeDetailsDescription.append(resources.getString(R.string.postcode));
            postcodeDetailsDescription.append(" ");
            postcodeDetailsDescription.append(postcodeDetails.getPostcode());

            rentPrices.add(new RentPriceEntry(anchorPostcodePriceMean,
                    referencePostcodePriceLow,
                    referencePostcodePriceHigh,
                    postcodeDetails.getPostcode(),
                    RentPriceEntryType.postcode,
                    postcodeDetailsDescription.toString()));
        }
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
