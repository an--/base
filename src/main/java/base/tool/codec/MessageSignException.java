package base.tool.codec;

import java.security.SignatureException;

/**
 * 消息签名异常
 * 
 * @author an
 *
 */
public class MessageSignException extends SignatureException {

    public static final String SIGN_SHA1_EXCEPTION = "SHA-1 签名过程出错！";

    public static final String SIGN_MD5_EXCEPTION = "MD5 签名过程出错！";

    public MessageSignException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MessageSignException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public MessageSignException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    public MessageSignException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
