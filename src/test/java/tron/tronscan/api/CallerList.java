package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
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
 * @Date:2019-10-15 17:55
 */
@Slf4j
public class CallerList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);

  /**
   * constructor.合约调用者信息统计
   */
  @Test(enabled = true, description = "List data synchronization calleraddressstatistic")
  public void getCallerAddressStatistic() {
    //Get response
    response = TronscanApiList.getCallerAddressStatistic(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() >= 4);
    //total
    Assert.assertTrue(!responseContent.get("total").toString().isEmpty());

    //max
    targetContent = responseContent.getJSONObject("max");

    //caller_amount
    Assert.assertTrue(Double.valueOf(targetContent.get("caller_amount").toString()) >= 0);
    //day
    Assert.assertTrue(Long.valueOf(targetContent.get("day").toString()) >= 0);

    //min
    targetContent = responseContent.getJSONObject("min");
    //caller_amount
    Assert.assertTrue(Double.valueOf(targetContent.get("caller_amount").toString()) >= 0);
    //day
    Assert.assertTrue(Long.valueOf(targetContent.get("day").toString()) >= 0);

    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(
          Double.valueOf(responseArrayContent.getJSONObject(i).get("caller_amount").toString())
              >= 0);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("day"));

    }
  }

  /**
   * constructor.合约调用者数量统计信息
   */
  @Test(enabled = true, description = "List data synchronization triggeramountstatistic")
  public void getCallerAddAmouStat() {
    //Get response
    response = TronscanApiList.getCallerAddAmouStat(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() >= 4);
    //total
    Long total = Long
        .valueOf(responseContent.get("total").toString());
    //totalTrigger
    Long totalTrigger = Long
        .valueOf(responseContent.get("totalTrigger").toString());
    Assert.assertTrue(totalTrigger >= total);

    //totalCallerAmount
    Assert.assertTrue(Long.valueOf(responseContent.get("totalCallerAmount").toString()) >= 0);

    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(
          Double.valueOf(responseArrayContent.getJSONObject(i).get("trigger_amount").toString())
              >= 0);
      Assert.assertTrue(
          Long.valueOf(responseArrayContent.getJSONObject(i).get("caller_amount").toString())
              >= 0);
      //name
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("name"));
      //contract_address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i)
          .getString("contract_address")).matches());

    }
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
