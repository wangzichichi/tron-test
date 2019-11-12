package tron.common.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasePage {
  public static WebDriver driver;
  public static String MOVETO = "moveto";
  public static String CLICK = "click";
  public static String TEXT = "gettext";
  public static String SENDKEY = "sendkeys";


  public BasePage(){
    try {
      System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver");
      driver = new ChromeDriver();
      driver.get("https://tronscan.org/#/");
      System.out.println("成功打开谷歌浏览器！");
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }catch (Exception e){
      System.out.println(e);
    }

  }






}
