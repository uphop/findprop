package com.lightson.findpropapi.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UpfrontPriceRelatedPostcodeDetails extends UpfrontPriceCompositeDetails {

    public UpfrontPriceRelatedPostcodeDetails(List<RentPricePostcodeDetails> relatedPostcodePrices) {
        if (relatedPostcodePrices != null && relatedPostcodePrices.size() > 0) {
            // get min / max
            Collections.sort(relatedPostcodePrices, new Comparator<RentPricePostcodeDetails>() {
                @Override
                public int compare(RentPricePostcodeDetails o1,
                        RentPricePostcodeDetails o2) {
                    return o1.getPrice().getPriceMean() - o2.getPrice().getPriceMean();
                }
            });

            int priceLow = relatedPostcodePrices.get(0).getPrice().getPriceMean();
            int priceHigh = relatedPostcodePrices.get(relatedPostcodePrices.size() - 1).getPrice().getPriceMean();

            // get mean
            int priceMean = (int) relatedPostcodePrices.stream().mapToDouble(d -> d.getPrice().getPriceMean()).average()
                    .orElse(0.0);

            RentPriceDetails sampleRelatedPostcodePrice = relatedPostcodePrices.get(0).getPrice();

            RentPriceDetails outputRentPrice = new RentPriceDetails();
            outputRentPrice.setCurrency(sampleRelatedPostcodePrice.getCurrency());
            outputRentPrice.setPeriod(sampleRelatedPostcodePrice.getPeriod());
            outputRentPrice.setPriceCount(relatedPostcodePrices.size());
            outputRentPrice.setPriceHigh(priceHigh);
            outputRentPrice.setPriceLow(priceLow);
            outputRentPrice.setPriceMean(priceMean);
            outputRentPrice.setPriceMedian(priceMean);
            
            initPrice(outputRentPrice);
        }
    }
}
