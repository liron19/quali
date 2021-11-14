package utilities;

import base.Browser;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import general.Constants;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RunWith(SpringRunner.class)
@Component
@Log4j
public class TestInfoLogger extends TestWatcher {
    private static final Logger log = Logger.getLogger(TestInfoLogger.class);
    public static ExtentReports extentReports = new ExtentReports(System.getProperty("user.dir") + "/Report/Report.html",
            true, NetworkMode.OFFLINE);
    public static ExtentTest extentTest;
    private static String dest;
    private static int count = 1;

    public TestInfoLogger() {
    }

    public static int getCount() {
        return count;
    }

    public static String captureScreen(String screenName, WebDriver driver) throws IOException {
        TakesScreenshot screen = (TakesScreenshot) driver;
        File src = screen.getScreenshotAs(OutputType.FILE);

        dest = System.getProperty("user.dir") + "/Report/" + screenName + ".png";
        File target = new File(dest);
        FileUtils.copyFile(src, target);
        return dest;
    }

    public static String getDest() {
        return dest;
    }

    public static void reporter(String status, WebElement webElement, String detail, WebDriver driver) {
        switch (status) {
            case "PASS":
                extentTest.log(LogStatus.PASS, getDetails(webElement, detail));
                break;
            case "FAIL":
                try {
                    captureScreen(extentTest.getTest().getName() + count, driver);
                    extentTest.log(LogStatus.FAIL, getDetails(webElement, detail) + " \nscreen shots below :"
                            + extentTest.addScreenCapture(extentTest.getTest().getName() + count + ".png"));
                    count++;
                } catch (Exception e) {
                    e.getMessage();
                    extentTest.log(LogStatus.FAIL, getDetails(webElement, detail) + "Take screen shoot failed");
                }
                break;
            case "WARNING":
                extentTest.log(LogStatus.WARNING, getDetails(webElement, detail));
                break;
            case "SUCCESS":
                extentTest.log(LogStatus.PASS, "The method " + detail + " Finish with success");
                break;
        }

    }

    private static String getDetails(WebElement webElement, String detail) {
        if (webElement != null)
            detail = detail.replace(detail, webElement + " " + detail);

        return detail;
    }

    @Override
    protected void starting(Description description) {
        super.starting(description);
        log.info("\nTest class " + description.getTestClass() + "\nname: " + description.getMethodName() + "\n STARTED");
        extentTest = extentReports.startTest(description.getMethodName(), description.getClassName());
        log.info("start time: " + extentTest.getStartedTime());
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);
        extentReports.endTest(extentTest);
        extentReports.flush();
        log.info("total time: " + printTotalTime(extentTest.getStartedTime(), extentTest.getEndedTime()));
        log.info("end time: " + extentTest.getEndedTime());
        log.info("test name: " + description.getMethodName() + "\nENDED \n\n");
        Browser.quit();
    }

    @Override
    protected void succeeded(Description description) {
        log.info("test status succeeded");
        super.succeeded(description);
        extentTest.log(LogStatus.PASS, description.getMethodName() + " : test succeeded");
    }

    @Override
    protected void failed(Throwable e, Description description) {
        log.info("test status: failed");
        super.failed(e, description);
        try {
            captureScreen(description.getMethodName() + getCount(), Browser.getDriver());
            extentTest.log(LogStatus.FAIL, Constants.TEST_METHOD + ": "
                    + description.getMethodName() + ",\n" + Constants.FAILED_METHOD + " :" + e.getStackTrace()[0].getMethodName()
                    + "\n" + Constants.FAILED_MESSAGE + ": " + e.getMessage() + " \n test failed, screen shots below "
                    + extentTest.addScreenCapture(extentTest.getTest().getName()
                    + getCount() + ".png"));
        } catch (Exception e1) {
            log.debug("Take screen shot failed");
            extentTest.log(LogStatus.FAIL, description.getMethodName() + ": test failed", e.getMessage());
        }

        Browser.quit();
    }

    @Override
    protected void skipped(AssumptionViolatedException e, Description description) {
        log.info("test status: skipped");
        super.skipped(e, description);
        Browser.quit();
    }

    private String printTotalTime(Date startDate, Date endDate) {
        LocalDateTime startLocalDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endLocalDateTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(startLocalDateTime, endLocalDateTime);
        return duration.getSeconds() + " sec";
    }

}
