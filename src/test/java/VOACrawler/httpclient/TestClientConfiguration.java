// /*
//  * ====================================================================
//  * Licensed to the Apache Software Foundation (ASF) under one
//  * or more contributor license agreements.  See the NOTICE file
//  * distributed with this work for additional information
//  * regarding copyright ownership.  The ASF licenses this file
//  * to you under the Apache License, Version 2.0 (the
//  * "License"); you may not use this file except in compliance
//  * with the License.  You may obtain a copy of the License at
//  *
//  *   http://www.apache.org/licenses/LICENSE-2.0
//  *
//  * Unless required by applicable law or agreed to in writing,
//  * software distributed under the License is distributed on an
//  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
//  * KIND, either express or implied.  See the License for the
//  * specific language governing permissions and limitations
//  * under the License.
//  * ====================================================================
//  *
//  * This software consists of voluntary contributions made by many
//  * individuals on behalf of the Apache Software Foundation.  For more
//  * information on the Apache Software Foundation, please see
//  * <http://www.apache.org/>.
//  *
//  */

// package org.apache.http.examples.client;

// import java.net.InetAddress;
// import java.net.UnknownHostException;
// import java.nio.charset.CodingErrorAction;
// import java.util.Arrays;
// import java.io.IOException;

// import javax.net.ssl.SSLContext;

// import org.apache.http.Consts;
// import org.apache.http.Header;
// import org.apache.http.HttpHost;
// import org.apache.http.HttpRequest;
// import org.apache.http.HttpResponse;
// import org.apache.http.ParseException;
// import org.apache.http.client.CookieStore;
// import org.apache.http.client.CredentialsProvider;
// import org.apache.http.client.config.AuthSchemes;
// import org.apache.http.client.config.CookieSpecs;
// import org.apache.http.client.config.RequestConfig;
// import org.apache.http.client.methods.CloseableHttpResponse;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.client.protocol.HttpClientContext;
// import org.apache.http.config.ConnectionConfig;
// import org.apache.http.config.MessageConstraints;
// import org.apache.http.config.Registry;
// import org.apache.http.config.RegistryBuilder;
// import org.apache.http.config.SocketConfig;
// import org.apache.http.conn.DnsResolver;
// import org.apache.http.conn.HttpConnectionFactory;
// import org.apache.http.conn.ManagedHttpClientConnection;
// import org.apache.http.conn.routing.HttpRoute;
// import org.apache.http.conn.socket.ConnectionSocketFactory;
// import org.apache.http.conn.socket.PlainConnectionSocketFactory;
// import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
// import org.apache.http.impl.DefaultHttpResponseFactory;
// import org.apache.http.impl.client.BasicCookieStore;
// import org.apache.http.impl.client.BasicCredentialsProvider;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.impl.conn.DefaultHttpResponseParser;
// import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
// import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
// import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
// import org.apache.http.impl.conn.SystemDefaultDnsResolver;
// import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
// import org.apache.http.io.HttpMessageParser;
// import org.apache.http.io.HttpMessageParserFactory;
// import org.apache.http.io.HttpMessageWriterFactory;
// import org.apache.http.io.SessionInputBuffer;
// import org.apache.http.message.BasicHeader;
// import org.apache.http.message.BasicLineParser;
// import org.apache.http.message.LineParser;
// import org.apache.http.ssl.SSLContexts;
// import org.apache.http.util.CharArrayBuffer;
// import org.apache.http.util.EntityUtils;


// import org.junit.Test;
// import static org.junit.Assert.*;
// // 自定义配置，很好
// /**
//  * This example demonstrates how to customize and configure the most common aspects
//  * of HTTP request execution and connection management.
//  */
// public class TestClientConfiguration {

//     @Test
//     public void testConfiguration(){

//         // Use custom message parser / writer to customize the way HTTP
//         // messages are parsed from and written out to the data stream.
//         // 
//         // 这个不是很懂诶
        
//         System.out.println("=== start begin ====");

