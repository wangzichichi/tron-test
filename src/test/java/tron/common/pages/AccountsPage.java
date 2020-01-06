package tron.common.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;



public class AccountsPage extends BasePage{

    Actions builder = new Actions(driver);

    public String account_btn = "//*[@id=\"navbar-top\"]/ul[1]/li[2]/div/a[5]";
    public String totalAccounts = "//*[@id=\"root\"]/main/div[1]/div/div/div/h3";
    public String blockchain = "//*[@id=\"navbar-top\"]/ul[1]/li[2]";
    public String accountGotoSearch = "//*[@id=\"root\"]/main/div[2]/div/div[2]/div/div/div/div/ul[1]/li[10]/div[2]/input";
    public String totalAccountsInfo = "//*[@id=\"root\"]/main/div[2]/div/div[1]/div/text()";
    public String totalAccountPromptInfo = "//*[@id=\"root\"]/main/div[2]/div/div[1]/div";
    public String accountInfo = "//*[@id=\"root\"]/main/div[2]/div/div[2]/div/div/div/div/div/div/div";
    public String accountTitleInfo = "//*[@id=\"root\"]/main/div[2]/div/div[2]/div/div/div/div/div/div/div/table/thead";
    public String bottomSelectListPage = "//*[@id=\"root\"]/main/div[2]/div/div[2]/div/div/div/div/ul[2]";

    public void openTransfer() {
        builder.moveToElement(driver.findElement(By.xpath(blockchain))).build().perform();
        driver.findElement(By.xpath(account_btn)).click();
    }

    public String getAccountNum() {
        String accountNUm = driver.findElement(By.xpath(totalAccounts)).getText();
        return accountNUm;
    }

    public void accountPageGotoSearch() {
        driver.findElement(By.xpath(accountGotoSearch)).sendKeys("2\n");
    }

    public String getAccountInfoNum() {
        String numInfo = driver.findElement(By.xpath(totalAccountsInfo)).getText();
        System.out.println("::::::::   " + numInfo);
        return numInfo;
    }

    public String getAccountPromptInfo() {
       String info = driver.findElement(By.xpath(totalAccountPromptInfo)).getText();
       String[] infoStr = info.split(" ");
       return infoStr[1];
    }

    public Boolean judgeAccountIfOrNotNull() {
        if (driver.findElement(By.xpath(accountInfo)) != null) {
            return true;
        }else {
            return false;
        }
    }

    public String getAccountTitleInfo() {
        String titleInfo = driver.findElement(By.xpath(accountTitleInfo)).getText();
        return titleInfo;
    }

    public Boolean getBottomListPage() {
        if (driver.findElement(By.xpath(bottomSelectListPage)) != null) {
            return true;
        }else {
            return false;
        }
    }

    public void endTest() {
        driver.quit();
    }
}
