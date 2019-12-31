package tron.tronscan.tronscanUi;

import org.testng.annotations.Test;
import tron.common.pages.BlockChainPage;

public class BlocksTest {
  private BlockChainPage nodesPage = null;

  @Test
  public void test0001block() throws Exception{
    nodesPage = new BlockChainPage();
    nodesPage.openNodes();
  }

}
