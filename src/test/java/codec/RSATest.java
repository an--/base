package codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.tool.codec.RSA;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSATest {

    static Logger logger = LoggerFactory.getLogger(RSATest.class);
    
    public static void main(String[] args) throws Exception {
//        encodeTest1();
    }

    public static void encodeTest() throws Exception {
        ByteArrayOutputStream encodeOut = new ByteArrayOutputStream();
        ByteArrayOutputStream decodeOut = new ByteArrayOutputStream();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA.ALGORITHM);
            keyPairGenerator.initialize(RSA.DEFAULTKEYSIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            String input = "111111111111111111111111111111111111111111111111111111111111111111111"
                    + "1111111111111111111111111111111111111111111111111111111111"
                    + "1111111111111111111111111111111111111111111111111111111111"
                    + "1111111111111111111111111111111111111111111111111111111111"
                    + "1111111111111111111111111111111111111111111111111111111111"
                    + "1111111111111111111111111111111111111111111111111111111111"
                    + "1111111111111111111111111111111111111111111111111111111111"
                    + "1111111111111111111111111111111111111111111111111111111111";

            byte[] inputBytes = input.getBytes(Charset.defaultCharset());
            int inputLength = inputBytes.length;

            PublicKey publicKey = keyPair.getPublic();
            Cipher cipher1 = Cipher.getInstance(publicKey.getAlgorithm());
            cipher1.init(Cipher.ENCRYPT_MODE,publicKey);

            BASE64Encoder base64Encoder = new BASE64Encoder();
            for (int i = 0; i < inputLength; i = i + RSA.DEFAULT_ENCRYPT_BLOCKSIZE) {

                int encodeLength = (RSA.DEFAULT_ENCRYPT_BLOCKSIZE <= inputLength - i ) ? RSA.DEFAULT_ENCRYPT_BLOCKSIZE : inputLength - i;
                byte[] encodeBytes = cipher1.doFinal(inputBytes, i, encodeLength);
                encodeOut.write(encodeBytes);
            }

            String encodeStr = base64Encoder.encode(encodeOut.toByteArray());
            logger.info("encodeStr = {}", encodeStr);

            PrivateKey privateKey = keyPair.getPrivate();
            Cipher cipher2 = Cipher.getInstance(privateKey.getAlgorithm());
            cipher2.init(Cipher.DECRYPT_MODE,privateKey);

            byte[] inputBytes2 = new BASE64Decoder().decodeBuffer(encodeStr);
            int inputByteLength = inputBytes2.length;
            for (int i = 0; i < inputByteLength; i = i + RSA.DEFAULT_DECRYPT_BLOCKSIZE) {
                int encodeLength = (RSA.DEFAULT_DECRYPT_BLOCKSIZE <= inputByteLength - i ) ? RSA.DEFAULT_DECRYPT_BLOCKSIZE : inputByteLength - i;
                byte[] decodeBytes = cipher2.doFinal(inputBytes2, i, encodeLength);
                decodeOut.write(decodeBytes);
            }

            String decodeStr = new String(decodeOut.toByteArray());
            logger.info("decodeStr = {}", decodeStr);
        } finally {
            encodeOut.close();
            decodeOut.close();
        }

    }

    public void generatKeyTest() {
        RSA.generatKey();
    }
    
   
    @Test
    public  void encodeTest1() throws IOException, NoSuchAlgorithmException {
        String src = "{\"income\":100,\"realName\":\"安杰\",\"balance\":1000,\"applyDays\":30,\"idCard\":\"421202199308183266\",\"mobile\":18670372832,\"maritalStatus\":1}";
        String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnLGHFl20VmKUBztDAw02\n5CYUjXiaPjb2r/yS5mK4XKQsDb5snRBxhGsWGfl5c+6PLGGcG2wE0Zs4u+MDwtbZ\n"
                + "IqPrOkXAZYA1tLVtABgzJ1XMO08RWE2aSia3Qe6xazleQpKu8k+o3OxkcDE7WIGS\nYoYVXeZkZMebMeuF4LkFwtys2KazhH7QbeMKIk9aq1tSk18sflWNzLKCZjqZoKPJ\n"
                + "ax6M4xdSOo3lKF9lbxr2NYZ9yowqkyFlZO4yhhv7HZFRxHt2XrNG2lb1s+d1g9O+\nlymUwizOf1hVIL/FK7pgtwYJbVxSpOL33J+Ue/K6zjrH6hQFwLHRQ4SsfqoW1el3\n9wIDAQAB";
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA.ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        
        logger.debug("keySize = {}", keyBytes.length);
        keyBytes = keyPair.getPublic().getEncoded();
        logger.debug("keySize = {}", keyBytes.length);
        byte[] encodeBytes = RSA.encryptByPublicKey(keyBytes, src.getBytes(), 2048 / 8 - 11);
        byte[] decodeBytes = RSA.decryptByPrivateKey(keyPair.getPrivate().getEncoded(), encodeBytes, 2048 / 8);
        
        logger.info(new BASE64Encoder().encode(encodeBytes));
        logger.info(new String(decodeBytes));
    }

}
