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
 * @Author:jh
 * @Date:2019-08-29 19:34
 */
public class InternalTranList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);


  /**
   * constructor.查询合约内部合约内交易
   */
  @Test(enabled = true, description = "List the internal transactions related to a specified account(only display the latest 10,000 data records in the query time range) ")
  public void getInternalTransaction() {
    //
    String address = "TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3";
    Map<String, String> Params = new HashMap<>();
    Params.put("start_timestamp", "1529856000000");
    Params.put("end_timestamp", "1567595388290");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("contract", address);
    //Three object "total" ,"data","rangeTotal"
    response = TronscanApiList.getInternalTransaction(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    Assert.assertTrue(rangeTotal >= total);

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
