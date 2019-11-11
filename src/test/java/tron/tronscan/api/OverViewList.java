package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

/**
 * ${params}
 *
 * @Author:jh
 * @Date:2019-08-29 19:03
 */
public class OverViewList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String, String> testAccount;


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Blockchain data overview in history")
  public void getOverViewList() {
    response = TronscanApiList.getOverViewList(tronScanNode);
    //log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //two object "success" and "data"
    Assert.assertTrue(Boolean.valueOf(responseContent.getString("success")));
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    targetContent = exchangeArray.getJSONObject(0);
    //data
    Assert.assertTrue(targetContent.containsKey("date"));
    //avgBlockTime
    Assert.assertTrue(Long.valueOf(targetContent.get("avgBlockTime").toString()) > 0);
    //totalBlockCount 区块数
    Assert.assertTrue(Long.valueOf(targetContent.get("totalBlockCount").toString()) > 1000);
    //totalTransaction 交易数
    Assert.assertTrue(Long.valueOf(targetContent.get("totalTransaction").toString()) > 0);
    //blockchainSize
    Assert.assertTrue(Long.valueOf(targetContent.get("blockchainSize").toString()) > 1000);
    //avgBlockSize
    Assert.assertTrue(Long.valueOf(targetContent.get("avgBlockSize").toString()) > 10);
    //
    Assert.assertFalse(targetContent.get("newTransactionSeen").toString().isEmpty());
    Assert.assertFalse(targetContent.get("newAddressSeen").toString().isEmpty());
    Assert.assertFalse(targetContent.get("totalAddress").toString().isEmpty());
    Assert.assertFalse(targetContent.get("newBlockSeen").toString().isEmpty());

  }
  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}