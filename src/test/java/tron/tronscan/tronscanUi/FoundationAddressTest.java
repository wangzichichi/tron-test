package tron.tronscan.tronscanUi;

import org.testng.annotations.Test;
import tron.common.pages.FoundationAddressesPage;

public class FoundationAddressTest {

    FoundationAddressesPage addressesPage = new FoundationAddressesPage();

    @Test(enabled = false   )
    public void totalTrxTest() {
        addressesPage.openFoundationPage();
        addressesPage.getFrozenTrxNum();
    }

    @Test(enabled = true)
    public void getUnfreezeTimeTest() {
        addressesPage.openFoundationPage();
        addressesPage.getUnfreezeTime();
        addressesPage.foundationAddressesPageEndTest();
    }

    @Test(enabled = true)
    public void getAddressListInfo() {
        addressesPage.openFoundationPage();
        addressesPage.getAddressInfo();
    }
}
