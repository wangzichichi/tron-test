package tron.tronscan.api;

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
 * @Date:2019-08-29 13:41
 */
@Slf4j
public class Token_Trx20 {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private JSONObject sonContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String, String> testAccount;

  /**
   * constructor.
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
    Assert.assertTrue(responseContent.size() >= 3);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    JSONArray exchangeArray = responseContent.getJSONArray("trc20_tokens");
    Assert.assertTrue(rangeTotal >= total);
    //Assert.assertTrue(responseContent.containsKey("rangeTotal"));

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
    Assert.assertTrue(!targetContent.get("git_hub").toString().isEmpty());
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
   * constructor.
   */
  @Test(enabled = true, description = "地址下的转账查询")
  public void getTokentrc20_transfer() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-timestamp");
    Params.put("limit", "20");
    Params.put("start", "0");
    Params.put("count", "true");
    Params.put("total", "0");
    Params.put("start_timestamp", "1529856000000");
    Params.put("end_timestamp", "1567588908615");
    Params.put("direction", "all");
    Params.put("address", "TMuA6YqfCeX8EhbfYEg5y7S4DqzSJireY9");
    response = TronscanApiList.getTokentrc20_transfer(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //three object, "total" and "Data"
    Assert.assertTrue(responseContent.size() == 3);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    JSONArray exchangeArray = responseContent.getJSONArray("transfers");
    Assert.assertTrue(total >= rangeTotal);
    //Assert.assertTrue(responseContent.containsKey("rangeTotal"));
    targetContent = exchangeArray.getJSONObject(0);
    //contractRet
    Assert.assertTrue(targetContent.containsKey("contractRet"));
    //amount > 1
    Assert.assertTrue(Long.valueOf(targetContent.get("amount").toString()) >= 1);
    sonContent = targetContent.getJSONObject("cost");
    //address
    Assert.assertTrue(sonContent.containsKey("net_fee"));
    Assert.assertTrue(sonContent.containsKey("energy_usage"));
    Assert.assertTrue(sonContent.containsKey("energy_fee"));
    //实际消耗的能量
    Long energy_usage_total = Long.valueOf(sonContent.get("energy_usage_total").toString());
    //允许使用的最大能量
    Long origin_energy_usage = Long.valueOf(sonContent.get("origin_energy_usage").toString());
    Assert.assertTrue(origin_energy_usage >= energy_usage_total);
    //net_usage
    Assert.assertTrue(Long.valueOf(sonContent.get("net_usage").toString()) >= 1);
    //date_created
    Assert.assertTrue(Long.valueOf(targetContent.get("date_created").toString()) >= 1566962952);
    //owner_address
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("owner_address")).matches());
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("to_address")).matches());
    Assert
        .assertTrue(patternAddress.matcher(targetContent.getString("contract_address")).matches());
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("from_address")).matches());
    //block
    Assert.assertTrue(Long.valueOf(targetContent.get("block").toString()) >= 100);
    //type
    Assert.assertTrue(targetContent.containsKey("type"));
    //confirmed
    Assert.assertTrue(Boolean.valueOf(targetContent.getString("confirmed")));
    //parentHash and hash  64 place
    Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
    Assert.assertTrue(patternHash.matcher(targetContent.getString("hash")).matches());
  }

  /**
   * constructor.查询trc20通证持有者
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

    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());

    Assert.assertTrue(rangeTotal >= total);
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
