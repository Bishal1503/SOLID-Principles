package com.epam.engx.cleandesign;

import java.util.List;

public class Assignment {

    private Worker worker;
    private List<Zone> zones;
    private double vendorBonus;

    public double getVendorBonus() {
        return vendorBonus;
    }

    public void setVendorBonus(double vendorBonus) {
        this.vendorBonus = vendorBonus;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }
}
