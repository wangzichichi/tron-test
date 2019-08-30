package tron.tronscan.account.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.sun.xml.internal.rngom.digested.DDataPattern.Param;
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
  private JSONObject proposalContent;
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
  @Test(enabled = true, description = "List all the foundation addresses ")
  public void getFundTest() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("page_index","1");
    Params.put("per_page","20");
    response = TronscanApiList.getFundTest(tronScanNode,Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(Integer.valueOf(responseContent.get("page_index").toString()) >= 1);
    Assert.assertTrue(responseContent.containsKey("message"));

    //data json
    targetContent = responseContent.getJSONObject("data");
    Assert.assertTrue(Long.valueOf(targetContent.get("total").toString()) >= 0);
    //data Contain data json
    JSONArray dataArray = targetContent.getJSONArray("data");
    targetContent = dataArray.getJSONObject(0);
    //address
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
    //balance
    Assert.assertTrue(Long.valueOf(targetContent.get("balance").toString()) >= 0);
    Assert.assertTrue(targetContent.containsKey("key"));

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
//    TronscanApiList.disConnect();
  }

}
