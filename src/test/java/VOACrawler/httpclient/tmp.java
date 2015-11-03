// package httpclient;

// import org.junit.Test;
// import static org.junit.Assert.*;

// import java.io.Closeable;
// import java.io.IOException;
// import java.net.ProxySelector;
// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.Collections;
// import java.util.LinkedList;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.TimeUnit;

// import javax.net.ssl.HostnameVerifier;
// import javax.net.ssl.SSLContext;
// import javax.net.ssl.SSLSocketFactory;

// import org.apache.http.impl.client.HttpClientBuilder;
// import org.apache.http.impl.client.CloseableHttpClient;


// import org.apache.http.ConnectionReuseStrategy;
// import org.apache.http.Header;
// import org.apache.http.HttpHost;
// import org.apache.http.HttpRequestInterceptor;
// import org.apache.http.HttpResponseInterceptor;
// import org.apache.http.annotation.NotThreadSafe;
// import org.apache.http.auth.AuthSchemeProvider;
// import org.apache.http.client.AuthenticationStrategy;
// import org.apache.http.client.BackoffManager;
// import org.apache.http.client.ConnectionBackoffStrategy;
// import org.apache.http.client.CookieStore;
// import org.apache.http.client.CredentialsProvider;
// import org.apache.http.client.HttpRequestRetryHandler;
// import org.apache.http.client.RedirectStrategy;
// import org.apache.http.client.ServiceUnavailableRetryStrategy;
// import org.apache.http.client.UserTokenHandler;
// import org.apache.http.client.config.AuthSchemes;
// import org.apache.http.client.config.RequestConfig;
// import org.apache.http.client.entity.InputStreamFactory;
// import org.apache.http.client.protocol.RequestAcceptEncoding;
// import org.apache.http.client.protocol.RequestAddCookies;
// import org.apache.http.client.protocol.RequestAuthCache;
// import org.apache.http.client.protocol.RequestClientConnControl;
// import org.apache.http.client.protocol.RequestDefaultHeaders;
// import org.apache.http.client.protocol.RequestExpectContinue;
// import org.apache.http.client.protocol.ResponseContentEncoding;
// import org.apache.http.client.protocol.ResponseProcessCookies;
// import org.apache.http.config.ConnectionConfig;
// import org.apache.http.config.Lookup;
// import org.apache.http.config.RegistryBuilder;
// import org.apache.http.config.SocketConfig;
// import org.apache.http.conn.ConnectionKeepAliveStrategy;
// import org.apache.http.conn.HttpClientConnectionManager;
// import org.apache.http.conn.SchemePortResolver;
// import org.apache.http.conn.routing.HttpRoutePlanner;
// import org.apache.http.conn.socket.ConnectionSocketFactory;
// import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
// import org.apache.http.conn.socket.PlainConnectionSocketFactory;
// import org.apache.http.conn.ssl.DefaultHostnameVerifier;
// import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
// import org.apache.http.conn.ssl.X509HostnameVerifier;
// import org.apache.http.conn.util.PublicSuffixMatcher;
// import org.apache.http.conn.util.PublicSuffixMatcherLoader;
// import org.apache.http.cookie.CookieSpecProvider;
// import org.apache.http.impl.DefaultConnectionReuseStrategy;
// import org.apache.http.impl.NoConnectionReuseStrategy;
// import org.apache.http.impl.auth.BasicSchemeFactory;
// import org.apache.http.impl.auth.DigestSchemeFactory;
// import org.apache.http.impl.auth.KerberosSchemeFactory;
// import org.apache.http.impl.auth.NTLMSchemeFactory;
// import org.apache.http.impl.auth.SPNegoSchemeFactory;
// import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
// import org.apache.http.impl.conn.DefaultRoutePlanner;
// import org.apache.http.impl.conn.DefaultSchemePortResolver;
// import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
// import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
// import org.apache.http.impl.execchain.BackoffStrategyExec;
// import org.apache.http.impl.execchain.ClientExecChain;
// import org.apache.http.impl.execchain.MainClientExec;
// import org.apache.http.impl.execchain.ProtocolExec;
// import org.apache.http.impl.execchain.RedirectExec;
// import org.apache.http.impl.execchain.RetryExec;
// import org.apache.http.impl.execchain.ServiceUnavailableRetryExec;
// import org.apache.http.protocol.HttpProcessor;
// import org.apache.http.protocol.HttpProcessorBuilder;
// import org.apache.http.protocol.HttpRequestExecutor;
// import org.apache.http.protocol.ImmutableHttpProcessor;
// import org.apache.http.protocol.RequestContent;
// import org.apache.http.protocol.RequestTargetHost;
// import org.apache.http.protocol.RequestUserAgent;
// import org.apache.http.ssl.SSLContexts;
// import org.apache.http.util.TextUtils;
// import org.apache.http.util.VersionInfo;


