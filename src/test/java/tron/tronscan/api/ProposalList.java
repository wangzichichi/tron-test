package tron.tronscan.api;

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
 * @Author:jh
 * @Date:2019-08-29 15:22
 */
public class ProposalList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private JSONObject proposalContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String, String> testAccount;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List all the proposals in the blockchain")
  public void getProposalList() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-number");
    Params.put("limit", "20");
    Params.put("start", "0");
    response = TronscanApiList.getProposalList(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //two object, "total" and "Data"
    Assert.assertTrue(responseContent.size() >= 2);
    Integer total = Integer.valueOf(responseContent.get("total").toString());
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    Assert.assertTrue(total >= 0);
    //
    targetContent = exchangeArray.getJSONObject(0);
    //createTime
    Assert.assertTrue(targetContent.containsKey("createTime"));

    //proposer json
    proposalContent = targetContent.getJSONObject("proposer");
    //producedTotal
    Assert.assertTrue(Double.valueOf(proposalContent.get("producedTotal").toString()) >= 0);
    //JSONArray proposalArray = responseContent.getJSONArray("proposer");
    //address
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(patternAddress.matcher(proposalContent.getString("address")).matches());
    //name
    Assert.assertTrue(!proposalContent.get("name").toString().isEmpty());
    //url
    Assert.assertTrue(!proposalContent.get("url").toString().isEmpty());
    //producePercentage
    Assert.assertTrue(Double.valueOf(proposalContent.get("producePercentage").toString()) >= 0);
    //latestSlotNumber
    Assert.assertTrue(Double.valueOf(proposalContent.get("latestSlotNumber").toString()) >= 0);
    //producer
//    Assert.assertTrue(Boolean.valueOf(proposalContent.getString("producer")));
    //votes
    Assert.assertTrue(Double.valueOf(proposalContent.get("votes").toString()) >= 0);
    //missedTotal
    Assert.assertTrue(Double.valueOf(proposalContent.get("missedTotal").toString()) >= 0);
    //producedTrx
    Assert.assertTrue(Double.valueOf(proposalContent.get("producedTrx").toString()) >= 0);
    //votesPercentage
    Assert.assertTrue(Double.valueOf(proposalContent.get("votesPercentage").toString()) >= 0);
    //latestBlockNumber
    Assert.assertTrue(Double.valueOf(proposalContent.get("latestBlockNumber").toString()) >= 0);
    //expirationTime
    Assert.assertTrue(targetContent.containsKey("expirationTime"));
    //approvals
    Assert.assertTrue(targetContent.containsKey("approvals"));
    //paramters
    Assert.assertTrue(targetContent.containsKey("paramters"));
    //state
    Assert.assertTrue(targetContent.containsKey("state"));
    //latestBlockNumber
    Assert.assertTrue(Long.valueOf(targetContent.get("proposalId").toString()) >= 0);
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }


}
