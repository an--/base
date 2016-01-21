package orther;

import com.alibaba.fastjson.JSONObject;

public class JSONTest {

    public static void main(String[] args) {
        System.out.println(JSONObject.parse(null));
        System.out.println(JSONObject.parse(""));
        System.out.println( null == JSONObject.toJSONString(null));
        System.out.println( "null".equals(JSONObject.toJSONString(null)));
        
        
        System.out.print(System.getProperty("https.protocols"));
    }
    
    
}
