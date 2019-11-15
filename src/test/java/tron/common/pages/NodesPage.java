package tron.common.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tron.common.utils.Base;

public class NodesPage extends BasePage {


  public String dropdown = "//div[@id='navbar-top']/ul/li[2]/span/a/span";
  public String navtop = "//div[@id='navbar-top']/ul/li[1]/span/a/span";
  public String nodes_bt = ".nav-item:nth-child(2) .dropdown-item:nth-child(1) > span";
  public String primary_tx = "//div[@id='navbar-top']/ul/li[2]/div/a/span";



  public void open(){
    Actions builder = new Actions(driver);
    builder.moveToElement(driver.findElement(By.xpath(dropdown))).click(driver.findElement(By.cssSelector(nodes_bt))).perform();
    builder.moveToElement(driver.findElement(By.xpath(navtop))).build().perform();
  }

  public String getPrimarytext(){
    WebDriverWait wait = new WebDriverWait(driver, 10);
    String primary = driver.findElement(By.xpath(primary_tx)).getText();
//    return Base.step(primary_tx,TEXT);wait.until(ExpectedConditions.visibilityOf(
    return primary;
  }

  public String getMostNodes(){
    return Base.step(primary_tx,TEXT);
  }

}
