package orther;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

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

public class BreakTest {
	
	private static Logger logger = LoggerFactory.getLogger(BreakTest.class);
	
	
	
    public static void main(String[] args) {
        
        
        
    }

}
