package tron.tronscan.springApi;

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

/**
 * ${params}
 *
 * @Author:tron
 * @Date:2019-11-25 17:27
 */
@Slf4j
public class TokensList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);

  /**
   * constructor.获取trc10 token持有者 Limit不为零
   */
  @Test(enabled = true, description = "Get token holders of a trc10 token;")
  public void getTokenholders() {
    String address = "TF5Bn4cJCT6GVeUgyCN4rBhDg42KBrpAjg";
    Map<String, String> params = new HashMap<>();
    params.put("address", address);
    response = TronscanApiList.getTokenholders(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //Assert.assertTrue(responseContent.size() == 3);
    //service_type
    Assert.assertTrue(responseContent.containsKey("service_type"));
    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      //name
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("name").toString().isEmpty());
      //balance
      Assert.assertTrue(
          Long.valueOf(responseArrayContent.getJSONObject(i).get("balance").toString()) >= 0);
      //address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(
          patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("address"))
              .matches());

    }
  }

  /**
   * constructor.获取trc10 token持有者 limit为零
   */
  @Test(enabled = true, description = "Get token holders of a trc10 token;")
  public void getTokenholdersLimit() {
    String address = "TF5Bn4cJCT6GVeUgyCN4rBhDg42KBrpAjg";
    Map<String, String> params = new HashMap<>();
    params.put("address", address);
    params.put("limit", "0");
    response = TronscanApiList.getTokenholders(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //Assert.assertTrue(responseContent.size() == 4);
    //service_type
    Assert.assertTrue(responseContent.containsKey("service_type"));
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //data list

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
