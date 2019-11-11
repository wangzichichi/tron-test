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

/**
 * ${params}
 *
 * @Author:tron
 * @Date:2019-09-17 17:31
 */
@Slf4j
public class TokenOverview {

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
  @Test(enabled = true, description = "查询通证列表")
  public void getTokenOverview() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "volume24hInTrx");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("order", "desc");
    params.put("filter", "all");
    params.put("order_current", "descend");
    response = TronscanApiList.getTokenOverview(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalAll = Long.valueOf(responseContent.get("totalAll").toString());
    Assert.assertTrue(totalAll >= total);
    JSONArray exchangeArray = responseContent.getJSONArray("tokens");
    targetContent = exchangeArray.getJSONObject(0);
    //marketcap
    Assert.assertTrue(Double.valueOf(targetContent.get("marketcap").toString()) >= 0);
    //address
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("contractAddress")).matches());
    //description
    Assert.assertTrue(targetContent.containsKey("description"));
    //supply
    Assert.assertTrue(Long.valueOf(targetContent.get("supply").toString()) >= 0);
    Assert.assertTrue(!targetContent.get("gain").toString().isEmpty());
    Assert.assertTrue(!targetContent.get("imgUrl").toString().isEmpty());
    //nrOfTokenHolders
    Assert.assertTrue(!targetContent.get("nrOfTokenHolders").toString().isEmpty());
    Assert.assertTrue(!targetContent.get("pairId").toString().isEmpty());
    //isTop
    Assert.assertTrue(Boolean.valueOf(targetContent.getString("isTop")));
    Assert.assertTrue(!targetContent.get("name").toString().isEmpty());
    Assert.assertTrue(!targetContent.get("projectSite").toString().isEmpty());
    Assert.assertTrue(!targetContent.get("abbr").toString().isEmpty());
    Integer decimals = Integer.valueOf(targetContent.get("decimal").toString());
    Assert.assertTrue(decimals >= 0 && decimals <= 18);
    //priceInTrx
    Assert.assertTrue(Double.valueOf(targetContent.get("priceInTrx").toString()) >= 0);
    //tokenType
    Assert.assertTrue(!targetContent.get("tokenType").toString().isEmpty());
    //volume24hInTrx
    Assert.assertTrue(Double.valueOf(targetContent.get("volume24hInTrx").toString()) >= 0);

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
