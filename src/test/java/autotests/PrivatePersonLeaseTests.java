package autotests;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.LeasePage;
import utils.DataProviders;
import utils.Find;
import utils.Helper;

import static pageObjects.General.LANGUAGE_VALUE_ET;
import static pageObjects.General.URL_TO_LEASE_CALCULATOR;
import static pageObjects.LeasePage.*;

@Slf4j
public class PrivatePersonLeaseTests extends DataProviders {

    double price = 10000;
    double downPaymentPercent = 10;
    double downPaymentSum = 100;
    double residualValuePercent = 10;
    double residualValue = 100;

    @BeforeClass
    public void openPage() {
        webDriver.navigate().to(URL_TO_LEASE_CALCULATOR);
        Helper.handleCookieBanner();
    }

    //needed for some tests that click on links/buttons based on their text
    @Test(description = "Verify that page is in Estonian, change language if needed", priority = 1)
    public void estonianLanguageSelectedTest() {
        String newLanguage = Helper.changeLanguageToEstonianIfNeeded();

        Assert.assertEquals(newLanguage, LANGUAGE_VALUE_ET);
    }

    @Test(description = "Verify that user is on lease page", priority = 2)
    public void correctPageDisplayedTest() {
        boolean applyForLeaseButtonDisplayed = Find.isDisplayed(APPLY_TO_LEASE_BUTTON);

        Assert.assertTrue(applyForLeaseButtonDisplayed);
    }

    @Test(description = "Verify that changing down payment percent changes down payment amount in euros correctly", priority = 3)
    public void changeDownPaymentPercentTest() throws InterruptedException {
        //Navigate to the actual calculator
        Find.findAndClick(CALCULATOR_BUTTON);
        Thread.sleep(1000);
        Find.findAndClick(PRIVATE_PERSON_RADIO_BUTTON_XPATH);
        Find.findAndClick(FINANCIAL_LEASE_RADIO_BUTTON);

        //Clear fields from old values and add new values
        Find.clearAndFillField(PRICE_FIELD, String.valueOf(price));
        Find.clearAndFillField(DOWNPAYMENT_PERCENTAGE_FIELD, String.valueOf(downPaymentPercent));

        double downPaymentCalculation = price*(downPaymentPercent/100);
        log.info("downPaymentCalculation - " + downPaymentCalculation);

        double downPaymentEuro = Double.parseDouble(Find.findAndGetValue(DOWNPAYMENT_FIELD));
        log.info("downPaymentEuro - " + downPaymentEuro);

        Assert.assertEquals(downPaymentEuro, downPaymentCalculation);
    }

    @Test(description = "Verify that changing down payment sum changes down payment amount percentage correctly", priority = 4)
    public void changeDownPaymentSumTest() {
        Find.clearAndFillField(PRICE_FIELD, String.valueOf(price));
        Find.clearAndFillField(DOWNPAYMENT_FIELD, String.valueOf(downPaymentSum));

        String downPaymentPercentageCalculation = String.format("%.2f", downPaymentSum/price*100);
        log.info("downPaymentPercentageCalculation - " + downPaymentPercentageCalculation);

        String downPaymentPercentage = Find.findAndGetValue(DOWNPAYMENT_PERCENTAGE_FIELD);
        log.info("downPaymentPercentage - " + downPaymentPercentage);

        Assert.assertTrue(downPaymentPercentageCalculation.contains(downPaymentPercentage));
    }

    @Test(description = "Verify that changing residual value percent changes residual value amount in euros correctly", priority = 5)
    public void changeResidualValuePercentTest() {
        Find.clearAndFillField(PRICE_FIELD, String.valueOf(price));
        Find.clearAndFillField(REMINDER_PERCENTAGE_FIELD, String.valueOf(residualValuePercent));

        double residualValueCalculation = price*(residualValuePercent/100);
        log.info("residualValueCalculation - " + residualValueCalculation);

        double residualValueEuro = Double.parseDouble(Find.findAndGetValue(REMINDER_FIELD));
        log.info("residualValueEuro - " + residualValueEuro);

        Assert.assertEquals(residualValueEuro, residualValueCalculation);
    }

