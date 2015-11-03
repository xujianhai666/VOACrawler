package org.xu.http;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Arrays;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

/**
 * 
 * 不同于之前的DefaultServiceUnavailableRetryStrategy ：
 *     DefaultServiceUnavailableRetryStrategy 只处理503的异常
 *     CrawlServiceUnvailableRetryStrategy 处理一列异常
 * @since 4.2
 */
@Immutable
public class CrawlServiceUnavailableRetryStrategy implements ServiceUnavailableRetryStrategy {

    /**
     * 重试次数,默认为1
     */
    private final int maxRetries;

    /**
     * 重试间隔，默认为1
     */
    private final long retryInterval;

    /**
     * 
     */
    private final Set<String> retrycodes;

    public CrawlServiceUnavailableRetryStrategy(final int maxRetries, final int retryInterval, final Collection<String> codes) {
        super();
        Args.positive(maxRetries, "Max retries");
        Args.positive(retryInterval, "Retry interval");
        this.maxRetries = maxRetries;
        this.retryInterval = retryInterval;
        this.retrycodes = new HashSet<String>();
        for(final String code : codes){
            this.retrycodes.add(code);
        }
    }

    /**
     * [CrawlServiceUnavailableRetryStrategy 默认 500 502 503 504 408状态码的服务器异常]
     * @param  maxRetries    [最大重试次数]
     * @param  retryInterval [重试间隔]
     * @return               [description]
     */
    public CrawlServiceUnavailableRetryStrategy(final int maxRetries, final int retryInterval) {
        this(maxRetries, retryInterval,Arrays.asList(
                "500", "502", "503", "504", "408"
            ));
    }

    public CrawlServiceUnavailableRetryStrategy() {
        this(1, 1000);
    }

    @Override
    public boolean retryRequest(final HttpResponse response, final int executionCount, final HttpContext context) {
        
        System.out.println(" 获取 Unvailable code : " + response.getStatusLine().getStatusCode());
        return executionCount <= maxRetries &&
            (this.retrycodes.contains(String.valueOf(response.getStatusLine().getStatusCode())));
    }

    @Override
    public long getRetryInterval() {
        return retryInterval;
    }

}