// import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
// import org.apache.http.impl.client.TargetAuthenticationStrategy;
// import org.apache.http.impl.client.ProxyAuthenticationStrategy;
// import org.apache.http.impl.client.DefaultUserTokenHandler;
// import org.apache.http.impl.client.NoopUserTokenHandler;

// import org.apache.http.impl.client.DefaultRedirectStrategy;

// import org.apache.http.impl.client.CookieSpecRegistries;
// import org.apache.http.impl.client.BasicCookieStore;
// import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
// import org.apache.http.impl.client.BasicCredentialsProvider;
// import org.apache.http.impl.client.IdleConnectionEvictor;

// import org.xu.http.InternalHttpClient;


// public class TestCustomClient{

// 	@Test
// 	public void TestCustom(){

// 		// 如何更好的搭配这两个函数
// 		int maxConnTotal = 10;
//     	int maxConnPerRoute = 10;
//     	long connTimeToLive = -1;
//     	TimeUnit connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;


//     	Collection<? extends Header> defaultHeaders = null;
//     	SocketConfig defaultSocketConfig = null;
//     	ConnectionConfig defaultConnectionConfig = null;
//     	RequestConfig defaultRequestConfig = null;

//     	boolean evictExpiredConnections = true;
//     	boolean evictIdleConnections = true;
//     	long maxIdleTime = 10;
//     	TimeUnit maxIdleTimeUnit = null;


//     	boolean systemProperties = false;
//     	boolean redirectHandlingDisabled = false;
//     	boolean automaticRetriesDisabled = false;
//     	boolean contentCompressionDisabled = false;
//     	boolean cookieManagementDisabled = false;
//     	boolean authCachingDisabled = false;
//     	boolean connectionStateDisabled = false;


//     	LinkedList<HttpRequestInterceptor> requestFirst = null;
//     	LinkedList<HttpRequestInterceptor> requestLast = null;
//      	LinkedList<HttpResponseInterceptor> responseFirst = null;
//     	LinkedList<HttpResponseInterceptor> responseLast = null;


//     	HttpRequestRetryHandler retryHandler = null;
//     	HttpRoutePlanner routePlanner = null;
//     	RedirectStrategy redirectStrategy = null;
//     	ConnectionBackoffStrategy connectionBackoffStrategy = null;
//     	BackoffManager backoffManager = null;
//     	ServiceUnavailableRetryStrategy serviceUnavailStrategy = null;
//     	// 两个Lookup
//     	Lookup<AuthSchemeProvider> authSchemeRegistry = null;
//     	Lookup<CookieSpecProvider> cookieSpecRegistry = null;
//     	Map<String, InputStreamFactory> contentDecoderMap = null;
//     	CookieStore cookieStore = null;
//     	CredentialsProvider credentialsProvider = null;
//     	String userAgent = "";
//     	HttpHost proxy = null;

//     	List<Closeable> closeables = null;
// 		boolean connManagerShared = false;


// 		System.out.println("开始测试 client");
// 		HttpClientBuilder builder = HttpClientBuilder.create();
// 		// CloseableHttpClient  client = builder.build();

// 		// useragent : 
// 		builder.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
// 		// 设置最大链接数量
// 		builder.setMaxConnTotal(10);
// 		// 设置每一个路由的最大链接 ？ 
// 		builder.setMaxConnPerRoute(10);



// // ===========================  下面使用自定义的创建
		
// 		// 常规的注入
// 		// PublicSuffixMatcher publicSuffixMatcherCopy = this.publicSuffixMatcher;
//   		// if (publicSuffixMatcherCopy == null) {
//   		// 		publicSuffixMatcherCopy = PublicSuffixMatcherLoader.getDefault();
//   		// }
// 		PublicSuffixMatcher publicSuffixMatcherCopy = PublicSuffixMatcherLoader.getDefault();


