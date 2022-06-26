package com.lightson.findpropapi.crawler.adapter;

import java.io.Serializable;
import java.util.Arrays;

public class PropertyDataRentResponse implements Serializable {
    private String points_analysed;
    private String radius;
    private PropertyDataUnitEnum unit;
    private int average;
    private int[] pc_range_70;
    private int[] pc_range_80;
    private int[] pc_range_90;
    private int[] pc_range_100;
    private PropertyDataRentRawDataPoint[] raw_data;

    public PropertyDataRentResponse(String points_analysed, String radius, PropertyDataUnitEnum unit, int average,
            int[] pc_range_70, int[] pc_range_80, int[] pc_range_90, int[] pc_range_100,
            PropertyDataRentRawDataPoint[] raw_data) {
        this.points_analysed = points_analysed;
        this.radius = radius;
        this.unit = unit;
        this.average = average;
        this.pc_range_70 = pc_range_70;
        this.pc_range_80 = pc_range_80;
        this.pc_range_90 = pc_range_90;
        this.pc_range_100 = pc_range_100;
        this.raw_data = raw_data;
    }

    public PropertyDataRentResponse() {
    }

    public String getPoints_analysed() {
        return points_analysed;
    }

    public void setPoints_analysed(String points_analysed) {
        this.points_analysed = points_analysed;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public PropertyDataUnitEnum getUnit() {
        return unit;
    }

    public void setUnit(PropertyDataUnitEnum unit) {
        this.unit = unit;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public int[] get70pc_range() {
        return pc_range_70;
    }

    public void set70pc_range(int[] pc_range_70) {
        this.pc_range_70 = pc_range_70;
    }

    public int[] get80pc_range() {
        return pc_range_80;
    }

    public void set80pc_range(int[] pc_range_80) {
        this.pc_range_80 = pc_range_80;
    }

    public int[] get90pc_range() {
        return pc_range_90;
    }

    public void set90pc_range(int[] pc_range_90) {
        this.pc_range_90 = pc_range_90;
    }

    public int[] get100pc_range() {
        return pc_range_100;
    }

    public void set100pc_range(int[] pc_range_100) {
        this.pc_range_100 = pc_range_100;
    }

    public PropertyDataRentRawDataPoint[] getRaw_data() {
        return raw_data;
    }

    public void setRaw_data(PropertyDataRentRawDataPoint[] raw_data) {
        this.raw_data = raw_data;
    }

    @Override
    public String toString() {
        return "PropertyDataRentResponse [average=" + String.valueOf(average) + ", pc_range_100="
                + Arrays.toString(pc_range_100)
                + ", pc_range_70=" + Arrays.toString(pc_range_70) + ", pc_range_80=" + Arrays.toString(pc_range_80)
                + ", pc_range_90=" + Arrays.toString(pc_range_90) + ", points_analysed=" + points_analysed + ", radius="
                + radius + ", raw_data=" + Arrays.toString(raw_data) + ", unit=" + unit + "]";
    }
}
