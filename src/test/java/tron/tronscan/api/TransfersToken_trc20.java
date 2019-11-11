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
public class TransfersToken_trc20 {

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
  @Test(enabled = true, description = "List the transactions related to a specified account")
  public void test01getBlockDetail() {
    //Get response
    String address = "TCN77KWWyUyi2A4Cu7vrh5dnmRyvUuME1E";
    Map<String, String> params = new HashMap<>();
    params.put("limit", "20");
    params.put("start", "0");
    params.put("contract_address", address);
    params.put("start_timestamp", "1529856000000");
    params.put("end_timestamp", "1552549912537");
    response = TronscanApiList.getTransfersTrc20List(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("total"));

    //data object
    responseArrayContent = responseContent.getJSONArray("token_transfers");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(responseObject.containsKey("transaction_id"));
    Assert.assertTrue(responseObject.containsKey("block_ts"));
    Assert.assertTrue(responseObject.containsKey("block"));
    Assert.assertTrue(responseObject.containsKey("quant"));
    Assert.assertTrue(responseObject.containsKey("confirmed"));
    Assert.assertTrue(responseObject.containsKey("contractRet"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(
        patternAddress.matcher(responseObject.getString("from_address")).matches());
    Assert.assertTrue(
        patternAddress.matcher(responseObject.getString("to_address")).matches());
  }


  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
