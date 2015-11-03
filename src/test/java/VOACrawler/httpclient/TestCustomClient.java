package httpclient;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;


import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.BackoffManager;
import org.apache.http.client.ConnectionBackoffStrategy;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.InputStreamFactory;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.client.protocol.RequestAuthCache;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.client.protocol.RequestExpectContinue;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.KerberosSchemeFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.impl.execchain.BackoffStrategyExec;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.impl.execchain.MainClientExec;
import org.apache.http.impl.execchain.ProtocolExec;
import org.apache.http.impl.execchain.RedirectExec;
import org.apache.http.impl.execchain.RetryExec;
import org.apache.http.impl.execchain.ServiceUnavailableRetryExec;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.TextUtils;
import org.apache.http.util.VersionInfo;


import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.TargetAuthenticationStrategy;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.client.DefaultUserTokenHandler;
import org.apache.http.impl.client.NoopUserTokenHandler;

import org.apache.http.impl.client.DefaultRedirectStrategy;

import org.apache.http.impl.client.CookieSpecRegistries;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.IdleConnectionEvictor;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.params.HttpProtocolParams;

// retry进行处理 
import org.apache.http.client.HttpRequestRetryHandler;


import org.xu.http.InternalHttpClient;
import org.xu.http.PollingRequestUserAgent;
import org.xu.http.CrawlHttpRequestRetryHandler;
import org.xu.http.CrawlHttpClientBuilder;
import org.xu.http.CrawlServiceUnavailableRetryStrategy;

public class TestCustomClient{


/**
 * [TestCustom description]
 *
 * 准备添加  redirect  retry DynamicUserAgent ResolveURL 的实现
 * 是否添加 文件保存(FileSave的实现) 的实现
 * url是否重复(布隆过滤器)
 * 自动登录(这个写例子)
 * 
 * 还有有没有其他的实现
 * 
 * 路由暂时不用管
 *
 * pipeline :  需要添加类似的模块
 *  	处理 files images media 的处理 
 * 
 * ajax stats refer 功能的实现
 *
 * 作为扩展
 * corestat debug feedexport logstats memdebug memusage statsmailer 
 *
 *
 * 最后，可别忘了 扫描项目和配置文件的功能，然后构建运行项目
 */

    


    @Test
    public void TestAgents(){

        // // 如何更好的搭配这两个函数
        // int maxConnTotal = 10;
        // int maxConnPerRoute = 10;
        // long connTimeToLive = -1;
        // TimeUnit connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;
        // // 常用的useragent,参考这个 : http://www.bubuko.com/infodetail-796803.html
        // // String[] useragents = new String[]{
        // //     "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
        // //     "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
        // //     "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0"
        // //     };


        // String[] useragents = {
        //     "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
        //     "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
        //     "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0"
        //     };
        // // List<String> ls_agents = new ArrayList<String>();
        // // ls_agents.add(useragents[0]);
        // // ls_agents.add(useragents[1]);
        // // ls_agents.add(useragents[2]);

        // System.out.println("开始测试 client");
        // HttpClientBuilder builder = HttpClientBuilder.create();
        // builder.setMaxConnTotal(10);
        // builder.setMaxConnPerRoute(10);
        // // builder.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
        // // builder.addInterceptorFirst(new PollingRequestUserAgent(ls_agents));
        // builder.addInterceptorFirst(new PollingRequestUserAgent(useragents));
        // builder.setRetryHandler(new CrawlHttpRequestRetryHandler());
        // // builder.addInterceptorFirst(new CrawlHttpRequestRetryHandler());

        // HttpGet httpGet = new HttpGet("http://www.51voa.com/VOA_Standard_English/microscope-brings-tiny-worlds-closer-researchers-66090.html");
        // CloseableHttpClient httpClient = builder.build();

        // // 构造请求
        // // 多做几次循环，打印UserAgent
        // for(int i=0;i<3;i++){
        //     CloseableHttpResponse response1 = null;
        //     try {
                
        //         response1 = httpClient.execute(httpGet);
        //         // 需要查看console知道
        //         System.out.println("响应行" + response1.getStatusLine());

        //         // 这里使用了一个总的参数获取的类 ： 门面
        //         // String agent = HttpProtocolParams.getUserAgent(response1.getParams());
        //         // System.out.println("user agent => " + agent);

        //     }catch(IOException ioe){


        //     } finally {
        //         try{
        //             response1.close();
        //         }catch(IOException e){

        //         } 
        //     }
        // }
        // System.out.println("结束了！");
    }


    // @Test
    // public void TestRetry(){
    //     // 测试retry 
    //             // 如何更好的搭配这两个函数
    //     int maxConnTotal = 10;
    //     int maxConnPerRoute = 10;
    //     long connTimeToLive = -1;
    //     TimeUnit connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;

