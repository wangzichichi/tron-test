package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class ContractsCode {

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
   * constructor.
   */
  @Test(enabled = true, description = "Get a single contract's abi & byteCode")
  public void test01getContractsCode() {
    //Get response
    String address = "TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3";
    Map<String, String> params = new HashMap<>();
    params.put("contract", address);
    response = TronscanApiList.getContractCode(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //status object
    targetContent = responseContent.getJSONObject("status");
    Assert.assertTrue(targetContent.containsKey("code"));
    Assert.assertTrue(targetContent.containsKey("message"));

    //data object
    targetContent = responseContent.getJSONObject("data");
    Assert.assertEquals(address, targetContent.getString("address"));
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(targetContent.containsKey("byteCode"));
    Assert.assertTrue(targetContent.containsKey("abi"));

  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get a single contract's detail ")
  public void getContractTest() {
    //Get response
    String address = "TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3";
    Map<String, String> Params = new HashMap<>();
    Params.put("contract", address);
    response = TronscanApiList.getContractTest(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    JSONObject responseObject = responseContent.getJSONObject("status");
    Assert.assertTrue(responseObject.containsKey("code"));
    Assert.assertTrue(responseObject.containsKey("message"));

    responseArrayContent = responseContent.getJSONArray("data");
    targetContent = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(targetContent.containsKey("address"));
    Assert.assertTrue(targetContent.containsKey("creator"));
    Assert.assertTrue(targetContent.containsKey("balance"));
    Assert.assertTrue(targetContent.containsKey("balanceInUsd"));
    Assert.assertTrue(targetContent.containsKey("trxCount"));

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
