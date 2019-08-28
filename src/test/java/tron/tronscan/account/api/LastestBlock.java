package tron.tronscan.account.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.tronscanApiList;
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
  @Test(enabled = true, description = "Get system status")
  public void getLastestBlockTest() {
    response = tronscanApiList.getLastestBlock(tronScanNode);
    //log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = tronscanApiList.parseResponseContent(response);
    tronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() >= 5);
  }
}
