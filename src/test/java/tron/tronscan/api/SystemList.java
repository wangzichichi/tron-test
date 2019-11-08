package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;


@Slf4j
public class SystemList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private JSONObject tpsContent;
  private JSONArray properArray;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String, String> testAccount;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get system status")
  public void getSystemStatus() {
    //Get response
    response = TronscanApiList.getSystemStatus(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() >= 5);

    //database has block and confirmedBlock, confirmedBlock <= block
    targetContent = responseContent.getJSONObject("database");
    long databaseBlock = Long.valueOf(targetContent.get("block").toString());
    long databaseConfirmedBlock = Long.valueOf(targetContent.get("confirmedBlock").toString());
    Assert.assertTrue(databaseBlock >= databaseConfirmedBlock);

    //sync has one key:value,
    targetContent = responseContent.getJSONObject("sync");
    Assert.assertTrue(Double.valueOf(targetContent.get("progress").toString()) >= 95);

    //network type should be mainnet
    targetContent = responseContent.getJSONObject("network");
    Assert.assertTrue(targetContent.get("type").equals("mainnet"));

    //full block is equal databaseBlock
    targetContent = responseContent.getJSONObject("full");
    long full = Long.valueOf(targetContent.get("block").toString());
    log.info("full:" + full + " , databaseBlock:" + databaseBlock);
    Assert.assertEquals(full, databaseBlock);

    //full block is equal databaseBlock
    targetContent = responseContent.getJSONObject("solidity");
    long solidity = Long.valueOf(targetContent.get("block").toString());
    Assert.assertTrue(solidity == databaseConfirmedBlock);

    testAccount = TronscanApiList.generateAddress();
    log.info("privateKey:" + testAccount.get("privateKey"));
    log.info("hexAddress:" + testAccount.get("hexAddress"));
    log.info("address:" + testAccount.get("address"));


  }

  /**
   * constructor.获取系统tps信息
   */
  @Test(enabled = true, description = "List data synchronization tps")
  public void getSystemTps() {
    //Get response
    response = TronscanApiList.getSystemTps(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() >= 2);
    //type
    Assert.assertTrue(!responseContent.get("type").toString().isEmpty());
    //data
    targetContent = responseContent.getJSONObject("data");
    //blockHeight
    Assert.assertTrue(!targetContent.get("blockHeight").toString().isEmpty());
    //currentTps
    Assert.assertTrue(!targetContent.get("currentTps").toString().isEmpty());
    //maxTps
    Assert.assertTrue(!targetContent.get("maxTps").toString().isEmpty());

  }

  /**
   * constructor.获取homepage相关信息
   */
  @Test(enabled = true, description = "List data synchronization homepage")
  public void getSystemHomepage() {
    //Get response
    response = TronscanApiList.getSystemHomepage(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() >= 5);
    //node
    targetContent = responseContent.getJSONObject("node");
    //total
    Assert.assertTrue(targetContent.containsKey("total"));
    //code
    Assert.assertTrue(targetContent.containsKey("code"));
    //data
    JSONArray exchangeArray = responseContent.getJSONArray("priceEUR");
    targetContent = exchangeArray.getJSONObject(0);
    //name
    Assert.assertTrue(!targetContent.get("name").toString().isEmpty());
    //symbol
    Assert.assertTrue(!targetContent.get("symbol").toString().isEmpty());
    //price_usd
    Assert.assertTrue(Double.valueOf(targetContent.get("price_usd").toString()) >= 0);
    //price_eur
    Assert.assertTrue(Double.valueOf(targetContent.get("price_eur").toString()) >= 0);

    targetContent = responseContent.getJSONObject("yesterdayStat");
    Assert.assertTrue(!targetContent.get("data").toString().isEmpty());
    //priceETH
    JSONArray ethArray = responseContent.getJSONArray("priceETH");
    targetContent = ethArray.getJSONObject(0);
    //name
    Assert.assertTrue(!targetContent.get("name").toString().isEmpty());
    //symbol
    Assert.assertTrue(!targetContent.get("symbol").toString().isEmpty());
    //price_usd
    Assert.assertTrue(Double.valueOf(targetContent.get("price_usd").toString()) >= 0);
    //price_eth
    Assert.assertTrue(Double.valueOf(targetContent.get("price_eth").toString()) >= 0);

    //tps
    targetContent = responseContent.getJSONObject("tps");
    //type
    Assert.assertTrue(!targetContent.get("type").toString().isEmpty());
    //tps Contain data
    tpsContent = targetContent.getJSONObject("data");
    Assert.assertTrue(!tpsContent.get("currentTps").toString().isEmpty());
    Assert.assertTrue(!tpsContent.get("maxTps").toString().isEmpty());

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
