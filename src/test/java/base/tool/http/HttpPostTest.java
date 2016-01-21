package base.tool.http;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpPostTest {

    @Test
    public void test() {
        
        
        Map<String, String> headMap = new HashMap<String,String>();
        headMap.put("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
        RequestUtil.doPostStringBody("http://127.0.0.1:8888/console/body", "{test:{a:1,b:2}}", headMap);

        fail("Not yet implemented");
    }
    
    
    /**
     *  test
     *
     * @return
     */
    public static String sslTest() {
        String dataStr = "%7B%22basic%22%3A%7B%22phone" +
                "%22%3A%2213818681415%22%2C%22location%22%3A%22%E4%B8%8A%E6%B5%B7%E5%B8%82%22%2C%22" +
                "name%22%3A%22%E9%99%B6%E6%98%8E%E4%BC%9F%22%2C%22idcard%22%3A%22310107198904282111%22%2C%22" +
                "type%22%3A1%7D%2C%22job%22%3A%7B%22office%22%3A%22%E5%85%B6%E4%BB%96%22%2C%22" +
                "name%22%3A%22%E5%85%B4%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%B9%BF%E5%9C%BA%22%7D%7D";
        String paramStr = "channelId=19&data=" + dataStr;

//        List<NameValuePair> nameValuePairList = new ArrayList<>();
//        nameValuePairList.add(new BasicNameValuePair("channelId","19"));
//        nameValuePairList.add(new BasicNameValuePair("data",dataStr));

        ContentType contentType = ContentType.create(ContentType.APPLICATION_FORM_URLENCODED.getMimeType(), 
                Charset.defaultCharset());

        HttpPost httpPost = new HttpPost("https://as-dev.wecash.net/platform/register/channelAuto");
//        try {
//            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(nameValuePairList);
//            httpPost.setEntity(httpEntity);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        httpPost.setHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.toString()));

        StringEntity httpEntity = new StringEntity(paramStr, contentType);
        httpPost.setEntity(httpEntity);

        HttpClient httpClient = HttpClients.custom().build();
        try {
            HttpResponse response = httpClient.execute(httpPost);
            return  EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "error";
    }

}