//         try{

//         HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

//             @Override
//             public HttpMessageParser<HttpResponse> create(
//                 SessionInputBuffer buffer, MessageConstraints constraints) {
//                 // httpcore里 main的
//                 LineParser lineParser = new BasicLineParser() {

//                     @Override
//                     public Header parseHeader(final CharArrayBuffer buffer) {
//                         try {
//                             return super.parseHeader(buffer);
//                         } catch (ParseException ex) {
//                             return new BasicHeader(buffer.toString(), null);
//                         }
//                     }

//                 };
//                 // 
//                 return new DefaultHttpResponseParser(
//                     buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {

//                     @Override
//                     protected boolean reject(final CharArrayBuffer line, int count) {
//                         // try to ignore all garbage preceding a status line infinitely
//                         return false;
//                     }

//                 };
//             }

//         };

//         // 
//         HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

//         // 自定义ConnectionFactory, 方便处理 outgoing http connections,
//         // Use a custom connection factory to customize the process of
//         // initialization of outgoing HTTP connections. Beside standard connection
//         // configuration parameters HTTP connection factory can define message
//         // parser / writer routines to be employed by individual connections.
//         HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
//                 requestWriterFactory, responseParserFactory);

//         // Client HTTP connection objects when fully initialized can be bound to
//         // an arbitrary network socket. The process of network socket initialization,
//         // its connection to a remote address and binding to a local one is controlled
//         // by a connection socket factory.

//         // SSL context for secure connections can be created either based on
//         // system or application specific properties.
//         // 创建一个系统的认证
//         SSLContext sslcontext = SSLContexts.createSystemDefault();

//         // Create a registry of custom connection socket factories for supported
//         // protocol schemes.
//         Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//             .register("http", PlainConnectionSocketFactory.INSTANCE)
//             .register("https", new SSLConnectionSocketFactory(sslcontext))
//             .build();

//         // Use custom DNS resolver to override the system DNS resolution.
//         // 自定义的dns解析
//         // 又是继承系统默认的dns解析
//         DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

//             @Override
//             public InetAddress[] resolve(final String host) throws UnknownHostException {
//                 if (host.equalsIgnoreCase("myhost")) {
//                     return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
//                 } else {
//                     return super.resolve(host);
//                 }
//             }

//         };

//         // Create a connection manager with custom configuration.
//         // 这个我希望进行监控
//         PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
//                 socketFactoryRegistry, connFactory, dnsResolver);

//         // Create socket configuration
//         // socket配置
//         SocketConfig socketConfig = SocketConfig.custom()
//             .setTcpNoDelay(true)
//             .build();
//         // Configure the connection manager to use socket configuration either
//         // by default or for a specific host.
//         // 
//         // 
//         // ConnectionManager的配置
//         connManager.setDefaultSocketConfig(socketConfig);
//         connManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);
//         // Validate connections after 1 sec of inactivity
//         connManager.setValidateAfterInactivity(1000);

//         // Create message constraints
//         // 消息的限制
//         MessageConstraints messageConstraints = MessageConstraints.custom()
//             .setMaxHeaderCount(200)
//             .setMaxLineLength(2000)
//             .build();

//         // Create connection configuration
//         // 
//         // 
//         // 
//         ConnectionConfig connectionConfig = ConnectionConfig.custom()
//             .setMalformedInputAction(CodingErrorAction.IGNORE)
//             .setUnmappableInputAction(CodingErrorAction.IGNORE)
//             .setCharset(Consts.UTF_8)
//             .setMessageConstraints(messageConstraints)
//             .build();
//         // Configure the connection manager to use connection configuration either
//         // by default or for a specific host.
//         connManager.setDefaultConnectionConfig(connectionConfig);
//         connManager.setConnectionConfig(new HttpHost("somehost", 80), ConnectionConfig.DEFAULT);

