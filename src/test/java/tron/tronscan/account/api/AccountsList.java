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
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class AccountsList {

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
  @Test(enabled = true, description = "List account")
  public void test01getAccount() {
    //Get response
    int limit = 3;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-balance");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    response = TronscanApiList.getAccount(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //data object
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals(limit,responseObject.size());
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("address")).matches());
    Assert.assertTrue(responseObject.containsKey("balance"));
    Assert.assertTrue(responseObject.containsKey("power"));
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.containsKey("rangeTotal"));
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disConnect();
  }

}
