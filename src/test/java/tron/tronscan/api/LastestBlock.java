package tron.tronscan.api;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
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
 * @Author:tron
 * @Date:2019-08-28 21:05
 */
public class LastestBlock {
  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String,String> testAccount;


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get the lastest block")
  public void getLastestBlockTest() {
    response = TronscanApiList.getLastestBlock(tronScanNode);
    //log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //the lastest block
    Assert.assertTrue(responseContent.size() >= 10);
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseContent.getString("witnessAddress")).matches());

    Assert.assertTrue(responseContent.containsKey("txTrieRoot"));
    //parentHash and hash  64 place
    Pattern patternHash = Pattern.compile("^0000000000[a-z0-9]{54}");
    Assert.assertTrue(patternHash.matcher(responseContent.getString("parentHash")).matches());
    Assert.assertTrue(patternHash.matcher(responseContent.getString("hash")).matches());
    //nrOfTrx greater 0
    Assert.assertTrue(responseContent.getLong("nrOfTrx") > 0);
    //timestamp
    Assert.assertFalse(responseContent.get("timestamp").toString().isEmpty());
    //confirmed true or false
    //Assert.assertTrue(Boolean.valueOf(targetContent.getString("confirmed")));
    Assert.assertTrue(responseContent.containsKey("confirmed"));
    Assert.assertTrue(responseContent.getLong("witnessId") >= 0);
    Assert.assertTrue(responseContent.getLong("size") > 0);
    Assert.assertTrue(responseContent.getLong("number") > 100);


  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
