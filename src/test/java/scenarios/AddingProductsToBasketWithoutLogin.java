package scenarios;
/*In this class, a product was added to the basket without login.*/

import cucumber.api.java.en.When;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import utility.VariablesFactory;

import java.util.Locale;

public class AddingProductsToBasketWithoutLogin {
    private static VariablesFactory variables = VariablesFactory.getInstance().getVariable(VariablesFactory.VariableType.PRIMARY);

    static {
        //The language of Java is set in English
        Locale.setDefault(new Locale("en", "EN"));
        Utility.iHaveOpenedWithBrowser("https://www.hepsiburada.com/", "chrome",variables);
    }

    private WebDriverWait wait = new WebDriverWait(variables.driver, 5, 100);

    @When("^adding products to the cart without login$")
    public void addingProductsToTheCartWithoutLogin() {
        int mForRandomProduct = Utility.generateRandomValue(1, 24); /*Here, a random number between 1-24 is generated to select a
        random product because 24 pieces of the product are showing on one page..*/
        String mRandomProduct = "//*/div[6]/div[3]/div/div/div/div/ul/li[" + mForRandomProduct + "]"; /*A random product selection
        was made to ensure that the test did not run for the same product each time.*/

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kitap-muzik-film-hobi")));
        variables.driver.findElement(By.id("kitap-muzik-film-hobi")).click();
        WebElement element = variables.driver.findElement(By.className("nav-home-wrapper"));
        wait.until(ExpectedConditions.visibilityOf(element)); /*This line will wait until the KİTAP, MÜZİK, FİLM, HOBİ panel is visible*/
        variables.driver.findElement(By.linkText("Uzaktan Kumandalı Araçlar")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("moreCategories")));
        variables.driver.findElement(By.linkText("Diğer Kategoriler")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*/a[contains(text(), 'Drone Yedek Parçaları')]")));
        variables.driver.findElement(By.partialLinkText("Drone Yedek Parçaları")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mRandomProduct)));
        variables.driver.findElement(By.xpath(mRandomProduct)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addToCart")));
        variables.driver.findElement(By.id("addToCart")).click();
    }
}
