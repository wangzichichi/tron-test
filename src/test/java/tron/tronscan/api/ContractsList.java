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
public class ContractsList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List contract list")
  public void test01getContractsList() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("count","true");
    params.put("limit",String.valueOf(limit));
    params.put("start","0");

    response = TronscanApiList.getContractsList(tronScanNode,params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);

    //3 key
    Assert.assertTrue(responseContent.size() == 4);
    Assert.assertTrue(responseContent.getLong("total") > 0);
    Assert.assertTrue(responseContent.getLong("rangeTotal") > 0);
    Assert.assertTrue(responseContent.containsKey("status"));

    //Address list
    responseArrayContent = responseContent.getJSONArray("data");
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i)
          .getString("address")).matches());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("name"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("trxCount"));

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
