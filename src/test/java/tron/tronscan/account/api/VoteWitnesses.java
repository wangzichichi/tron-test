package tron.tronscan.account.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.tronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class VoteWitnesses {

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
  @Test(enabled = true, description = "List all the votes info of the witnesses")
  public void test01getVoteWitnesses() {
    //Get response
    response = tronscanApiList.getVoteWitnesses(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = tronscanApiList.parseResponseContent(response);
    tronscanApiList.printJsonContent(responseContent);

    //Three key,total/totalVotes/fastestRise/data
    Assert.assertEquals(responseContent.size(),4);
    Integer total = responseContent.getInteger("total");
    Long totalVotes = responseContent.getLong("totalVotes");
    Integer dataSize = responseContent.getJSONArray("data").size();
    Assert.assertEquals(total,dataSize);
    Assert.assertTrue(totalVotes > 0);

    //Key data
    targetContent = responseContent.getJSONArray("data").getJSONObject(0);
    Assert.assertTrue(targetContent.containsKey("hasPage"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(targetContent.containsKey("changeVotes"));
    Assert.assertTrue(targetContent.containsKey("lastRanking"));
    Assert.assertTrue(targetContent.containsKey("change_cycle"));
    Assert.assertTrue(targetContent.containsKey("realTimeRanking"));
    Assert.assertTrue(targetContent.containsKey("lastCycleVotes"));
    Assert.assertTrue(targetContent.containsKey("realTimeVotes"));
    Assert.assertTrue(targetContent.containsKey("votesPercentage"));
    Assert.assertTrue(targetContent.containsKey("url"));

    //Key fastestRise
    targetContent = responseContent.getJSONObject("fastestRise");
    Assert.assertTrue(targetContent.containsKey("hasPage"));
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(targetContent.containsKey("changeVotes"));
    Assert.assertTrue(targetContent.containsKey("lastRanking"));
    Assert.assertTrue(targetContent.containsKey("change_cycle"));
    Assert.assertTrue(targetContent.containsKey("realTimeRanking"));
    Assert.assertTrue(targetContent.containsKey("lastCycleVotes"));
    Assert.assertTrue(targetContent.containsKey("realTimeVotes"));
    Assert.assertTrue(targetContent.containsKey("votesPercentage"));
    Assert.assertTrue(targetContent.containsKey("url"));
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    tronscanApiList.disGetConnect();
  }

}
