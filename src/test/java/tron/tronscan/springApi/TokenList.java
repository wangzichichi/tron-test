package tron.tronscan.springApi;

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
 * @Date:2019-11-26 15:04
 */
@Slf4j
public class TokenList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscanSpring.ip.list")
      .get(0);

  /**
   * constructor.limit不为零
   */
  @Test(enabled = true, description = " List all the trc10 tokens in the blockchain")
  public void test01getTokensList() {
    //Get response
    Map<String, String> params = new HashMap<>();
    int limit = 20;
    String status = "ico";
    params.put("sort", "-name");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("status", status);
    response = TronscanApiList.getTokensList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("service_type"));
    //object data
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(limit, responseArrayContent.size());
    targetContent = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(targetContent.getLong("totalTransactions") >= 0);
    Assert.assertTrue(targetContent.containsKey("country"));
    Assert.assertTrue(targetContent.getLong("tokenID") >= 1000000);
    Assert.assertTrue(targetContent.getLong("participated") > 0);
    Assert.assertTrue(targetContent
        .getInteger("precision") >= 0 && targetContent.getInteger("precision") <= 7);
    Assert.assertTrue(targetContent.containsKey("num"));
    Assert.assertTrue(targetContent.containsKey("available"));
    Assert.assertTrue(targetContent.containsKey("reputation"));
    Assert.assertTrue(targetContent.containsKey("description"));
    Assert.assertTrue(targetContent.containsKey("issuedPercentage"));
    Assert.assertTrue(targetContent.containsKey("nrOfTokenHolders"));
    Assert.assertTrue(targetContent.containsKey("voteScore"));
    Assert.assertTrue(targetContent.containsKey("dateCreated"));
    Assert.assertTrue(targetContent.containsKey("price"));
    Assert.assertTrue(targetContent.containsKey("percentage"));
    Assert.assertTrue(targetContent.containsKey("startTime"));
    Assert.assertTrue(targetContent.containsKey("id"));
    Assert.assertTrue(targetContent.containsKey("issued"));
    Assert.assertTrue(targetContent.containsKey("trxNum"));
    Assert.assertTrue(targetContent.containsKey("abbr"));
    Assert.assertTrue(targetContent.containsKey("website"));
    Assert.assertTrue(targetContent.containsKey("github"));
    Assert.assertTrue(targetContent.containsKey("availableSupply"));
    Assert.assertTrue(targetContent.containsKey("totalSupply"));
    Assert.assertTrue(targetContent.containsKey("index"));
    Assert.assertTrue(targetContent.containsKey("frozenTotal"));
    Assert.assertTrue(targetContent.containsKey("frozen"));
    Assert.assertTrue(targetContent.containsKey("canShow"));
    Assert.assertTrue(targetContent.containsKey("remaining"));
    Assert.assertTrue(targetContent.containsKey("url"));
    Assert.assertTrue(targetContent.containsKey("frozenPercentage"));
    Assert.assertTrue(targetContent.containsKey("imgUrl"));
    Assert.assertTrue(targetContent.containsKey("isBlack"));
    Assert.assertTrue(targetContent.containsKey("remainingPercentage"));
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(targetContent.containsKey("ownerAddress"));
    Assert.assertTrue(targetContent.containsKey("endTime"));
    Assert.assertTrue(targetContent.containsKey("white_paper"));
    Assert.assertTrue(targetContent.containsKey("social_media"));
  }

  /**
   * constructor.limit为零
   */
  @Test(enabled = true, description = " List all the trc10 tokens in the blockchain")
  public void test01getTokensListLimit() {
    //Get response
    Map<String, String> params = new HashMap<>();
    int limit = 0;
    String status = "ico";
    params.put("sort", "-name");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("status", status);
    response = TronscanApiList.getTokensList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.containsKey("rangeTotal"));
    Assert.assertTrue(responseContent.containsKey("service_type"));

  }

  /**
   * constructor.查询参与页，limit不为零
   */
  @Test(enabled = true, description = " List all the trc10 tokens in the blockchain")
  public void getTokensList() {
    //Get response
    Map<String, String> params = new HashMap<>();
    int limit = 20;
    String status = "ico";
    params.put("sort", "rank");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("status", status);
    params.put("showAll", "2");
    response = TronscanApiList.getTokenList(tronScanNode, params);
//    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("service_type"));
    //object data
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(limit, responseArrayContent.size());
    targetContent = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(targetContent.getLong("totalTransactions") >= 0);
    Assert.assertTrue(targetContent.containsKey("country"));
    Assert.assertTrue(targetContent.getLong("tokenID") >= 1000000);
    Assert.assertTrue(targetContent.getLong("participated") >= 0);
    Assert.assertTrue(targetContent
        .getInteger("precision") >= 0 && targetContent.getInteger("precision") <= 7);
    Assert.assertTrue(targetContent.containsKey("num"));
    Assert.assertTrue(targetContent.containsKey("available"));
    Assert.assertTrue(targetContent.containsKey("reputation"));
    Assert.assertTrue(targetContent.containsKey("description"));
    Assert.assertTrue(targetContent.containsKey("issuedPercentage"));
    Assert.assertTrue(targetContent.containsKey("nrOfTokenHolders"));
    Assert.assertTrue(targetContent.containsKey("voteScore"));
    Assert.assertTrue(targetContent.containsKey("dateCreated"));
    Assert.assertTrue(targetContent.containsKey("price"));
    Assert.assertTrue(targetContent.containsKey("percentage"));
    Assert.assertTrue(targetContent.containsKey("startTime"));
    Assert.assertTrue(targetContent.containsKey("id"));
    Assert.assertTrue(targetContent.containsKey("issued"));
    Assert.assertTrue(targetContent.containsKey("trxNum"));
    Assert.assertTrue(targetContent.containsKey("abbr"));
    Assert.assertTrue(targetContent.containsKey("website"));
    Assert.assertTrue(targetContent.containsKey("github"));
    Assert.assertTrue(targetContent.containsKey("availableSupply"));
    Assert.assertTrue(targetContent.containsKey("totalSupply"));
    Assert.assertTrue(targetContent.containsKey("index"));
    Assert.assertTrue(targetContent.containsKey("frozenTotal"));
    Assert.assertTrue(targetContent.containsKey("frozen"));
    Assert.assertTrue(targetContent.containsKey("canShow"));
    Assert.assertTrue(targetContent.containsKey("remaining"));
    Assert.assertTrue(targetContent.containsKey("url"));
    Assert.assertTrue(targetContent.containsKey("frozenPercentage"));
    Assert.assertTrue(targetContent.containsKey("imgUrl"));
    Assert.assertTrue(targetContent.containsKey("isBlack"));
    Assert.assertTrue(targetContent.containsKey("remainingPercentage"));
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(targetContent.containsKey("ownerAddress"));
    Assert.assertTrue(targetContent.containsKey("endTime"));
    Assert.assertTrue(targetContent.containsKey("white_paper"));
    Assert.assertTrue(targetContent.containsKey("social_media"));
  }

  /**
   * constructor.查询参与页，limit为零
   */
  @Test(enabled = true, description = " List all the trc10 tokens in the blockchain")
  public void getTokensListLimit() {
    //Get response
    Map<String, String> params = new HashMap<>();
    int limit = 0;
    String status = "ico";
    params.put("sort", "rank");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("status", status);
    params.put("showAll", "2");
    response = TronscanApiList.getTokenList(tronScanNode, params);
//    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("service_type"));
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
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
