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
public class ContractTriggerList {

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
  @Test(enabled = true, description = " List all the triggers of the contracts in the blockchain")
  public void test01getContractTrigger() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-timestamp");
    params.put("count", "true");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("start_timestamp", "1548000000000");
    params.put("end_timestamp", "1548060167540");
    response = TronscanApiList.getContractTrigger(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);

    //object data
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(responseArrayContent.size() == 20);
    Assert.assertTrue(Integer.valueOf(responseObject.getString("block")) > 0);
    Assert.assertTrue(responseObject.containsKey("callData"));
    Assert.assertTrue(responseObject.containsKey("callValue"));
    Assert.assertTrue(responseObject.containsKey("contractType"));
    Assert.assertTrue(responseObject.containsKey("token"));
    Assert.assertTrue(Boolean.valueOf(responseObject.getString("confirmed")));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert
        .assertTrue(patternAddress.matcher(responseObject.getString("contractAddress")).matches());
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("ownerAddress")).matches());
    Assert.assertTrue(!responseObject.getString("hash").isEmpty());
    Assert.assertTrue(!responseObject.getString("timestamp").isEmpty());
    Assert.assertTrue(!responseObject.getString("result").isEmpty());

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
