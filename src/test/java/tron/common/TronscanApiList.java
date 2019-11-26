package tron.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import tron.common.utils.Configuration;


@Slf4j
public class TronscanApiList {

  static HttpClient httpClient;
  static HttpPost httppost;
  static HttpGet httpget;
  static HttpResponse response;
  static Integer connectionTimeout = Configuration.getByPath("testng.conf")
      .getInt("defaultParameter.httpConnectionTimeout");
  static Integer soTimeout = Configuration.getByPath("testng.conf")
      .getInt("defaultParameter.httpSoTimeout");
  static String blockChainNode = Configuration.getByPath("testng.conf")
      .getStringList("blockChain.ip.list").get(0);
  static String transactionString;
  static String transactionSignString;
  static JSONObject responseContent;
  static JSONObject signResponseContent;
  static JSONObject transactionApprovedListContent;
  static Long requestTime = 0L;

  static {
    PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
    pccm.setDefaultMaxPerRoute(80);
    pccm.setMaxTotal(100);

    httpClient = new DefaultHttpClient(pccm);
  }

  /**
   * constructor.获取系统参数
   */
  public static HttpResponse getSystemStatus(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/system/status";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.获取系统tps信息
   */
  public static HttpResponse getSystemTps(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/system/tps";
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.获取系统tps信息
   */
  public static HttpResponse getSystemHomepage(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/system/homepage-bundle";
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse broadcast(String tronscanNode, String transactionHex) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/broadcast";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("transaction", transactionHex);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getContractEvents(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode
          + "api/contract/events"
          + "?start=0&limit=20&start_timestamp=1548000000000&end_timestamp=1548056638507";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getChainparameters(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/chainparameters";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getExchangesList(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/exchanges/list?sort=-balance";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getExchangesListAll(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/exchanges/listall";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getWitnesses(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/witness";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMaintenance_Statistic(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/witness/maintenance-statistic";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getLastestBlock(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/block/latest";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getNodeMap(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/nodemap";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.上传节点信息
   */
  public static HttpResponse getInfo_upload(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/v2/node/info_upload";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.查询节点概览信息
   */
  public static HttpResponse getOverview_upload(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/v2/node/overview_upload";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTokentrc20(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/token_trc20";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTokentrc20_transfer(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/trc10trc20-transfer";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getProposalList(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/proposal";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionTest(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/exchange/transaction";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getOverViewList(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/stats/overview";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getInternalTransaction(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/internal-transaction";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getToken20_holders(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/token_trc20/holders";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAnnouncement(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/announcement";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getExchangAphTest(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/exchange/kgraph";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getVoteTest(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/vote";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getCurrent_Cycle(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/vote/current-cycle";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getNext_Cycle(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/vote/next-cycle";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getContractTest(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/contract";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getFundTest(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/fund";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.获取Bittorrent的流通量和市值
   */
  public static HttpResponse getBitt_fund(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/bittorrent/fund";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.获取bittorrent代笔解锁时间表图
   */
  public static HttpResponse getBitt_graphic(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/bittorrent/graphic";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.查询wink信息
   */
  public static HttpResponse getWinkFund(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/wink/fund";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.获取wink代笔解锁时间表图
   */
  public static HttpResponse getWink_graphic(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/wink/graphic";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getContractTransactionList(String tronscanNode,
      String contractAddress, Integer limit) {
    try {
      String requestUrl = "http://" + tronscanNode
          + "api/contracts/transaction?sort=-timestamp&count=true&"
          + "limit=" + limit
          + "&start=0&"
          + "contract=" + contractAddress;
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getVoteWitnesses(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/vote/witness";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getVoteWitness(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/vote/witness";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getSearch(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/search";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getDonatorsList(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/listdonators";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getContractsList(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/contracts";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getSingleTokenList(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "/api/token?id=1001761&showAll=1";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.查询列表参与页
   */
  public static HttpResponse getTokenList(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "/api/token/list";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionInfo(String tronscanNode, String hash) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/transaction-info?hash=" + hash;
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getSimple_Transaction(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/simple-transaction";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */

  public static HttpResponse getAccount(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/account/list";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */

  public static HttpResponse getAccountList(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/account";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */

  public static HttpResponse getAccountStats(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/account/stats";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */

  public static HttpResponse getAccountVote(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/account/votes";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.查看SR信息
   */

  public static HttpResponse getAccountSr(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/account/sr";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.查看SR信息
   */

  public static HttpResponse getTotalCount(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/count";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockDetail(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/block";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getTransactionList(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/transaction";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getTransferList(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/transfer";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.能量统计接口
   */
  public static HttpResponse getEnergyStatistic(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/energystatistic";
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.获取能量每日统计信息
   */
  public static HttpResponse getEnergyDailyStatistic(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/energydailystatistic";
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.trigger统计
   */
  public static HttpResponse getTriggerStatistic(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/triggerstatistic";
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.trigger总数统计
   */
  public static HttpResponse getTriggerAmountStatistic(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/triggeramountstatistic";
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.合约调用者信息统计
   */
  public static HttpResponse getCallerAddressStatistic(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/calleraddressstatistic";
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.合约调用者数量统计信息
   */
  public static HttpResponse getCallerAddAmouStat(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/calleraddressamountstatistic";
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.合约能量统计信息
   */
  public static HttpResponse getOneContractEnergy(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/onecontractenergystatistic";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.单个合约调用统计信息
   */
  public static HttpResponse getOneContractTrigger(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/onecontracttriggerstatistic";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.单个合约调用者统计信息
   */
  public static HttpResponse getOneContractCaller(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/onecontractcallerstatistic";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.一个合约的caller信息
   */
  public static HttpResponse getCallers(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/onecontractcallers";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.一个合约的callvalue统计信息
   */
  public static HttpResponse getCaller_value(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/onecontract-callvalue";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.获取simple-transfer方法
   */
  public static HttpResponse getSimple_transfer(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/simple-transfer";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getFundsInfo(String tronscanNode) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/funds";
      response = createGetConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getContractCode(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/contracts/code";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }

    return response;
  }

  public static HttpResponse getContractTrigger(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/contracts/trigger";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }

    return response;
  }

  public static HttpResponse getTokensList(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/token";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }

    return response;
  }

  /**
   * constructor.根据tokenName获取token的信息
   */
  public static HttpResponse getTokensAddress(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/token/address";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }

    return response;
  }

  /**
   * constructor.获取trc10 token持有者
   */
  public static HttpResponse getTokenholders(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/tokenholders";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }

    return response;
  }

  public static HttpResponse getVotersList(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/vote";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }

    return response;
  }

  public static HttpResponse getSingleProposalList(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/proposal";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getExchangeTradeData(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "exchange/kgraph";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getAssetTransferList(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/asset/transfer";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getTransfersTrc20List(String tronscanNode,
      Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/token_trc20/transfers";
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.add CNY price
   */
  public static HttpResponse getProxyList(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/system/proxy";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.查询通证列表
   */
  public static HttpResponse getTokenOverview(String tronscanNode, Map<String, String> params) {
    try {
      String requestUrl = "http://" + tronscanNode + "api/tokens/overview";
      System.out.println(requestUrl);
      response = createGetConnect(requestUrl, params);
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse createConnect(String url) {
    return createConnect(url, null);
  }

  /**
   * constructor.
   */
  public static HttpResponse createConnect(String url, JsonObject requestBody) {
    try {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
          connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

      httppost = new HttpPost(url);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Close");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
      System.out.println("请求开始时间： " + formatter.format(new Date()));
      response = httpClient.execute(httppost);
      System.out.println("请求结束时间： " + formatter.format(new Date()));
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse createGetConnect(String url) {
    return createGetConnect(url, null);
  }

  /**
   * constructor.
   */
  public static HttpResponse createGetConnect(String url, Map<String, String> params) {
    try {
      httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
          connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
      if (params != null) {
        StringBuffer stringBuffer = new StringBuffer(url);
        stringBuffer.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
          stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        url = stringBuffer.toString();
      }
      httpget = new HttpGet(url);
      httpget.setHeader("Content-type", "application/json; charset=utf-8");
      httpget.setHeader("Connection", "Close");
//      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
//      System.out.println("请求开始时间： "+formatter.format(new Date()));
      Instant startTime = Instant.now();
      response = httpClient.execute(httpget);
      Instant endTime = Instant.now();
      requestTime = Duration.between(startTime, endTime).toMillis();
      System.out.println(url + " 请求总耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
//      System.out.println("请求结束时间： "+formatter.format(new Date()));
    } catch (Exception e) {
      e.printStackTrace();
      httpget.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static JSONArray parseArrayResponseContent(HttpResponse response) {
    try {
      String result = EntityUtils.toString(response.getEntity());
      System.out.println(result);
      StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
      response.setEntity(entity);
      JSONArray obj = JSONArray.parseArray(result);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static JSONObject parseResponseContent(HttpResponse response) {
    try {
      String result = EntityUtils.toString(response.getEntity());
      result = result.substring(0, result.lastIndexOf("}"));
      result = result + ",\"requestTime\":" + requestTime + "}";
      StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
      response.setEntity(entity);
      JSONObject obj = JSONObject.parseObject(result);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  /**
   * constructor.
   */
  public static void printJsonContent(JSONObject responseContent) {
    log.info("----------------------------Print JSON Start---------------------------");
    for (String str : responseContent.keySet()) {
      log.info(str + ":" + responseContent.get(str));
    }
    log.info("JSON content size are: " + responseContent.size());
    log.info("----------------------------Print JSON End-----------------------------");
  }

  /**
   * constructor.
   */
  public static void printJsonArrayContent(JSONArray responseContent) {
    log.info("----------------------------Print JSON Start---------------------------");
    for (int i = 0; i < responseContent.size(); i++) {
      for (String str : responseContent.getJSONObject(i).keySet()) {
        log.info(str + ":" + responseContent.get(i).toString());
      }
    }
    log.info("JSON content size are: " + responseContent.size());
    log.info("----------------------------Print JSON End-----------------------------");
  }


  /**
   * constructor.
   */
  public static HashMap<String, String> generateAddress() {
    try {
      String requestUrl = "http://" + blockChainNode + "/wallet/generateaddress";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    responseContent = parseResponseContent(response);
    HashMap<String, String> accountInfo = new HashMap<>();
    accountInfo.put("privateKey", responseContent.getString("privateKey"));
    accountInfo.put("address", responseContent.getString("address"));
    accountInfo.put("hexAddress", responseContent.getString("hexAddress"));
    log.info("privateKey:" + accountInfo.get("privateKey"));
    log.info("hexAddress:" + accountInfo.get("hexAddress"));
    log.info("address:" + accountInfo.get("address"));
    return accountInfo;
  }


  /**
   * constructor.
   */
  public static void disConnect() {
    httppost.releaseConnection();
  }

  /**
   * constructor.
   */
  public static void disGetConnect() {
    httpget.releaseConnection();
  }

}