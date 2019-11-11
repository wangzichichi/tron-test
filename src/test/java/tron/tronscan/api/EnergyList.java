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

  /**
   * constructor.能量统计接口
   */
  @Test(enabled = true, description = "List data synchronization energystatistic")
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
   * constructor.获取能量每日统计信息
   */
  @Test(enabled = true, description = "List data synchronization energydailystatistic")
  public void getEnergyDailyStatistic() {
    //Get response
    response = TronscanApiList.getEnergyDailyStatistic(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() >= 3);
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalEnergy = Long.valueOf(responseContent.get("totalEnergy").toString());
    Assert.assertTrue(totalEnergy >= total);

    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      //contract_address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i)
          .getString("contract_address")).matches());
      //trx
      Assert.assertTrue(
          Long.valueOf(responseArrayContent.getJSONObject(i).get("trx").toString()) >= 0);
      //name
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("name"));
      //total_energy
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
