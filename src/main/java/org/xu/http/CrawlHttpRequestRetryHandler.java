/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.xu.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.impl.client.RequestWrapper;

/**
 * 爬虫使用的 retryheandler
 * 
 * @since 4.0
 */
// 
@Immutable
public class CrawlHttpRequestRetryHandler implements HttpRequestRetryHandler {

    public static final CrawlHttpRequestRetryHandler INSTANCE = new CrawlHttpRequestRetryHandler();

    private final int retryCount;

    private final boolean requestSentRetryEnabled;

    private final Set<Class<? extends IOException>> nonRetriableClasses;

    private final Set<String> retrycodes;

    // 感觉是 c++ 的习惯
    protected CrawlHttpRequestRetryHandler(
            final int retryCount,
            final boolean requestSentRetryEnabled,
            final Collection<Class<? extends IOException>> clazzes,final Collection<String> codes) {
        super();
        this.retryCount = retryCount;
        this.requestSentRetryEnabled = requestSentRetryEnabled;

        this.nonRetriableClasses = new HashSet<Class<? extends IOException>>();
        for (final Class<? extends IOException> clazz: clazzes) {
            this.nonRetriableClasses.add(clazz);
        }
        this.retrycodes = new HashSet<String>();
        for(final String code : codes){
            this.retrycodes.add(code);
        }
    }

    // 全部放倒配置文件中去
    protected CrawlHttpRequestRetryHandler(
            final int retryCount,
            final boolean requestSentRetryEnabled,
            final Collection<Class<? extends IOException>> clazzes) {
        this(retryCount, requestSentRetryEnabled, clazzes, Arrays.asList(
                "500", "502", "503", "504", "408"
            ));
    }

    @SuppressWarnings("unchecked")
    public CrawlHttpRequestRetryHandler(final int retryCount, final boolean requestSentRetryEnabled) {
        this(retryCount, requestSentRetryEnabled, Arrays.asList(
                InterruptedIOException.class,
                UnknownHostException.class,
                ConnectException.class,
                SSLException.class));
    }


    public CrawlHttpRequestRetryHandler() {
        this(3, false);
    }

    /**
     * different from original implementation :
     *     add http response status , in contrast to berore; 
     */
    @Override
    public boolean retryRequest(
            final IOException exception,
            final int executionCount,
            final HttpContext context) {

        // 改变了逻辑
        // Args.notNull(exception, "Exception parameter");
        // 超时异常需要进行处理
        Args.notNull(context, "HTTP context");
        
        if (executionCount > this.retryCount) {
            return false;
        }
        // 怎呢看都觉得怪怪的
        // 异常情况的处理
                // 
        final HttpClientContext clientContext = HttpClientContext.adapt(context);
        // 源代码中不不有这项功能
        final HttpRequest request = clientContext.getRequest();
        // response ?
        final HttpResponse response = clientContext.getResponse();
        System.out.println("response code = > " + response.getStatusLine().getStatusCode());
        String code = String.valueOf(response.getStatusLine().getStatusCode());

        if(exception != null){
            if (this.nonRetriableClasses.contains(exception.getClass())) {
            return false;
            } else {
                // 可能是子类的问题
                for (final Class<? extends IOException> rejectException : this.nonRetriableClasses) {
                    if (rejectException.isInstance(exception)) {
                    return false;
                    }
                }
            }
        }else{
            if(this.retrycodes.contains(code)){
                return true;
            }else{
                System.out.println("进行状态码处理");
                return false;
            }
        }


        if(requestIsAborted(request)){
            return false;
        }

        if (handleAsIdempotent(request)) {
            // Retry if the request is considered idempotent
            return true;
        }

        if (!clientContext.isRequestSent() || this.requestSentRetryEnabled) {
            // Retry if the request has not been sent fully or
            // if it's OK to retry methods that have been sent
            return true;
        }
        // otherwise do not retry
        return false;
    }

    public boolean isRequestSentRetryEnabled() {
        return requestSentRetryEnabled;
    }

    public int getRetryCount() {
        return retryCount;
    }

    protected boolean handleAsIdempotent(final HttpRequest request) {
        return !(request instanceof HttpEntityEnclosingRequest);
    }

    @Deprecated
    protected boolean requestIsAborted(final HttpRequest request) {
        HttpRequest req = request;
        if (request instanceof RequestWrapper) { // does not forward request to original
            req = ((RequestWrapper) request).getOriginal();
        }
        return (req instanceof HttpUriRequest && ((HttpUriRequest)req).isAborted());
    }
}
