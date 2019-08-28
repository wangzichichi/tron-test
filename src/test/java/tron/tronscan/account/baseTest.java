package tron.tronscan.account;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import tron.common.utils.WebBrowser;

public class baseTest {
  public String LoginURL = "http://*************";
  private static String URL = "https://tronscan.org/#/";
  WebBrowser webBrowser = new WebBrowser();
  public static WebDriver driver;
  public static By by;
  @BeforeSuite(enabled = false)
  public void start() throws Exception {
    try {

      driver = webBrowser.startChrome(URL);
//	driver = useBrowser.startIE(URL);
//	driver = useBrowser.startFirefox(URL);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Test(enabled = false)
  public void test() {
    System.out.println("test");
    Actions builder = new Actions(driver);
    builder.moveToElement(driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div/div[2]/div[2]/ul/li/div"))).build().perform();
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div/div[2]/div[2]/ul/li/div/ul/li[3]")).click();
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div/div[2]/div[2]/ul/li/div[2]/ul/li[1]/div[1]/input")).clear();
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div/div[2]/div[2]/ul/li/div[2]/ul/li[1]/div[1]/input"))
        .sendKeys("7400E3D0727F8A61041A8E8BF86599FE5597CE19DE451E59AED07D60967A5E25");
    driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div/div[2]/div[2]/ul/li/div[2]/ul/li[1]/button")).click();

    //String name = driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div[1]/div/div[1]/table/tbody/tr[2]/td/span")).getText();
    //System.out.println(name);
  }

  @AfterSuite(enabled = false)
  public void end() throws Exception {
    WebBrowser.tearDownBrowser();
  }

  public void moveToElement(WebDriver driver, By locator) {
    Actions builder = new Actions(driver);
    builder.moveToElement(driver.findElement(locator)).perform();
  }

}
