package base.tool.codec;

/**
 * @author an
 *
 */
public class CodecException extends Exception {
    
    private CodecException() {
        super();
        // TODO Auto-generated constructor stub
    }

    private CodecException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    private CodecException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public CodecException(CodecExceptionEnum codecExceptionEnum) {
        super(codecExceptionEnum.message);
        // TODO Auto-generated constructor stub
    }

    private CodecException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public static enum CodecExceptionEnum {

        BASE64_DECODE_ERROR("Base64 字符串 decode 时出错！"),
        ILLEGAL_KEY("错误的密钥！"),
        ENCRYPT_EXCEPTION("加密过程出错！"),
        DECRYPT_EXCEPTION("解密过程出错！");

        private String message;

        private CodecExceptionEnum(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    
}
