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
public class JavaTronApiList {

  static HttpClient httpClient;
  static HttpPost httppost;
  static HttpGet httpget;
  static HttpResponse response;
  static Integer connectionTimeout = Configuration.getByPath("testng.conf")
      .getInt("defaultParameter.httpConnectionTimeout");
  static Integer soTimeout = Configuration.getByPath("testng.conf")
      .getInt("defaultParameter.httpSoTimeout");
  static String javatronNode = Configuration.getByPath("testng.conf")
      .getStringList("javatron.ip.list").get(0);
  static String transactionString;
  static String transactionSignString;
  static JSONObject responseContent;
  static JSONObject signResponseContent;
  static JSONObject transactionApprovedListContent;

  static {
    PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
    pccm.setDefaultMaxPerRoute(80);
    pccm.setMaxTotal(100);

    httpClient = new DefaultHttpClient(pccm);
  }


  public static HttpResponse createConnect(String url) {
    return createConnect(url, null);
  }

  public static HttpResponse createConnect(String url, JsonObject requestBody) {
    try {
      httpClient.getParams()
          .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
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
      response = httpClient.execute(httppost);
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
      System.out.println(url);
      httpget = new HttpGet(url);
      httpget.setHeader("Content-type", "application/json; charset=utf-8");
      httpget.setHeader("Connection", "Close");
//      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
//      System.out.println("请求开始时间： "+formatter.format(new Date()));
      Instant startTime = Instant.now();
      response = httpClient.execute(httpget);
      Instant endTime = Instant.now();
//      System.out.println("请求总耗时："+ Duration.between(startTime, endTime).toMillis()+" 毫秒" );
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
      System.out.println(result);
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


//-------------------------------------------------Method--------------------------------------------------


  public static HttpResponse getNowBlock() {
    try {
      Map<String, String> params = new HashMap<>();
      String requestUrl = "http://" + javatronNode + "walletsolidity/getnowblock";
      params.put("visible","true");
      response = createGetConnect(requestUrl,params);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getblockbynum(Map<String, String> params) {
    try {
      String requestUrl = "http://" + javatronNode + "walletsolidity/getblockbynum";
      params.put("visible","true");
      response = createGetConnect(requestUrl,params);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static HttpResponse getTransactionId(Map<String, String> params) {
    try{
      String requestUrl = "http://" + javatronNode + "walletsolidity/gettransactionbyid";
      params.put("visible","true");
      response = createGetConnect(requestUrl, params);
    }catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

}