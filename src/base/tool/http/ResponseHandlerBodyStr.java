package base.tool.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;

/**
 * 
 * @description 处理响应，返回字符串类型的相应正文。<br>
 *              只处理状态码在大于等于200小于300的响应，遇到其他状态响应， 抛出ClientProtocolException。
 * 
 * @author an 2014年7月11日
 *
 */
public class ResponseHandlerBodyStr implements ResponseHandler<String> {
	
	@Override
	public String handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		/* 处理状态码在200到300之间的响应 */
		int status = response.getStatusLine().getStatusCode();
		if (HttpStatus.SC_OK <= status
				&& HttpStatus.SC_MULTIPLE_CHOICES > status) {
			HttpEntity entity = response.getEntity();
			ContentType contentType = ContentType.getOrDefault(entity);
			Charset charSet = contentType.getCharset();
			charSet = (null == charSet ? Charset.defaultCharset() : charSet );
			
			Reader reader = new InputStreamReader(entity.getContent(), charSet);
			try {
				char[] charbuf = new char[1024];
				StringBuffer strbuf = new StringBuffer();
				int readlength = -1;
				while (-1 != (readlength = reader.read(charbuf))) {
					strbuf.append(charbuf, 0, readlength);
				}
				
				return strbuf.toString();
			} finally {
				if (null != reader) {
					reader.close();
				}
			}
		} else {
			throw new ClientProtocolException("Unexpected response status :"
					+ status);
		}
	}
}