package utils;

import org.testng.annotations.DataProvider;
import properties.Base;

import static pageObjects.LeasePage.*;

public class DataProviders extends Base {

    public static final String VALUE_0 = "0";
    public static final String VALUE_0_00 = "0.00";
    public static final String VALUE_1 = "1";
    public static final String VALUE_5 = "5";
    public static final String VALUE_6 = "6";
    public static final String VALUE_10 = "10";
    public static final String VALUE_25 = "25";
    public static final String VALUE_75 = "75";

    public static final String YEARS_7 = "84";
    public static final String KOOS_SISSEMAKSEGA = "Koos sissemaksega";
    public static final String AJATAMINE_KOLMELE_KUULE = "Ajatamine kolmele kuule";
    public static final String TASUMINE_KOLMANDAL_KUUL = "Tasumine kolmandal kuul";

    static double minPrice = 7500;
    static double maxPrice = 6400000;

    @DataProvider(name = "financialLeaseWithoutVAT")
    public static Object[][] dpMethodFinancial(){
        return new Object[][] {
                //{price, downPaymentPercent, leasePeriodYears, leasePeriodMonths, interest, residualValue, expectedMonthlyPayment}
                {minPrice, VALUE_10, VALUE_0, VALUE_6, VALUE_5, VALUE_0, "1141.46"},
                {minPrice, VALUE_10, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "95.40"},
                {minPrice, VALUE_10, VALUE_0, VALUE_6, VALUE_1, VALUE_0, "1128.28"},
                {minPrice, VALUE_10, YEARS_7, VALUE_0, VALUE_25, VALUE_0, "170.85"},
                {minPrice, VALUE_10, VALUE_0, VALUE_6, VALUE_5, VALUE_25, "832.20"},
                {maxPrice, VALUE_10, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "81411.31"},
                {minPrice, VALUE_75, VALUE_0, VALUE_6, VALUE_5, VALUE_0, "317.07"},
                {minPrice, VALUE_75, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "26.50"}};
    }

    @DataProvider(name = "operationalLeaseWithVAT")
    public static Object[][] dpMethodOperationalWithVAT(){
        return new Object[][] {
                //{price, downPaymentPercent, leasePeriodYears, leasePeriodMonths, interest, residualValuePercent, expectedMonthlyPayment}
                {minPrice, VALUE_10, VALUE_0, VALUE_6, VALUE_5, VALUE_0, "1138.48"},
                {minPrice, VALUE_10, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "92.57"},
                {minPrice, VALUE_10, VALUE_0, VALUE_6, VALUE_1, VALUE_0, "1127.69"},
                {minPrice, VALUE_10, YEARS_7, VALUE_0, VALUE_25, VALUE_0, "151.89"},
                {minPrice, VALUE_10, VALUE_0, VALUE_6, VALUE_5, VALUE_25, "828.64"},
                {maxPrice, VALUE_10, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "78993.38"},
                {minPrice, VALUE_75, VALUE_0, VALUE_6, VALUE_5, VALUE_0, "316.24"},
                {minPrice, VALUE_75, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "25.71"}};
    }

    @DataProvider(name = "operationalLeaseWithoutVAT")
    public static Object[][] dpMethodOperationalWithoutVAT(){
        return new Object[][] {
                //{price, downPaymentPercent, leasePeriodYears, leasePeriodMonths, interest, residualValuePercent, expectedMonthlyPayment}
                {minPrice, VALUE_10, VALUE_0, VALUE_6, VALUE_5, VALUE_0, "1388.95"},
                {minPrice, VALUE_10, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "112.93"},
                {minPrice, VALUE_10, VALUE_0, VALUE_6, VALUE_1, VALUE_0, "1375.78"},
                {minPrice, VALUE_10, YEARS_7, VALUE_0, VALUE_25, VALUE_0, "185.31"},
                {minPrice, VALUE_10, VALUE_0, VALUE_6, VALUE_5, VALUE_25, "1010.94"},
                {maxPrice, VALUE_10, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "96371.93"},
                {minPrice, VALUE_75, VALUE_0, VALUE_6, VALUE_5, VALUE_0, "385.82"},
                {minPrice, VALUE_75, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "31.37"}};
    }

    @DataProvider(name = "legalEntityFinancialLeaseWithVAT")
    public static Object[][] dpMethodLegalFinancialWithVAT(){
        return new Object[][] {
                //{price, VATPaymentTime, downPaymentPercent, leasePeriodYears, leasePeriodMonths, interest, residualValuePercent, expectedMonthlyPayment}
                {minPrice, KOOS_SISSEMAKSEGA, VALUE_10, VALUE_0, VALUE_6, VALUE_5, VALUE_0, "935.62"},
                {minPrice, AJATAMINE_KOLMELE_KUULE, VALUE_10, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "77.84"},
                {minPrice, TASUMINE_KOLMANDAL_KUUL, VALUE_10, VALUE_0, VALUE_6, VALUE_1, VALUE_0, "721.86"},
                {minPrice, KOOS_SISSEMAKSEGA, VALUE_10, YEARS_7, VALUE_0, VALUE_25, VALUE_0, "140.04"},
                {minPrice, AJATAMINE_KOLMELE_KUULE, VALUE_10, VALUE_0, VALUE_6, VALUE_5, VALUE_25, "511.17"},
                {maxPrice, TASUMINE_KOLMANDAL_KUUL, VALUE_10, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "66276.13"},
                {minPrice, AJATAMINE_KOLMELE_KUULE, VALUE_75, VALUE_0, VALUE_6, VALUE_5, VALUE_0, "227.89"},
                {minPrice, KOOS_SISSEMAKSEGA, VALUE_75, YEARS_7, VALUE_0, VALUE_5, VALUE_0, "21.72"},
                {minPrice, AJATAMINE_KOLMELE_KUULE, VALUE_10, VALUE_0, VALUE_6, VALUE_1, VALUE_0, "809.81"},
                {minPrice, TASUMINE_KOLMANDAL_KUUL, VALUE_75, YEARS_7, VALUE_0, VALUE_25, VALUE_0, "38.78"}};
    }

    @DataProvider(name = "fieldsToFill")
    public static Object[][] dpFieldsToFill(){
        return new Object[][] {{PRICE_FIELD, 9}, {DOWNPAYMENT_FIELD, 6}, {DOWNPAYMENT_PERCENTAGE_FIELD, 5}, {INTEREST_RATE_FIELD, 4}, {REMINDER_FIELD, 6}, {REMINDER_PERCENTAGE_FIELD, 5}};
    }

}
