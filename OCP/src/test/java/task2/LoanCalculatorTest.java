package task2;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LoanCalculatorTest {

    private LoanCalculator loanCalculator;

    @Test
    public void studentLoanForYoungPoorPerson() {
        loanCalculator = new StudentLoanCalculator();
        assertThat(loanCalculator.calculateLoan(18, 100), is(100));
    }

    @Test
    public void studentLoanForOldPerson() {
        loanCalculator = new StudentLoanCalculator();
        assertThat(loanCalculator.calculateLoan(21, 100), is(250));
    }

    @Test
    public void studentLoanForRichPerson() {
        loanCalculator = new StudentLoanCalculator();
        assertThat(loanCalculator.calculateLoan(18, 2_000), is(200));
    }

    @Test
    public void carLoanForYoungPoorPerson() {
        loanCalculator = new CarLoanCalculator();
        assertThat(loanCalculator.calculateLoan(20, 500), is(2_000));
    }

    @Test
    public void carLoanForAdultPoorPerson() {
        loanCalculator = new CarLoanCalculator();
        assertThat(loanCalculator.calculateLoan(45, 500), is(3_000));
    }

    @Test
    public void carLoanForOldPoorPerson() {
        loanCalculator = new CarLoanCalculator();
        assertThat(loanCalculator.calculateLoan(60, 500), is(3_500));
    }

    @Test
    public void carLoanForYoungRichPerson() {
        loanCalculator = new CarLoanCalculator();
        assertThat(loanCalculator.calculateLoan(20, 2_000), is(4_000));
    }

    @Test
    public void carLoanForAdultRichPerson() {
        loanCalculator = new CarLoanCalculator();
        assertThat(loanCalculator.calculateLoan(45, 2_000), is(6_000));
    }

    @Test
    public void carLoanForOldRichPerson() {
        loanCalculator = new CarLoanCalculator();
        assertThat(loanCalculator.calculateLoan(60, 2_000), is(7_000));
    }

    @Test
    public void houseLoanForYoungPoorPerson() {
        loanCalculator = new HouseLoanCalculator();
        assertThat(loanCalculator.calculateLoan(20, 500), is(100_000));
    }

    @Test
    public void houseLoanForOldPoorPerson() {
        loanCalculator = new HouseLoanCalculator();
        assertThat(loanCalculator.calculateLoan(60, 500), is(100_000));
    }

    @Test
    public void houseLoanForYoungRichPerson() {
        loanCalculator = new HouseLoanCalculator();
        assertThat(loanCalculator.calculateLoan(20, 65_000), is(200_000));
    }

    @Test
    public void houseLoanForOldRichPerson() {
        loanCalculator = new HouseLoanCalculator();
        assertThat(loanCalculator.calculateLoan(60, 65_000), is(400_000));
    }
}
