package pageObjects;

import org.openqa.selenium.By;
import utils.DataProviders;
import utils.Find;
import utils.Wait;

import java.util.Objects;

public class LeasePage {

    public static final By APPLY_TO_LEASE_BUTTON = By.linkText("Taotle liisingut");
    public static final By CALCULATOR_BUTTON = By.linkText("Kalkulaator");
    public static final By PRIVATE_PERSON_RADIO_BUTTON_XPATH = By.xpath("//label[contains(text(),'eraisikuna')]");
    public static final By LEGAL_ENTITY_RADIO_BUTTON = By.id("account_type-1");
    public static final By FINANCIAL_LEASE_RADIO_BUTTON = By.id("kap_rent");
    public static final By OPERATIONAL_LEASE_RADIO_BUTTON = By.xpath("//label[contains(text(),'kasutusrent')]");
    public static final By IS_VAT_INCLUDED_BUTTON = By.xpath("//label[contains(text(),'Hind sisaldab käibemaksu')]");
    public static final By IS_VAT_INCLUDED_CHECKBOX = By.id("vat_included");
    public static final By MARITAL_STATUS_LABEL = By.xpath("//label[contains(text(),'Perekonnaseis')]");
    public static final By MAX_MONTHLY_PAYMENT_TAB = By.linkText("Maksimaalne kuumakse");
    public static final By EXAMPLE_MONTHLY_PAYMENT_TAB = By.linkText("Näidiskuumakse");
    public static final By PAYMENT_SCHEDULE_LINK = By.linkText("Maksegraafik");

    public static final By PRICE_FIELD = By.id("price");
    public static final By DOWNPAYMENT_PERCENTAGE_FIELD = By.id("initial_percentage");
    public static final By DOWNPAYMENT_FIELD = By.id("initial");
    public static final By REMINDER_PERCENTAGE_FIELD = By.id("reminder_percentage");
    public static final By REMINDER_FIELD = By.id("reminder");
    public static final By YEARS_DROPDOWN = By.name("years");
    public static final By MONTHS_DROPDOWN = By.name("months");
    public static final By INTEREST_RATE_FIELD = By.id("interest_rate");
    public static final By MONTHLY_PAYMENT_SUM = By.className("payment");
    public static final By VAT_SCHEDULING_DROPDOWN = By.id("vat_scheduling");

    public static void fillLeasePeriod(String leasePeriodYears, String leasePeriodMonths) {
        Find.findAndClick(YEARS_DROPDOWN);
        Wait.waitUntilVisibleAndClick(By.xpath("//option[@value='"+ leasePeriodYears +"']"));
        if (!Objects.equals(leasePeriodMonths, DataProviders.VALUE_0)) {
            Find.findAndClick(MONTHS_DROPDOWN);
            Wait.waitUntilVisibleAndClick(By.xpath("//option[@value='"+ leasePeriodMonths +"']"));
        }
    }

    public static void changeVATPaymentSchedule(String VATPaymentTime) {
        String VATPayment = Find.findAndGetText(VAT_SCHEDULING_DROPDOWN);
        if (!Objects.equals(VATPaymentTime, VATPayment)) {
            Find.findAndClick(VAT_SCHEDULING_DROPDOWN);
            Wait.waitUntilVisibleAndClick(By.xpath("//option[contains(text(),'" + VATPaymentTime + "')]"));
        }
    }

}
