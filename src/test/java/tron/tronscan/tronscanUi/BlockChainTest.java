package tron.tronscan.tronscanUi;

import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.pages.NodesPage;

public class BlockChainTest {
  private NodesPage nodesPage = null;

  @Test
  public void test0001block() throws Exception{
    nodesPage = new NodesPage();
    nodesPage.open();
    System.out.println(nodesPage.getPrimarytext());
  }

}
