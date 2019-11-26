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
 * @Date:2019-11-25 16:22
 */
@Slf4j
public class AccountsList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscanSpring.ip.list")
      .get(0);

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List account")
  public void test01getAccount() {
    //Get response
    int limit = 3;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-balance");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    response = TronscanApiList.getAccount(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    Assert.assertEquals(limit + 1, responseObject.size());
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseObject.getString("address")).matches());
    Assert.assertTrue(responseObject.containsKey("balance"));
    Assert.assertTrue(responseObject.containsKey("power"));
    Assert.assertTrue(responseContent.containsKey("total"));
    Assert.assertTrue(responseContent.containsKey("rangeTotal"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get a single account's detail ")
  public void getAccountList() {
    //Get response
    String address = "TWd4WrZ9wn84f5x1hZhL4DHvk738ns5jwb";
    Map<String, String> params = new HashMap<>();
    params.put("address", address);
    response = TronscanApiList.getAccountList(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    Assert.assertTrue(responseContent.size() >= 18);
    //allowExchange
    Assert.assertTrue(responseContent.containsKey("allowExchange"));
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(responseContent.getString("address")).matches());
    Assert.assertTrue(responseContent.containsKey("frozen_supply"));
    Assert.assertTrue(responseContent.containsKey("accountType"));
    Assert.assertTrue(responseContent.containsKey("addressTag"));
    Assert.assertTrue(responseContent.containsKey("name"));
    Assert.assertTrue(responseContent.containsKey("voteTotal"));
    Assert.assertTrue(Long.valueOf(responseContent.get("totalTransactionCount").toString()) >= 0);
    Assert.assertTrue(responseContent.containsKey("activePermissions"));

    //trc20token_balances json
    JSONArray exchangeArray = responseContent.getJSONArray("trc20token_balances");
    targetContent = exchangeArray.getJSONObject(0);
    //symbol
    Assert.assertTrue(!targetContent.get("symbol").toString().isEmpty());
    //balance
    Assert.assertTrue(Double.valueOf(targetContent.get("balance").toString()) >= 0);
    //decimals
    Integer decimals = Integer.valueOf(targetContent.get("decimals").toString());
    Assert.assertTrue(decimals >= 0 && decimals <= 18);
    //name
    Assert.assertTrue(!targetContent.get("name").toString().isEmpty());
    //contract_address
    Assert
        .assertTrue(patternAddress.matcher(targetContent.getString("contract_address")).matches());

    //bandwidth json
    targetContent = responseContent.getJSONObject("bandwidth");
    //energyRemaining
    Assert.assertTrue(Long.valueOf(targetContent.get("energyRemaining").toString()) >= 0);
    //totalEnergyLimit
    Assert.assertTrue(Long.valueOf(targetContent.get("totalEnergyLimit").toString()) >= 0);
    Assert.assertTrue(Long.valueOf(targetContent.get("totalEnergyWeight").toString()) >= 0);
    Assert.assertTrue(Double.valueOf(targetContent.get("netUsed").toString()) >= 0);
    Assert.assertTrue(Double.valueOf(targetContent.get("storageLimit").toString()) >= 0);
    Assert.assertTrue(Double.valueOf(targetContent.get("storagePercentage").toString()) >= 0);
    //assets
    Assert.assertTrue(targetContent.containsKey("assets"));

    //frozen json
    targetContent = responseContent.getJSONObject("frozen");
    Assert.assertTrue(Integer.valueOf(targetContent.get("total").toString()) >= 0);
    Assert.assertTrue(targetContent.containsKey("balances"));

    //accountResource json
    targetContent = responseContent.getJSONObject("accountResource");
    Assert.assertTrue(targetContent.containsKey("frozen_balance_for_energy"));

    //tokenBalances json
    JSONArray tokenBalancesArray = responseContent.getJSONArray("tokenBalances");
    targetContent = tokenBalancesArray.getJSONObject(0);
    Assert.assertTrue(Long.valueOf(targetContent.get("balance").toString()) >= 0);
    Assert.assertTrue(targetContent.containsKey("name"));

    //enabled
    Assert.assertTrue(responseContent.containsKey("enabled"));
    //url
    Assert.assertTrue(responseContent.containsKey("url"));

    //balances json
    JSONArray balancesArray = responseContent.getJSONArray("balances");
    targetContent = balancesArray.getJSONObject(0);
    Assert.assertTrue(Long.valueOf(targetContent.get("balance").toString()) >= 0);
    Assert.assertTrue(targetContent.containsKey("name"));

    //delegated json
    targetContent = responseContent.getJSONObject("delegated");
    Assert.assertTrue(targetContent.containsKey("sentDelegatedBandwidth"));
    Assert.assertTrue(targetContent.containsKey("sentDelegatedResource"));
    Assert.assertTrue(targetContent.containsKey("receivedDelegatedBandwidth"));
    //delegated contain receivedDelegatedResource
    JSONArray receivedDelegateArray = targetContent.getJSONArray("receivedDelegatedResource");
    targetContent = receivedDelegateArray.getJSONObject(0);
    Assert.assertTrue(targetContent.containsKey("expire_time_for_energy"));
    //from address
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("from")).matches());
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("to")).matches());

    //representative json
    targetContent = responseContent.getJSONObject("representative");
    Assert.assertTrue(Integer.valueOf(targetContent.get("lastWithDrawTime").toString()) >= 0);
    Assert.assertTrue(Integer.valueOf(targetContent.get("allowance").toString()) >= 0);
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }
}
