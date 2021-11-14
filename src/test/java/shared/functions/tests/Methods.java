package shared.functions.tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utilities.SeleniumUtils;

import java.io.*;
import java.net.Socket;

public class Methods extends SeleniumUtils {

    public void step1() {
        clickOnElement(sharedUi.step1Page.doorButton);
        clickOnElement(sharedUi.step1Page.startButton);
        for (WebElement button : sharedUi.step1Page.proceedButtons) {
            clickOnElement(button);
            if (isElementPresented(sharedUi.step2Page.videoSubmit, 1))
                break;
        }
    }

    public void step2() {
        switchToIframe(sharedUi.step2Page.videoIframe);
        clickOnElement(sharedUi.step2Page.playButton);
        waitFewSeconds(10000);
        clickOnElement(sharedUi.step2Page.muteButton);
        mainIframe();
        clickOnElement(sharedUi.step2Page.videoSubmit);
    }

    public void step3() {
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.down);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.up);
        clickOnElement(sharedUi.step3Page.up);
        clickOnElement(sharedUi.step3Page.up);
        clickOnElement(sharedUi.step3Page.left);
        clickOnElement(sharedUi.step3Page.left);
        clickOnElement(sharedUi.step3Page.up);
        clickOnElement(sharedUi.step3Page.up);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.up);
        clickOnElement(sharedUi.step3Page.up);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.down);
        clickOnElement(sharedUi.step3Page.down);
        clickOnElement(sharedUi.step3Page.down);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.down);
        clickOnElement(sharedUi.step3Page.down);
        clickOnElement(sharedUi.step3Page.down);
        clickOnElement(sharedUi.step3Page.down);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.down);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.right);
        clickOnElement(sharedUi.step3Page.submit);
    }

    public void step4() {
        clickOnElement(sharedUi.step4Page.mapElement);
        sharedUi.step4Page.mapElement.sendKeys("i");
        for (int i = 0; i < 17; i++) {
            sharedUi.step4Page.mapElement.sendKeys(Keys.UP);
        }
        for (int i = 0; i < 47; i++) {
            sharedUi.step4Page.mapElement.sendKeys(Keys.LEFT);
        }
        clickOnElement(sharedUi.step4Page.submit);
    }

    public void step5() {
        setTextInTextField(sharedUi.step6Page.inputCaptcha, "", false, null);
        waitFewSeconds(10000);
        clickOnElement(sharedUi.step6Page.submit);
    }

    public void step6() throws IOException {
        Socket socket = new Socket("54.80.137.197", 5001);
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String text;
        do {
            text = reader.readLine();
            String reverseText = new StringBuilder(text).reverse().toString();

        } while (text.equals("'\\u0000' 0"));

        socket.close();
        setTextInTextField(sharedUi.step7Page.inputSocket, text, false, null);
        clickOnElement(sharedUi.step7Page.submit);
    }

}
