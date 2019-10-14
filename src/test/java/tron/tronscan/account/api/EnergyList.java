package tron.tronscan.account.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
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
 * @Date:2019-10-14 16:07
 */
@Slf4j
public class EnergyList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String, String> testAccount;

  /**
   * constructor.能量统计接口
   */
  @Test(enabled = true, description = "List data synchronization tps")
  public void getEnergyStatistic() {
    //Get response
    response = TronscanApiList.getEnergyStatistic(tronScanNode);
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

    //total_energy
    Assert.assertTrue(Long.valueOf(targetContent.get("total_energy").toString()) >= 0);
    //energy
    Assert.assertTrue(Long.valueOf(targetContent.get("energy").toString()) >= 0);
    //trx
    Assert.assertTrue(Long.valueOf(targetContent.get("trx").toString()) >= 0);
    Assert.assertTrue(Long.valueOf(targetContent.get("day").toString()) >= 0);

    //min
    targetContent = responseContent.getJSONObject("min");
    //total_energy
    Assert.assertTrue(Long.valueOf(targetContent.get("total_energy").toString()) >= 0);
    //energy
    Assert.assertTrue(Long.valueOf(targetContent.get("energy").toString()) >= 0);
    //trx
    Assert.assertTrue(Long.valueOf(targetContent.get("trx").toString()) >= 0);
    Assert.assertTrue(Long.valueOf(targetContent.get("day").toString()) >= 0);

    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(
          Long.valueOf(responseArrayContent.getJSONObject(i).get("trx").toString()) >= 0);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("day"));
      //
      Long total_energy = Long
          .valueOf(responseArrayContent.getJSONObject(i).get("total_energy").toString());
      Long energy = Long
          .valueOf(responseArrayContent.getJSONObject(i).get("energy").toString());
      Assert.assertTrue(total_energy >= energy);

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
