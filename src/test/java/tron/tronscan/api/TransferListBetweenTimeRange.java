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
public class TransferListBetweenTimeRange {

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
  @Test(enabled = true, description = "List the transactions between time range")
  public void test01getTransferListBetweenTimeRange() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-timestamp");
    params.put("count", "true");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("start_timestamp", "1548000000000");
    params.put("end_timestamp", "1548057645667");

    response = TronscanApiList.getTransferList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //3 key
    Assert.assertTrue(responseContent.containsKey("total"));
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(responseArrayContent.size(), limit);
    Assert.assertTrue(responseContent.getLong("rangeTotal") > 0);

    //data
    targetContent = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(targetContent.containsKey("data"));
    Assert.assertTrue(targetContent.containsKey("block"));
    Assert.assertTrue(targetContent.containsKey("transactionHash"));
    Assert.assertTrue(targetContent.containsKey("confirmed"));
    Assert.assertTrue(targetContent.containsKey("timestamp"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent
        .getString("transferFromAddress")).matches());
    Assert.assertTrue(patternAddress.matcher(targetContent
        .getString("transferToAddress")).matches());
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
