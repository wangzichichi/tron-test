package tron.tronscan.springApi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.JavaTronApiList;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.tronscan.api.TransactionList;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TransactionDetail {
  private JSONObject responseContent;
  private JSONObject javatronResponseContent;
  private JSONArray responseArrayContent;
  private JSONObject proposalContent;

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

  /**
   * constructor. limit不为零
   */
  @Test(enabled = true, description = "List the transactions related to a specified account")
  public void test01getBlockDetail() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-number");
    Params.put("limit", "20");
    Params.put("count", "true");
    Params.put("start", "0");
    Params.put("address", "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9");
    response = TronscanApiList.getTransactionList(onlineNode, Params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    org.junit.Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    org.junit.Assert.assertTrue(responseObject.containsKey("hash"));
    org.junit.Assert.assertTrue(responseObject.containsKey("block"));
    org.junit.Assert.assertTrue(responseObject.containsKey("timestamp"));
    org.junit.Assert.assertTrue(responseObject.containsKey("contractType"));
    org.junit.Assert.assertTrue(responseObject.containsKey("confirmed"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    org.junit.Assert.assertTrue(patternAddress.matcher(responseObject.getString("ownerAddress")).matches());
    org.junit.Assert.assertTrue(patternAddress.matcher(responseObject.getString("toAddress")).matches());

    responseObject = responseObject.getJSONObject("contractData");
    org.junit.Assert.assertTrue(responseObject.containsKey("amount"));
//    Assert.assertTrue(responseObject.containsKey("asset_name"));
    org.junit.Assert.assertTrue(patternAddress.matcher(responseObject.getString("owner_address")).matches());
    org.junit.Assert.assertTrue(patternAddress.matcher(responseObject.getString("to_address")).matches());
  }

  /**
   * constructor. limit为零
   */
  @Test(enabled = true, description = "List the transactions related to a specified account")
  public void test02getBlockDetail() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-number");
    Params.put("limit", "0");
    Params.put("count", "true");
    Params.put("start", "0");
    Params.put("address", "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9");
    response = TronscanApiList.getTransactionList(onlineNode, Params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    org.junit.Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    org.junit.Assert.assertTrue(responseContent.containsKey("rangeTotal"));
    org.junit.Assert.assertTrue(responseContent.containsKey("wholeChainTxCount"));
  }


  /**
   * constructor. limit不为零
   */
  @Test(enabled = true, description = "List the transactions in the blockchain(only display the latest 10,000 data records in the query time range)")
  public void getTransactionTestRang() {
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("count", "true");
    Params.put("start", "0");
    Params.put("start_timestamp", "1548000000000");
    Params.put("end_timestamp", "1548056638507");
    response = TronscanApiList.getTransactionList(onlineNode, Params);

    org.junit.Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data","rangeTotal"
    //service_type
    JSONArray exchangeArray = responseContent.getJSONArray("data");

    targetContent = exchangeArray.getJSONObject(0);
    //contractRet
    org.junit.Assert.assertTrue(targetContent.containsKey("contractRet"));
    //cost json
    org.junit.Assert.assertTrue(targetContent.containsKey("cost"));
    //data
    org.junit.Assert.assertTrue(targetContent.containsKey("data"));
    //contractRet
    org.junit.Assert.assertTrue(!targetContent.get("contractType").toString().isEmpty());
    //fee
    org.junit.Assert.assertTrue(targetContent.containsKey("fee"));
    //toAddress
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    org.junit.Assert.assertTrue(patternAddress.matcher(targetContent.getString("toAddress")).matches());
    org.junit.Assert.assertTrue(patternAddress.matcher(targetContent.getString("ownerAddress")).matches());
    //confirmed
    org.junit.Assert.assertTrue(Boolean.valueOf(targetContent.getString("confirmed")));
    org.junit.Assert.assertTrue(targetContent.containsKey("Events"));
    org.junit.Assert.assertTrue(targetContent.containsKey("SmartCalls"));
    org.junit.Assert.assertTrue(targetContent.containsKey("block"));
    //hash
    Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
    org.junit.Assert.assertTrue(patternHash.matcher(targetContent.getString("hash")).matches());
    org.junit.Assert.assertTrue(targetContent.containsKey("id"));
    //contractData json
    proposalContent = targetContent.getJSONObject("contractData");
    //contractData Contain owner_address，contract_address
    org.junit.Assert.assertTrue(patternAddress.matcher(proposalContent.getString("owner_address")).matches());
    //timestamp
    org.junit.Assert.assertTrue(targetContent.containsKey("timestamp"));


  }

  /**
   * constructor.查询区块上交易列表 limit不为零
   */
  @Test(enabled = true, description = "查询区块上交易列表")
  public void getTransactionTestBlock() {
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("count", "true");
    Params.put("start", "0");
    Params.put("total", "0");
    Params.put("block", "14456772");
    response = TronscanApiList.getTransactionList(onlineNode, Params);

    org.junit.Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data","rangeTotal"
    JSONArray exchangeArray = responseContent.getJSONArray("data");

    targetContent = exchangeArray.getJSONObject(0);
    //contractRet
    org.junit.Assert.assertTrue(targetContent.containsKey("contractRet"));
    //cost json
    proposalContent = targetContent.getJSONObject("cost");
    //net_fee
    org.junit.Assert.assertTrue(Long.valueOf(proposalContent.get("net_fee").toString()) >= 0);
    //energy_usage
    org.junit.Assert.assertTrue(Long.valueOf(proposalContent.get("energy_usage").toString()) >= 0);
    //energy_fee
    org.junit.Assert.assertTrue(Long.valueOf(proposalContent.get("energy_fee").toString()) >= 0);
    //energy_usage_total
    org.junit.Assert
        .assertTrue(Long.valueOf(proposalContent.get("energy_usage_total").toString()) >= 0);
    //origin_energy_usage
    org.junit.Assert
        .assertTrue(Long.valueOf(proposalContent.get("origin_energy_usage").toString()) >= 0);
    //net_usage
    org.junit.Assert.assertTrue(Long.valueOf(proposalContent.get("net_usage").toString()) >= 0);
    //data
    org.junit.Assert.assertTrue(targetContent.containsKey("data"));
    //contractRet
    org.junit.Assert.assertTrue(!targetContent.get("contractType").toString().isEmpty());
    //fee
    org.junit.Assert.assertTrue(targetContent.containsKey("fee"));
    //toAddress
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    org.junit.Assert.assertTrue(patternAddress.matcher(targetContent.getString("toAddress")).matches());
    org.junit.Assert.assertTrue(patternAddress.matcher(targetContent.getString("ownerAddress")).matches());
    //confirmed
    org.junit.Assert.assertTrue(Boolean.valueOf(targetContent.getString("confirmed")));
    org.junit.Assert.assertTrue(targetContent.containsKey("Events"));
    org.junit.Assert.assertTrue(targetContent.containsKey("SmartCalls"));
    org.junit.Assert.assertTrue(targetContent.containsKey("block"));
    //hash
    Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
    org.junit.Assert.assertTrue(patternHash.matcher(targetContent.getString("hash")).matches());
    org.junit.Assert.assertTrue(targetContent.containsKey("id"));
    //contractData json
    proposalContent = targetContent.getJSONObject("contractData");
    org.junit.Assert.assertTrue(proposalContent.containsKey("data"));
    //contractData Contain owner_address，contract_address
    org.junit.Assert.assertTrue(patternAddress.matcher(proposalContent.getString("owner_address")).matches());
    org.junit.Assert.assertTrue(
        patternAddress.matcher(proposalContent.getString("contract_address")).matches());
    //timestamp
    org.junit.Assert.assertTrue(targetContent.containsKey("timestamp"));

  }


//todo add
  @Test(enabled = true)
  public void test03GetTransactionDetail() throws Exception{
    //Get block Transaction from walletsolidity
    javatronResponse = JavaTronApiList.getNowBlock();
    log.info("code is " + javatronResponse.getStatusLine().getStatusCode());
    Assert.assertEquals(javatronResponse.getStatusLine().getStatusCode(), 200);
    javatronResponseContent = JavaTronApiList.parseResponseContent(javatronResponse);
    JavaTronApiList.printJsonContent(javatronResponseContent);
    JSONObject javatronObject = javatronResponseContent.getJSONObject("block_header");
    JSONObject raw_data = javatronObject.getJSONObject("raw_data");
    JSONArray transactions = javatronResponseContent.getJSONArray("transactions");
    String number = raw_data.getString("number");


    Map<String,String> params = new HashMap<>();
    params.put("num",String.valueOf(Integer.valueOf(number)-5));
    javatronResponse = JavaTronApiList.getblockbynum(params);
    log.info("code is " + javatronResponse.getStatusLine().getStatusCode());
    Assert.assertEquals(javatronResponse.getStatusLine().getStatusCode(), 200);
    javatronResponseContent = JavaTronApiList.parseResponseContent(javatronResponse);
    JavaTronApiList.printJsonContent(javatronResponseContent);
    javatronObject = javatronResponseContent.getJSONObject("block_header");
    raw_data = javatronObject.getJSONObject("raw_data");
    transactions = javatronResponseContent.getJSONArray("transactions");
    int total = transactions.size();
    number = raw_data.getString("number");

    //data object（new interface）
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "200");
    Params.put("count", "true");
    Params.put("start", "0");
    Params.put("total", "0");
    Params.put("block", number);
    response = TronscanApiList.getTransactionList(onlineNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data","rangeTotal"
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    Assert.assertEquals(responseContent.getString("total"),String.valueOf(total));
    targetContent = exchangeArray.getJSONObject(0);
    //contractRet
    Assert.assertTrue(targetContent.containsKey("contractRet"));
    //cost json
    proposalContent = targetContent.getJSONObject("cost");

  }

  @Test
  public void test04transcationTime(){
    int tmp = 1;
    int min = 1;
    int max = 999999;
    int timeNew = 0;
    int timeOld = 0;
    Long oldTime = 0L;
    Long onlineTime = 0L;
    for (;tmp<500;tmp++){
      int num = min + (int) (Math.random() * (max - min + 1));
      String randomNumber = String.valueOf(num);
      log.info("Block number is: " + randomNumber);
      Map<String, String> params = new HashMap<>();
      params.put("visible", "true");
      params.put("num", randomNumber);
      response = TronscanApiList.getTransactionList(oldNode,params);
      responseContent = TronscanApiList.parseResponseContent(response);
      oldTime += responseContent.getLong("requestTime");
      log.info("旧接口共请求："+tmp+"次，"+"总耗时："+oldTime+"ms，"+"平均耗时："+oldTime/tmp+"ms");

      response = TronscanApiList.getTransactionList(tronScanNode,params);
      responseContent = TronscanApiList.parseResponseContent(response);
      onlineTime += responseContent.getLong("requestTime");
      log.info("新接口共请求："+tmp+"次，"+"总耗时："+onlineTime+"ms，"+"平均耗时："+onlineTime/tmp+"ms");

    }
    System.out.println(oldTime);
    System.out.println(onlineTime);

  }
}
