package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasicUiScreen {

    public BasicUiScreen(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
