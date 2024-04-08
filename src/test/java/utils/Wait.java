package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import properties.Base;

import java.time.Duration;

public class Wait extends Base {

    public static WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));

    public static void waitUntilVisible(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void waitUntilVisibleAndClick(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by)).click();
    }

}
