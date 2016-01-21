package base.tool.http;

import java.util.Iterator;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class HttpHeaderUtil {
    
    /**
     * map2HttpHeaders
     *
     * @param headerMap http header {name,value}
     * @return
     */
    public static Header[] map2HttpHeaders(Map<String, String> headerMap){
        int size = headerMap.size();
        Header[] headers = new BasicHeader[size];
        Iterator<Map.Entry<String,String>> itEntrySet = headerMap.entrySet().iterator();
        int index = 0;
        while (itEntrySet.hasNext()) {
            Map.Entry<String,String> entry = itEntrySet.next();
            headers[index++] = new BasicHeader(entry.getKey(),entry.getValue());
        }
        return headers;
    }

}
