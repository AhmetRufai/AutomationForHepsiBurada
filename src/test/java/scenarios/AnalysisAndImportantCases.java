package scenarios;
/*
Teslimat Seçenekleri sayfasında "Tahmini Kargoya Veriliş" süresi ve gönderecek olan firmaya göre gruplandırılma yapılıyor.
Case1: Eğer 2 ürün aynı fakat satıcıları farklı ise bu 2 ürün farklı teslimat grubları altında bulunur.
Case2: Eğer 2 ürünün satıcıları aynı fakat kargoya veriliş süreleri farklı ise bu 2 ürün farklı teslimat grubları altında bulunur.
Case3: Eğer 2 ürünün kargoya veriliş süresi ve satıcı aynı ise, bu 2 ürün aynı teslimat grubu altında bulunur.
Case4: Eğer 2 ürünün satıcıları farklı fakat kargoya veriliş süreleri aynı ise bu 2 ürün farklı teslimat grubları altında bulunur.
Case5: Eğer 2 ürünün satıcıları ve kargoya veriliş süreleri farklı ise bu 2 ürün farklı teslimat grubları altında bulunur.
*/

import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utility.VariablesFactory;

import java.util.ArrayList;
import java.util.List;

public class AnalysisAndImportantCases {
    private static VariablesFactory variables = VariablesFactory.getInstance().getVariable(VariablesFactory.VariableType.SECONDARY);
    private WebDriverWait wait = new WebDriverWait(variables.driver, 5, 100);
    private int counterForMainPage;

    @When("^analysis and important cases$")
    public void analysisAndImportantCases() {
        String mXpathFilterForHepsiBurada = "//*[@alt=\"Hepsiburada\"][@title=\"Hepsiburada\"]";
        String mXpathFilterForAnother = "//*/li[7]/ol/li[2]";
        String mXpathGroupSize = "//*[@id=\"delivery-container\"]/div/div[3]/ul/li[1]/div/div[1]/ul";
        int mOldChildrenSize;
        int mNewChildrenSize;
        List<String> mOldSameProductsSizeList;
        List<String> mNewSameProductsSizeList;

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("logo-hepsiburada")));
        variables.driver.findElement(By.className("logo-hepsiburada")).click();
        addProductToBasket("Noki Fosforlu Kalem 5 Li 90005Pvc", mXpathFilterForHepsiBurada);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mXpathGroupSize)));
        mOldChildrenSize = variables.driver.findElements(By.xpath("." + mXpathGroupSize)).size(); /*product=a,vendor=x,deliver=1*/

        addProductToBasket("Noki Fosforlu Kalem 5 Li 90005Pvc", mXpathFilterForAnother);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mXpathGroupSize)));
        mNewChildrenSize = variables.driver.findElements(By.xpath("." + mXpathGroupSize)).size(); /*product=a,vendor=y,deliver=1*/
        if (mOldChildrenSize == mNewChildrenSize) {//****************Case1******************
            System.out.println("Grouping Successful for Case1 and Case4");
        } else {
            Assert.fail("Grouping Unsuccessful for Case1 and Case4!!!");
        }

        addProductToBasket("Microsoft Office 365 Bireysel Abonelik – 1 Kullanıcı", mXpathFilterForHepsiBurada);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mXpathGroupSize)));
        mNewChildrenSize = variables.driver.findElements(By.xpath("." + mXpathGroupSize)).size(); /*product=b,vendor=x,deliver=2*/
        if (mOldChildrenSize == mNewChildrenSize) {//****************Case2******************
            System.out.println("Grouping Successful for Case2");
        } else {
            Assert.fail("Grouping Unsuccessful for Case2!!!");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mXpathGroupSize)));
        mOldSameProductsSizeList = findSameProductsSameVendorList();
        addProductToBasket("Oral-B Vitality Plus Şarj Edilebilir Diş Fırçası", mXpathFilterForHepsiBurada);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mXpathGroupSize)));
        mNewChildrenSize = variables.driver.findElements(By.xpath("." + mXpathGroupSize)).size(); /*product=c,vendor=x,deliver=1*/
        mNewSameProductsSizeList = findSameProductsSameVendorList();
        if (((mOldChildrenSize + 1 == mNewChildrenSize) && (mOldSameProductsSizeList.equals(mNewSameProductsSizeList))) ||
                ((mOldChildrenSize == mNewChildrenSize) && !(mOldSameProductsSizeList.equals(mNewSameProductsSizeList)))) {//****************Case3******************
            /*(the number of rows in the group increased AND the number of products did not change) OR
            (the number of rows in the group has not changed AND the number of products has changed) checked with "if" check*/
            System.out.println("Grouping Successful for Case3");
        } else {
            Assert.fail("Grouping Unsuccessful for Case3!!!");
        }
    }


    private void addProductToBasket(String product, String filter) {/*This method was used to add A product from the X vendor to the basket.*/
        String xpathForFirstProduct = "//*/div[1]/div[4]/div/div/div/div/ul/li[1]";

        if (counterForMainPage > 0) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
            variables.driver.findElement(By.id("logo")).click();
        }
        counterForMainPage += 1;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productSearch")));
        variables.driver.findElement(By.id("productSearch")).click();
        variables.driver.findElement(By.id("productSearch")).sendKeys(product);
        variables.driver.findElement(By.id("productSearch")).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(filter)));
        variables.driver.findElement(By.xpath(filter)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathForFirstProduct)));
        variables.driver.findElement(By.xpath(xpathForFirstProduct)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addToCart")));
        variables.driver.findElement(By.id("addToCart")).click();
        String xpathBtnForCompleteShopping = "//*[@id=\"short-summary\"]/div[1]/div[2]/button";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathBtnForCompleteShopping)));
        variables.driver.findElement(By.xpath(xpathBtnForCompleteShopping)).click();
    }

    private List<String> findSameProductsSameVendorList() {/*This method returns the list of the number of products added to the cart from the vendor x.*/
        List<WebElement> mList;
        List<String> mListText = new ArrayList<>();

        String xpathProductsSizeForSameProductSameVendor = "//*/li[*]/div[1]/span";
        mList = variables.driver.findElements(By.xpath(xpathProductsSizeForSameProductSameVendor));
        for (WebElement webElement : mList) {
            mListText.add(webElement.getText());
        }
        return mListText;
    }
}
