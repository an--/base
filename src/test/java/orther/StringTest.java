package orther;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 字符相关测试
 * 
 * 编码,和 byte[] 相互转换
 * 
 * @author an
 *
 */
public class StringTest {
	
	/**
	 * 当前文件编码方式
	 * 
	 * @return
	 */
	public static String getFielEncoding() {
		return System.getProperty("file.encoding");
	}
	
	/**
	 * 按字节长度截取字符串时，不割裂字符
	 * 
	 * 最开始的想法,转换成 char 后 小于128 的占一个字节，其他按两个字节算。
	 * 
	 * 没有考虑编码的问题
	 * 
	 * @param stringSrc
	 * @param length
	 * @return
	 */
	public static String subStringByByteLength(String stringSrc, int length){
		StringBuilder charS = new StringBuilder();
		int byteCount = 0;
		for(int i = 0;i < length ; i++){
			char c = stringSrc.charAt(i);
			if(c < 128){
				byteCount++;	
			}else{
				byteCount += 2;//此处有问题，utf-8汉字一般占 3byte
			}
			if( byteCount <= length ){
				charS.append(c);
			}else{
				break;
			}
		}
		return charS.toString();
	}
	
	
	
	/**
	 * 按字节长度截取字符串时，不割裂字符
	 * @param stringSrc
	 * @param length
	 * @return
	 */
	public static String subStringByByteLength1(String stringSrc, int length){
		StringBuilder charS = new StringBuilder();
		int strL = stringSrc.length();
		int byteCount = 0;
		for(int i = 0; i < strL; i++){
			Character c = stringSrc.charAt(i);
			byteCount += String.valueOf(c).getBytes().length;
			if(byteCount <= length){
				charS.append(c);
			}else{
				break;
			}
		}
		return charS.toString();
	}
	
	/**
	 * 编码测试
	 * 
	 * getBytes 是对字符按指定编码方式进行 encode 操作，
	 * new String(byte[]) 是对 byte[] 按指定的编码进行 decode 操作 
	 * 
	 * char 和 String 都是表示字符，与编码方式无关，与自然语言对应。
	 * 
	 * 不同的字符，在不同的编码方式下 encode 表现为不同的 byte[]
	 * byte[] 在 decode 时，需要知道编码方式
	 * 
	 * utf-8 编码方式下 "中" 占 3 字节
	 * GBK 和 GB2312 编码方式下 "中" 占 2 字节
	 * 
	 * GBK 和 GB2312 两种编码方式对 "中" 编码后的结果相同，都是 [-42, -48]
	 * 
	 */
	public static void charsetByteLengthPrint(){
		
		char zh = '中';
		char en = 'e';
		
		System.out.println(Arrays.toString(("" + en).getBytes(Charset.defaultCharset())));
		System.out.println(Arrays.toString(("" + zh).getBytes(Charset.defaultCharset())));
		
		try {
			System.out.println(Arrays.toString(("" + zh).getBytes("GBK")));
			System.out.println(Arrays.toString(("" + zh).getBytes("GB2312")));
			System.out.println(new String(("" + zh).getBytes("GB2312"), "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args){
		
//		System.out.println("我abc".getBytes().length);
	
		charsetByteLengthPrint();
//		System.out.println();
	}

}
