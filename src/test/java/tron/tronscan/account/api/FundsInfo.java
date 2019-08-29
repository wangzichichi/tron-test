package tron.tronscan.account.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.utils.Configuration;
import tron.common.utils.Utils;
import tron.common.TronscanApiList;

@Slf4j
public class FundsInfo {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf").getStringList("tronscan.ip.list")
      .get(0);
  /**
   * constructor.
   */
  @Test(enabled = true, description = "List the transactions related to a specified account")
  public void test01FundsInfo() {
    //Get response
    response = TronscanApiList.getFundsInfo(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("genesisBlockIssue"));
    Assert.assertTrue(responseContent.containsKey("totalBlockPay"));
    Assert.assertTrue(responseContent.containsKey("totalNodePay"));
    Assert.assertTrue(responseContent.containsKey("burnPerDay"));
    Assert.assertTrue(responseContent.containsKey("burnByCharge"));
    Assert.assertTrue(responseContent.containsKey("totalTurnOver"));
    Assert.assertTrue(responseContent.containsKey("fundSumBalance"));
    Assert.assertTrue(responseContent.containsKey("donateBalance"));
    Assert.assertTrue(responseContent.containsKey("fundTrx"));
    Assert.assertTrue(responseContent.containsKey("turnOver"));
  }


  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
//    TronscanApiList.disConnect();
  }

}
