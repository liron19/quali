package page.objects;

import base.BasicUiScreen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Step6Page extends BasicUiScreen {

    @FindBy(id = "notABotCaptchaResponse")
    public WebElement inputCaptcha;
    @FindBy(id = "notABotCaptchaSubmit")
    public WebElement submit;



    public Step6Page(WebDriver driver) {
        super(driver);
    }
}
