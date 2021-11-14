package utilities;

import base.Browser;
import base.SharedUi;
import general.Constants;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@NoArgsConstructor
@ContextConfiguration(classes = {TestInfoLogger.class})
public class SeleniumUtils extends TestInfoLogger {

    public final Logger log = Logger.getLogger(SeleniumUtils.class.getName());
    public SharedUi sharedUi;

    @Rule
    @Autowired
    public TestInfoLogger testInfoLogger;

    @Rule
    public Timeout testTimeOut = new Timeout(Constants.TEST_TIMEOUT, TimeUnit.MINUTES);
    public WebElement element;

    //----------------------------Before&After Methods------------------------------

    // Init browser and shared ui before run
    @Before
    public void init() {
        Browser.initiateDriver();
        sharedUi = new SharedUi(Browser.getDriver());
    }

    // Flush in the end of run in order to erase any previous data on the report and
    // create a new report
    @After
    public void end() {
        // Flush method is used to erase any previous data on the report and create a
        // new report.
        TestInfoLogger.extentReports.flush();
    }


    //----------------------------Wait Methods------------------------------

    // Default wait
    public WebDriverWait waitFor() {
        return new WebDriverWait(Browser.getDriver(), 30L);
    }

    // Wait until page load
    public void waitPageLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = driver1 -> driver1 != null
                && ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete");
        waitFor().until(pageLoadCondition);
    }

    public void waitUntilElementNotPresented(WebElement element) {
        int counter = 0;
        while (elementToBeClickableFalseOrPositive(Browser.getDriver(), element) && counter < 20) {
            waitFewSeconds(300);
            counter++;
        }

        Assert.assertTrue("Element shouldn't be presented", !elementToBeClickableFalseOrPositive(Browser.getDriver(), element));
    }

    /**
     * Waiting for specific element
     *
     * @param webElement - By Id, Xpath, Accessibility and ClassName
     */
    public void elementToBeClickable(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), 10);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            log.debug(webElement + " is available for click");
            TestInfoLogger.reporter("PASS", webElement, "is available for click", Browser.getDriver());
        } catch (Exception var4) {
            log.error(webElement + " is not available for click");
            TestInfoLogger.reporter("WARNING", webElement, "is not available for click", Browser.getDriver());
        }
    }

    /**
     * Waiting for specific element by seconds
     *
     * @param webElement
     * @param seconds
     */
    public void elementToBeClickableBySeconds(WebElement webElement, int seconds) {

        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), seconds);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            System.out.println(webElement + " is available for click");
        } catch (Exception e) {
            System.out.println(webElement + " is not available for click");
        }
    }

    /**
     * @param webElement - By Id, Xpath, Accessibility and ClassName
     * @return true if element is exists, false if not
     */
    public boolean elementToBeClickableFalseOrPositive(WebDriver driver, WebElement webElement) {

        boolean isAppear = false;
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            isAppear = true;
        } catch (Exception e) {
            waitFewSeconds(1000);
        }

        return isAppear;
    }


    /**
     * Sleep in the test
     *
     * @param seconds - 1000 = second
     */
    public void waitFewSeconds(Integer seconds) {

        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param webElement - By Id, Xpath, Accessibility and ClassName
     * @param timeOut    - 1000 = second
     * @return true if element is exists, false if not
     */
    public boolean isElementPresented(WebElement webElement, int timeOut) {

        waitFewSeconds(timeOut);
        return elementToBeClickableFalseOrPositive(Browser.getDriver(), webElement);
    }

    //----------------------------Scroll Methods------------------------------

    // Scroll down in page
    public void scrollDown() {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) Browser.getDriver();
            jse.executeScript("window.scrollBy(0,250)");
        } catch (Exception var4) {
            Assert.fail("Couldn't scroll down");
        }
    }

    public void scrollDownFewTimes(int numOfScroll){
        for (int i=0;i<=numOfScroll;i++)
            scrollDown();
    }

    // Scroll up in page
    public void scrollUp() {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) Browser.getDriver();
            jse.executeScript("window.scrollBy(0,-250)");
        } catch (Exception var4) {
            Assert.fail("Couldn't scroll up");
        }
    }

    /**
     * @param webElement scroll down to given element
     */
    public void scrollToElement(WebElement webElement) {

        try {
            ((JavascriptExecutor) Browser.getDriver()).executeScript("arguments[0].scrollIntoView(true);", webElement);
            Thread.sleep(500);
            log.debug("Success to scroll to element: " + webElement);
            TestInfoLogger.reporter("PASS", webElement, "Success to scroll to element: " + webElement.toString(),
                    Browser.getDriver());
        } catch (Exception var4) {
            log.debug("Fail to  scroll to element: " + webElement);
            Assert.fail();
            TestInfoLogger.reporter("FAIL", webElement, "\nFailed to scroll to element: " + webElement.toString(),
                    Browser.getDriver());
        }
    }

    /**
     * Click on specific element with JS
     * <p>
     * - By Id, Xpath, Accessibility and ClassName
     */
    public void scrollToElementWithJS(WebElement webElement) {

        elementToBeClickable(webElement);

        try {

            JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
            waitFewSeconds(3000);
            js.executeScript("arguments[0].scrollIntoView(true);", webElement);
        } catch (Exception e) {
            log.debug("Fail to click on element: " + webElement);
            Assert.fail();
            TestInfoLogger.reporter("FAIL", webElement, "\nFailed to click on element: " + webElement.toString(),
                    Browser.getDriver());
        }

    }

    //----------------------------Click Methods------------------------------

    /**
     * Click on specific element with JS
     * <p>
     * - By Id, Xpath, Accessibility and ClassName
     */
    public void clickOnElementWithJS(WebElement webElement) {

        elementToBeClickable(webElement);

        try {
            JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", webElement);
            js.executeScript("arguments[0].click();", webElement);
            log.debug("Success to click on " + webElement);
        } catch (Exception e) {
            log.debug("Fail to click on element: " + webElement);
            Assert.fail();
            TestInfoLogger.reporter("FAIL", webElement, "\nFailed to click on element: " + webElement.toString(),
                    Browser.getDriver());
        }

    }
    public static void mainIframe(){
        Browser.getDriver().switchTo().defaultContent();
    }

    public void switchToIframe(WebElement iframe) {
        waitFewSeconds(2000);
        Browser.getDriver().switchTo().frame(iframe);
    }
    /**
     * Click on specific element
     *
     * @param webElement - By Id, Xpath, Accessibility and ClassName
     */
    public void clickOnElement(WebElement webElement) {

        elementToBeClickable(webElement);
        try {
            webElement.click();
            log.debug("Success to click on element: " + webElement);
            TestInfoLogger.reporter("PASS", webElement, "Success to click on element: " + webElement.toString(),
                    Browser.getDriver());
        } catch (Exception var4) {
            log.debug("Fail to click on element: " + webElement);
            Assert.fail();
            TestInfoLogger.reporter("FAIL", webElement, "\nFailed to click on element: " + webElement.toString(),
                    Browser.getDriver());
        }
    }

    /**
     * Click on specific element by seconds
     *
     * @param webElement - By Id, Xpath, Accessibility and ClassName
     */
    public void clickOnElementBySeconds(WebElement webElement, int sec) {

        elementToBeClickableBySeconds(webElement, sec);

        try {

            webElement.click();
            System.out.println("Success to click on " + webElement);
        } catch (Exception e) {
            System.out.println("Failed to click on " + webElement);
            Assert.fail();
            TestInfoLogger.reporter("FAIL", webElement, "\nFailed to click on element: " + webElement.toString(),
                    Browser.getDriver());
        }

    }

    /**
     * Select option in drop down list
     *
     * @param optionText      - the text of the required option in drop down list
     * @param dropDownElement - element of drop down list
     */
    public void selectOption(String optionText, WebElement dropDownElement) {
        clickOnElement(dropDownElement);
        Select oSelect = new Select(dropDownElement);
        oSelect.selectByVisibleText(optionText);
    }

    //----------------------------Send Keys Methods------------------------------

    /**
     * Set text in the field
     *
     * @param text       - string
     * @param webElement - By Id, Xpath, Accessibility and ClassName
     * @Param action - action on field - tab or enter
     * @Param keys - the action required
     */
    public void setTextInTextField(WebElement webElement, String text, boolean action, @Nullable String keys) {

        elementToBeClickable(webElement);

        try {
            webElement.clear();
            webElement.sendKeys(text);

            if (action)
                actionOnField(webElement, keys);

            log.debug("Success to send keys to " + webElement + ", the text is " + text);

        } catch (Exception e) {
            log.debug("Failed to send keys to " + webElement + ", the text is " + text);
            Assert.fail();
            TestInfoLogger.reporter("FAIL", webElement,
                    "Failed to send keys to " + webElement + ", the text is " + text, Browser.getDriver());
        }
    }

    public void actionOnField(WebElement element, String action) {

        switch (action) {

            case Constants.ENTER:
                element.sendKeys(Keys.ENTER);
                break;

            case Constants.TAB:
                element.sendKeys(Keys.TAB);
                break;

        }

    }

    //----------------------------Find Elements Methods------------------------------

    /**
     * @param byId
     * @return
     */
    public WebElement webElementById(String byId) {

        element = Browser.getDriver().findElement(By.id(byId));
        return element;
    }

    /**
     * @param byClassName
     * @return
     */
    public WebElement webElementClassName(String byClassName) {

        element = Browser.getDriver().findElement(By.className(byClassName));
        return element;

    }

    /**
     * @param byXpath
     * @return
     */
    public WebElement webElementXpath(String byXpath) {

        element = Browser.getDriver().findElement(By.xpath(byXpath));
        return element;
    }

    /**
     * @param byName
     * @return
     */
    public WebElement webElementByName(String byName) {

        element = Browser.getDriver().findElement(By.name(byName));
        return element;

    }

    /**
     * Return list of elements with the same identifier
     *
     * @param by - By Id, Xpath, Accessibility and ClassName
     */
    public List<WebElement> getWebElementsList(By by) {
        return Browser.getDriver().findElements(by);
    }

    //----------------------------Tabs Methods------------------------------

    /**
     * Change to requested tab
     *
     * @param destinationTab - Requested tab: 1 for first tab,2 for second tab and so on
     */
    public void changeTab(int destinationTab) {
        ArrayList<String> tabs = getOpenTabs();
        Assert.assertFalse(destinationTab + " is not a valid tab number", destinationTab < 1);
        Assert.assertTrue("Tab number " + destinationTab + " is not exist", destinationTab <= tabs.size());
        Browser.getDriver().switchTo().window(tabs.get(destinationTab - 1));
    }

    public void openNewTab() {
        ((JavascriptExecutor) Browser.getDriver()).executeScript("window.open()");
        ArrayList<String> tabs = getOpenTabs();
        Browser.getDriver().switchTo().window(tabs.get(tabs.size() - 1));
    }

    public ArrayList<String> getOpenTabs() {
        return new ArrayList(Browser.getDriver().getWindowHandles());
    }

    //----------------------------Frame Methods------------------------------

    /**
     * @param frameNumber - frame number destination
     */
    public void changeFrame(int frameNumber) {
        List<WebElement> frames = getWebElementsList(By.tagName("iframe"));
        Assert.assertTrue("Frame number " + frameNumber + " doesn't exist", frames.size() > frameNumber);
        Browser.getDriver().switchTo().frame(frameNumber);
    }
}
