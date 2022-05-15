package com.epam.engx.cleandesign;

public class Aperture {


    private double height;
    private double width;

    public Aperture(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

}
