package com.lightson.findpropapi.model;

public class RentPriceHelper {
    public final static int DAYS_IN_WEEK = 7;
    public final static int DAYS_IN_MONTH = 31;
    public final static int MONTHS_IN_YEAR = 12;
    public final static int WEEKS_IN_YEAR = 52;


    public static int getPricePCW(int price, String period) {
        switch (RentPricePeriodEnum.valueOf(period)) {
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

    public static int getPricePCM(int price, String period) {
        switch (RentPricePeriodEnum.valueOf(period)) {
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

    
}
