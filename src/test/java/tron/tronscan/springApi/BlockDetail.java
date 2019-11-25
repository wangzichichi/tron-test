package tron.tronscan.springApi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.JavaTronApiList;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class BlockDetail {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject javatronResponseContent;
  private JSONArray responseArrayContent;
  private JSONArray javatronResponseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private HttpResponse javatronResponse;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscanSpring.ip.list")
      .get(0);
  private String onlineNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);
  private String oldNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscanOld.ip.list")
      .get(0);
  String number = "0";

  /**
   * constructor
   */
  @Test(enabled = false, description = "Get a single block's detail")
  public void test01getBlockDetail() throws Exception{
    //Get block from walletsolidity
    javatronResponse = JavaTronApiList.getNowBlock();
    log.info("code is " + javatronResponse.getStatusLine().getStatusCode());
    Assert.assertEquals(javatronResponse.getStatusLine().getStatusCode(), 200);
    javatronResponseContent = JavaTronApiList.parseResponseContent(javatronResponse);
    JavaTronApiList.printJsonContent(javatronResponseContent);
    JSONObject javatronObject = javatronResponseContent.getJSONObject("block_header");
    JSONObject raw_data = javatronObject.getJSONObject("raw_data");
    String blockID = javatronResponseContent.getString("blockID");
    number = raw_data.getString("number");
    String txTrieRoot = raw_data.getString("txTrieRoot");
    String witness_address = raw_data.getString("witness_address");
    String parentHash = raw_data.getString("parentHash");
    String timestamp = raw_data.getString("timestamp");
    Thread.sleep(15000);


    //Get response
    Map<String, String> params = new HashMap<>();
    String blockNumber = number;
    params.put("number", blockNumber);
    response = TronscanApiList.getBlockDetail(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    responseArrayContent = responseContent.getJSONArray("data");
    System.out.println(responseContent.getString("requestTime"));
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals(responseObject.getString("hash"),blockID);
    Assert.assertEquals(responseObject.getString("timestamp"),timestamp);
    Assert.assertEquals(responseObject.getString("parentHash"),parentHash);
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("witnessAddress")).matches());
    Assert.assertEquals(blockNumber, responseObject.getString("number"));
    Assert.assertEquals(responseObject.getString("witnessAddress"),witness_address);
  }

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

  @Test(enabled = false)
  public void test03getblocknum0() throws Exception{
//Get block from walletsolidity
    Map<String,String> params = new HashMap<>();
    params.put("num","0");
    javatronResponse = JavaTronApiList.getblockbynum(params);
    log.info("code is " + javatronResponse.getStatusLine().getStatusCode());
    Assert.assertEquals(javatronResponse.getStatusLine().getStatusCode(), 200);
    javatronResponseContent = JavaTronApiList.parseResponseContent(javatronResponse);
    JavaTronApiList.printJsonContent(javatronResponseContent);
    JSONObject javatronObject = javatronResponseContent.getJSONObject("block_header");
    JSONObject raw_data = javatronObject.getJSONObject("raw_data");
    String blockID = javatronResponseContent.getString("blockID");
    String number = raw_data.getString("number");
    String txTrieRoot = raw_data.getString("txTrieRoot");
    String witness_address = raw_data.getString("witness_address");
    String parentHash = raw_data.getString("parentHash");
    String timestamp = raw_data.getString("timestamp");

    //Get response
    Map<String, String> params2 = new HashMap<>();
    String blockNumber = "0";
    params2.put("number", blockNumber);
    response = TronscanApiList.getBlockDetail(onlineNode, params2);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals(responseObject.getString("hash"),blockID);
    Assert.assertEquals(responseObject.getString("timestamp"),timestamp);
    Assert.assertEquals(responseObject.getString("parentHash"),parentHash);
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("witnessAddress")).matches());
    Assert.assertEquals(blockNumber, responseObject.getString("number"));
    Assert.assertEquals(responseObject.getString("witnessAddress"),witness_address);
  }

  @Test(invocationCount = 10)
  public void test03getblockbyrandomNum() throws Exception{
    int min = 1;
    int max = 999999;
    int num = min + (int)(Math.random() * (max-min+1));
    String randomNumber = String.valueOf(num);
    log.info("Block number is: " + randomNumber);


//Get block from walletsolidity
    Map<String,String> params = new HashMap<>();
    params.put("num",randomNumber);
    javatronResponse = JavaTronApiList.getblockbynum(params);
    log.info("code is " + javatronResponse.getStatusLine().getStatusCode());
    Assert.assertEquals(javatronResponse.getStatusLine().getStatusCode(), 200);
    javatronResponseContent = JavaTronApiList.parseResponseContent(javatronResponse);
    JavaTronApiList.printJsonContent(javatronResponseContent);
    JSONObject javatronObject = javatronResponseContent.getJSONObject("block_header");
    JSONObject raw_data = javatronObject.getJSONObject("raw_data");
    String blockID = javatronResponseContent.getString("blockID");
    String number = raw_data.getString("number");
    String txTrieRoot = raw_data.getString("txTrieRoot");
    String witness_address = raw_data.getString("witness_address");
    String parentHash = raw_data.getString("parentHash");
    String timestamp = raw_data.getString("timestamp");

    //Get response
    Map<String, String> params2 = new HashMap<>();
    params2.put("number", randomNumber);
    response = TronscanApiList.getBlockDetail(onlineNode, params2);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals(responseObject.getString("hash"),blockID);
    Assert.assertEquals(responseObject.getString("timestamp"),timestamp);
    Assert.assertEquals(responseObject.getString("parentHash"),parentHash);
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("witnessAddress")).matches());
    Assert.assertEquals(randomNumber, responseObject.getString("number"));
    Assert.assertEquals(responseObject.getString("witnessAddress"),witness_address);
  }

  @Test()
  public void test04requestTime(){
    int tmp = 1;
    int min = 1;
    int max = 999999;
    int timeNew = 0;
    int timeOld = 0;
    Long oldTime = 0L;
    Long onlineTime = 0L;
    List<Long> oldRequestTime = new LinkedList<>();
    List<Long> newRequestTime = new LinkedList<>();
    for (;tmp<1000;tmp++){
      int num = min + (int) (Math.random() * (max - min + 1));
      String randomNumber = String.valueOf(num);
      log.info("Block number is: " + randomNumber);
      Map<String, String> params = new HashMap<>();
      params.put("visible", "true");
      params.put("num", randomNumber);
      response = TronscanApiList.getBlockDetail(oldNode, params);
      responseContent = TronscanApiList.parseResponseContent(response);
      oldTime += responseContent.getLong("requestTime");
      oldRequestTime.add(responseContent.getLong("requestTime"));
      log.info("旧接口共请求："+tmp+"次，"+"总耗时："+oldTime+"ms，"+"平均耗时："+oldTime/tmp+"ms");

      response = TronscanApiList.getBlockDetail(tronScanNode, params);
      responseContent = TronscanApiList.parseResponseContent(response);
      newRequestTime.add(responseContent.getLong("requestTime"));
      onlineTime += responseContent.getLong("requestTime");
      log.info("新接口共请求："+tmp+"次，"+"总耗时："+onlineTime+"ms，"+"平均耗时："+onlineTime/tmp+"ms");

    }
    System.out.println(oldRequestTime);
    System.out.println(newRequestTime);
  }




  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}

