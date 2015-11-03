package org.xu.http;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Arrays;
import java.util.Collections;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;   

/**
 * 采用轮询策略添加UserAgent
 *
 * "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50[\r][\n]" 
 * 需要写一个简单的awk程序
 */
public class PollingRequestUserAgent extends DynamicRequestUserAgent{


    private List<String> agents = new ArrayList<String>(); 

    // 如何循环获取下一个
    // 
    // private  volatile int next = 0;
    // 这个最好进行循环
    private AtomicInteger position = new AtomicInteger(0);
    private int length = 0;


    // 简单的轮询策略，是否有必要循环
    public String getRandomUserAgent(){
        // 原子安全的
        // current.getAndIncrement(); 但是这样技术就没有办法进行循环
        // 模拟atomicInteger的循环的处理方法
        int current = 0;
        // 忙循环 ：有问题吗？
        for (;;) {
            current = position.get();
            int next = (current + 1)%length;
            if (position.compareAndSet(current, next)){
                break;
            }
        }
        // int next = (current.getAndIncrement() + 1)%length;
        return this.agents.get(current);
    }

    public PollingRequestUserAgent() {
        super();
    }

    public PollingRequestUserAgent(List<String> userAgents) {
        super();
        // Collections.copy(this.agents, userAgents);
        this.agents = new ArrayList(userAgents);
        this.length = this.agents.size();
    }

    public PollingRequestUserAgent(String[] userAgents){
        this(Arrays.asList(userAgents));

    }

    public void process(final HttpRequest request, final HttpContext context) 
        throws HttpException, IOException{
        if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        String agent = getRandomUserAgent();
        request.addHeader(HTTP.USER_AGENT, agent);

        // 动态添加 UserAgent
    //     if (!request.containsHeader(HTTP.USER_AGENT)) {
    //         String useragent = HttpProtocolParams.getUserAgent(request.getParams());
    //         if (useragent != null) {
    //             request.addHeader(HTTP.USER_AGENT, useragent);
    //         }
    //     }
    // }
    }   
}