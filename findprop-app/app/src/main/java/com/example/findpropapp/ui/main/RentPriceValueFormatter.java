package com.example.findpropapp.ui.main;

import com.example.findpropapp.model.RentPriceDetails;

public class RentPriceValueFormatter {
    private static final String DEFAULT_CURRENCY_CHARACTER = "£";
    private static final String DEFAULT_PERIOD_PER_MONTH_TEXT = "pcm";
    private static final String DEFAULT_PERIOD_PER_WEEk_TEXT = "pcw";

    public static String getPriceWithPeriodAsString(RentPriceDetails price) {
        StringBuilder result = new StringBuilder();
        if(price != null) {
            result.append(DEFAULT_CURRENCY_CHARACTER);
            result.append(price.getPriceMean());
            result.append(" ");
            if (price.getPeriod().equalsIgnoreCase("month")) {
                result.append(DEFAULT_PERIOD_PER_MONTH_TEXT);
            } else {
                result.append(DEFAULT_PERIOD_PER_WEEk_TEXT);
            }
        }
        return result.toString();
    }

    public static String getPriceWithPeriodAsString(int price, String period) {
        StringBuilder result = new StringBuilder();
        if(period != null) {
            result.append(DEFAULT_CURRENCY_CHARACTER);
            result.append(price);
            result.append(" ");
            if (period.equalsIgnoreCase("month")) {
                result.append(DEFAULT_PERIOD_PER_MONTH_TEXT);
            } else {
                result.append(DEFAULT_PERIOD_PER_WEEk_TEXT);
            }
        }
        return result.toString();
    }


}
