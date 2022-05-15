package com.epam.engx.cleandesign;


import java.util.ArrayList;
import java.util.List;

public class Zone {

    private double height;
    private double width;
    private String type;
    private List<Aperture> apertures = new ArrayList<>();

    public Zone(String type, double height, double width) {
        this.height = height;
        this.width = width;
        this.type = type;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setApertures(List<Aperture> apertures) {
        this.apertures = apertures;
    }

    public List<Aperture> getApertures() {
        return apertures;
    }

    public String getType() {
        return type;
    }

}
