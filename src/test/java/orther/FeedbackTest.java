package orther;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import base.tool.codec.CodecException;
import base.tool.codec.MessageSign;
import base.tool.codec.aes.Codec1;
import base.tool.http.HttpConnectionManager;

public class FeedbackTest {

    private static Logger logger = LoggerFactory.getLogger(BreakTest.class);
    
    public static void feedbackTest() throws CodecException, ParseException, IOException {

        String url = "http://121.40.67.83:8080/application/feedback/phase.do";
        String secretKey = "C5FFD7B50E258BB4";

        JSONObject feedBackInfo = new JSONObject();
        feedBackInfo.put("apply_no", "201512090085000007");
        feedBackInfo.put("apply_no_channel", "11");
        feedBackInfo.put("phase", "50");
        feedBackInfo.put("description", "放款成功");
        feedBackInfo.put("is_first_application", "1");
        feedBackInfo.put("is_loan_success", "1");
        feedBackInfo.put("loan_term", "30");
        feedBackInfo.put("loan_amount", "1000");
        feedBackInfo.put("loan_rates", "100");

        String feecbackStr = feedBackInfo.toJSONString();
        logger.info(feecbackStr);
        String encodeFeedback = Codec1.strEncodBase64(secretKey, feedBackInfo.toJSONString());

        TreeMap<String, String> paramMap = new TreeMap<>();
        paramMap.put("apply_no", "201512140075000002");
        paramMap.put("channel_no", "79");
        paramMap.put("application_feedback_info", encodeFeedback);
        paramMap.put("timestamp", "" + System.currentTimeMillis());

        String signature = MessageSign.signSHA1ToHexStr(MessageSign.paramTreeMapToString(paramMap));

        paramMap.put("signature", signature);
        
        HttpPost httpPost = new HttpPost(url);
        HttpEntity httpEntity = new UrlEncodedFormEntity(map2NameValuePairList(paramMap), Charset.defaultCharset());
        httpPost.setEntity(httpEntity);
        CloseableHttpResponse response = HttpConnectionManager.getHttpClient().execute(httpPost);

        logger.info(EntityUtils.toString(response.getEntity()));
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
