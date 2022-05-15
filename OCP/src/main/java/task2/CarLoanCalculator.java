package task2;

public class CarLoanCalculator implements LoanCalculator {
    @Override
    public int calculateLoan(int age, int income) {
        int loan = 2_000;

        if (age > 50) {
            loan += 1_500;
        } else if (age > 30) {
            loan += 1_000;
        }

        return IncomeMultiplier.multiply(loan, income);
    }
}