    @Test(description = "Verify that changing residual value sum changes residual value amount percentage correctly", priority = 6)
    public void changeResidualValueSumTest() {
        Find.clearAndFillField(PRICE_FIELD, String.valueOf(price));
        Find.clearAndFillField(REMINDER_FIELD, String.valueOf(residualValue));

        String residualPercentageCalculation = String.format("%.2f", residualValue/price*100);
        log.info("residualPercentageCalculation - " + residualPercentageCalculation);

        String residualValuePercentage = Find.findAndGetValue(REMINDER_PERCENTAGE_FIELD);
        log.info("residualValuePercentage - " + residualValuePercentage);

        Assert.assertTrue(residualPercentageCalculation.contains(residualValuePercentage));
    }

    @Test(dataProvider = "financialLeaseWithoutVAT", description = "Verify that monthly payment amount changes with " +
            "the values that are given to the calculator. Use percentage values for down payment and residual value inputs.", priority = 7)
    public void getPrivatePersonFinancialLeaseWithPercentagesTest(double price, String downPaymentPercent,
                                                                  String leasePeriodYears, String leasePeriodMonths, String interest,
                                                                  String residualValuePercent, String expectedMonthlyPayment) {
        Find.clearAndFillField(PRICE_FIELD, String.valueOf(price));
        Find.clearAndFillField(DOWNPAYMENT_PERCENTAGE_FIELD, downPaymentPercent);
        LeasePage.fillLeasePeriod(leasePeriodYears, leasePeriodMonths);
        Find.clearAndFillField(INTEREST_RATE_FIELD, interest);
        Find.clearAndFillField(REMINDER_PERCENTAGE_FIELD, residualValuePercent);

        String actualMonthlyPayment = Find.findAndGetText(MONTHLY_PAYMENT_SUM);

        Assert.assertEquals(actualMonthlyPayment, expectedMonthlyPayment);
    }

    @Test(dataProvider = "financialLeaseWithoutVAT", description = "Verify that monthly payment amount changes with " +
            "the values that are given to the calculator. Use sum amounts for down payment and residual value inputs.", priority = 8)
    public void getPrivatePersonFinancialLeaseWithEurosTest(double price, String downPaymentPercentage,
                                                                  String leasePeriodYears, String leasePeriodMonths, String interest,
                                                                  String residualPercentage, String expectedMonthlyPayment) {

        String downPaymentAmount = Helper.calculateValueFromPercentage(price, downPaymentPercentage);
        String residualValue = Helper.calculateValueFromPercentage(price, residualPercentage);

        Find.clearAndFillField(PRICE_FIELD, String.valueOf(price));
        Find.clearAndFillField(DOWNPAYMENT_FIELD, downPaymentAmount);
        LeasePage.fillLeasePeriod(leasePeriodYears, leasePeriodMonths);
        Find.clearAndFillField(INTEREST_RATE_FIELD, interest);
        Find.clearAndFillField(REMINDER_FIELD, residualValue);

        String actualMonthlyPayment = Find.findAndGetText(MONTHLY_PAYMENT_SUM);

        Assert.assertEquals(actualMonthlyPayment, expectedMonthlyPayment);
    }

    @Test(description = "Verify that User can switch from financial lease to operational lease.", priority = 9)
    public void switchPrivatePersonFinancialLeaseToOperationalLeaseTest() {
        Find.findAndClick(OPERATIONAL_LEASE_RADIO_BUTTON);

        boolean vatIncludedCheckboxDisplayed = Find.isDisplayed(IS_VAT_INCLUDED_BUTTON);

        Assert.assertTrue(vatIncludedCheckboxDisplayed);
    }

    @Test(dataProvider = "operationalLeaseWithVAT", description = "Verify that monthly payment amount changes with " +
            "the values that are given to the calculator. Use percentage values for down payment and residual value inputs.", priority = 10)
    public void getPrivatePersonOperationalLeaseWithPercentagesAndVATTest(double price, String downPaymentPercent,
                                                                  String leasePeriodYears, String leasePeriodMonths, String interest,
                                                                  String residualValuePercent, String expectedMonthlyPayment) {
        Helper.toggleVATIncluded(true);
        Find.clearAndFillField(PRICE_FIELD, String.valueOf(price));
        Find.clearAndFillField(DOWNPAYMENT_PERCENTAGE_FIELD, downPaymentPercent);
        LeasePage.fillLeasePeriod(leasePeriodYears, leasePeriodMonths);
        Find.clearAndFillField(INTEREST_RATE_FIELD, interest);
        Find.clearAndFillField(REMINDER_PERCENTAGE_FIELD, residualValuePercent);

        String actualMonthlyPayment = Find.findAndGetText(MONTHLY_PAYMENT_SUM);

        Assert.assertEquals(actualMonthlyPayment, expectedMonthlyPayment);
    }

