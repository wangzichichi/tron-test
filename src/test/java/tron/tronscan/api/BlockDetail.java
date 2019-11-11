package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class BlockDetail {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);

  /**
   * constructor
   */
  @Test(enabled = true, description = "Get a single block's detail")
  public void test01getBlockDetail() {
    //Get response
    Map<String, String> params = new HashMap<>();
    String blockNumber = "111112";
    params.put("number", blockNumber);
    response = TronscanApiList.getBlockDetail(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("service_type"));
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(responseObject.containsKey("hash"));
    Assert.assertTrue(responseObject.containsKey("size"));
    Assert.assertTrue(responseObject.containsKey("timestamp"));
    Assert.assertTrue(responseObject.containsKey("txTrieRoot"));
    Assert.assertTrue(responseObject.containsKey("parentHash"));
    Assert.assertTrue(responseObject.containsKey("witnessId"));
    Assert.assertTrue(responseObject.containsKey("nrOfTrx"));
    Assert.assertTrue(responseObject.containsKey("confirmed"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("witnessAddress")).matches());
    Assert.assertEquals(blockNumber, responseObject.getString("number"));
  }

  /**
   * constructor limit不为零
   */
  @Test(enabled = true, description = "List the blocks in the blockchain")
  public void test02getBlocksList() {
    //Get response
    int limit = 11;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-number");
    params.put("limit", String.valueOf(limit));
    params.put("count", "true");
    params.put("start", "20");
    params.put("start_timestamp", "1551715200000");
    params.put("end_timestamp", "1551772172616");
    response = TronscanApiList.getBlockDetail(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.containsKey("service_type"));

    //data object
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals(limit, responseObject.size());
    Assert.assertTrue(responseObject.containsKey("hash"));
    Assert.assertTrue(responseObject.containsKey("size"));
    Assert.assertTrue(!responseObject.getString("timestamp").isEmpty());
    Assert.assertTrue(responseObject.containsKey("txTrieRoot"));
    Assert.assertTrue(!responseObject.getString("parentHash").isEmpty());
    Assert.assertTrue(responseObject.containsKey("witnessId"));
    Assert.assertTrue(responseObject.containsKey("nrOfTrx"));
    Assert.assertTrue(Boolean.valueOf(responseObject.getString("confirmed")));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("witnessAddress")).matches());
  }

  /**
   * constructor limit为零
   */
  @Test(enabled = true, description = "List the blocks in the blockchain")
  public void test03getBlocksList() {
    //Get response
    int limit = 0;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-number");
    params.put("limit", String.valueOf(limit));
    params.put("count", "true");
    params.put("start", "20");
    params.put("start_timestamp", "1551715200000");
    params.put("end_timestamp", "1551772172616");
    response = TronscanApiList.getBlockDetail(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() == 3);
    Assert.assertTrue(responseContent.containsKey("service_type"));
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);

  }

  /**
   * constructor. limit不为零
   */
  @Test(enabled = true, description = "List all the blocks produced by the specified SR in the blockchain")
  public void getBlocksList() {
    //Get response
    int limit = 20;
    String address = "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9";
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-number");
    params.put("limit", String.valueOf(limit));
    params.put("count", "true");
    params.put("start", "0");
    params.put("producer", address);

    response = TronscanApiList.getBlockDetail(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() == 2);
    Assert.assertTrue(responseContent.containsKey("service_type"));
    Assert.assertTrue(responseContent.containsKey("data"));
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
