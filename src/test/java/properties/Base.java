package properties;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.util.Properties;

public class Base {

    public static WebDriver webDriver;
    public static Properties appProps;

    @SneakyThrows
    @BeforeSuite
    public void suiteSetup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-fullscreen");

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";

        appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        System.setProperty("webdriver.chrome.driver", appProps.getProperty("chromedriver"));
        webDriver = new ChromeDriver(options);
    }

    @AfterSuite
    public void suiteTearDown() {
        webDriver.quit();
    }

}
