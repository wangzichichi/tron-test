package tron.tronscan.tronscanUi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import tron.common.utils.WebBrowser;

public class DemoTest {
  private static String URL = "https://tronscan.org/#/";
  WebBrowser webBrowser = new WebBrowser();
  public static WebDriver driver;
  public static By by;
  @BeforeSuite(enabled = true)
  public void start() throws Exception {
    try {
      driver = webBrowser.startChrome(URL);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Test(enabled = true)
  public void test() {
    System.out.println("test");
    Actions builder = new Actions(driver);
    builder.moveToElement(driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div/div[2]/div[2]/ul/li/div"))).build().perform();
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div/div[2]/div[2]/ul/li/div/ul/li[3]")).click();
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div/div[2]/div[2]/ul/li/div[2]/ul/li[1]/div[1]/input")).clear();
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div/div[2]/div[2]/ul/li/div[2]/ul/li[1]/div[1]/input"))
        .sendKeys("7400E3D0727F8A61041A8E8BF86599FE5597CE19DE451E59AED07D60967A5E25");
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div/div[2]/div[2]/ul/li/div[2]/ul/li[1]/button")).click();

  }

  @AfterSuite(enabled = true)
  public void end() throws Exception {
    WebBrowser.tearDownBrowser();
  }

  public void moveToElement(WebDriver driver, By locator) {
    Actions builder = new Actions(driver);
    builder.moveToElement(driver.findElement(locator)).perform();
  }

}
