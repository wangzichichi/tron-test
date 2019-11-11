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
public class TransactionList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject sonContent;
  private JSONObject proposalContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
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
    response = TronscanApiList.getTransactionList(tronScanNode, Params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("service_type"));
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
//    Assert.assertTrue(responseObject.containsKey("asset_name"));
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("owner_address")).matches());
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("to_address")).matches());
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
    response = TronscanApiList.getTransactionList(tronScanNode, Params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() == 4);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.containsKey("rangeTotal"));
    Assert.assertTrue(responseContent.containsKey("service_type"));
    Assert.assertTrue(responseContent.containsKey("wholeChainTxCount"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List a single the exchange pair's transaction records ")
  public void getTransactionTest() {
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("count", "true");
    Params.put("start", "0");
    response = TronscanApiList.getTransactionTest(tronScanNode, Params);

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
    //tokenID
    Assert.assertTrue(targetContent.containsKey("tokenID"));
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
    response = TronscanApiList.getTransactionList(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data","rangeTotal"
    Assert.assertTrue(responseContent.size() == 2);
    //service_type
    Assert.assertTrue(responseContent.containsKey("service_type"));
    JSONArray exchangeArray = responseContent.getJSONArray("data");

    targetContent = exchangeArray.getJSONObject(0);
    //contractRet
    Assert.assertTrue(targetContent.containsKey("contractRet"));
    //cost json
    Assert.assertTrue(targetContent.containsKey("cost"));
    //data
    Assert.assertTrue(targetContent.containsKey("data"));
    //contractRet
    Assert.assertTrue(!targetContent.get("contractType").toString().isEmpty());
    //fee
    Assert.assertTrue(targetContent.containsKey("fee"));
    //toAddress
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("toAddress")).matches());
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("ownerAddress")).matches());
    //confirmed
    Assert.assertTrue(Boolean.valueOf(targetContent.getString("confirmed")));
    Assert.assertTrue(targetContent.containsKey("Events"));
    Assert.assertTrue(targetContent.containsKey("SmartCalls"));
    Assert.assertTrue(targetContent.containsKey("block"));
    //hash
    Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
    Assert.assertTrue(patternHash.matcher(targetContent.getString("hash")).matches());
    Assert.assertTrue(targetContent.containsKey("id"));
    //contractData json
    proposalContent = targetContent.getJSONObject("contractData");
    //contractData Contain owner_address，contract_address
    Assert.assertTrue(patternAddress.matcher(proposalContent.getString("owner_address")).matches());
    Assert.assertTrue(
        patternAddress.matcher(proposalContent.getString("to_address")).matches());
    //call_value
    Assert.assertTrue(Long.valueOf(proposalContent.get("amount").toString()) >= 0);
    //timestamp
    Assert.assertTrue(targetContent.containsKey("timestamp"));


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
    Params.put("block", "12448572");
    response = TronscanApiList.getTransactionList(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data","rangeTotal"
    Assert.assertTrue(responseContent.size() == 2);
    JSONArray exchangeArray = responseContent.getJSONArray("data");

    targetContent = exchangeArray.getJSONObject(0);
    //contractRet
    Assert.assertTrue(targetContent.containsKey("contractRet"));
    //cost json
    proposalContent = targetContent.getJSONObject("cost");
    //net_fee
    Assert.assertTrue(Long.valueOf(proposalContent.get("net_fee").toString()) >= 0);
    //energy_usage
    Assert.assertTrue(Long.valueOf(proposalContent.get("energy_usage").toString()) >= 0);
    //energy_fee
    Assert.assertTrue(Long.valueOf(proposalContent.get("energy_fee").toString()) >= 0);
    //energy_usage_total
    Assert.assertTrue(Long.valueOf(proposalContent.get("energy_usage_total").toString()) >= 0);
    //origin_energy_usage
    Assert.assertTrue(Long.valueOf(proposalContent.get("origin_energy_usage").toString()) >= 0);
    //net_usage
    Assert.assertTrue(Long.valueOf(proposalContent.get("net_usage").toString()) >= 0);
    //data
    Assert.assertTrue(targetContent.containsKey("data"));
    //contractRet
    Assert.assertTrue(!targetContent.get("contractType").toString().isEmpty());
    //fee
    Assert.assertTrue(targetContent.containsKey("fee"));
    //toAddress
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("toAddress")).matches());
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("ownerAddress")).matches());
    //confirmed
    Assert.assertTrue(Boolean.valueOf(targetContent.getString("confirmed")));
    Assert.assertTrue(targetContent.containsKey("Events"));
    Assert.assertTrue(targetContent.containsKey("SmartCalls"));
    Assert.assertTrue(targetContent.containsKey("block"));
    //hash
    Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
    Assert.assertTrue(patternHash.matcher(targetContent.getString("hash")).matches());
    Assert.assertTrue(targetContent.containsKey("id"));
    //contractData json
    proposalContent = targetContent.getJSONObject("contractData");
    Assert.assertTrue(proposalContent.containsKey("data"));
    //contractData Contain owner_address，contract_address
    Assert.assertTrue(patternAddress.matcher(proposalContent.getString("owner_address")).matches());
    Assert.assertTrue(
        patternAddress.matcher(proposalContent.getString("contract_address")).matches());
    //timestamp
    Assert.assertTrue(targetContent.containsKey("timestamp"));

  }


  /**
   * constructor.simple-transaction接口
   */
  @Test(enabled = true, description = "List the transactions under specified conditions;")
  public void getSimple_Transaction() {
    response = TronscanApiList.getSimple_Transaction(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //total_votes
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);

    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {

      //hash
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("hash").toString().isEmpty());
      //timestamp
      Assert.assertTrue(
          Long.valueOf(responseArrayContent.getJSONObject(i).get("timestamp").toString()) >= 0);
      //ownerAddress
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(
          patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("ownerAddress"))
              .matches());
      Assert.assertTrue(
          patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("toAddress"))
              .matches());
      //contractType
      Assert.assertTrue(
          !responseArrayContent.getJSONObject(i).get("contractType").toString().isEmpty());
      //confirmed
      Assert.assertTrue(
          Boolean.valueOf(responseArrayContent.getJSONObject(i).getString("confirmed")));
      //contractData
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("contractData"));

    }

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
