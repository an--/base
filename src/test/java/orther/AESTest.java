package orther;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.tool.codec.aes.AES;

public class AESTest {
    
    private static Logger logger = LoggerFactory.getLogger(AESTest.class); 

    public static void main(String[] args) throws UnsupportedEncodingException {
        String secretKey = "A3B4C5D6E6F70901";
        String iv = "A3B4C5D6E6F70901";

        String key = "3FADAE9950B216AF";
        String encodedUrl = "www.jiedianqian.com?apply_no=201512160086000007&channel_no=86"
                + "&user_attribute=n%2FFd2BW5PwT6CS%2B3gnERzJ1ctFAJIcb9GiBp6sN1xgYxXgiPeZ0QE66gm7SdxesH9eQotxhztrEkQdlIgkaaHgw11csfNzxoiJc16fOpbfcVgREExurtu1N26g6pj8rz"
                + "&signature=BA8C5E55AD16C623A9760067543A62A102BB20D2";
        
        String applyNoStr = "201512160086000007";
        String channelNoStr = "86";
        String urlEncodedAttrStr = "n%2FFd2BW5PwT6CS%2B3gnERzJ1ctFAJIcb9GiBp6sN1xgYxXgiPeZ0QE66gm7SdxesH9eQotxhztrEkQdlIgkaaHgw11csfNzxoiJc16fOpbfcVgREExurtu1N26g6pj8rz";
        String decodedUserAttr = URLDecoder.decode(urlEncodedAttrStr, "utf-8");
        String signatureStr = "BA8C5E55AD16C623A9760067543A62A102BB20D2";
        
        String pNameApplyNo = "apply_no";
        String pNameChannelNo = "channel_no";
        String pNameUserAttribute = "user_attribute";
        TreeMap<String, String> paramMap = new TreeMap<>();
        paramMap.put(pNameApplyNo, applyNoStr);
        paramMap.put(pNameChannelNo, channelNoStr);
        paramMap.put(pNameUserAttribute, decodedUserAttr);

        String sha1Str = MessageSign.paramTreeMapToString(paramMap);
        logger.debug("before sha1 : {}", sha1Str);
        
        logger.info(MessageSign.signSHA1HexStr(sha1Str));
        
        logger.info(Codec.base64StrDecode(key, decodedUserAttr));
        
    }

}
