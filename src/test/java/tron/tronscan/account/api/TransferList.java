package tron.tronscan.account.api;

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
import tron.common.tronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class TransferList {

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
    Map<String, String> params = new HashMap<>();
    String address = "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9";
    params.put("sort", "-timestamp");
    params.put("count", "true");
    params.put("limit", "20");
    params.put("start", "0");
    params.put("token", "_");
    params.put("address", address);
    response = tronscanApiList.getTransferList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = tronscanApiList.parseResponseContent(response);
    tronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("total"));

    //data object
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(responseObject.containsKey("data"));
    Assert.assertTrue(responseObject.containsKey("block"));
    Assert.assertTrue(responseObject.containsKey("transactionHash"));
    Assert.assertTrue(responseObject.containsKey("confirmed"));
    Assert.assertTrue(responseObject.containsKey("timestamp"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(
        patternAddress.matcher(responseObject.getString("transferFromAddress")).matches());
    Assert.assertTrue(
        patternAddress.matcher(responseObject.getString("transferToAddress")).matches());
  }


  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    tronscanApiList.disConnect();
  }

}