//         // 默认也是使用的这个，执行者,  会被代理方式实现
//         // HttpRequestExecutor requestExecCopy = this.requestExec;
//         // if (requestExecCopy == null) {
//             // requestExecCopy = new HttpRequestExecutor();
//         // }
// 		HttpRequestExecutor requestExecCopy = new HttpRequestExecutor();

// 		// 连接管理，有池化的功能
//         // HttpClientConnectionManager connManagerCopy = this.connManager;
//         HttpClientConnectionManager connManagerCopy = null;

//         // connManagerCopy == null  begin
//         // 连接管理的配置
//         if (connManagerCopy == null) {
//             // 分层的连接socket管理
//             // LayeredConnectionSocketFactory sslSocketFactoryCopy = this.sslSocketFactory;
//             LayeredConnectionSocketFactory sslSocketFactoryCopy = null;
//             // 创建ssl
//             if (sslSocketFactoryCopy == null) {


//  				final String[] supportedProtocols = null;
//                 final String[] supportedCipherSuites = null;
                
//                 // HostnameVerifier hostnameVerifierCopy = this.hostnameVerifier;
//                 HostnameVerifier hostnameVerifierCopy = null;
//                 if (hostnameVerifierCopy == null) {
//                     hostnameVerifierCopy = new DefaultHostnameVerifier(publicSuffixMatcherCopy);
//                 }
//                 SSLContext sslContext = null;
//                 if (sslContext != null) {
//                     sslSocketFactoryCopy = new SSLConnectionSocketFactory(
//                             sslContext, supportedProtocols, supportedCipherSuites, hostnameVerifierCopy);
//                 } else {
//                     if (systemProperties) {
//                         sslSocketFactoryCopy = new SSLConnectionSocketFactory(
//                                 (SSLSocketFactory) SSLSocketFactory.getDefault(),
//                                 supportedProtocols, supportedCipherSuites, hostnameVerifierCopy);
//                     } else {
//                         sslSocketFactoryCopy = new SSLConnectionSocketFactory(
//                                 SSLContexts.createDefault(),
//                                 hostnameVerifierCopy);
//                     }
//                 }
//             }

//             // 池化的连接管理
//             // 注册了 http  https 的socket处理
//             // 
//             // connTimeToLive 
//             @SuppressWarnings("resource")
//             final PoolingHttpClientConnectionManager poolingmgr = new PoolingHttpClientConnectionManager(
//                     RegistryBuilder.<ConnectionSocketFactory>create()
//                         .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                         .register("https", sslSocketFactoryCopy)
//                         .build(),
//                     null,
//                     null,
//                     null,
//                     connTimeToLive,
//                     connTimeToLiveTimeUnit != null ? connTimeToLiveTimeUnit : TimeUnit.MILLISECONDS);

//             if (defaultSocketConfig != null) {
//                 poolingmgr.setDefaultSocketConfig(defaultSocketConfig);
//             }
//             if (defaultConnectionConfig != null) {
//                 poolingmgr.setDefaultConnectionConfig(defaultConnectionConfig);
//             }
//             if (systemProperties) {
//                 String s = System.getProperty("http.keepAlive", "true");
//                 if ("true".equalsIgnoreCase(s)) {
//                     s = System.getProperty("http.maxConnections", "5");
//                     final int max = Integer.parseInt(s);
//                     poolingmgr.setDefaultMaxPerRoute(max);
//                     poolingmgr.setMaxTotal(2 * max);
//                 }
//             }
//             if (maxConnTotal > 0) {
//                 poolingmgr.setMaxTotal(maxConnTotal);
//             }
//             if (maxConnPerRoute > 0) {
//                 poolingmgr.setDefaultMaxPerRoute(maxConnPerRoute);
//             }
//             connManagerCopy = poolingmgr;
//         }


