package com.epam.engx.cleandesign;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class FundCalculatorIntegrationTest {

    private static final double DELTA = 0.001;
    private final FundCalculator fundCalculator = new FundCalculator();
    private final List<Assignment> assignments = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        BillCalculator billCalculator = new BillCalculator();
        HashMap<String, Double> zoneTypeWorkPrice = new HashMap<>();
        zoneTypeWorkPrice.put("Wall", 15.0);
        zoneTypeWorkPrice.put("Floor", 10.0);
        zoneTypeWorkPrice.put("Ceiling", 12.0);
        billCalculator.zoneTypeWorkPrice = zoneTypeWorkPrice;
        fundCalculator.setBillCalculator(billCalculator);

    }

    @Test
    public void shouldCalculateZeroBalanceWhenNoAssignments() {
        assertBalance(0.0);
    }

    @Test (expected = WrongZoneTypeException.class)
    public void shouldThrowExceptionWhenZoneWithWrongType() {
        assign(new Worker(250, 30), asList(new Zone("Other", 5.0, 5.0)));
        fundCalculator.getFundBalance(assignments);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignment() {
        assign(new Worker(250, 30), asList(new Zone("Wall", 5.0, 5.0)));
        assertBalance(250);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWithBigVendorBonus() {
        assign(new Worker(250, 30), asList(new Zone("Wall", 5.0, 5.0)));
        setBigVendorBonusToFirstAssignment();
        assertBalance(175);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneBigWallWithApertureAssignment() {
        assign(new Worker(250, 30), asList(getWallWithApertures()));
        assertBalance(100);
    }

    private Zone getWallWithApertures() {
        Zone wall = new Zone("Wall", 10.0, 10.0);
        wall.setApertures(asList(new Aperture(9.0, 5.0), new Aperture(9.0, 4.0)));
        return wall;
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneFloorAssignment() {
        assign(new Worker(180, 30), asList(new Zone("Floor", 5.0, 5.0)));
        assertBalance(209);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneCeilingAssignment() {
        assign(new Worker(200, 30), asList(new Zone("Ceiling", 5.0, 5.0)));
        assertBalance(235);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneSmallCeilingAssignment() {
        assign(new Worker(200, 30), asList(new Zone("Ceiling", 3.0, 5.0)));
        assertBalance(15);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithTwoCeilingAssignment() {
        assign(new Worker(200, 30), asList(new Zone("Ceiling", 2.0, 5.0), new Zone("Ceiling", 3.0, 5.0)));
        assertBalance(235);
    }

    @Test
    public void shouldCalculateBalanceWhenOneJuniorWorkerWithOneCeilingAssignment() {
        assign(new Worker(200, 30, true), asList(new Zone("Ceiling", 5.0, 5.0)));
        assertBalance(300);
    }

    @Test
    public void shouldCalculateBalanceWhenOneJuniorWorkerWithOneCeilingAssignmentWithBigVendorBonus() {
        assign(new Worker(200, 30, true), asList(new Zone("Ceiling", 5.0, 5.0)));
        setBigVendorBonusToFirstAssignment();
        assertBalance(250);
    }

    @Test
    public void shouldCalculateBalanceWhenOneJuniorCheapWorkerWithOneCeilingAssignment() {
        assign(new Worker(100, 30, true), asList(new Zone("Ceiling", 5.0, 5.0)));
        assertBalance(400);
    }

    @Test
    public void shouldCalculateBalanceWhenOneSeniorCheapWorkerWithOneCeilingAssignment() {
        assign(new Worker(180, 30), asList(new Zone("Ceiling", 5.0, 5.0)));
        assertBalance(259);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWorksTwoDaysButLessAmountThenMaxPerDay() {
        assign(new Worker(250, 30), asList(new Zone("Wall", 9.0, 5.0)));
        assertBalance(450);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWorksTwoDaysAndMoreAmountThenMaxPerDay() {
        assign(new Worker(250, 30), asList(new Zone("Wall", 11.0, 5.0)));
        assertBalance(782.5);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWorksOneDaysButMoreAmountThenMaxPerDay() {
        assign(new Worker(250, 60), asList(new Zone("Wall", 11.0, 5.0)));
        assertBalance(1082.5);
    }

    @Test
    public void shouldCalculateBalanceWhenTwoWorkersWithOneWallAssignment() {
        assign(new Worker(250, 30), asList(new Zone("Wall", 5.0, 5.0)));
        assign(new Worker(250, 30), asList(new Zone("Wall", 5.0, 5.0)));
        assertBalance(500);
    }

    @Test
    public void shouldCalculateBalanceForComplexTestcase() {
        assign(new Worker(250, 40), asList(new Zone("Floor", 5.0, 3.0), new Zone("Wall", 10.0, 10.0)));
        setBigVendorBonusToFirstAssignment();
        Worker worker = new Worker(200, 30, true);
        assign(worker, asList(new Zone("Ceiling", 5.0, 5.0), getWallWithApertures()));
        assign(worker, asList(new Zone("Ceiling", 5.0, 5.0), getWallWithApertures()));
        assign(new Worker(280, 60), asList(new Zone("Wall", 1.0, 5.0), new Zone("Wall", 11.0, 5.0)));
        assertBalance(4221.5);
    }

    private void setBigVendorBonusToFirstAssignment() {
        assignments.get(0).setVendorBonus(100);
    }

    private void assign(Worker worker, List<Zone> zones) {
        Assignment ass = new Assignment();
        ass.setWorker(worker);
        ass.setZones(zones);
        ass.setVendorBonus(50);
        assignments.add(ass);
    }


    private void assertBalance(double expected) {
        assertEquals(expected, fundCalculator.getFundBalance(assignments), DELTA);
    }


}