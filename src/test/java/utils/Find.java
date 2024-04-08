package utils;

import org.openqa.selenium.By;
import properties.Base;

public class Find extends Base {

    public static String findAndGetText(By by) {
        return webDriver.findElement(by).getText();
    }

    public static String findAndGetValue(By by) {
        return webDriver.findElement(by).getAttribute("value");
    }

    public static void findAndClick(By by) {
        webDriver.findElement(by).click();
    }

    public static boolean isDisplayed(By by) {
        return webDriver.findElement(by).isDisplayed();
    }

    public static void clearAndFillField(By fieldToClear, String keysToSend) {
        webDriver.findElement(fieldToClear).clear();
        webDriver.findElement(fieldToClear).sendKeys(keysToSend);
    }

}
