package base;

import general.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import static general.Constants.*;

public class Browser {

    public static String isRemoteDriver;
    private static WebDriver driver;
    private static String url = URL;

    // Return the driver
    public static WebDriver getDriver() {
        return driver;
    }

    // Initiate the driver
    public static WebDriver initiateDriver() {
        if (driver == null) {
            isRemoteDriver = "false";
            WebDriverManager.chromedriver().setup();
            switch (TEST_DRIVER_TYPE) {
                case CHROME_DRIVER_TYPE:
//                System.setProperty(Constants.CHROME_WEB_DRIVER, Constants.CHROME_DRIVER_LOCATION);
                    driver = new ChromeDriver();
                    break;
                case FIREFOX_DRIVER_TYPE:
                    System.setProperty(Constants.FIREFOX_WEB_DRIVER, Constants.FIREFOX_DRIVER_LOCATION);
                    driver = new FirefoxDriver();
                case Constants.INTERNET_EXPLORER:
                    System.getProperty(Constants.INTERNET_EXPLORER_WEB_DRIVER, Constants.INTERNET_EXPLORER_DRIVER_LOCATION);
                    driver = new InternetExplorerDriver();
            }

            driver.manage().window().maximize();
        }
        driver.get(url);
        return driver;

    }

    /**
     * Open url
     *
     * @param url - url destination
     */
    public static void open(String url) {
        driver.get(url);
    }

    // Close driver
    public static void close() {
        driver.close();
    }

    // Quit driver
    public static void quit() {
        driver.quit();
    }


}
