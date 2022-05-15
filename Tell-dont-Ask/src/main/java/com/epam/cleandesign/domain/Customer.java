package com.epam.cleandesign.domain;


import com.epam.cleandesign.exceptions.NotEligibleForMortgageException;

public class Customer {


    private int id;

    private String firstName;

    private String lastName;

    private Double balance;

    private byte badCreditHistoryCount;

    public Customer(int id, String firstName, String lastName, Double balance, byte badCreditHistoryCount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.badCreditHistoryCount = badCreditHistoryCount;
    }

    public void updateBalance(Double amount) {
        if (this.eligibleForMortgage(amount))
            balance = balance + amount;
        else
            throw new NotEligibleForMortgageException();

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Double getBalance() {
        return balance;
    }

    public byte getBadCreditHistoryCount() {
        return badCreditHistoryCount;
    }

    private boolean eligibleForMortgage(Double amountRequested) {
        if (this.getBadCreditHistoryCount() == 0 && this.getBalance() > 0)
            return this.getBalance() * 2 >= amountRequested;

        return false;
    }


}
