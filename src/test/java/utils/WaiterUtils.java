package utils;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.webdriver;
import static java.time.Duration.ofSeconds;
import static utils.Constants.COMPLETE;
import static utils.Constants.TIME_TO_WAIT;

import org.openqa.selenium.support.ui.WebDriverWait;

public class WaiterUtils {

    public static void waitForPageLoadComplete() {
        new WebDriverWait(webdriver().object(), ofSeconds(TIME_TO_WAIT)).until(webDriver -> (COMPLETE.equals(
                executeJavaScript(
                        "return document.readyState"))));
    }

}
