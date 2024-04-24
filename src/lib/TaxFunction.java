package lib;

public class TaxFunction {

    private static final int NON_TAXABLE_INCOME_SINGLE = 54000000;
    private static final int NON_TAXABLE_INCOME_SPOUSE = 4500000;
    private static final int NON_TAXABLE_INCOME_CHILD = 4500000;
    private static final double TAX_RATE = 0.05;

    public enum MaritalStatus {
        SINGLE, MARRIED, DIVORCED
    }

    public static int calculateTax(int monthlySalary, int additionalMonthlyIncome, int numberOfMonthWorking, int annualDeductible, MaritalStatus maritalStatus, int numberOfChildren) {
        validateInputs(monthlySalary, additionalMonthlyIncome, numberOfMonthWorking, annualDeductible, maritalStatus, numberOfChildren);

        int nonTaxableIncome = calculateNonTaxableIncome(maritalStatus, numberOfChildren);
        int taxableIncome = ((monthlySalary + additionalMonthlyIncome) * numberOfMonthWorking) - annualDeductible - nonTaxableIncome;

        if (taxableIncome < 0) {
            return 0;
        }

        return (int) Math.round(TAX_RATE * taxableIncome);
    }

    private static void validateInputs(int monthlySalary, int additionalMonthlyIncome, int numberOfMonthWorking, int annualDeductible, MaritalStatus maritalStatus, int numberOfChildren) {
        if (numberOfMonthWorking > 12) {
            throw new IllegalArgumentException("Months worked cannot exceed 12");
        }

        if (numberOfChildren > 3) {
            throw new IllegalArgumentException("Maximum number of children is 3");
        }
    }

    private static int calculateNonTaxableIncome(MaritalStatus maritalStatus, int numberOfChildren) {
        int nonTaxableIncome = NON_TAXABLE_INCOME_SINGLE;

        if (maritalStatus == MaritalStatus.MARRIED) {
            nonTaxableIncome += NON_TAXABLE_INCOME_SPOUSE;
        }

        nonTaxableIncome += numberOfChildren * NON_TAXABLE_INCOME_CHILD;
        return nonTaxableIncome;
    }
}
