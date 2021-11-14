package page.objects;

import base.BasicUiScreen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Step4Page extends BasicUiScreen {

    @FindBy(id = "map")
    public WebElement mapElement;
    @FindBy(id = "OpenLayers_Layer_Vector_25_vroot")
    public WebElement circle;
    @FindBy(id = "mapsChallengeSubmit")
    public WebElement submit;



    public Step4Page(WebDriver driver) {
        super(driver);
    }

}