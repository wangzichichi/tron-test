package tron.tronscan.account.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.utils.Configuration;
import tron.common.utils.Utils;
import tron.common.tronscanApiList;

@Slf4j
public class WitnessesList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf").getStringList("tronscan.ip.list")
      .get(0);

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List all the witnesses in the blockchain")
  public void test01getWitnesses() {
    //Get response
    response = tronscanApiList.getWitnesses(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseArrayContent = tronscanApiList.parseArrayResponseContent(response);
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(responseArrayContent.size() >= 27);
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("address")).matches());
    Assert.assertTrue(responseObject.containsKey("name"));
    Assert.assertTrue(responseObject.containsKey("producer"));
    Assert.assertTrue(responseObject.containsKey("missedTotal"));
    Assert.assertTrue(responseObject.containsKey("producedTotal"));
    Assert.assertFalse(responseObject.getString("url").isEmpty());
    Assert.assertTrue(responseObject.getLong("latestBlockNumber") < responseObject.getLong("latestSlotNumber"));
    Assert.assertTrue(responseObject.getLong("votes") > 0);
    Assert.assertTrue(responseObject.getLong("producePercentage") < 100);
    Assert.assertTrue(responseObject.getLong("votesPercentage") > 0);
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
//    tronscanApiList.disConnect();
  }

}
