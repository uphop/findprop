package com.lightson.findpropapp.ui.main;

import com.lightson.findpropapp.model.RentPriceDetails;
import com.lightson.findpropapp.model.RentPricePeriodEnum;
import com.lightson.findpropapp.model.UpfrontPriceDetails;
import com.lightson.findpropapp.model.UtilityPriceDetails;

public class RentPriceValueFormatter {
    private static final String DEFAULT_CURRENCY_CHARACTER = "£";
    private static final String DEFAULT_PERIOD_PER_MONTH_TEXT = "pcm";
    private static final String DEFAULT_PERIOD_PER_WEEK_TEXT = "pcw";

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

    public static RentPriceDetails getPriceDetailsPCW(RentPriceDetails inputPrice) {
        RentPriceDetails outputPrice = new RentPriceDetails();
        outputPrice.setPriceLow(getPricePCW(inputPrice.getPriceLow(), inputPrice.getPeriod()));
        outputPrice.setPriceHigh(getPricePCW(inputPrice.getPriceHigh(), inputPrice.getPeriod()));
        outputPrice.setPriceMean(getPricePCW(inputPrice.getPriceMean(), inputPrice.getPeriod()));
        outputPrice.setPriceMedian(getPricePCW(inputPrice.getPriceMedian(), inputPrice.getPeriod()));

        outputPrice.setPriceCount(inputPrice.getPriceCount());
        outputPrice.setCurrency(inputPrice.getCurrency());
        outputPrice.setPeriod(RentPricePeriodEnum.week);

        return outputPrice;
    }

    public static RentPriceDetails getPriceDetailsPCM(RentPriceDetails inputPrice) {
        RentPriceDetails outputPrice = new RentPriceDetails();
        outputPrice.setPriceLow(getPricePCM(inputPrice.getPriceLow(), inputPrice.getPeriod()));
        outputPrice.setPriceHigh(getPricePCM(inputPrice.getPriceHigh(), inputPrice.getPeriod()));
        outputPrice.setPriceMean(getPricePCM(inputPrice.getPriceMean(), inputPrice.getPeriod()));
        outputPrice.setPriceMedian(getPricePCM(inputPrice.getPriceMedian(), inputPrice.getPeriod()));

        outputPrice.setPriceCount(inputPrice.getPriceCount());
        outputPrice.setCurrency(inputPrice.getCurrency());
        outputPrice.setPeriod(RentPricePeriodEnum.month);

        return outputPrice;
    }
}
