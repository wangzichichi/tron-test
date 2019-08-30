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
public class TransactionList {

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
  public void test01getBlockDetail() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort","-number");
    Params.put("limit","20");
    Params.put("count","true");
    Params.put("start","0");
    Params.put("address","TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9");
    response = TronscanApiList.getTransactionList(tronScanNode,Params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.containsKey("rangeTotal"));
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(responseObject.containsKey("hash"));
    Assert.assertTrue(responseObject.containsKey("block"));
    Assert.assertTrue(responseObject.containsKey("timestamp"));
    Assert.assertTrue(responseObject.containsKey("contractType"));
    Assert.assertTrue(responseObject.containsKey("confirmed"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("ownerAddress")).matches());
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("toAddress")).matches());

    responseObject = responseObject.getJSONObject("contractData");
    Assert.assertTrue(responseObject.containsKey("amount"));
    Assert.assertTrue(responseObject.containsKey("asset_name"));
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("owner_address")).matches());
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("to_address")).matches());
  }
  /**
   * constructor.
   */
  @Test(enabled = true, description = "List a single the exchange pair's transaction records ")
  public void getTransactionTest(){
    Map<String, String> Params = new HashMap<>();
    Params.put("sort","-timestamp");
    Params.put("limit","20");
    Params.put("count","true");
    Params.put("start","0");
    response = TronscanApiList.getTransactionTest(tronScanNode,Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data","rangeTotal"
    Assert.assertTrue(responseContent.size() >= 3);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    Assert.assertTrue(rangeTotal >= total);

    targetContent = exchangeArray.getJSONObject(0);
    //exchangeID
    Assert.assertTrue(Integer.valueOf(targetContent.get("exchangeID").toString()) >= 1);
    //blockID
    Assert.assertTrue(Long.valueOf(targetContent.get("blockID").toString()) >= 1);
    //blockID
    Assert.assertTrue(Long.valueOf(targetContent.get("tokenID").toString()) >= 10000);
    //createTime
    Assert.assertTrue(targetContent.containsKey("createTime"));
    //hash
    Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
    Assert.assertTrue(patternHash.matcher(targetContent.getString("trx_hash")).matches());
    //quant
    Assert.assertTrue(targetContent.containsKey("quant"));
    //address
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("creatorAddress")).matches());
    //confirmed
    Assert.assertTrue(Boolean.valueOf(targetContent.getString("confirmed")));

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
