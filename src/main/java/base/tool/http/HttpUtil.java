package base.tool.http;

import java.io.File;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-9-10
 * Time: 15:55:54
 * To change this template use File | Settings | File Templates.
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil{

    private static transient Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static HttpClient getHttpClient() {
        int connectionTimeOut = 5000;
        int connectionTimeToLive = 5000;
        int socketTimeout = 10000;
        
        String keyStorePath = "/Users/an/workspace/git/base/src/main/resource/keystore";
        String pwd = "123456";
        File keyStoreDir = new File(keyStorePath);
        File keyStoreFile = keyStoreDir.listFiles()[0];
        
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(keyStoreFile, pwd.toCharArray()).build();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeOut)               //  建立连接的超时时间
                .setSocketTimeout(socketTimeout)                    // soket 读取时间
                .build();
        return HttpClients.custom()
                .setSSLContext(sslContext)
                .setConnectionTimeToLive(connectionTimeToLive, TimeUnit.MILLISECONDS)      // 持久链接的最大生存时间
                .setDefaultRequestConfig(defaultRequestConfig)
                .disableAutomaticRetries()
                .build();
    }

    /**
     * 添加请求头到httpPost
     *
     * @param httpPost
     * @param headerMap
     */
    public static void addHeaders(HttpPost httpPost, Map<String,String> headerMap){
        if(null != headerMap && !headerMap.isEmpty() ){
            Header[] headers = HttpHeaderUtil.map2HttpHeaders(headerMap);
            for(int i = 0,l = headers.length; i < l; i++){
                httpPost.setHeader(headers[i]);
            }
        }
    }

    /**
     * 创建 form 类型 post 请求
     *
     * @param url
     * @param headerMap     请求头
     * @param paramMap      请求参数
     * @return
     */
    public static HttpPost createHttpPostFormUrlecoded(String url,
                                                       Map<String,String> headerMap,
                                                       Map<String, ?> paramMap){

        List<NameValuePair> pairList = map2NameValuePairList(paramMap);
        return createHttpPostFormUrlecoded(url, headerMap, pairList);
    }


    /**
     * 创建 form 类型 post 请求
     *
     * @param url
     * @param headerMap     请求头
     * @param pairList      请求参数
     * @return
     */
    public static HttpPost createHttpPostFormUrlecoded(String url,
                                                       Map<String,String> headerMap,
                                                       List<NameValuePair> pairList){

        HttpPost httpPost = new HttpPost(url);
        addHeaders(httpPost, headerMap);
        HttpEntity httpEntity = new UrlEncodedFormEntity(pairList, Charset.defaultCharset());
        httpPost.setEntity(httpEntity);
        return httpPost;
    }

    /**
     * 创建 conten-type = application/json HttpPost
     *
     * @param url
     * @param headerMap
     * @param paramMap
     * @return
     * @throws UnsupportedEncodingException
     */
    public static HttpPost createHttpPostJSON(String url,
                                              Map<String,String> headerMap,
                                              Map<String,Object> paramMap) {

        HttpPost httpPost = new HttpPost(url);
        addHeaders(httpPost, headerMap);
        StringEntity httpEntity = new StringEntity(JSONObject.toJSONString(paramMap), ContentType.APPLICATION_JSON);
        httpEntity.setContentType(ContentType.APPLICATION_JSON.toString());
        httpPost.setEntity(httpEntity);
        return httpPost;
    }

    /**
     * 发送一个 post 请求，使用默认的 CloseableHttpClient
     * 自定义 httpPost
     * 可自定义ResponseHandler
     *
     *
     * @param httpPost
     * @param responseHandler   自定义 ResponseHandler，不能为null
     * @return
     */
    public static <T> T doPost(HttpPost httpPost, ResponseHandler<T> responseHandler){

        HttpClient client = getHttpClient();
        int httpPostHashCode = httpPost.hashCode();
        try {
            log.info("doPost > httpPost.hashCode = {} , URI = {} ",
                    httpPostHashCode, httpPost.getURI());
            return client.execute(httpPost, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("doPost > error , uri = ({}) , httpPost.hashCode = {} , Exception = {} , exception message = {} ",
                    httpPost.getURI(), httpPostHashCode, e.getClass(), e.getMessage());
            return null;
        } finally {
            httpPost.releaseConnection();
        }
    }


    /**
     * 发送一个 post 请求，使用默认的 CloseableHttpClient
     * content-type = application/x-www-form-urlencoded; charset = defaultCharset;
     * 可自定义 RequstHeader
     * 可自定义ResponseHandler
     *
     * @param url
     * @param headerMap
     * @param paramMap
     * @param responseHandler
     * @param <T>
     * @return
     */
    public static <T> T doPostForm(String url,
                                   Map<String,String> headerMap,
                                   Map<String,Object> paramMap,
                                   ResponseHandler<T> responseHandler){

        HttpPost httpPost = createHttpPostFormUrlecoded(url, headerMap, paramMap);
        int httpPostHashCode = httpPost.hashCode();
        HttpClient client = getHttpClient();
        try {
            log.info("doPost > httpPost.hashCode = {} , URI = {} ",
                    httpPostHashCode, httpPost.getURI());
            return client.execute(httpPost, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("doPost > error , uri = ({}) , httpPost.hashCode = {} , Exception = {} , exception message = {} ",
                    httpPost.getURI(), httpPostHashCode, e.getClass(), e.getMessage());
            return null;
        } finally {
            httpPost.releaseConnection();
        }
    }


    /**
     * 发送一个 post 请求，使用默认的 CloseableHttpClient
     * 自定义 httpPost
     * 返回 请求的 response String
     *
     * @param httpPost
     * @return
     */
    public static String doPost(HttpPost httpPost) {
        HttpClient client = getHttpClient();
        int httpPostHashCode = httpPost.hashCode();
        try {
            log.info("doPost > httpPost.hashCode = {} , URI = {} ",
                    httpPostHashCode, httpPost.getURI());
            HttpResponse response = client.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("doPost > error , uri = ({}) , httpPost.hashCode = {} , Exception = {} , exception message = {} ",
                    httpPost.getURI(), httpPostHashCode, e.getClass(), e.getMessage());
            return null;
        } finally {
          httpPost.releaseConnection();
        }
    }

    /**
     *  发送一个 get 请求，使用默认的 CloseableHttpClient
     *
     *  返回 String response
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        int httpGetHashCode = httpGet.hashCode();
        HttpClient client = getHttpClient();
        try {
            log.info("doGet > doGet.hashCode = {} , URI = {} ",
                    httpGetHashCode, httpGet.getURI());
            HttpResponse response = client.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK <= status && HttpStatus.SC_MULTIPLE_CHOICES > status) {
                return EntityUtils.toString(response.getEntity());
            } else {
                log.warn("doGet > error, HttpClient get url=({}), Unexpected response status : ({}).", httpGet.getURI(), status);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("doGet > error , uri = ({}) , httpGet.hashCode = {} , Exception = {} , exception message = {} ",
                    httpGet.getURI(), httpGetHashCode, e.getClass(), e.getMessage());
            return null;
        } finally {
            httpGet.releaseConnection();
        }
    }
    
    /**
     * paraMap to form param
     *
     * @param paramMap
     * @return
     */
    public static List<NameValuePair> map2NameValuePairList( Map<String , ?> paramMap ){
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(paramMap.size());
        for( Iterator<? extends Map.Entry<String, ?>> iterator = paramMap.entrySet().iterator() ; iterator.hasNext() ;  ){
            Map.Entry<String,?> entry = iterator.next();
            Object value = entry.getValue();
            if( value != null ){
                NameValuePair nameValuePair;
                if(value instanceof String){
                    nameValuePair = new BasicNameValuePair(entry.getKey(), (String) value);
                } else {
                    nameValuePair = new BasicNameValuePair(entry.getKey(), JSONObject.toJSONString(value));
                }
                nameValuePairList.add(nameValuePair);
            }
        }
        return nameValuePairList;
    }
    
    

}