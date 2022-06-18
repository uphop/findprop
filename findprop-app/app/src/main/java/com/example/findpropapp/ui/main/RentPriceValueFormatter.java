package com.example.findpropapp.ui.main;

import com.example.findpropapp.model.RentPriceDetails;
import com.example.findpropapp.model.RentPricePeriodEnum;
import com.example.findpropapp.model.UpfrontPriceDetails;
import com.example.findpropapp.model.UtilityPriceDetails;

public class RentPriceValueFormatter {
    private static final String DEFAULT_CURRENCY_CHARACTER = "£";
    private static final String DEFAULT_PERIOD_PER_MONTH_TEXT = "pcm";
    private static final String DEFAULT_PERIOD_PER_WEEK_TEXT = "pcw";
    private static final String DEFAULT_PERIOD_ONE_OFF_TEXT = "";

    private final static int DAYS_IN_WEEK = 7;
    private final static int DAYS_IN_MONTH = 31;
    private final static int MONTHS_IN_YEAR = 12;
    private final static int WEEKS_IN_YEAR = 52;

    public static String getPriceWithPeriodAsString(RentPriceDetails price) {
        StringBuilder result = new StringBuilder();
        if (price != null) {
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
        if (period != null) {
            result.append(DEFAULT_CURRENCY_CHARACTER);
            result.append(price);
            switch (period) {
                case month:
                    result.append(" ");
                    result.append(DEFAULT_PERIOD_PER_MONTH_TEXT);
                    break;
                case week:
                    result.append(" ");
                    result.append(DEFAULT_PERIOD_PER_WEEK_TEXT);
                    break;
                default:
                    break;
            }

        }
        return result.toString();
    }

    public static String getPriceAsString(int price) {
        StringBuilder result = new StringBuilder();
        result.append(DEFAULT_CURRENCY_CHARACTER);
        result.append(price);
        return result.toString();
    }

    private static int getPricePCW(int price, RentPricePeriodEnum period) {
        switch (period) {
            case day:
                return price * DAYS_IN_WEEK;
            case week:
                return price;
            case month:
                return price * MONTHS_IN_YEAR / WEEKS_IN_YEAR;
            case year:
                return price / WEEKS_IN_YEAR;
            case one_off:
                return -1;
            default:
                return -1;
        }
    }

    public static int getPricePCM(int price, RentPricePeriodEnum period) {
        switch (period) {
            case day:
                return price * DAYS_IN_MONTH;
            case week:
                return price * WEEKS_IN_YEAR / MONTHS_IN_YEAR;
            case month:
                return price;
            case year:
                return price / MONTHS_IN_YEAR;
            case one_off:
                return -1;
            default:
                return -1;
        }
    }

    public static int getRentPricePCW(RentPriceDetails price) {
        return getPricePCW(price.getPriceMean(), price.getPeriod());
    }

    public static int getRentPricePCM(RentPriceDetails price) {
        return getPricePCM(price.getPriceMean(), price.getPeriod());
    }

    public static int getUtilityCostPCW(UtilityPriceDetails price) {
        return getPricePCW(price.getPriceMean(), price.getPeriod());
    }

    public static int getUtilityCostPCM(UtilityPriceDetails price) {
        return getPricePCM(price.getPriceMean(), price.getPeriod());
    }

    public static int getUpfrontCostPCW(UpfrontPriceDetails price) {
        return getPricePCW(price.getPriceMean(), price.getPeriod());
    }

    public static int getUpfrontCostPCM(UpfrontPriceDetails price) {
        return getPricePCM(price.getPriceMean(), price.getPeriod());
    }
}
