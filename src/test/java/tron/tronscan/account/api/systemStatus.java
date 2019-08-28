package tron.tronscan.account.api;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.utils.Configuration;
import tron.common.utils.Utils;
import tron.common.tronscanApiList;

@Slf4j
public class systemStatus {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf").getStringList("tronscan.ip.list")
      .get(0);

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get system status")
  public void getSystemStatus() {
    //Get response
    response = tronscanApiList.getSystemStatus(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = tronscanApiList.parseResponseContent(response);
    tronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() >= 5);

    //database has block and confirmedBlock, confirmedBlock <= block
    targetContent = responseContent.getJSONObject("database");
    log.info(targetContent.get("confirmedBlock").toString());
    log.info(targetContent.get("block").toString());
    Assert.assertTrue(Long.valueOf(targetContent.get("confirmedBlock").toString()) > Long.valueOf(targetContent.get("block").toString()));

    //sync has one key:value,
    targetContent = responseContent.getJSONObject("sync");
    Assert.assertTrue(Double.valueOf(targetContent.get("progress").toString()) >= 99);
    log.info(targetContent.get("progress").toString());
    //network should

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    tronscanApiList.disConnect();
  }

}
