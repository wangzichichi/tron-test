package tron.tronscan.account.api;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.tronscanApiList;
import tron.common.utils.Configuration;


@Slf4j
public class SystemStatus {

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
    long databaseBlock = Long.valueOf(targetContent.get("block").toString());
    long databaseConfirmedBlock = Long.valueOf(targetContent.get("confirmedBlock").toString());
    Assert.assertTrue(databaseBlock >= databaseConfirmedBlock);

    //sync has one key:value,
    targetContent = responseContent.getJSONObject("sync");
    Assert.assertTrue(Double.valueOf(targetContent.get("progress").toString()) >= 95);

    //network type should be mainnet
    targetContent = responseContent.getJSONObject("network");
    Assert.assertTrue(targetContent.get("type").equals("mainnet"));

    //full block is equal databaseBlock
    targetContent = responseContent.getJSONObject("full");
    long full = Long.valueOf(targetContent.get("block").toString());
    log.info("full:" + full +  " , databaseBlock:" + databaseBlock);
    Assert.assertEquals(full,databaseBlock);


    //full block is equal databaseBlock
    targetContent = responseContent.getJSONObject("solidity");
    long solidity = Long.valueOf(targetContent.get("block").toString());
    Assert.assertTrue(solidity == databaseConfirmedBlock);

    testAccount = tronscanApiList.generateAddress();
    log.info("privateKey:" + testAccount.get("privateKey") );
    log.info("hexAddress:" + testAccount.get("hexAddress") );
    log.info("address:" + testAccount.get("address") );




  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    tronscanApiList.disConnect();
  }

}
