package base.tool.http;

import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import base.tool.http.util.HttpClientManagers;

/**
 * http client connection pool manager
 * 
 * @author an
 *
 */
public class HttpClientConnectionPool {

    private HttpClientConnectionManager connectionManager;

    private HttpClientBuilder clientBuilder;
    
    /** 用于构造 {@link PoolingHttpClientConnectionManager} **/
    private Registry<ConnectionSocketFactory> socketFactoryRegistry;
    
    /** 用于构造  **/
    private HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory;

    /** connection timeToLive **/
    private int timeToLive;
    
    /** connection timeToLive timeUnit **/
    private TimeUnit connTimeToLiveTimeUnit;

    /** 最大链接数 **/
    private int maxConnTotal;

    /** 每个路由最大链接数 **/
    private int maxConnPerRoute;
    
    /** 关闭过期的连接 **/
    private boolean evictExpiredConnections = true;
    
    /** 关闭空闲连接 **/
    private boolean evictIdleConnections = true;

    private void configClientBuilder() {
        this.clientBuilder = HttpClientBuilder.create();
        
        if (null == this.connectionManager) {
            this.connectionManager = new PoolingHttpClientConnectionManager(this.socketFactoryRegistry,timeToLive,
                    this.connTimeToLiveTimeUnit);
        }
        
    }

    public HttpClient getCloseableHttpClient() {
        if (null == this.clientBuilder) {
            this.configClientBuilder();
        }
        return this.clientBuilder.build();
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getMaxConnTotal() {
        return maxConnTotal;
    }

    public void setMaxConnTotal(int maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
    }

    public int getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(int maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }

    public TimeUnit getConnTimeToLiveTimeUnit() {
        return connTimeToLiveTimeUnit;
    }

    public void setConnTimeToLiveTimeUnit(TimeUnit connTimeToLiveTimeUnit) {
        this.connTimeToLiveTimeUnit = connTimeToLiveTimeUnit;
    }

}
