package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;


@Slf4j
public class ContractEvents {

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
  @Test(enabled = true, description = "Get contract events")
  public void getContractEvents() {
    //Get response
    response = TronscanApiList.getContractEvents(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    JSONArray contractEventArray = responseContent.getJSONArray("data");

    //amount
    targetContent = contractEventArray.getJSONObject(0);
    Assert.assertTrue(Long.valueOf(targetContent.get("amount").toString()) >= 0);

    //transferFromAddress
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent
        .getString("transferFromAddress")).matches());

    //data
    Assert.assertTrue(targetContent.containsKey("data"));

    //decimals
    Integer decimals = Integer.valueOf(targetContent.get("decimals").toString());
    Assert.assertTrue(decimals >= 0 && decimals <= 7);

    //tokenName
    Assert.assertTrue(!targetContent.get("tokenName").toString().isEmpty());

    //transferToAddress
    Assert.assertTrue(patternAddress.matcher(targetContent
        .getString("transferToAddress")).matches());

    //block
    Assert.assertTrue(Integer.valueOf(targetContent.get("block").toString()) > 0);

    //confirmed
    Assert.assertTrue(Boolean.valueOf(targetContent.getString("confirmed")));

    //transactionHash
    Assert.assertTrue(!targetContent.get("transactionHash").toString().isEmpty());

    //timestamp
    Assert.assertTrue(!targetContent.get("timestamp").toString().isEmpty());

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }


}
