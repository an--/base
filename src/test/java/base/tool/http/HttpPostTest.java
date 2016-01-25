package base.tool.http;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpPostTest {
    
    private static Logger logger = LoggerFactory.getLogger(HttpPostTest.class);

    @Test
    public void test() {
        
        keyStoreTest();

        fail("Not yet implemented");
    }
    
    public static void keyStoreTest() {
        
        String pwd = "123456";
        
        String keyStorePath = "/Users/an/workspace/git/base/src/main/resource/keystore";
//        String classPath = Thread.currentThread().getContextClassLoader().getResource("/").toString();
        
        File keyStoreDir = new File(keyStorePath);
        File keyStoreFile = keyStoreDir.listFiles()[0];
        FileInputStream keyStoreFileIn;
        try {
            keyStoreFileIn = new FileInputStream(keyStoreFile);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(keyStoreFileIn, pwd.toCharArray());
            
            String url = "https://zhuhai.szrrjc.com/services/rongzhijia";
            
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", "1");
            HttpPost httpPost = HttpUtil.createHttpPostFormUrlecoded(url, null, paramMap);
            String response = HttpUtil.doPost(httpPost);
            logger.info("keyStoreTest > uri = {} , response = {}", response);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }
    
    public static void clientTimeSettingTest() {
        String hostStr = "http://www.google.com/";
        String url = hostStr + "/accountManager/ajax/doLogin";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", "mager");
        paramMap.put("password", "mager");
        
        HttpPost httpPost = HttpUtil.createHttpPostFormUrlecoded(url, null, paramMap);
        String responseBodyStr = HttpUtil.doPost(httpPost);
        logger.info("clientTimeSettingTest > response = {} ", responseBodyStr);
        
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
