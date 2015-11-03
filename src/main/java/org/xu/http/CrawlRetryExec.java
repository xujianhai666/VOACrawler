package org.xu.http;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.NonRepeatableRequestException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.Args;

import org.apache.http.impl.execchain.ClientExecChain;
// import org.xu.RequestEntityProxy;


/**
 *
 * different from original RetryExec :
 *     in addition to IOException, now adding a judgement after the exechain .
 *
 * in common, three retry stategy :
 *        request meta + middleware : scrapy
 *        proxy : httpclient
 *        engine + push_back : ? no implementation
 *        
 * what about the exception : 
 *         store
 *         throw
 *         ignore 
 *         how to combine thet three strategy. 
 * @since 4.3
 */
@Immutable
public class CrawlRetryExec implements ClientExecChain {

    private final Log log = LogFactory.getLog(CrawlRetryExec.class);

    // 两个实现
    private final ClientExecChain requestExecutor;
    private final HttpRequestRetryHandler retryHandler;

    public CrawlRetryExec(
            final ClientExecChain requestExecutor,
            final HttpRequestRetryHandler retryHandler) {
        Args.notNull(requestExecutor, "HTTP request executor");
        Args.notNull(retryHandler, "HTTP request retry handler");
        this.requestExecutor = requestExecutor;
        this.retryHandler = retryHandler;
    }

    @Override
    public CloseableHttpResponse execute(
            final HttpRoute route,
            final HttpRequestWrapper request,
            final HttpClientContext context,
            final HttpExecutionAware execAware) throws IOException, HttpException {

        Args.notNull(route, "HTTP route");
        Args.notNull(request, "HTTP request");
        Args.notNull(context, "HTTP context");
        
        // 获取所有头部，用来做什么
        final Header[] origheaders = request.getAllHeaders();

         System.out.println("开始循环处理");
        // 常规的循环处理
        for (int execCount = 1;; execCount++) {
            try {
                CloseableHttpResponse response = this.requestExecutor.execute(route, request, context, execAware);
                
                if (retryHandler.retryRequest(null, execCount, context)) {
                    System.out.println("显示过了 重试");
                }else{
                    System.out.println("结束显示了 没有重试！");
                    return response;
                }
            } catch (final IOException ex) {
                // 这个是
                if (execAware != null && execAware.isAborted()) {
                    this.log.debug("Request has been aborted");
                    throw ex;
                }
                // retryRequest ? 判断是否重试
                if (retryHandler.retryRequest(ex, execCount, context)) {
                    if (this.log.isInfoEnabled()) {
                        this.log.info("I/O exception ("+ ex.getClass().getName() +
                                ") caught when processing request to "
                                + route +
                                ": "
                                + ex.getMessage());
                    }
                    if (this.log.isDebugEnabled()) {
                        // 怎么这样的实现 ？
                        this.log.debug(ex.getMessage(), ex);
                    }
                    // 相关代理的处理
                    // 请求是否可重复？这是什么意思
                    if (!RequestEntityProxy.isRepeatable(request)) {
                        this.log.debug("Cannot retry non-repeatable request");
                        throw new NonRepeatableRequestException("Cannot retry request " +
                                "with a non-repeatable request entity", ex);
                    }
                    // 重新设置request ： 就是重新设置头部
                    // 喂喂喂，request entity呢？
                    request.setHeaders(origheaders);
                    if (this.log.isInfoEnabled()) {
                        this.log.info("Retrying request to " + route);
                    }
                } else {
                    if (ex instanceof NoHttpResponseException) {
                        final NoHttpResponseException updatedex = new NoHttpResponseException(
                                route.getTargetHost().toHostString() + " failed to respond");
                        updatedex.setStackTrace(ex.getStackTrace());
                        throw updatedex;
                    } else {
                        throw ex;
                    }
                }
            }
        }
    }
}