//         // 连接重用策略
//         // ConnectionReuseStrategy reuseStrategyCopy = this.reuseStrategy;
//         ConnectionReuseStrategy reuseStrategyCopy = null;
//         if (reuseStrategyCopy == null) {
//             if (systemProperties) {
//                 final String s = System.getProperty("http.keepAlive", "true");
//                 if ("true".equalsIgnoreCase(s)) {
//                     reuseStrategyCopy = DefaultConnectionReuseStrategy.INSTANCE;
//                 } else {
//                     reuseStrategyCopy = NoConnectionReuseStrategy.INSTANCE;
//                 }
//             } else {
//                 reuseStrategyCopy = DefaultConnectionReuseStrategy.INSTANCE;
//             }
//         }
//         // 保活机制
//         // 建造器＝模式
//         // ConnectionKeepAliveStrategy keepAliveStrategyCopy = this.keepAliveStrategy;
//         ConnectionKeepAliveStrategy keepAliveStrategyCopy = null;
//         if (keepAliveStrategyCopy == null) {
//             keepAliveStrategyCopy = DefaultConnectionKeepAliveStrategy.INSTANCE;
//         }
//         // AuthenticationStrategy targetAuthStrategyCopy = this.targetAuthStrategy;
//         AuthenticationStrategy targetAuthStrategyCopy = null;
//         if (targetAuthStrategyCopy == null) {
//             targetAuthStrategyCopy = TargetAuthenticationStrategy.INSTANCE;
//         }
//         // AuthenticationStrategy proxyAuthStrategyCopy = this.proxyAuthStrategy;
//         AuthenticationStrategy proxyAuthStrategyCopy = null;
//         if (proxyAuthStrategyCopy == null) {
//             proxyAuthStrategyCopy = ProxyAuthenticationStrategy.INSTANCE;
//         }

//         // UserTokenHandler userTokenHandlerCopy = this.userTokenHandler;
//         UserTokenHandler userTokenHandlerCopy = null;
//         if (userTokenHandlerCopy == null) {
//             if (!connectionStateDisabled) {
//                 userTokenHandlerCopy = DefaultUserTokenHandler.INSTANCE;
//             } else {
//                 userTokenHandlerCopy = NoopUserTokenHandler.INSTANCE;
//             }
//         }

//         // 建议从配置文件中获取
//         String userAgentCopy = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50";
//         if (userAgentCopy == null) {
//             if (systemProperties) {
//                 userAgentCopy = System.getProperty("http.agent");
//             }
//             if (userAgentCopy == null) {
//                 userAgentCopy = VersionInfo.getUserAgent("Apache-HttpClient",
//                         "org.apache.http.client", getClass());
//             }
//         }

//         ClientExecChain execChain = createMainExec(
//                 requestExecCopy,
//                 connManagerCopy,
//                 reuseStrategyCopy,
//                 keepAliveStrategyCopy,
//                 new ImmutableHttpProcessor(new RequestTargetHost(), new RequestUserAgent(userAgentCopy)),
//                 targetAuthStrategyCopy,
//                 proxyAuthStrategyCopy,
//                 userTokenHandlerCopy);

//         // 这里进行了装饰
//         // 这个函数是多余的 ！
//         // execChain = decorateMainExec(execChain);

//         // HttpProcessor httpprocessorCopy = this.httpprocessor;
//         HttpProcessor httpprocessorCopy = null;
//         if (httpprocessorCopy == null) {

//             // 又是创建
//             // 默认是没有的
//             // 
//             // HttpProcessorBuilder 又是建造者模式
//             // 
//             final HttpProcessorBuilder b = HttpProcessorBuilder.create();
//             if (requestFirst != null) {
//                 for (final HttpRequestInterceptor i: requestFirst) {
//                     b.addFirst(i);
//                 }
//             }
//             if (responseFirst != null) {
//                 for (final HttpResponseInterceptor i: responseFirst) {
//                     b.addFirst(i);
//                 }
//             }
            
//             // 常见的内容处理
//             // new RequestUserAgent(userAgentCopy),  这个是什么玩意 
//             b.addAll(
//                     new RequestDefaultHeaders(defaultHeaders),
//                     new RequestContent(),
//                     new RequestTargetHost(),
//                     new RequestClientConnControl(),
//                     new RequestUserAgent(userAgentCopy),
//                     new RequestExpectContinue());

