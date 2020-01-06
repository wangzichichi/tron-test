package tron.tronscan.tronscanUi;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import tron.common.pages.AccountsPage;
import tron.common.utils.WebBrowser;


public class AccountsTest {
    AccountsPage accountsPage = new AccountsPage();

    @Test(enabled = true)
    public void totalAccountNumTest() throws InterruptedException {
        accountsPage.openTransfer();
        Thread.sleep(2000);
        //检查Total Accounts数值
        String numStr = "";
        String[] numList = accountsPage.getAccountNum().split(",");
        for (int i = numList.length -1; i >= 0; i --) {
            numStr = numList[i] + numStr;
        }
        int a = Integer.parseInt(numStr);
        if (a > 0) {
            System.out.println("Total Accounts" + accountsPage.getAccountNum());
        }else {
            System.out.println("Total Accounts 0" );
        }

        accountsPage.endTest();
    }

    @Test(enabled = true)
    public void totalAccountsPromptTest() {
        accountsPage.openTransfer();
        accountsPage.accountPageGotoSearch();
        accountsPage.endTest();
    }

    @Test(enabled = false)
    public void totalAccountInfoTest() {
        accountsPage.openTransfer();
        accountsPage.getAccountInfoNum();
        accountsPage.endTest();
    }

    @Test(enabled = true)
    public void totalAccountPromptInfoTest() {
        accountsPage.openTransfer();
        int accountPromptNum = Integer.parseInt(accountsPage.getAccountPromptInfo());
        if (accountPromptNum > 0) {
            System.out.println("总共有" + accountPromptNum + "账户");
        }else {
            System.out.println("总共有0个账户");
        }

        accountsPage.endTest();
    }

    @Test(enabled = true)
    public void accountInfoTest() {
        accountsPage.openTransfer();
        if (accountsPage.judgeAccountIfOrNotNull()) {
            System.out.println("信息显示正常");
        }else {
            System.out.println("信息显示异常");
        }
        accountsPage.endTest();
    }

    @Test(enabled = true)
    public void getTitleInfoTest() {
        accountsPage.openTransfer();
        if (accountsPage.getAccountTitleInfo() != null) {
            System.out.println(accountsPage.getAccountTitleInfo());
        }else {
            System.out.println("数据显示为null");
        }
        accountsPage.endTest();
    }

    @Test(enabled = true)
    public void getBottomListPageTest() {
        accountsPage.openTransfer();
        if (accountsPage.getBottomListPage()) {
            System.out.println("信息显示正常");
        }else {
            System.out.println("信息显示异常");
        }
        accountsPage.endTest();
    }

    @AfterSuite(enabled = true)
    public void accountTestEnd() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
