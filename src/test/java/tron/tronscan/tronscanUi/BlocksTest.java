package tron.tronscan.tronscanUi;

import org.testng.annotations.Test;
import tron.common.pages.BlockChainPage;

public class BlocksTest {
  private BlockChainPage blocksPage = null;

  @Test
  public void test0001blocks() throws Exception{
    blocksPage = new BlockChainPage();
    blocksPage.openBlocks();
  }

}
