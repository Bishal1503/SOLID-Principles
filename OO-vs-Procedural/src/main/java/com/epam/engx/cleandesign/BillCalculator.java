package com.epam.engx.cleandesign;

import java.util.Map;

import static com.epam.engx.cleandesign.AreaUtil.getArea;


public class BillCalculator {

    public static Map<String, Double> zoneTypeWorkPrice;

    private static final double MATERIAL_AREA_FACTOR = 10;
    private static final int ONE_DAY_MAX_AREA = 50;
    private static final double MULTI_DAY_PRICE_FACTOR = 1.1;

    public Double calculateZoneBillPrice(Zone zone) {
        validateZone(zone);
        return getZoneBillPrice(zone);
    }

    private Double getZoneBillPrice(Zone zone) {
        double area = getArea(zone);
        return getMaterialPrice(area) + getWorkPrice(area, zone.getType());
    }

    private void validateZone(Zone zone) {
        if (isNotContainsKey(zone, zoneTypeWorkPrice))
            throw new WrongZoneTypeException();
    }

    private boolean isNotContainsKey(Zone zone, Map<String, Double> zoneTypeWorkPrice) {
        return !zoneTypeWorkPrice.keySet().contains(zone.getType());
    }

    private double getMaterialPrice(double area) {
        return area * MATERIAL_AREA_FACTOR;
    }

    private double getWorkPrice(double area, String type) {
        double price = area * zoneTypeWorkPrice.get(type);
        if (area < ONE_DAY_MAX_AREA)
            return price;
        return price * MULTI_DAY_PRICE_FACTOR;
    }

}
