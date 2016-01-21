package base.tool.codec.aes;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.tool.codec.CodecException;
import base.tool.codec.MessageSign;

public class Codec1 {
    
    private static Logger logger = LoggerFactory.getLogger(Codec1.class);

    public static final Charset CHARSET = Charset.forName("utf-8");

    public static final byte keyStrSzie = 16;

    public static final int BLOCK_SIZE = 32;

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     *
     * @param secretStr
     * @param inputStr
     * @return
     * @throws CodecException
     */
    public static byte[] encode(String secretStr, String inputStr) throws CodecException {
        byte[] secretKeyBytes = secretStr.getBytes(CHARSET);
        if (keyStrSzie != secretKeyBytes.length) {
            throw new CodecException(CodecException.CodecExceptionEnum.ILLEGAL_KEY);
        }
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);
        byte[] inputBytes = inputStr.getBytes(CHARSET);

        int inputSize = inputBytes.length;
        int padSize = BLOCK_SIZE - (inputSize%BLOCK_SIZE);
        byte padByte = (byte) padSize;
        int withPadSize = inputSize + padSize;
        byte[] inputBytePading = new byte[withPadSize];
        System.arraycopy(inputBytes, 0, inputBytePading, 0, inputSize);
        for (int i = 1; i <= padSize; i++) {
            inputBytePading[withPadSize - i] = padByte;
        }

        byte[] outputBytes = AES.encryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytePading);
        return outputBytes;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     * 并对加密后的字节数组调用 sun.misc.BASE64Encoder.encode 方法，
     * 转换成 base64 字符串返回。
     *
     * @param secretStr
     * @param inputStr
     * @return
     * @throws CodecException
     */
    public static String strEncodBase64(String secretStr, String inputStr) throws CodecException {
        String base64Str = Base64.encodeBase64String(encode(secretStr, inputStr));
        logger.debug("strEncodBase64 > base64 encrypt = {}", base64Str);
        return base64Str;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     *
     * @param secretStr
     * @param inputBytes
     * @return
     * @throws CodecException
     */
    public static byte[] decode(String secretStr, byte[] inputBytes) throws CodecException {
        byte[] secretKeyBytes = secretStr.getBytes(CHARSET);
        if (keyStrSzie != secretKeyBytes.length) {
            throw new CodecException(CodecException.CodecExceptionEnum.ILLEGAL_KEY);
        }
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);

        byte[] outputBytes = AES.decryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytes);

        int outputSize = outputBytes.length;
        byte padByte = outputBytes[outputSize - 1];
        int noPadSize = outputSize - padByte;
        byte[] outputByteNoPading = new byte[noPadSize];
        System.arraycopy(outputBytes, 0, outputByteNoPading, 0, noPadSize);

        return outputByteNoPading;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     * 并对加密后的字节数组调用 sun.misc.BASE64Encoder.encode 方法，
     * 转换成 base64 字符串返回。
     *
     * @param secretStr
     * @param inputStr
     * @return
     * @throws CodecException
     */
    public static String base64StrDecode(String secretStr, String inputStr) throws CodecException{
        byte[] inputBytes;
        inputBytes = Base64.decodeBase64(inputStr);
        String outputStr = new String(decode(secretStr, inputBytes), CHARSET);
        logger.debug("base64Decode > base64 decrypt = {}", outputStr);
        return outputStr;
    }

    /**
     * 生成 key 的方法，对关键字进行 “SHA-1” 摘要，然后取摘要的前16个字节。
     * key 的字符长度必须大于等于8；
     *
     * @param inputStr
     * @return
     */
    public static String genreateKey(String inputStr) {
        if ( 8 <= inputStr.length()) {
            return MessageSign.signSHA1ToHexStr(inputStr).substring(0, 16);
        } else {
            throw new IllegalArgumentException("input String length less 8");
        }
    }
    
    public static void main(String[] args) throws CodecException {
        
        String key = "3FADAE9950B216AF";
        String userAttrStr = "{\"phone\":\"13800138000\",\"name\":\"张三\",\"idcard\":\"320882198512022828\",\"gender\":\"1\"}";
        String encodedStr =  strEncodBase64(key, userAttrStr);
        logger.info("encodedStr");
        logger.info(new String(base64StrDecode(key,encodedStr)));
    }

}
