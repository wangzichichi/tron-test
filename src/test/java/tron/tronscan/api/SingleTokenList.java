package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class SingleTokenList {

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
  @Test(enabled = true, description = "List a single trc10 token's detail")
  public void test01getSingleTokenList() {
    //Get response
    response = TronscanApiList.getSingleTokenList(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //Three key, "total","totalAll","data"
    Assert.assertTrue(responseContent.size() == 3);
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.containsKey("totalAll"));

    //data
    responseArrayContent = responseContent.getJSONArray("data");
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
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
