package com.epam.engx.cleandesign;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class AreaUtil {

    private AreaUtil() {
    }

    public static double getArea(Zone zone) {
        return zone.getHeight() * zone.getWidth() - summing(zone.getApertures(), AreaUtil::getApertureArea);
    }

    private static double getApertureArea(Aperture a) {
        return a.getWidth() * a.getHeight();
    }
}
