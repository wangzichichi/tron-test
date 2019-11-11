package tron.tronscan.api;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import tron.common.utils.Configuration;


@Slf4j
public class Broadcast {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String, String> testAccount;
//  private String transactionHex = Configuration.getByPath("testng.conf")
//      .getString("defaultParameter.transactionHex");

  /**
   * constructor. 广播接口，得签名交易的，接口应该是要发起一笔交易的，所以先暂停自动化
   */
//  @Test(enabled = true, description = "Broadcast transaction hex")
//  public void test01BroadcastTransactionHex() {
//    //Get response
//    response = TronscanApiList.broadcast(tronScanNode, transactionHex);
//    log.info("code is " + response.getStatusLine().getStatusCode());
//    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
//    responseContent = TronscanApiList.parseResponseContent(response);
//    TronscanApiList.printJsonContent(responseContent);
//
//    //key 4
//    Assert.assertTrue(responseContent.containsKey("code"));
//    Assert.assertTrue(responseContent.containsKey("success"));
//    Assert.assertTrue(responseContent.containsKey("message"));
//    Assert.assertTrue(responseContent.containsKey("transaction"));
//
//  }

  /**
   * constructor.
   */
//  @AfterClass
//  public void shutdown() throws InterruptedException {
//    TronscanApiList.disConnect();
//  }

}