    @Test(dataProvider = "operationalLeaseWithVAT", description = "Verify that monthly payment amount changes with " +
            "the values that are given to the calculator. Use sum amounts for down payment and residual value inputs.", priority = 11)
    public void getPrivatePersonOperationalLeaseWithEurosAndVATTest(double price, String downPaymentPercentage,
                                                            String leasePeriodYears, String leasePeriodMonths, String interest,
                                                            String residualPercentage, String expectedMonthlyPayment) {
        Helper.toggleVATIncluded(true);
        String downPaymentAmount = Helper.calculateValueFromPercentage(price, downPaymentPercentage);
        String residualValue = Helper.calculateValueFromPercentage(price, residualPercentage);

        Find.clearAndFillField(PRICE_FIELD, String.valueOf(price));
        Find.clearAndFillField(DOWNPAYMENT_FIELD, downPaymentAmount);
        LeasePage.fillLeasePeriod(leasePeriodYears, leasePeriodMonths);
        Find.clearAndFillField(INTEREST_RATE_FIELD, interest);
        Find.clearAndFillField(REMINDER_FIELD, residualValue);

        String actualMonthlyPayment = Find.findAndGetText(MONTHLY_PAYMENT_SUM);

        Assert.assertEquals(actualMonthlyPayment, expectedMonthlyPayment);
    }

    @Test(description = "Verify that User can turn VAT included off on private person operational lease.", priority = 12)
    public void toggleVATIncludedOffForPrivatePersonOperationalLeaseTest() {
        String originalPayment = Find.findAndGetText(MONTHLY_PAYMENT_SUM);

        Helper.toggleVATIncluded(false);

        String newPayment = Find.findAndGetText(MONTHLY_PAYMENT_SUM);

        Assert.assertNotEquals(originalPayment, newPayment);
    }

    @Test(dataProvider = "operationalLeaseWithoutVAT", description = "Verify that monthly payment amount changes with " +
            "the values that are given to the calculator. Use percentage values for down payment and residual value inputs.", priority = 13)
    public void getPrivatePersonOperationalLeaseWithPercentagesTest(double price, String downPaymentPercent,
                                                                    String leasePeriodYears, String leasePeriodMonths, String interest,
                                                                    String residualValuePercent, String expectedMonthlyPayment) {
        Helper.toggleVATIncluded(false);
        Find.clearAndFillField(PRICE_FIELD, String.valueOf(price));
        Find.clearAndFillField(DOWNPAYMENT_PERCENTAGE_FIELD, downPaymentPercent);
        LeasePage.fillLeasePeriod(leasePeriodYears, leasePeriodMonths);
        Find.clearAndFillField(INTEREST_RATE_FIELD, interest);
        Find.clearAndFillField(REMINDER_PERCENTAGE_FIELD, residualValuePercent);

        String actualMonthlyPayment = Find.findAndGetText(MONTHLY_PAYMENT_SUM);

        Assert.assertEquals(actualMonthlyPayment, expectedMonthlyPayment);
    }

    @Test(dataProvider = "operationalLeaseWithoutVAT", description = "Verify that monthly payment amount changes with " +
            "the values that are given to the calculator. Use sum amounts for down payment and residual value inputs.", priority = 14)
    public void getPrivatePersonOperationalLeaseWithEurosTest(double price, String downPaymentPercentage,
                                                              String leasePeriodYears, String leasePeriodMonths, String interest,
                                                              String residualPercentage, String expectedMonthlyPayment) {
        Helper.toggleVATIncluded(false);
        String downPaymentAmount = Helper.calculateValueFromPercentage(price, downPaymentPercentage);
        String residualValue = Helper.calculateValueFromPercentage(price, residualPercentage);

        Find.clearAndFillField(PRICE_FIELD, String.valueOf(price));
        Find.clearAndFillField(DOWNPAYMENT_FIELD, downPaymentAmount);
        LeasePage.fillLeasePeriod(leasePeriodYears, leasePeriodMonths);
        Find.clearAndFillField(INTEREST_RATE_FIELD, interest);
        Find.clearAndFillField(REMINDER_FIELD, residualValue);

        String actualMonthlyPayment = Find.findAndGetText(MONTHLY_PAYMENT_SUM);

        Assert.assertEquals(actualMonthlyPayment, expectedMonthlyPayment);
    }

}
