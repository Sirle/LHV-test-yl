package pageObjects;

import org.openqa.selenium.By;

public class General {

    public static final String URL_TO_LEASE_CALCULATOR = "https://www.lhv.ee/et/liising#kalkulaator";
    public static final String LANGUAGE_VALUE_ET = "ET";
    public static final String SCRIPT_ALERT_TEST = "<script>alert(“test”)</script>";

    public static final By LANGUAGE_SELECT_DROPDOWN = By.className("language-select-toggle");
    public static final By LANGUAGE_LINK_ET = By.linkText("et");

}