    //     System.out.println("开始测试 client");
    //     CrawlHttpClientBuilder builder = CrawlHttpClientBuilder.create();
    //     builder.setMaxConnTotal(10);
    //     builder.setMaxConnPerRoute(10);
    //     builder.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
    //     builder.setRetryHandler(new CrawlHttpRequestRetryHandler());
    //     // builder.addInterceptorFirst(new CrawlHttpRequestRetryHandler());

    //     // HttpGet httpGet = new HttpGet("http://www.51voa.com/VOA_Standard_English/microscope-brings-tiny-worlds-closer-researchers-66090.html");
    //     HttpGet httpGet = new HttpGet("http://127.0.0.1:8888/500");
    //     CloseableHttpClient httpClient = builder.build();

    //     // 构造请求
    //     // 多做几次循环，打印UserAgent
    //     // 需要做一个超时处理，超时处理＝》retry,达到max后，put into wait  queue
    //     // java的retry的处理方式，不太适合 进行多次包装，信息不容易流转
        
    //     CloseableHttpResponse response1 = null;
    //     try {        
    //         response1 = httpClient.execute(httpGet);
    //         // 需要查看console知道
    //         // System.out.println("响应行" + response1.getStatusLine());

    //         }catch(IOException ioe){


    //         } finally {
    //             // try{
    //             //     response1.close();
    //             // }catch(IOException e){

    //             // } 
    //         }
    
    //     System.out.println("结束了！");
    // }


    @Test
    public void TestUnavailableRetry(){
        // 测试retry 
        // 如何更好的搭配这两个函数
        int maxConnTotal = 10;
        int maxConnPerRoute = 10;
        long connTimeToLive = -1;
        TimeUnit connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;

        System.out.println("开始测试 client");
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setMaxConnTotal(10);
        builder.setMaxConnPerRoute(10);
        builder.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
        CrawlServiceUnavailableRetryStrategy unavailable = new CrawlServiceUnavailableRetryStrategy(3,100); 
        builder.setServiceUnavailableRetryStrategy(unavailable);
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8888/500");
        CloseableHttpClient httpClient = builder.build();
        CloseableHttpResponse response1 = null;
        try {        
            response1 = httpClient.execute(httpGet);

            }catch(IOException ioe){


            } finally {
                // try{
                //     response1.close();
                // }catch(IOException e){

                // } 
            }
    
        System.out.println("结束了！");
    }

    




	// @Test
	// public void TestCustom(){

	// 	// 如何更好的搭配这两个函数
	// 	int maxConnTotal = 10;
 //    	int maxConnPerRoute = 10;
 //    	long connTimeToLive = -1;
 //    	TimeUnit connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;


	// 	System.out.println("开始测试 client");
	// 	HttpClientBuilder builder = HttpClientBuilder.create();
	// 	// CloseableHttpClient  client = builder.build();

	// 	// useragent : useragent应该对request进行动态处理
	// 	builder.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
	// 	// 设置最大链接数量
	// 	builder.setMaxConnTotal(10);
	// 	// 设置每一个路由的最大链接 ？ 
	// 	builder.setMaxConnPerRoute(10);


	// 	CloseableHttpClient httpClient = builder.build();


	// 	// 构造请求
	// 	try {
 //            HttpGet httpGet = new HttpGet("http://www.51voa.com/VOA_Standard_English/microscope-brings-tiny-worlds-closer-researchers-66090.html");
 //            CloseableHttpResponse response1 = httpClient.execute(httpGet);

 //            try {
 //                System.out.println(response1.getStatusLine());
 //                HttpEntity entity1 = response1.getEntity();
 //                System.out.println("HTTP Entity : " );

 //                InputStream  is = entity1.getContent();
 //                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
 //                String linestr = null;
 //                // 只有被读取， wire才会被调用
 //                while((linestr = reader.readLine())!=null){
 //                	System.out.println("line = > " + linestr);

 //                }
 //                System.out.println("结束了！");
 //                // do something useful with the response body
 //                // and ensure it is fully consumed
 //                // EntityUtils.consume(entity1);
 //            } finally {
 //                response1.close();
 //            }
 //        }catch(Exception ex){

 //        }

