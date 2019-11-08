package tron.tronscan.api;


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
 * @Author:jh
 * @Date:2019-08-29 20:03
 */
public class ExchangeAphList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);


  /**
   * constructor.
   */
  @Test(enabled = true, description = "List a single the exchange pair's trade chart data ")
  public void getExchangAphTest() {
    //
    Map<String, String> Params = new HashMap<>();
    Params.put("exchange_id", "9");
    Params.put("granularity", "1h");
    Params.put("time_start", "1547510400");
    Params.put("time_end", "1548062933");
    //Five object
    response = TronscanApiList.getExchangAphTest(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //exchange_id
    Assert.assertTrue(Long.valueOf(responseContent.get("exchange_id").toString()) > 0);
    //
    Assert.assertTrue(responseContent.containsKey("data"));
    Assert.assertTrue(responseContent.containsKey("time_start"));
    Assert.assertTrue(responseContent.containsKey("granularity"));
    Assert.assertTrue(responseContent.containsKey("time_end"));
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
