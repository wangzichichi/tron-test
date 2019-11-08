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
public class SingleProposalList {

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
   * constructor
   */
  @Test(enabled = true, description = "List a single proposal detail")
  public void test01getSingleProposalList() {
    //Get response
    Map<String, String> params = new HashMap<>();
    String id = "16";
    params.put("id", id);
    response = TronscanApiList.getSingleProposalList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("proposalId"));
    Assert.assertTrue(responseContent.containsKey("expirationTime"));
    Assert.assertTrue(responseContent.containsKey("createTime"));

    //approvals array
    responseArrayContent = responseContent.getJSONArray("approvals");
    //object approval
    targetContent = responseArrayContent.getJSONObject(0);
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(targetContent.containsKey("url"));
    Assert.assertTrue(targetContent.containsKey("missedTotal"));
    Assert.assertTrue(targetContent.containsKey("producedTotal"));
    Assert.assertTrue(targetContent.containsKey("producedTrx"));
    Assert.assertTrue(Double.valueOf(targetContent.getString("producePercentage")) <= 100);
    Assert.assertTrue(Double.valueOf(targetContent.getString("votesPercentage")) <= 100);
    Assert.assertTrue(!targetContent.getString("latestBlockNumber").isEmpty());
    Assert.assertTrue(!targetContent.getString("latestSlotNumber").isEmpty());
    Assert.assertTrue(!targetContent.getString("latestSlotNumber").isEmpty());

    //object proposer
    targetContent = responseContent.getJSONObject("proposer");
    Assert.assertTrue(targetContent.containsKey("name"));
    Assert.assertTrue(targetContent.containsKey("url"));
    Assert.assertTrue(targetContent.containsKey("missedTotal"));
    Assert.assertTrue(targetContent.containsKey("producedTotal"));
    Assert.assertTrue(targetContent.containsKey("producedTrx"));
    Assert.assertTrue(targetContent.containsKey("votes"));
    Assert.assertTrue(Double.valueOf(targetContent.getString("producePercentage")) <= 100);
    Assert.assertTrue(Double.valueOf(targetContent.getString("votesPercentage")) <= 100);
    Assert.assertTrue(!targetContent.getString("latestBlockNumber").isEmpty());
    Assert.assertTrue(!targetContent.getString("latestSlotNumber").isEmpty());
    Assert.assertTrue(!targetContent.getString("latestSlotNumber").isEmpty());
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
  }


  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
