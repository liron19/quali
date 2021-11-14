package base;

import org.openqa.selenium.WebDriver;
import page.objects.*;


public class SharedUi {

    public Step3Page step3Page;
    public Step1Page step1Page;
    public Step2Page step2Page;
    public Step4Page step4Page;
    public Step6Page step6Page;
    public Step7Page step7Page;

    public SharedUi(WebDriver driver) {
        this.step3Page = new Step3Page(driver);
        this.step1Page = new Step1Page(driver);
        this.step2Page = new Step2Page(driver);
        this.step4Page = new Step4Page(driver);
        this.step6Page = new Step6Page(driver);
        this.step7Page = new Step7Page(driver);
    }
}
