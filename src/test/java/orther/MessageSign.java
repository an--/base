package orther;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * * <p> Every implementation of the Java platform is required to support
 * the following standard {@code MessageDigest} algorithms:
 * <ul>
 * <li>{@code MD5}</li>
 * <li>{@code SHA-1}</li>
 * <li>{@code SHA-256}</li>
 * </ul>
 * 
 * @author an
 *
 */
public class MessageSign {
    
    public static final Logger logger = LoggerFactory.getLogger(MessageSign.class);
    
    public static final Charset CHARSET = Charset.forName("utf-8");
    
    public static final String ALGORITHMS_SHA1 = "SHA-1";
    
    /**
     * 消息摘要，使用参数 algorithms 指定的算法
     * 
     * @param algorithms
     * @param inputStr
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] sign(String algorithms, String inputStr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHMS_SHA1);
        messageDigest.update(inputStr.getBytes(CHARSET));
        return messageDigest.digest();
    }
    
    /**
     * 转换 byte[] 到十六进制字符串
     * 
     * @param inputBytes
     * @return
     */
    public static String byte2HexStr(byte[] inputBytes) {
        StringBuilder hexstrBuilder = new StringBuilder();
        for (int i = 0; i < inputBytes.length; i++) {
            byte temp = inputBytes[i];
            if (0x0 <= temp && 0xf >= temp) {
                hexstrBuilder.append('0').append(Integer.toHexString(temp));
            } else {
                hexstrBuilder.append(Integer.toHexString(temp & 0xFF));
            }
        }
        return hexstrBuilder.toString().toUpperCase();
    }
    
    /**
     * SHA1 签名,并将签名结果转换成 十六进制字符串
     * 
     * @param inputStr
     * @return
     */
    public static String signSHA1HexStr(String inputStr) {
        try {
            return byte2HexStr(sign(ALGORITHMS_SHA1, inputStr));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 拼接参数字符串
     * 
     * @param paramMap
     * @return
     */
    public static String paramTreeMapToString(TreeMap<String, String> paramMap) {
        StringBuilder paramStrBuilder = new StringBuilder();
        
        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator(); 
        while (it.hasNext()) {
            Map.Entry<String, String> entrySet = it.next();
            paramStrBuilder.append(entrySet.getKey()).append(entrySet.getValue());
        }
        return paramStrBuilder.toString();
    }
    
    /**
     * 利用 TreeMap 对 param 参数名称进行字典排序，然后拼接字符串，然后进行签名
     * 
     * @param applyNo
     * @param channelNo
     * @param applyInfo
     * @param userAttribute
     * @param timestamp
     * @return
     */
    public static String signTest() {
        
        String applyNo = "201512140075000002";
        String channelNo = "211"; 
        String userAttribute = "3Zp3/xXZ6elYPlqfsWgiR45kPk+oiKUqu0fW2a5hdhMURh58/PlfYjY0TGXNmc9QDlQ3aM14ShlJ\njgqVom4RSOsdQwhhlqbyWP77UjoDVBKCRdcIhzPFfhDSLAkK6d/0"; 
        
        String pNameApplyNo = "apply_no";
        String pNameChannelNo = "channel_no";
        String pNameUserAttribute = "user_attribute";
        TreeMap<String, String> paramMap = new TreeMap<>();
        paramMap.put(pNameApplyNo, applyNo);
        paramMap.put(pNameChannelNo, channelNo);
        paramMap.put(pNameUserAttribute, userAttribute);
        
        return signSHA1HexStr(paramTreeMapToString(paramMap));
    }
    
    public static void main(String[] args) {
        logger.info(signTest(), "utf-8");
        logger.info(signSHA1HexStr("人人聚财").substring(0, 16));
    }

}
