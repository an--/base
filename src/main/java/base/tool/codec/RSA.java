package base.tool.codec;

import sun.security.rsa.RSAPublicKeyImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSA{
    
    private static Logger logger = LoggerFactory.getLogger(RSA.class);
    
    public static final String ALGORITHM = "RSA";
    
    public static final int DEFAULTKEYSIZE = 1024;

    public static final int DEFAULT_DECRYPT_BLOCKSIZE = DEFAULTKEYSIZE / 8;

    public static final int DEFAULT_ENCRYPT_BLOCKSIZE = DEFAULT_DECRYPT_BLOCKSIZE / 8 - 11;
	
    /**
     * RSA 加密, key 和 输入字符串 都转换成字节数组处理
     *
     * @param keyBytes
     * @param input
     * @return
     */
    public static byte[] encryptByPublicKey(byte[] keyBytes, byte[] input, Integer blockSize) {
        Cipher cipher;
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            Key key = new RSAPublicKeyImpl(keyBytes);

            cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);

            int _blockSize = DEFAULT_ENCRYPT_BLOCKSIZE;
            if (null != blockSize) {
                _blockSize = blockSize;
            }

            int inputLength = input.length;
            for (int offset = 0; offset < inputLength; offset += _blockSize) {
                int currentBlockSize = _blockSize < inputLength - offset ? _blockSize : inputLength - offset;
                byteOut.write(cipher.doFinal(input, offset, currentBlockSize));
            }
            return byteOut.toByteArray();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static byte[] decryptByPrivateKey(byte[] keyBytes, byte[] input, Integer blockSize) {
        Cipher cipher;
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            KeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PrivateKey key = keyFactory.generatePrivate(keySpec);

            cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);

            int _blockSize = DEFAULT_DECRYPT_BLOCKSIZE;
            if (null != blockSize) {
                _blockSize = blockSize;
            }

            int inputLength = input.length;
            for (int offset = 0; offset < inputLength; offset += _blockSize) {
                int currentBlockSize = _blockSize < inputLength - offset ? _blockSize : inputLength - offset;
                byteOut.write(cipher.doFinal(input, offset, currentBlockSize));
            }
            return byteOut.toByteArray();
        
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                byteOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
        return null;
    }
    
    public static void generatKey() {
        
        try {
            
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            
            keyPairGenerator.initialize(DEFAULTKEYSIZE);
            
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            
            PublicKey publicKey = keyPair.getPublic();
            logger.info("generatKey > publicKey format = {}", publicKey.getFormat());
            logger.info("generatKey > publicKey encode = {}", publicKey.getEncoded());
            
            PrivateKey privateKey = keyPair.getPrivate();
            logger.info("generatKey > privateKey format = {}", privateKey.getFormat() );
            logger.info("generatKey > privateKey encode = {}", privateKey.getEncoded() );            
            
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    

}
