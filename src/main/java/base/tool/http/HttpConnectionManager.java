package base.tool.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @description 当前应用使用的HttpClient管理类。<br>
 *              （HttpClient版本为4.3.4）
 * @author an 2014年8月13日
 *
 */
public class HttpConnectionManager {

	/** 链接池配置 **/
	private static PoolingHttpClientConnectionManager connManager;

	/** 参数配置 **/
	private static RequestConfig requestConfig;

	/** 应用所使用的CloseableHttpClient实例，通过getHttpClient()方法获取，复用 **/
	private static CloseableHttpClient httpClient;

	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 20;
	/**
	 * 路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 100;

	/** 从链接池获取链接时最大等待时间 **/
	public final static int MAX_CONNECTIONREQUESTTIMEOUT = 10000;

	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 20000;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 30000;

	static {

		connManager = new PoolingHttpClientConnectionManager();

		// 最大连接接数
		connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		// 设置路由最大链接数
		connManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

		requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(MAX_CONNECTIONREQUESTTIMEOUT)
				.setSocketTimeout(READ_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).build();

		// // 设置连接超时时间
		// connectionManager.getParams().setConnectionTimeout(CONNECT_TIMEOUT);
		// // 设置读取超时时间
		// connectionManager.getParams().setSoTimeout(READ_TIMEOUT);

	}

	/**
	 * @description 获取重用的CloseableHttpClient
	 * 
	 * @author an
	 * @createDate 2014年8月13日
	 * 
	 * @return
	 */
	public static CloseableHttpClient getHttpClient() {
		if (null == httpClient) {
			httpClient = HttpClients.custom().setConnectionManager(connManager)
					.setDefaultRequestConfig(requestConfig).build();
		}
		return httpClient;
	}

	// /**
	// * @description 获取指定路由的链接
	// *
	// * @author an
	// * @createDate 2014年8月12日
	// *
	// * @param route
	// * 指定的路由
	// * @return
	// * @throws ExecutionException
	// * @throws InterruptedException
	// * @throws ConnectionPoolTimeoutException
	// */
	// public static HttpClientConnection getHttpConnection(HttpRoute route)
	// throws ConnectionPoolTimeoutException, InterruptedException,
	// ExecutionException {
	// return getHttpClientConnection(route, null);
	// }
	//
	// /**
	// * @description 获取指定路由的链接
	// *
	// * @author an
	// * @createDate 2014年8月12日
	// *
	// * @param route
	// * @param state
	// * @return
	// * @throws ConnectionPoolTimeoutException
	// * @throws InterruptedException
	// * @throws ExecutionException
	// */
	// public static HttpClientConnection getHttpClientConnection(HttpRoute
	// route,
	// Object state) throws ConnectionPoolTimeoutException,
	// InterruptedException, ExecutionException {
	// return connManager.requestConnection(route, null).get(
	// MAX_ROUTE_CONNECTIONS, TimeUnit.MILLISECONDS);
	// }
	//
	// /**
	// * @description 发布链接
	// *
	// * @author an
	// * @createDate 2014年8月13日
	// *
	// * @param conn
	// */
	// public static void releaseHttpClientConnection(HttpClientConnection conn)
	// {
	// if (null != conn) {
	// connManager.releaseConnection(conn, null, MAX_ROUTE_CONNECTIONS,
	// TimeUnit.MILLISECONDS);
	// }
	// }
}
