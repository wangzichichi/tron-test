package tron.common.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tron.common.utils.Base;

public class BlockChainPage extends BasePage {


  public String dropdown = "//div[@id='navbar-top']/ul/li[2]/span/a/span";
  public String navtop = "//div[@id='navbar-top']/ul/li[1]/span/a/span";
  public String nodes_bt = ".nav-item:nth-child(2) .dropdown-item:nth-child(1) > span";
  public String blocks_bt = ".nav-item:nth-child(2) .dropdown-item:nth-child(2) > span";
  public String tronsaction_bt = ".nav-item:nth-child(2) .dropdown-item:nth-child(3) > span";


  public void openNodes(){
    Actions builder = new Actions(driver);
    builder.moveToElement(driver.findElement(By.xpath(dropdown))).click(driver.findElement(By.cssSelector(nodes_bt))).perform();
    builder.moveToElement(driver.findElement(By.xpath(navtop))).build().perform();
  }

  public void openBlocks(){
    Actions builder = new Actions(driver);
    builder.moveToElement(driver.findElement(By.xpath(dropdown))).click(driver.findElement(By.cssSelector(blocks_bt))).perform();
    builder.moveToElement(driver.findElement(By.xpath(navtop))).build().perform();
  }

  public void openTracsaction(){
    Actions builder = new Actions(driver);
    builder.moveToElement(driver.findElement(By.xpath(dropdown))).click(driver.findElement(By.cssSelector(tronsaction_bt))).perform();
    builder.moveToElement(driver.findElement(By.xpath(navtop))).build().perform();
  }

}
