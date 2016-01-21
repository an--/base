package base.tool.http;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil {
    
    private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);
    
    /**
     * 发送一个 post 请求，使用默认的 CloseableHttpClient
     * post 请求内容为String 类型
     * 可自定义 http header
     *
     * @param url               请求地址
     * @param requestBody       请求内容 String
     * @param headerMap         自定义请求头,可为null
     * @return
     */
    public static String doPostStringBody(String url, String requestBody, Map<String,String> headerMap){
        return doPostStringBody(url, requestBody, headerMap, null);
    }

    /**
     * 发送一个 post 请求，使用默认的 CloseableHttpClient
     * post 请求内容为String 类型
     * 可自定义 http header
     * 可自定义ResponseHandler
     *
     *
     * @param url               请求地址
     * @param requestBody       请求内容 String
     * @param headerMap         自定义请求头,可为null
     * @param responseHandler   自定义 ResponseHandler，可为null,为null时返回status code 为 200 到 300 之间的 response
     * @return
     */
    public static String doPostStringBody(String url, String requestBody, Map<String,String> headerMap, ResponseHandler<String> responseHandler){
        if(TextUtils.isBlank(url)){
            return null;
        }

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        StringEntity httpEntity = new StringEntity(requestBody, "utf-8");
        httpPost.setEntity(httpEntity);

        if(null != headerMap && headerMap.isEmpty() ){
            httpPost.setHeaders(HttpHeaderUtil.map2HttpHeaders(headerMap));
        }

        try {
            if (null != responseHandler) {
                return client.execute(httpPost, responseHandler);
            } else {
                HttpResponse response = client.execute(httpPost);
                /* 处理状态码在200到300之间的响应 */
                int status = response.getStatusLine().getStatusCode();
                if(200 <= status && 300 > status){
                    return EntityUtils.toString(response.getEntity());
                } else {
                    logger.error("httpclient post({}), unexpected response status :{}", url, status);
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("httpclient post error({}).", url);
            return null;
        }
    }
    
}
