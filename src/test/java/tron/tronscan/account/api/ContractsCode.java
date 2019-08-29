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
import tron.common.tronscanApiList;

@Slf4j
public class ContractsCode {

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
  @Test(enabled = true, description = "Get a single contract's abi & byteCode")
  public void test01getContractsCode() {
    //Get response
    String address = "TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3";
    Map<String, String> Params = new HashMap<>();
    Params.put("contract",address);
    response = tronscanApiList.getContractCode(tronScanNode,Params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = tronscanApiList.parseResponseContent(response);
    tronscanApiList.printJsonContent(responseContent);
    targetContent = responseContent.getJSONObject("status");
    Assert.assertTrue(targetContent.containsKey("code"));
    Assert.assertTrue(targetContent.containsKey("message"));

    targetContent = responseContent.getJSONObject("data");
    Assert.assertEquals(address,targetContent.getString("address"));
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(targetContent.containsKey("byteCode"));
    Assert.assertTrue(targetContent.containsKey("abi"));

  }


  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
//    tronscanApiList.disConnect();
  }

}
