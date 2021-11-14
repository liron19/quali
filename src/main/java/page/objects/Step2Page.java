package page.objects;

import base.BasicUiScreen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Step2Page extends BasicUiScreen {

    @FindBy(id = "aVideoSubmit")
    public WebElement videoSubmit;

    @FindBy(xpath = "//button[@aria-label='Mute (m)']")
    public WebElement muteButton;

    @FindBy(xpath = "//button[@aria-label='Play']")
    public WebElement playButton;

    @FindBy(id = "aVideoPlayer")
    public WebElement videoIframe;


    public Step2Page(WebDriver driver) {
        super(driver);
    }


}
