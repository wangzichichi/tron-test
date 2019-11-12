package tron.common.pages;

import tron.common.utils.Base;

public class NodesPage extends BasePage {


  public String dropdown = "//div[@id='navbar-top']/ul/li/a/span";
  public String nodes_bt = ".nav-item:nth-child(1) > .dropdown-menu > .dropdown-item:nth-child(1) > span";
  public String primary_tx = ".text-primary";



  public void open(){
    Base.step(dropdown,MOVETO);
    Base.step(nodes_bt,CLICK);
  }

  public String getPrimarytext(){
    return Base.step(primary_tx,TEXT);
  }

  public String getMostNodes(){
    return Base.step(primary_tx,TEXT);
  }

}
