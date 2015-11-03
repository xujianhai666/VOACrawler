package org.xu.http;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.HttpRequestInterceptor;

/**
 * 动态添加 UserAgent
 */
public abstract class DynamicRequestUserAgent implements HttpRequestInterceptor {

    /**
     * [getRandomUserAgent 动态获取useragetn,随机策略可以使轮询、随机数、负载]
     * @return [description]
     */
    public abstract String getRandomUserAgent();

    public DynamicRequestUserAgent() {
        super();
    }


    public abstract void process(final HttpRequest request, final HttpContext context) 
        throws HttpException, IOException;
}
