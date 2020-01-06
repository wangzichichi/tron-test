package tron.tronscan.tronscanUi;

import org.testng.annotations.Test;
import tron.common.pages.BlockChainPage;

public class TransactionTest {
  private BlockChainPage transactionPage = null;

  @Test
  public void test0001transaction() throws Exception{
    transactionPage = new BlockChainPage();
    transactionPage.openTracsaction();
  }
}
