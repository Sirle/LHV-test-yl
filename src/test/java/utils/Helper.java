package utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import pageObjects.General;
import properties.Base;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static pageObjects.LeasePage.IS_VAT_INCLUDED_BUTTON;
import static pageObjects.LeasePage.IS_VAT_INCLUDED_CHECKBOX;
import static utils.Wait.wait;

@Slf4j
public class Helper extends Base {

    public static void handleCookieBanner() {
        boolean cookieBannerDisplayed = Find.isDisplayed(By.id("pirukas"));
        if (cookieBannerDisplayed) {
            Find.findAndClick(By.id("acceptPirukas"));
        }
    }

    public static String changeLanguageToEstonianIfNeeded() {
        String originalLanguage = Find.findAndGetText(General.LANGUAGE_SELECT_DROPDOWN);

        switch (originalLanguage) {
            case "ET" : break;
            case "EN" , "RU" : {
                Find.findAndClick(General.LANGUAGE_SELECT_DROPDOWN);
                Wait.waitUntilVisible(General.LANGUAGE_LINK_ET);
                Find.findAndClick(General.LANGUAGE_LINK_ET);
            }
        }

        return Find.findAndGetText(General.LANGUAGE_SELECT_DROPDOWN);
    }

    public static String calculateValueFromPercentage(double price, String downPaymentPercentage) {
        return String.valueOf(price * (Double.parseDouble(downPaymentPercentage) / 100));
    }

    public static void toggleVATIncluded(boolean VATIncluded) {
        boolean toggleOn = webDriver.findElement(IS_VAT_INCLUDED_CHECKBOX).isSelected();
        if (toggleOn != VATIncluded) {
            Find.findAndClick(IS_VAT_INCLUDED_BUTTON);
            log.info("Toggled VAT included: " + VATIncluded);
        } else log.info("VAT included already: " + VATIncluded);
    }

    public static String clickLinkAndSwitchToNewTab(By linkToClick) {
        int numberOfTabs;

        //Store the ID of the original window
        String originalWindow = webDriver.getWindowHandle();

        //Check that we don't have another window open already
        numberOfTabs = webDriver.getWindowHandles().size();
        assert numberOfTabs == 1;
        Find.findAndClick(linkToClick);

        //Wait for the new tab
        wait.until(numberOfWindowsToBe(numberOfTabs+1));

        //Loop through until we find a new window handle
        for (String windowHande : webDriver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHande)) {
                webDriver.switchTo().window(windowHande);
                break;
            }
        }

        return originalWindow;
    }

}