//             // 这两个得使用
//             if (!cookieManagementDisabled) {
//                 b.add(new RequestAddCookies());
//             }
//             if (!contentCompressionDisabled) {
//                 if (contentDecoderMap != null) {
//                     final List<String> encodings = new ArrayList<String>(contentDecoderMap.keySet());
//                     Collections.sort(encodings);
//                     b.add(new RequestAcceptEncoding(encodings));
//                 } else {
//                     b.add(new RequestAcceptEncoding());
//                 }
//             }
//             // 请求的缓存处理
//             if (!authCachingDisabled) {
//                 b.add(new RequestAuthCache());
//             }
//             // 响应的cookie管理
//             if (!cookieManagementDisabled) {
//                 b.add(new ResponseProcessCookies());
//             }
//             if (!contentCompressionDisabled) {
//                 if (contentDecoderMap != null) {
//                 	// 建造 InputStream 的工厂, 这边看看
//                     final RegistryBuilder<InputStreamFactory> b2 = RegistryBuilder.create();

//                     for (Map.Entry<String, InputStreamFactory> entry: contentDecoderMap.entrySet()) {
//                         b2.register(entry.getKey(), entry.getValue());
//                     }
//                     // 加入 b2的成员
//                     b.add(new ResponseContentEncoding(b2.build()));
//                 } else {
//                     b.add(new ResponseContentEncoding());
//                 }
//             }
//             if (requestLast != null) {
//                 for (final HttpRequestInterceptor i: requestLast) {
//                     b.addLast(i);
//                 }
//             }
//             if (responseLast != null) {
//                 for (final HttpResponseInterceptor i: responseLast) {
//                     b.addLast(i);
//                 }
//             }
//             // 最后一次build建造
//             httpprocessorCopy = b.build();
//         }

//         // 有进行了一次包装
//         // 这个有进行了一次包装
//         execChain = new ProtocolExec(execChain, httpprocessorCopy);

//         // execChain = decorateProtocolExec(execChain);

//         // Add request retry executor, if not disabled
//         // 
//         if (!automaticRetriesDisabled) {
//             // HttpRequestRetryHandler retryHandlerCopy = this.retryHandler;
//             HttpRequestRetryHandler retryHandlerCopy = null;
//             if (retryHandlerCopy == null) {
//                 retryHandlerCopy = DefaultHttpRequestRetryHandler.INSTANCE;
//             }
//             // 又进行了一次封装 : 
//             execChain = new RetryExec(execChain, retryHandlerCopy);
//         }

//         // 路由策略， 这个后期可能会改，现在不用
//         // HttpRoutePlanner routePlannerCopy = this.routePlanner;
//         HttpRoutePlanner routePlannerCopy = null;
//         if (routePlannerCopy == null) {
//             // SchemePortResolver schemePortResolverCopy = this.schemePortResolver;
//             SchemePortResolver schemePortResolverCopy = null;
//             if (schemePortResolverCopy == null) {
//                 schemePortResolverCopy = DefaultSchemePortResolver.INSTANCE;
//             }
//             if (proxy != null) {
//                 routePlannerCopy = new DefaultProxyRoutePlanner(proxy, schemePortResolverCopy);
//             } else if (systemProperties) {
//                 routePlannerCopy = new SystemDefaultRoutePlanner(
//                         schemePortResolverCopy, ProxySelector.getDefault());
//             } else {
//                 routePlannerCopy = new DefaultRoutePlanner(schemePortResolverCopy);
//             }
//         }

//         // Add redirect executor, if not disabled
//         // 里面 有了重定向的处理
//         if (!redirectHandlingDisabled) {
//             // RedirectStrategy redirectStrategyCopy = this.redirectStrategy;
//             RedirectStrategy redirectStrategyCopy = null;
//             if (redirectStrategyCopy == null) {
//                 redirectStrategyCopy = DefaultRedirectStrategy.INSTANCE;
//             }
//             execChain = new RedirectExec(execChain, routePlannerCopy, redirectStrategyCopy);
//         }


//         // Optionally, add service unavailable retry executor
//         // 重试策略，也得自己写
//         // final ServiceUnavailableRetryStrategy serviceUnavailStrategyCopy = this.serviceUnavailStrategy;
//         final ServiceUnavailableRetryStrategy serviceUnavailStrategyCopy = null;
//         if (serviceUnavailStrategyCopy != null) {
//             execChain = new ServiceUnavailableRetryExec(execChain, serviceUnavailStrategyCopy);
//         }
//         // Optionally, add connection back-off executor
//         if (backoffManager != null && connectionBackoffStrategy != null) {
//             execChain = new BackoffStrategyExec(execChain, connectionBackoffStrategy, backoffManager);
//         }


