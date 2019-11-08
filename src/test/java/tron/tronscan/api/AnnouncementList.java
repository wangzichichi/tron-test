package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
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
 * @Date:2019-09-30 17:07
 */
public class AnnouncementList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String, String> testAccount;

  /**
   * constructor.查询trc20通证持有者
   */
  @Test(enabled = true, description = "查询trc20通证持有者")
  public void getAnnouncement() {
    //
    String address = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "10");
    Params.put("start", "10");
    Params.put("type", "1");
    Params.put("status", "0");
    //Three object "total" ,"data","rangeTotal"
    response = TronscanApiList.getAnnouncement(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Assert.assertTrue(Long.valueOf(responseContent.get("total").toString()) > 1);
    //Address list
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(
          Long.valueOf(responseArrayContent.getJSONObject(i).get("id").toString()) > 1);
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("titleEN").toString().isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("titleCN").toString().isEmpty());
      Assert
          .assertTrue(!responseArrayContent.getJSONObject(i).get("contextEN").toString().isEmpty());
      Assert
          .assertTrue(!responseArrayContent.getJSONObject(i).get("contextCN").toString().isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("type").toString().isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("status").toString().isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("createTime"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("updateTime"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("extInfo"));
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
