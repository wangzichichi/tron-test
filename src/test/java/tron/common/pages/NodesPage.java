package tron.common.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class NodesPage  {
  public  WebDriver driver;
  public  String nodes = ".text-secondary";

  public  String getNodesNum() throws Exception{
    Thread.sleep(10000);
    String num = driver.findElement(By.cssSelector(nodes)).toString();
    return num;
  }
}
