package tron.tronscan.tronscanUi;

import org.testng.annotations.Test;
import tron.common.pages.BlockChainPage;
import tron.common.pages.NodesPage;

public class NodesTest {
  private BlockChainPage startPage = null;
  private NodesPage nodesPage = null;

  @Test
  public void test0001nodes() throws Exception{
    startPage = new BlockChainPage();
    startPage.openNodes();
    nodesPage = new NodesPage();
    System.out.println(nodesPage.getNodesNum());
  }
}
