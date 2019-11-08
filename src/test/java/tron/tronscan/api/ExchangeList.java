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
public class ExchangeList {

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
  @Test(enabled = true, description = "Get exchange list")
  public void getExchangeList() {
    //Get response
    response = TronscanApiList.getExchangesList(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //Two object, "total" and "Data"
    Assert.assertTrue(responseContent.size() >= 2);
    Integer total = Integer.valueOf(responseContent.get("total").toString());
    JSONArray exchangeArray = responseContent.getJSONArray("Data");
    Assert.assertTrue(exchangeArray.size() == total);

    //first_token_id
    targetContent = exchangeArray.getJSONObject(0);
    Long firstTokenId = Long.valueOf(targetContent.get("first_token_id").toString());
    Assert.assertTrue(firstTokenId > 1000000);

    //up_down_percent > -1 && up_down_percent < 1
    Double upDownPercent = Double.valueOf(targetContent.get("up_down_percent").toString());
    Assert.assertTrue(upDownPercent >= -1 && upDownPercent <= 1);

    //second_token_balance
    Long secondTokenBalance = Long.valueOf(targetContent.get("second_token_balance").toString());
    Assert.assertTrue(secondTokenBalance >= 0);

    //exchange_name
    String exchangeName = targetContent.getString("exchange_name");
    Assert.assertTrue(exchangeName.contains(firstTokenId.toString()));

    //volume
    Assert.assertTrue(!targetContent.get("volume").toString().isEmpty());

    //exchange_id
    Assert.assertTrue(Integer.valueOf(targetContent.get("exchange_id").toString()) > 0);

    //high
    Assert.assertTrue(!targetContent.get("high").toString().isEmpty());

    //first_token_balance
    Long firstTokenBalance = Long.valueOf(targetContent.get("first_token_balance").toString());
    Assert.assertTrue(firstTokenBalance >= 0);

    //high
    Assert.assertTrue(!targetContent.get("low").toString().isEmpty());

    //Price
    Double price = Double.valueOf(targetContent.get("price").toString());
    Assert.assertTrue(price >= 0);

    //first_owner_address
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(targetContent
        .getString("first_owner_address")).matches());

    //creator_address
    Assert.assertTrue(patternAddress.matcher(targetContent.getString("creator_address")).matches());

    //svolume
    Assert.assertTrue(!targetContent.get("svolume").toString().isEmpty());

    //first_token_abbr
    Assert.assertTrue(!targetContent.get("first_token_abbr").toString().isEmpty());

    //exchange_abbr_name
    Assert.assertTrue(!targetContent.get("exchange_abbr_name").toString().isEmpty());

    //second_token_id
    Assert.assertTrue(!targetContent.get("second_token_id").toString().isEmpty());

    //status
    Assert.assertTrue(!targetContent.get("status").toString().isEmpty());

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }


}
