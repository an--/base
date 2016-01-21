package base.tool.codec.aes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.tool.codec.CodecException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * AES codec
 *
 * Created by an on 15/12/8.
 */
public class AES {

    private static Logger logger = LoggerFactory.getLogger(AES.class);

    public static final String ALGORITHM = "AES";

    public static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";

    public static final String AES_CBC_NOPADDING = "AES/CBC/NoPadding";

    public static final String CHARSET = "UTF-8";

    public static final String baseStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    /**
     * AES/CBC/NoPadding encrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param secretKeyBytes
     * @param intVectorBytes
     * @param input
     * @return
     */
    public static byte[] encryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input)
            throws CodecException {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, ALGORITHM);

            Cipher cipher = Cipher.getInstance(AES_CBC_NOPADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(input);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CodecException(CodecException.CodecExceptionEnum.DECRYPT_EXCEPTION);
        }
    }


    /**
     * AES/CBC/NoPadding decrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param secretKeyBytes
     * @param intVectorBytes
     * @param input
     * @return
     */
    public static byte[] decryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input)
            throws CodecException {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, ALGORITHM);

            Cipher cipher = Cipher.getInstance(AES_CBC_NOPADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(input);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CodecException(CodecException.CodecExceptionEnum.ENCRYPT_EXCEPTION);
        }
    }

    /**
     * AES/CBC/PKCS5Padding encrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param secretKeyStr
     * @param intVector
     * @param input
     * @return
     */
    public static byte[] CBCPKCS5PaddingEncrypt(String secretKeyStr, String intVector, byte[] input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVector.getBytes(CHARSET));
            SecretKey secretKey = new SecretKeySpec(secretKeyStr.getBytes(CHARSET), ALGORITHM);

            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(input);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES/CBC/PKCS5Padding decrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param secretKeyStr
     * @param intVector
     * @param input
     * @return
     */
    public static byte[] CBCPKCS5PaddingDecrypt(String secretKeyStr, String intVector, byte[] input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVector.getBytes(CHARSET));
            SecretKey secretKey = new SecretKeySpec(secretKeyStr.getBytes(CHARSET), ALGORITHM);

            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(input);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES/CBC/PKCS5Padding encrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param secretKeyStr
     * @param intVector
     * @param input
     * @return
     */
    public static String CBCPKCS5PaddingEncryptBase64Str(String secretKeyStr, String intVector, String input) {

        try {
            logger.debug("CBCPKCS5PaddingEncryptBase64Str > input = {}", input);
            byte[] inputBytes = input.getBytes(CHARSET);
            byte[] outputBytes = CBCPKCS5PaddingEncrypt(secretKeyStr, intVector, inputBytes);

            String outputStr = (null == outputBytes ? null : new BASE64Encoder().encode(outputBytes));

            logger.debug("CBCPKCS5PaddingEncryptBase64Str > output = {}", outputStr);
            return outputStr;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES/CBC/PKCS5Padding decrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param secretKeyStr
     * @param intVector
     * @param input
     * @return
     */
    public static String CBCPKCS5PaddingDecryptBase64Str(String secretKeyStr, String intVector, String input) {

        try {

            logger.debug("CBCPKCS5PaddingEncryptBase64Str > input = {}", input);
            byte[] inputBytes = new BASE64Decoder().decodeBuffer(input);

            byte[] outputBytes = CBCPKCS5PaddingDecrypt(secretKeyStr, intVector, inputBytes);

            String outputStr = (null == outputBytes ? null : new String(outputBytes));

            logger.debug("CBCPKCS5PaddingEncryptBase64Str > output = {}", outputStr);
            return outputStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