//         // 认证机制
//         // Lookup<AuthSchemeProvider> authSchemeRegistryCopy = this.authSchemeRegistry;
//         Lookup<AuthSchemeProvider> authSchemeRegistryCopy = null;
//         if (authSchemeRegistryCopy == null) {
//             authSchemeRegistryCopy = RegistryBuilder.<AuthSchemeProvider>create()
//                 .register(AuthSchemes.BASIC, new BasicSchemeFactory())
//                 .register(AuthSchemes.DIGEST, new DigestSchemeFactory())
//                 .register(AuthSchemes.NTLM, new NTLMSchemeFactory())
//                 .register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory())
//                 .register(AuthSchemes.KERBEROS, new KerberosSchemeFactory())
//                 .build();
//         }
//         // Lookup<CookieSpecProvider> cookieSpecRegistryCopy = this.cookieSpecRegistry;
//         Lookup<CookieSpecProvider> cookieSpecRegistryCopy = null;
//         if (cookieSpecRegistryCopy == null) {
//             cookieSpecRegistryCopy = CookieSpecRegistries.createDefault(publicSuffixMatcherCopy);
//         }

//         // CookieStore : 这个是什么，不需要管理
//         // CookieStore defaultCookieStore = this.cookieStore;
//         CookieStore defaultCookieStore = null;
//         if (defaultCookieStore == null) {
//             defaultCookieStore = new BasicCookieStore();
//         }

//         // 认证处理
//         // CredentialsProvider defaultCredentialsProvider = this.credentialsProvider;
//         CredentialsProvider defaultCredentialsProvider = null;
//         if (defaultCredentialsProvider == null) {
//             if (systemProperties) {
//                 defaultCredentialsProvider = new SystemDefaultCredentialsProvider();
//             } else {
//                 defaultCredentialsProvider = new BasicCredentialsProvider();
//             }
//         }


//         List<Closeable> closeablesCopy = closeables != null ? new ArrayList<Closeable>(closeables) : null;
//         if (!connManagerShared) {
//             if (closeablesCopy == null) {
//                 closeablesCopy = new ArrayList<Closeable>(1);
//             }
//             final HttpClientConnectionManager cm = connManagerCopy;

//             if (evictExpiredConnections || evictIdleConnections) {
//                 final IdleConnectionEvictor connectionEvictor = new IdleConnectionEvictor(cm,
//                         maxIdleTime > 0 ? maxIdleTime : 10, maxIdleTimeUnit != null ? maxIdleTimeUnit : TimeUnit.SECONDS);
//                 closeablesCopy.add(new Closeable() {

//                     @Override
//                     public void close() throws IOException {
//                         connectionEvictor.shutdown();
//                     }

//                 });
//                 connectionEvictor.start();
//             }
//             closeablesCopy.add(new Closeable() {

//                 @Override
//                 public void close() throws IOException {
//                     cm.shutdown();
//                 }

//             });
//         }

//         InternalHttpClient client = new InternalHttpClient(
//                 execChain,
//                 connManagerCopy,
//                 routePlannerCopy,
//                 cookieSpecRegistryCopy,
//                 authSchemeRegistryCopy,
//                 defaultCookieStore,
//                 defaultCredentialsProvider,
//                 defaultRequestConfig != null ? defaultRequestConfig : RequestConfig.DEFAULT,
//                 closeablesCopy);
    	

// 		System.out.println("配置结束");

// 	}


// 	protected ClientExecChain createMainExec(
//             final HttpRequestExecutor requestExec,
//             final HttpClientConnectionManager connManager,
//             final ConnectionReuseStrategy reuseStrategy,
//             final ConnectionKeepAliveStrategy keepAliveStrategy,
//             final HttpProcessor proxyHttpProcessor,
//             final AuthenticationStrategy targetAuthStrategy,
//             final AuthenticationStrategy proxyAuthStrategy,
//             final UserTokenHandler userTokenHandler)
//     {
//         return new MainClientExec(
//                 requestExec,
//                 connManager,
//                 reuseStrategy,
//                 keepAliveStrategy,
//                 proxyHttpProcessor,
//                 targetAuthStrategy,
//                 proxyAuthStrategy,
//                 userTokenHandler);
//     }

// }