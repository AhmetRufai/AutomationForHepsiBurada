package utility;
/* if a method must be called in more than one class, that method is found here. */

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Utility {

    public static int generateRandomValue(Integer min, Integer max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static void iHaveOpenedWithBrowser(String baseUrl, String browser, VariablesFactory variables) {
        if (browser.toLowerCase().contains("chrome")) {
            WebDriverManager.chromedriver().arch32().setup();
            //For Decline Notification
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", prefs);
            variables.driver = new ChromeDriver(options);
        } else if (browser.toLowerCase().contains("firefox")) {
            WebDriverManager.firefoxdriver().arch32().setup();
            variables.driver = new FirefoxDriver();
        } else if (browser.toLowerCase().contains("explorer")) {
            WebDriverManager.iedriver().arch32().setup();
            variables.driver = new InternetExplorerDriver();
        } else System.out.println("Enter a valid browser name(chrome,firefox,explorer)");
        variables.driver.get(baseUrl);
        variables.driver.manage().window().maximize();

        boolean mTextSearch1 = variables.driver.getPageSource().contains("Sepetim");
        boolean mTextSearch2 = variables.driver.getPageSource().contains("Siparişlerim");
        if (!(mTextSearch1 || mTextSearch2)) {
            System.out.println("An error occurred, page failed to load!");
            System.exit(-1);
        }
    }
}
