package tron.tronscan.account.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
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
public class Token_Trx20 {
  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
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
    Params.put("limit","20");
    Params.put("start","0");
    response = TronscanApiList.getTokentrc20(tronScanNode,Params);

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
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("contract_address")).matches());
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
    Assert.assertTrue(Double.valueOf(targetContent.get("total_supply_with_decimals").toString()) >= 1000);
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
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
