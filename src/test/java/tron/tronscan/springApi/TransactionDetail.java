package tron.tronscan.springApi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.JavaTronApiList;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.tronscan.api.TransactionList;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TransactionDetail {
  private JSONObject responseContent;
  private JSONObject javatronResponseContent;
  private JSONArray responseArrayContent;

  private JSONArray javatronResponseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private HttpResponse javatronResponse;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);

  @Test(enabled = true)
  public void test01GetTransactionDetail() throws Exception{
    //old interface
    Map<String,String> params = new HashMap<>();
    params.put("value", "a9ca689f1ab97c1efd596b00d57a089af5071a9ef87e6f443c930ea674afa513");
    javatronResponse = JavaTronApiList.getTransactionId(params);
    log.info("code is" + javatronResponse.getStatusLine().getStatusCode());
    Assert.assertEquals(javatronResponse.getStatusLine().getStatusCode(), 200);
    javatronResponseContent = JavaTronApiList.parseResponseContent(javatronResponse);
    JavaTronApiList.printJsonContent(javatronResponseContent);
    responseArrayContent = javatronResponseContent.getJSONArray("signature");
    JSONObject raw_data = javatronResponseContent.getJSONObject("raw_data");
    JSONArray contract = raw_data.getJSONArray("contract");
    JSONObject parameter = contract.getJSONObject(0);
    JSONObject value = parameter.getJSONObject("parameter");
    JSONObject valueInfo = value.getJSONObject("value");
    String owner_address = valueInfo.getString("owner_address");
    String contract_address = valueInfo.getString("contract_address");
    String type = parameter.getString("type");
    String ref_block_hash = raw_data.getString("ref_block_hash");
    String timestamp = raw_data.getString("timestamp");
    Thread.sleep(3000);

    //data object（new interface）
    Map<String,String> newParams = new HashMap<>();
//        newParams.put("txID","a9ca689f1ab97c1efd596b00d57a089af5071a9ef87e6f443c930ea674afa513");
//        newParams.put("fee_limit","10000000");
//        newParams.put("ref_block_hash", "1929193cea2451c2");
//        newParams.put("timestamp","1573464210274");
//        response = TronscanApiList.getTransactionList(tronScanNode,newParams);
//        log.info("code is " + response.getStatusLine().getStatusCode());
//        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
//        responseContent = TronscanApiList.parseResponseContent(response);
//        TronscanApiList.printJsonContent(responseContent);
//        System.out.println("<><><><><><><>   " + responseContent);

  }
}
