package tron.common.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import tron.common.utils.Base;

public class FoundationAddressesPage extends BasePage{
    Actions builder = new Actions(driver);

    public String blockchain = "//*[@id=\"navbar-top\"]/ul[1]/li[2]";
    public String fundationAddressPage = "//*[@id=\"navbar-top\"]/ul[1]/li[2]/div/a[7]";
    public String totalTrxInfo = "//*[@id=\"root\"]/main/div[1]/div[1]/div/div";
    public String frozenTrxNum = "//*[@id=\"root\"]/main/div[1]/div[3]/div/div/h3/span";
    public String unfreezeTime = "//*[@id=\"root\"]/main/div[1]/div[4]/div/div/h3";
    public String addressList = "//*[@id=\"root\"]/main/div[2]/div/div/div/div/div[2]/div/div/div/div/div/table";
    public String totalAddressNum = "//*[@id=\"root\"]/main/div[2]/div/div/div/div/div[1]/text()[1]";

    public void openFoundationPage() {
        builder.moveToElement(driver.findElement(By.xpath(blockchain))).build().perform();
        driver.findElement(By.xpath(fundationAddressPage)).click();
    }

    public String getFrozenTrxNum() {
        String trxNum = driver.findElement(By.xpath(frozenTrxNum)).getText();
        System.out.println(";;;;;;   " + trxNum);
        return trxNum;
    }

    public String getUnfreezeTime() {
        String planNum = driver.findElement(By.xpath(unfreezeTime)).getText();
        if (planNum != null) {
            return planNum;
        }else {
            System.out.println("冻结时间异常");
            return null;
        }
    }

    public Boolean addressListNum() {
        if (driver.findElement(By.xpath(addressList)).getSize() != null) {

            return true;
        }else {
            return false;
        }
    }

    public String getAddressInfo() {
        String info = driver.findElement(By.xpath(totalAddressNum)).getText();
        System.out.println(",,,,,,,    " + info);
        return info;
    }

    public void foundationAddressesPageEndTest() {
        driver.quit();
    }
}
