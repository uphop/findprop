package com.example.findpropapp.ui.main;

import com.example.findpropapp.model.RentPriceDetails;
import com.example.findpropapp.model.RentPricePeriodEnum;

public class RentPriceValueFormatter {
    private static final String DEFAULT_CURRENCY_CHARACTER = "£";
    private static final String DEFAULT_PERIOD_PER_MONTH_TEXT = "pcm";
    private static final String DEFAULT_PERIOD_PER_WEEK_TEXT = "pcw";

    public static String getPriceWithPeriodAsString(RentPriceDetails price) {
        StringBuilder result = new StringBuilder();
        if(price != null) {
            result.append(price.getCurrency().getDisplayName());
            result.append(price.getPriceMean());
            result.append(" ");
            if (price.getPeriod() == RentPricePeriodEnum.month) {
                result.append(DEFAULT_PERIOD_PER_MONTH_TEXT);
            } else {
                result.append(DEFAULT_PERIOD_PER_WEEK_TEXT);
            }
        }
        return result.toString();
    }

    public static String getPriceWithPeriodAsString(int price, RentPricePeriodEnum period) {
        StringBuilder result = new StringBuilder();
        if(period != null) {
            result.append(DEFAULT_CURRENCY_CHARACTER);
            result.append(price);
            result.append(" ");
            if (period == RentPricePeriodEnum.month) {
                result.append(DEFAULT_PERIOD_PER_MONTH_TEXT);
            } else {
                result.append(DEFAULT_PERIOD_PER_WEEK_TEXT);
            }
        }
        return result.toString();
    }


}
