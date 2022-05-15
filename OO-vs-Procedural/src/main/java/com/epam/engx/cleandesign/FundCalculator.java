package com.epam.engx.cleandesign;

import java.util.List;

import static com.epam.engx.cleandesign.CalculationUtil.summing;

public class FundCalculator {

    private static final double SENIOR_BONUS_FACTOR = 1.5;
    private SalaryCalculator salaryCalculator;
    private BillCalculator billCalculator;

    public FundCalculator() {
        salaryCalculator = new SalaryCalculator();
        billCalculator = new BillCalculator();
    }

    public double getFundBalance(List<Assignment> assignments) {
        double salaries = 0.0;
        double bill = 0.0;
        for (Assignment ass : assignments) {
            double totalArea = summing(ass.getZones(), AreaUtil::getArea);
            salaries += (salaryCalculator.calculateSalary(ass.getWorker(), totalArea) + getAssignmentBonus(ass));
            bill += summing(ass.getZones(), billCalculator::calculateZoneBillPrice);
        }
        return bill - salaries;
    }

    private double getAssignmentBonus(Assignment ass) {
        if (ass.getWorker().isJunior())
            return ass.getVendorBonus();
        else
            return ass.getVendorBonus() * SENIOR_BONUS_FACTOR;
    }

    public void setBillCalculator(BillCalculator billCalculator) {
        this.billCalculator = billCalculator;
    }
}
