package task2;

public class StudentLoanCalculator implements LoanCalculator {
    @Override
    public int calculateLoan(int age, int income) {
        int loan = 100;

        if (age >= 21) {
            loan += 150;
        }

        return IncomeMultiplier.multiply(loan, income);
    }
}
