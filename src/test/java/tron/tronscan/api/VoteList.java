package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
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
 * @Date:2019-08-29 20:16
 */
public class VoteList {

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
  @Test(enabled = true, description = "List all the votes info made by a specified voter  ")
  public void getInternalTransaction() {
    //
    String voter = "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9";
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-votes");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("voter", voter);
    //Three object "total" ,"data","totalVotes"
    response = TronscanApiList.getVoteTest(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("totalVotes").toString());
    Assert.assertTrue(rangeTotal >= total);
    Assert.assertTrue(responseContent.containsKey("data"));
  }

  /**
   * constructor.获取本轮投票
   */
  @Test(enabled = true, description = "Get current round vote time;")
  public void getCurrent_Cycle() {
    response = TronscanApiList.getCurrent_Cycle(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //total_votes
    Assert.assertTrue(Long.valueOf(responseContent.get("total_votes").toString()) >= 10000);
    //candidates
    JSONArray exchangeArray = responseContent.getJSONArray("candidates");
    targetContent = exchangeArray.getJSONObject(0);
    //hasPage
    Assert.assertTrue(!targetContent.get("hasPage").toString().isEmpty());
    //address
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
    //name
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(!targetContent.get("url").toString().isEmpty());

    //votes
    Assert.assertTrue(Long.valueOf(targetContent.get("votes").toString()) >= 0);
    //realTimeVotes
    Assert.assertTrue(Long.valueOf(targetContent.get("realTimeVotes").toString()) >= 0);
    Assert.assertTrue(!targetContent.get("change_cycle").toString().isEmpty());
    Assert.assertTrue(!targetContent.get("change_day").toString().isEmpty());
  }

  /**
   * constructor.获取下一轮投票情况
   */
  @Test(enabled = true, description = "Get next round vote time;")
  public void getNext_Cycle() {
    response = TronscanApiList.getNext_Cycle(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //nextCycle
    Assert.assertTrue(Long.valueOf(responseContent.get("nextCycle").toString()) >= 10000);
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
