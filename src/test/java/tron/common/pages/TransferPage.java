package tron.common.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

public class TransferPage extends BasePage{

    public String transfer_btn = "//*[@id=\"navbar-top\"]/ul[1]/li[2]/div/a[4]/span";
    public String blockchain = "//*[@id=\"navbar-top\"]/ul[1]/li[2]";
    public String addressInfo = "//*[@id=\"root\"]/main/div/div/div[2]/div/div/div/div/div/div/div/table/tbody";
    public String gotoInput = "//*[@id=\"root\"]/main/div/div/div[2]/div/div/div/div/ul[1]/li[10]/div[2]/input";
    public String totalInfo = "//*[@id=\"root\"]/main/div/div/div[1]/div";

    Actions builder = new Actions(driver);

    public void openTransfer() {
        builder.moveToElement(driver.findElement(By.xpath(blockchain))).build().perform();
        driver.findElement(By.xpath(transfer_btn)).click();
    }

    public String getContent(String str) {
       String content = driver.findElement(By.xpath(str)).getText();
       System.out.println("打印content:= " + content);
       return content;
    }

    public String getAddressInfo() {
        String addressContent = driver.findElement(By.xpath(addressInfo)).getText();
        System.out.println("addressContent:= " + addressContent);
        return addressContent;
    }

    public boolean listSize() {
        if (driver.findElement(By.xpath(addressInfo)) != null) {
            return true;
        }else {
            return false;
        }
    }

    public void gotoInputText() {
        if (driver.findElement(By.xpath(gotoInput)) != null) {
            driver.findElement(By.xpath(gotoInput)).sendKeys("2\n");
        }
    }

    public String totalTransferInfo() {
        String info = driver.findElement(By.xpath(totalInfo)).getText();
        System.out.println("LLLLLLL   " + info);
        return info;
    }

    public void transferPageEnd() {
        driver.quit();
    }
}
