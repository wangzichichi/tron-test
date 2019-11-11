package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
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
 * @Author:jh
 * @Date:2019-08-29 11:26
 */
@Slf4j
public class NodeMap {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List all the nodes in the blockchain")
  public void getNodeMap() {
    response = TronscanApiList.getNodeMap(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() >= 3);
    Integer total = Integer.valueOf(responseContent.get("total").toString());
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    Assert.assertTrue(exchangeArray.size() == total);
    Assert.assertTrue(Double.valueOf(responseContent.get("code").toString()) >= 0);
    //country
    targetContent = exchangeArray.getJSONObject(0);
    Assert.assertTrue(targetContent.containsKey("country"));
    //lng and lat Contain double
    Assert.assertTrue(Double.valueOf(targetContent.get("lng").toString()) > -1000);
    Assert.assertTrue(Double.valueOf(targetContent.get("lat").toString()) > 0);

    Assert.assertTrue(targetContent.containsKey("ip"));


  }


  /**
   * constructor.上传节点信息,下载的信息为表格形式
   */
  @Test(enabled = true, description = "download the transaction info of a address (recent 10,000 transactions)")
  public void getInfo_upload() {
    String address = "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9";
    Map<String, String> Params = new HashMap<>();
    Params.put("address", address);
    response = TronscanApiList.getInfo_upload(tronScanNode, Params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    //TronscanApiList.printJsonContent(responseContent);

  }

  /**
   * constructor.查询节点概览信息,下载的信息为表格形式
   */
  @Test(enabled = true, description = "download daily new address number and transaction number info")
  public void getOverview_upload() {
    response = TronscanApiList.getOverview_upload(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    //responseContent = TronscanApiList.parseResponseContent(response);

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
