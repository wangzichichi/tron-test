package tron.tronscan.api;

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
 * @Date:2019-10-09 16:55
 */
@Slf4j
public class TotalCount {

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
   * constructor.查询总数接口
   */
  @Test(enabled = true, description = "Get a super totalCount's github link")
  public void getTotalCount() {
    //Get response
    String address = "TAahLbGTZk6YuCycii72datPQEtyC5x231";
    Map<String, String> params = new HashMap<>();
    params.put("address", address);
    params.put("type", "trc10trc20");
    response = TronscanApiList.getTotalCount(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() == 2);
    //count
    Assert.assertTrue(Long.valueOf(responseContent.get("count").toString()) >= 0);
    Assert.assertTrue(!responseContent.get("type").toString().isEmpty());

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
