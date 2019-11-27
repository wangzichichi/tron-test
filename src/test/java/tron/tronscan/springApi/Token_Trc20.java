package tron.tronscan.springApi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
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
 * @Date:2019-11-25 17:09
 */
@Slf4j
public class Token_Trc20 {


  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private JSONObject sonContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscanSpring.ip.list")
      .get(0);

  /**
   * constructor.查询trc20通证持有者 增加service_type字段 Limit不为零
   */
  @Test(enabled = true, description = "查询trc20通证持有者")
  public void getInternal_transaction() {
    //
    String address = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-balance");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("contract_address", address);
    //Three object "total" ,"data","rangeTotal"
    response = TronscanApiList.getToken20_holders(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("service_type"));
    //Address list
    JSONArray exchangeArray = responseContent.getJSONArray("trc20_tokens");
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(exchangeArray.size() > 0);
    for (int i = 0; i < exchangeArray.size(); i++) {
      Assert.assertTrue(
          patternAddress.matcher(exchangeArray.getJSONObject(i).getString("holder_address"))
              .matches());
      Assert.assertTrue(
          Long.valueOf(exchangeArray.getJSONObject(i).get("balance").toString()) > 1000);
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("addressTag"));
    }

  }

  /**
   * constructor.查询trc20通证持有者 增加service_type字段 Limit为零
   */
  @Test(enabled = true, description = "查询trc20通证持有者")
  public void getInternal_transactionLimit() {
    //
    String address = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-balance");
    Params.put("limit", "0");
    Params.put("start", "0");
    Params.put("contract_address", address);
    //Three object "total" ,"data","rangeTotal"
    response = TronscanApiList.getToken20_holders(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("service_type"));
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);

  }

  /**
   * constructor.limit不为零
   */
  @Test(enabled = true, description = "List all the trc20 tokens in the blockchain")
  public void getTokentrc20() {
    //Get response
    Map<String, String> Params = new HashMap<>();
//    Params.put("sort","-balance");
    Params.put("limit", "20");
    Params.put("start", "0");
    response = TronscanApiList.getTokentrc20(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.containsKey("service_type"));
    JSONArray exchangeArray = responseContent.getJSONArray("trc20_tokens");

    targetContent = exchangeArray.getJSONObject(0);
    //icon_url
    Assert.assertTrue(targetContent.containsKey("icon_url"));
    //total_supply and issue_ts Contain > 0
    Assert.assertTrue(Double.valueOf(targetContent.get("total_supply").toString()) >= 0);
    Assert.assertTrue(Double.valueOf(targetContent.get("issue_ts").toString()) >= 0);
    //volume24h and index
    Assert.assertTrue(Double.valueOf(targetContent.get("volume24h").toString()) >= 0);
    Assert.assertTrue(Long.valueOf(targetContent.get("index").toString()) >= 1);
    Assert.assertTrue(!targetContent.get("symbol").toString().isEmpty());
    //total_supply_str can empty
    Assert.assertTrue(targetContent.containsKey("total_supply_str"));
    //contract_address
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert
        .assertTrue(patternAddress.matcher(targetContent.getString("contract_address")).matches());
    //gain
    Assert.assertTrue(targetContent.containsKey("gain"));
    //home_page
    Assert.assertTrue(!targetContent.get("home_page").toString().isEmpty());
    Assert.assertTrue(Double.valueOf(targetContent.get("volume").toString()) >= 0);
    //contract_address
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("issue_address")).matches());
    //token_desc
    Assert.assertTrue(!targetContent.get("token_desc").toString().isEmpty());
    Assert.assertTrue(Double.valueOf(targetContent.get("price_trx").toString()) >= 0);
    //git_hub
    Assert.assertTrue(targetContent.containsKey("git_hub"));
    //price
    Assert.assertTrue(targetContent.containsKey("price"));
    //total_supply_with_decimals
    Assert.assertTrue(
        Double.valueOf(targetContent.get("total_supply_with_decimals").toString()) >= 1000);
    //decimals
    Integer decimals = Integer.valueOf(targetContent.get("decimals").toString());
    Assert.assertTrue(decimals >= 0 && decimals <= 7);
    //name
    Assert.assertTrue(!targetContent.get("name").toString().isEmpty());
    //social_media_list
    Assert.assertTrue(!targetContent.get("social_media_list").toString().isEmpty());
    Assert.assertTrue(!targetContent.get("issue_time").toString().isEmpty());
    Assert.assertTrue(targetContent.containsKey("white_paper"));
    Assert.assertTrue(targetContent.containsKey("social_media"));
  }

  /**
   * constructor.limit为零
   */
  @Test(enabled = true, description = "List all the trc20 tokens in the blockchain")
  public void getTokentrc20Limit() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("limit", "0");
    Params.put("start", "0");
    response = TronscanApiList.getTokentrc20(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() >= 3);
    Assert.assertTrue(responseContent.containsKey("service_type"));
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
