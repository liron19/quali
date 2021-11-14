package page.objects;

import base.BasicUiScreen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Step1Page extends BasicUiScreen {

    @FindBy(xpath = "//img[@src='/static/door.png']")
    public WebElement doorButton;
    @FindBy(id = "start")
    public WebElement startButton;
    @FindBy(xpath = "//button[text()='Proceed']")
    public List<WebElement> proceedButtons;

    public Step1Page(WebDriver driver) {
        super(driver);
    }

}
