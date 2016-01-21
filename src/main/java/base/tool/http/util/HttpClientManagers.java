package base.tool.http.util;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * http client manager util
 * 
 * @author an
 *
 */
public class HttpClientManagers {

    /**
     * config new PoolingHttpClientConnectionManager
     * 
     * @param timeToLive
     * @param timeUnit
     * @param maxConnTotal
     * @param maxConnPerRoute
     * @return
     */
    public static PoolingHttpClientConnectionManager createConnectionManager(int timeToLive, TimeUnit timeUnit, int maxConnTotal, int maxConnPerRoute) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(timeToLive,
                timeUnit);
        if (maxConnTotal > 0) {
            connectionManager.setMaxTotal(maxConnTotal);
        }
        if (maxConnPerRoute > 0) {
            connectionManager.setDefaultMaxPerRoute(maxConnPerRoute);
        }
        return connectionManager;
    }
    
    /**
     * create HttpClientBuilder
     * 
     * @return
     */
    public static HttpClientBuilder createHttpClientBuilder(HttpClientConnectionManager connectionManager) {
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        if(null != connectionManager) {
            clientBuilder.setConnectionManager(connectionManager);
        }
        return clientBuilder;
    }
    
}
