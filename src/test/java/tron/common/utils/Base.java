package tron.common.utils;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tron.common.pages.BasePage;

public class Base extends BasePage {

  public static String step(String element,String action){
    WebElement webElement = null;
    Actions builder = new Actions(driver);
    String text = "";
    if (element.contains(">")){
      webElement = driver.findElement(By.cssSelector(element));
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }else if (element.contains("//")){
      webElement = driver.findElement(By.xpath(element));
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    switch (action){
      case "click":webElement.click();
      case "gettext":text = webElement.getText();
      case "sendkeys":webElement.sendKeys();
      case "moveto":builder.moveToElement(webElement).build().perform();
    }
    return text;
  }

}
