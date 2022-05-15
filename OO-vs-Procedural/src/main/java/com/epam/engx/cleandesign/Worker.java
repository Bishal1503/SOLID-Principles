package com.epam.engx.cleandesign;

public class Worker {
    private double dailyRate;
    private double amountPerDay;
    private boolean isJunior = false;

    public Worker(double dailyRate, double amountPerDay) {
        this.dailyRate = dailyRate;
        this.amountPerDay = amountPerDay;
    }

    public Worker(double dailyRate, double amountPerDay, boolean isJunior) {
        this.dailyRate = dailyRate;
        this.amountPerDay = amountPerDay;
        this.isJunior = isJunior;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public double getAmountPerDay() {
        return amountPerDay;
    }

    public boolean isJunior() {
        return isJunior;
    }

}
