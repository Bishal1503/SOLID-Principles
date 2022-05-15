package task2;

public class HouseLoanCalculator implements LoanCalculator {
    @Override
    public int calculateLoan(int age, int income) {
        int loan = 100_000;

        if (age > 30 && income > loan / 2) {
            loan *= 2;
        }

        return IncomeMultiplier.multiply(loan, income);
    }
}
