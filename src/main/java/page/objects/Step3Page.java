package page.objects;

import base.BasicUiScreen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Step3Page extends BasicUiScreen {

    @FindBy(xpath = "//*[@onclick='up();']")
    public WebElement up;
    @FindBy(xpath = "//*[@onclick='down();']")
    public WebElement down;
    @FindBy(xpath = "//*[@onclick='left();']")
    public WebElement left;
    @FindBy(xpath = "//*[@onclick='right();']")
    public WebElement right;
    @FindBy(id = "crystalMazeFormSubmit")
    public WebElement submit;

    public Step3Page(WebDriver driver) {
        super(driver);
    }

}
