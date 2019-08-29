package tron.tronscan.account.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.tronscanApiList;
import tron.common.utils.Configuration;

/**
 * ${params}
 *
 * @Author:jh
 * @Date:2019-08-29 11:26
 */
public class NodeMap {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String, String> testAccount;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List all the nodes in the blockchain")
  public void getNodeMap() {
    response = tronscanApiList.getNodeMap(tronScanNode);
    //log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = tronscanApiList.parseResponseContent(response);
    tronscanApiList.printJsonContent(responseContent);


    //Two object, "total" and "Data"
    Assert.assertTrue(responseContent.size() >= 2);
    Integer total = Integer.valueOf(responseContent.get("total").toString());
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    Assert.assertTrue(exchangeArray.size() == total);

    //country
    targetContent = exchangeArray.getJSONObject(0);
    Assert.assertTrue(targetContent.containsKey("country"));
    //lng and lat Contain double
    Assert.assertTrue(Double.valueOf(targetContent.get("lng").toString())> -1000);
    Assert.assertTrue(Double.valueOf(targetContent.get("lat").toString())> 0);

    Assert.assertTrue(targetContent.containsKey("ip"));



  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    tronscanApiList.disGetConnect();
  }
}
