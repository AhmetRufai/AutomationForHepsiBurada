package scenarios;
/*In this class, login to the site and add a product to the basket.*/

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.Utility;
import utility.VariablesFactory;

import java.util.Locale;

public class LoginAndAddProductsToBasket {
    private static VariablesFactory variables = VariablesFactory.getInstance().getVariable(VariablesFactory.VariableType.SECONDARY);

    static {
        //The language of Java is set in English
        Locale.setDefault(new Locale("en", "EN"));
        Utility.iHaveOpenedWithBrowser("https://www.hepsiburada.com/", "chrome", variables);
    }

    private WebDriverWait wait = new WebDriverWait(variables.driver, 5, 100);


    // In this method, the site is accessed.
    @When("^sign in as \"([^\"]*)\" and \"([^\"]*)\"$")
    public void signInAsAnd(String eMailAddress, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myAccount")));
        variables.driver.findElement(By.id("myAccount")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
        variables.driver.findElement(By.id("login")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        variables.driver.findElement(By.id("email")).sendKeys(eMailAddress);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        variables.driver.findElement(By.id("password")).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-login-submit")));
        variables.driver.findElement(By.cssSelector(".btn-login-submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myAccount")));
        int size = textCounter();
        if (size == 2) {
            System.out.println("Login Successful");
        } else {
            Assert.fail("Login Unsuccessful!!!");
        }
    }

    @And("^find \"([^\"]*)\" and add to basket$")
    public void findAndAddToBasket(String product) {
        String mXpathForFirstProduct = "//*/div[2]/div/div/div/ul/li[1]"; /*The first product in any product group when using the search bar*/
        String mXpathAddToBasketFromTheFirstVendor = "//*/tr[1]/td[3]/div/form/button";
        String mXpathAddToBasketFromTheSecondVendor = "//*/tr[2]/td[3]/div/form/button";
        boolean mCheckOtherVendors; /*I'm checking to see if "Diğer Satıcılar" text exists on the
        page. If there is only one vendor, this text does not appear on the page.*/
        String mXpathFilterBar = "//*/section/div[1]/div[2]/div/div/ul/li[1]/a";
        boolean mCloseAutoFilter; /*The results are automatically filtered when a single product appears. This is closed because it disrupts integrity.*/

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productSearch")));
        variables.driver.findElement(By.id("productSearch")).click();
        variables.driver.findElement(By.id("productSearch")).sendKeys(product);
        variables.driver.findElement(By.id("productSearch")).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mXpathFilterBar)));
        mCloseAutoFilter = variables.driver.getPageSource().contains("Filtreleri Temizle");
        if (mCloseAutoFilter) {
            variables.driver.findElement(By.id("btnClearFilters")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mXpathForFirstProduct)));
        variables.driver.findElement(By.xpath(mXpathForFirstProduct)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("merchantLists")));
        mCheckOtherVendors = variables.driver.findElement(By.xpath("//*[contains(.,.)]")).getText().contains("Diğer Satıcılar - ");
        if (mCheckOtherVendors) {
            variables.driver.findElement(By.xpath(mXpathAddToBasketFromTheFirstVendor)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-secondary")));
            variables.driver.findElement(By.className("btn-secondary")).click(); /*Back to the same product page to add the same product to basket from another vendor*/
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("merchantLists")));
            variables.driver.findElement(By.xpath(mXpathAddToBasketFromTheSecondVendor)).click();
        } else {
            variables.driver.findElement(By.id("addToCart")).click();
        }
    }

    private int textCounter() {
        WebElement body = variables.driver.findElement(By.tagName("body"));
        String bodyText = body.getText();
        int count = 0;
        while (bodyText.contains("Hesabım")) {
            count++;
            // We continue to search where we left off ...
            bodyText = bodyText.substring(bodyText.indexOf("Hesabım") + "Hesabım".length());
        }
        return count;
    }
}