//         // 持久化连接的 每个路由或者最大限制
//         // 这个可以用户自定义
//         // Configure total max or per route limits for persistent connections
//         // that can be kept in the pool or leased by the connection manager.
//         connManager.setMaxTotal(100);
//         connManager.setDefaultMaxPerRoute(10);
//         connManager.setMaxPerRoute(new HttpRoute(new HttpHost("somehost", 80)), 20);

//         // Use custom cookie store if necessary.
//         CookieStore cookieStore = new BasicCookieStore();
//         // Use custom credentials provider if necessary.
//         CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//         // Create global request configuration
//         RequestConfig defaultRequestConfig = RequestConfig.custom()
//             .setCookieSpec(CookieSpecs.DEFAULT)
//             .setExpectContinueEnabled(true)
//             .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
//             .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
//             .build();

//         // Create an HttpClient with the given custom dependencies and configuration.
//         // 可以自定义，还是不错的
//         // 
//         // ConnectionManager  CookieStore CredentialsProvider Proxy DefaultRequestConfig
//         // Proxy 
//         // 
//         // tmd, 可以添加拦截器吗？
//         CloseableHttpClient httpclient = HttpClients.custom()
//             .setConnectionManager(connManager)
//             .setDefaultCookieStore(cookieStore)
//             .setDefaultCredentialsProvider(credentialsProvider)
//             .setDefaultRequestConfig(defaultRequestConfig)
//             .build();
//             // .setConnectionManager(connManager)
//             // .setDefaultCookieStore(cookieStore)
//             // .setDefaultCredentialsProvider(credentialsProvider)
//             // .setProxy(new HttpHost("myproxy", 8080))
//             // .setDefaultRequestConfig(defaultRequestConfig)
//             // .build();

//         try {
//             HttpGet httpget = new HttpGet("http://www.51voa.com/VOA_Standard_1.html");
//             // Request configuration can be overridden at the request level.
//             // They will take precedence over the one set at the client level.
//             // 
//             // socket超时  连接超时  (连接和socket的不同在巴黎)
//             // ConnectionRequest超时  代理
//             // 
//             RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
//                 .setSocketTimeout(5000)
//                 .setConnectTimeout(5000)
//                 .setConnectionRequestTimeout(5000)
//                 .build();
//                 // .setSocketTimeout(5000)
//                 // .setConnectTimeout(5000)
//                 // .setConnectionRequestTimeout(5000)
//                 // .setProxy(new HttpHost("myotherproxy", 8080))
//                 // .build();
//             httpget.setConfig(requestConfig);

//             // Execution context can be customized locally.
//             HttpClientContext context = HttpClientContext.create();
//             // Contextual attributes set the local context level will take
//             // precedence over those set at the client level.
//             // 
//             // 放在了这里
//             context.setCookieStore(cookieStore);
//             context.setCredentialsProvider(credentialsProvider);

//             System.out.println("executing request " + httpget.getURI());
//             CloseableHttpResponse response = httpclient.execute(httpget, context);

//             System.out.println("---------------获取响应-------------------------");
            
//             try {

//                 System.out.println("----------------------------------------");
//                 System.out.println(response.getStatusLine());
//                 // 又是这个导致的问题！
//                 System.out.println(EntityUtils.toString(response.getEntity()));
//                 System.out.println("----------------------------------------");

//                 // Once the request has been executed the local context can
//                 // be used to examine updated state and various objects affected
//                 // by the request execution.

//                 // Last executed request
//                 context.getRequest();
//                 // Execution route
//                 context.getHttpRoute();
//                 // Target auth state
//                 context.getTargetAuthState();
//                 // Proxy auth state
//                 context.getTargetAuthState();
//                 // Cookie origin
//                 context.getCookieOrigin();
//                 // Cookie spec used
//                 context.getCookieSpec();
//                 // User security token
//                 context.getUserToken();

//             } finally {
//                 response.close();
//             }
//         } finally {
//             httpclient.close();
//         }
//     }catch(IOException exx){

//     }

//         System.out.println("=== kill  now ====");
//     }

// }

