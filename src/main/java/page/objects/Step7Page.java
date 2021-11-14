package page.objects;

import base.BasicUiScreen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Step7Page extends BasicUiScreen {

    @FindBy(id = "socketGateMessage")
    public WebElement inputSocket;
    @FindBy(id = "//*[text()='Submit']")
    public WebElement submit;


    public Step7Page(WebDriver driver) {
        super(driver);
    }
}
