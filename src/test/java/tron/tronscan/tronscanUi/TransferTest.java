package tron.tronscan.tronscanUi;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import tron.common.pages.TransferPage;
import tron.common.utils.WebBrowser;

public class TransferTest {
    private String totalInfo = "//*[@id=\"root\"]/main/div/div/div[1]/div/text()";

    TransferPage transferPage = new TransferPage();

    @Test(enabled = false)
    public void totalTransactionsTest() throws InterruptedException {
        transferPage.openTransfer();
        Thread.sleep(5000);
        //查询Total of Transactions是不是为空
        transferPage.getContent(totalInfo);
    }

    @Test(enabled = true)
    public void addressTest() {
        transferPage.openTransfer();
        if (transferPage.listSize()) {
            System.out.println("数据不为空");
        }else {
            System.out.println("数据为空");
        }

        transferPage.transferPageEnd();
    }

    @Test(enabled = true)
    public void goSearchTest() {
        transferPage.openTransfer();
        transferPage.gotoInputText();
        transferPage.transferPageEnd();
    }

    @Test(enabled = false)
    public void totralTransferTest() {
        transferPage.openTransfer();
        transferPage.totalTransferInfo();
        transferPage.transferPageEnd();
    }

    @AfterSuite(enabled = true)
    public void transferTestEnd() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
