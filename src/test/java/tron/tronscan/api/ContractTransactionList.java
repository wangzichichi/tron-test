package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class ContractTransactionList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private String contractAddress = Configuration.getByPath("testng.conf")
      .getString("defaultParameter.contractAddress");

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List the transactions related to an smart contract")
  public void test01ContractTransactionList() {
    //Get response
    int limit = 20;
    response = TronscanApiList.getContractTransactionList(tronScanNode, contractAddress, limit);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long
        .valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long
        .valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //data
    Assert.assertTrue(responseContent.containsKey("data"));
//    responseArrayContent = responseContent.getJSONArray("data");
//    targetContent = responseArrayContent.getJSONObject(0);
//    int dataSize = responseArrayContent.size();
//    Assert.assertEquals(dataSize, limit);
//    Assert.assertTrue(targetContent.containsKey("contractRet"));
//    Assert.assertTrue(targetContent.containsKey("cost"));
//    Assert.assertTrue(targetContent.containsKey("toAddressType"));
//    Assert.assertTrue(targetContent.containsKey("confirmed"));
//    Assert.assertTrue(targetContent.containsKey("toAddress"));
//    Assert.assertTrue(targetContent.containsKey("token"));
//    Assert.assertTrue(targetContent.containsKey("ownAddressType"));
//    Assert.assertTrue(targetContent.containsKey("call_data"));
//    Assert.assertTrue(targetContent.containsKey("ownAddress"));
//    Assert.assertTrue(targetContent.containsKey("txFee"));
//    Assert.assertTrue(targetContent.containsKey("block"));
//    Assert.assertTrue(targetContent.containsKey("parentHash"));
//    Assert.assertTrue(targetContent.containsKey("txHash"));
//    Assert.assertTrue(targetContent.containsKey("value"));
//    Assert.assertTrue(targetContent.containsKey("timestamp"));
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
