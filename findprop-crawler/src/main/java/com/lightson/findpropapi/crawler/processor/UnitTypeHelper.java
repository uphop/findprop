package com.lightson.findpropapi.crawler.processor;

import com.lightson.findpropapi.crawler.adapter.PropertyDataUnitEnum;
import com.lightson.findpropapi.crawler.model.CurrencyEnum;
import com.lightson.findpropapi.crawler.model.PeriodEnum;

public class UnitTypeHelper {
    public static CurrencyEnum getCurrency(PropertyDataUnitEnum unit) {
        switch (unit) {
            case gbp_per_month:
                return CurrencyEnum.gbp;
            case gbp_per_week:
                return CurrencyEnum.gbp;
        }
        return null;
    }

    public static PeriodEnum getPeriod(PropertyDataUnitEnum unit) {
        switch (unit) {
            case gbp_per_month:
                return PeriodEnum.month;
            case gbp_per_week:
                return PeriodEnum.week;
        }
        return null;
    }
}
