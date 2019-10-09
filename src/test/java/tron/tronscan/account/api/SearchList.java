package tron.tronscan.account.api;

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

/**
 * ${params}
 *
 * @Author:tron
 * @Date:2019-10-09 16:03
 */
@Slf4j
public class SearchList {

  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);

  /**
   * constructor.
   */
  @Test(enabled = true, description = "搜索框查询接口")
  public void getSearch() {
    Map<String, String> Params = new HashMap<>();
    Params.put("term", "1");
    response = TronscanApiList.getSearch(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    JSONArray exchangeArray = TronscanApiList.parseArrayResponseContent(response);
    targetContent = exchangeArray.getJSONObject(0);
    Assert.assertTrue(exchangeArray.size() > 0);
    for (int i = 0; i <= targetContent.size(); i++) {
      Assert.assertTrue(targetContent.containsKey("value"));
      Assert.assertTrue(targetContent.containsKey("desc"));
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