	// 	System.out.println("配置结束");
	// }

}


        // if (!automaticRetriesDisabled) {
            // HttpRequestRetryHandler retryHandlerCopy = this.retryHandler;
            // if (retryHandlerCopy == null) {
                // retryHandlerCopy = DefaultHttpRequestRetryHandler.INSTANCE;
            // }
            // 有进行了一次封装
            // execChain = new RetryExec(execChain, retryHandlerCopy);
        // }



	// 这个不懂啊
    // socketConfig ! 需要实现一下， 最大空闲时间
    // private SocketConfig defaultSocketConfig;
    // private ConnectionConfig defaultConnectionConfig;
    // private RequestConfig defaultRequestConfig;
    // private boolean evictExpiredConnections;
    // private boolean evictIdleConnections;
    // private long maxIdleTime;
    // private TimeUnit maxIdleTimeUnit;



    // 这个需要实现
    // private HttpRequestRetryHandler retryHandler;
    // 
    // 这个以后自己实现
    // private HttpRoutePlanner routePlanner;
    // 
    // 
    // 这个需要实现
    // private RedirectStrategy redirectStrategy;
    // 
    // private ConnectionBackoffStrategy connectionBackoffStrategy;
    // private BackoffManager backoffManager;
    // 
    // 这个需要实现
    // private ServiceUnavailableRetryStrategy serviceUnavailStrategy;
    // 
    
   	// 这个可以自定义，后面实现一下
    // private SSLContext sslContext;
     
             // 整体的装配很麻烦 ！
        // 直接复制黏贴处理



	
	// 先看看参数吧 !
	// 
	// 
    // private HttpRequestExecutor requestExec;
    // private HostnameVerifier hostnameVerifier;
    // private LayeredConnectionSocketFactory sslSocketFactory;
    
	// 这个可以自定义，后面实现一下
    // private SSLContext sslContext;
    // // 连接管理
    // private HttpClientConnectionManager connManager;
    // private boolean connManagerShared;
    // 
    // schema port ! 
    // private SchemePortResolver schemePortResolver;
    // private ConnectionReuseStrategy reuseStrategy;   
    // private ConnectionKeepAliveStrategy keepAliveStrategy;
    // 
    // 这两个不是很明白
    // private AuthenticationStrategy targetAuthStrategy;
    // private AuthenticationStrategy proxyAuthStrategy;
    // private UserTokenHandler userTokenHandler;
    // private HttpProcessor httpprocessor;

    // 反正就是一群拦截器
    // 这些可以使用，把自己的实现放到里面
    // private LinkedList<HttpRequestInterceptor> requestFirst = null;
    // private LinkedList<HttpRequestInterceptor> requestLast = null;
    // private LinkedList<HttpResponseInterceptor> responseFirst = null;
    // private LinkedList<HttpResponseInterceptor> responseLast = null;

    // 这个需要实现
    // private HttpRequestRetryHandler retryHandler;
    // 
    // 这个以后自己实现
    // private HttpRoutePlanner routePlanner;
    // 
    // 
    // 这个需要实现
    // private RedirectStrategy redirectStrategy;
    // 
    // private ConnectionBackoffStrategy connectionBackoffStrategy;
    // private BackoffManager backoffManager;
    // 
    // 这个需要实现
    // private ServiceUnavailableRetryStrategy serviceUnavailStrategy;
    // 
    // 
    // private Lookup<AuthSchemeProvider> authSchemeRegistry;
    // private Lookup<CookieSpecProvider> cookieSpecRegistry;
    // private Map<String, InputStreamFactory> contentDecoderMap;
    // private CookieStore cookieStore;
    // private CredentialsProvider credentialsProvider;
    // 
    // useragent ! 这个简单一点的话，就是配置文件处理或者默认
    // private String userAgent;
    // 
    // 代理以后再说！
    // private HttpHost proxy;
    // 多个header
    // private Collection<? extends Header> defaultHeaders;
    // 
    // socketConfig ! 需要实现一下， 最大空闲时间
    // private SocketConfig defaultSocketConfig;
    // private ConnectionConfig defaultConnectionConfig;
    // private RequestConfig defaultRequestConfig;
    // private boolean evictExpiredConnections;
    // private boolean evictIdleConnections;
    // private long maxIdleTime;
    // private TimeUnit maxIdleTimeUnit;


    // 这些有什么？
    // private boolean systemProperties;
    // 
    // 下面两个 : 全部开启吧
    // private boolean redirectHandlingDisabled;  默认会打开，不用处理了
    // private boolean automaticRetriesDisabled;  自动重定向
    // private boolean contentCompressionDisabled;   和这个相关 : contentDecoderMap =>
    // private boolean cookieManagementDisabled;  默认开启
    // private boolean authCachingDisabled;    默认开启
    // private boolean connectionStateDisabled;   默认 DefaultUserTokenHandler，这个是什么


	// 用户配置
    // private int maxConnTotal = 0;
    // private int maxConnPerRoute = 0;

    // private long connTimeToLive = -1;
    // private TimeUnit connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;

    // private List<Closeable> closeables;

    // private PublicSuffixMatcher publicSuffixMatcher;











