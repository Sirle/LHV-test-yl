package autotests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DataProviders;
import utils.Find;
import utils.Helper;
import utils.Wait;

import static pageObjects.General.*;
import static pageObjects.LeasePage.*;

public class AdditionalTests extends DataProviders {

    @BeforeClass
    public void openPage() {
        webDriver.navigate().to(URL_TO_LEASE_CALCULATOR);
        Helper.handleCookieBanner();
    }

    @Test(description = "Verify that user is on lease page", priority = 1)
    public void correctPageDisplayedTest() throws InterruptedException {
        boolean applyForLeaseButtonDisplayed = Find.isDisplayed(APPLY_TO_LEASE_BUTTON);

        //Navigate to the actual calculator
        Find.findAndClick(CALCULATOR_BUTTON);
        Thread.sleep(1000);

        Assert.assertTrue(applyForLeaseButtonDisplayed);
    }

    @Test(dataProvider = "fieldsToFill", description = "Verify that input fields have length limit and text input results in monthly payment sum of 0.00", priority = 2)
    public void insertLongTextToPriceFieldTest(By fieldToFill, int expectedLength) throws InterruptedException {
        Find.clearAndFillField(fieldToFill, SCRIPT_ALERT_TEST);

        Thread.sleep(500);
        String fieldText = Find.findAndGetValue(fieldToFill);
        int inserterTextLength = SCRIPT_ALERT_TEST.length();
        int currentFieldTextLength = fieldText.length();

        //if current field text length is smaller than original input that means script cannot be written in the field and will not have an effect
        boolean priceFieldHasLimit = currentFieldTextLength<inserterTextLength;

        webDriver.findElement(fieldToFill).clear();

        Assert.assertTrue(priceFieldHasLimit);
        Assert.assertEquals(currentFieldTextLength, expectedLength);
        Assert.assertEquals(Find.findAndGetText(MONTHLY_PAYMENT_SUM), VALUE_0_00);
    }

    //not sure if this calculator was also supposed to be covered, so I just added opening test for good measure
    @Test(description = "Verify that maximum monthly payment tab opens", priority = 3)
    public void openMaximumMonthlyPaymentTabTest() throws InterruptedException {
        Find.findAndClick(MAX_MONTHLY_PAYMENT_TAB);
        Thread.sleep(1000);
        boolean maritalStatusLabelDisplayed = Find.isDisplayed(MARITAL_STATUS_LABEL);

        Assert.assertTrue(maritalStatusLabelDisplayed);
    }

    @Test(description = "Verify that payment schedule page opens", priority = 4)
    public void openPaymentSchedulePageTest() {
        Find.findAndClick(EXAMPLE_MONTHLY_PAYMENT_TAB);
        Wait.waitUntilVisible(PRICE_FIELD);
        Find.clearAndFillField(PRICE_FIELD, "10000");
        Find.clearAndFillField(DOWNPAYMENT_PERCENTAGE_FIELD, "10");
        Find.clearAndFillField(REMINDER_PERCENTAGE_FIELD, "10");
        Find.clearAndFillField(INTEREST_RATE_FIELD, "5");
        String originalWindow = Helper.clickLinkAndSwitchToNewTab(PAYMENT_SCHEDULE_LINK);

        String headingText = Find.findAndGetText(By.tagName("h1"));

        //close the newly opened tab
        webDriver.close();

        //Switch back to original tab
        webDriver.switchTo().window(originalWindow);

        Assert.assertEquals(headingText, "Näidismaksegraafik");
    }

}
