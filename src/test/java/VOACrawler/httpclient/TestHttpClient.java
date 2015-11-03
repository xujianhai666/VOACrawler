package httpclient;


import org.junit.Test;

import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


// public class TestHttpClient{

// 	@Test
// 	public void testClient(){

//         try {
//         	CloseableHttpClient httpclient = HttpClients.createDefault();
//             HttpGet httpGet = new HttpGet("http://www.51voa.com/VOA_Standard_English/microscope-brings-tiny-worlds-closer-researchers-66090.html");
//             CloseableHttpResponse response1 = httpclient.execute(httpGet);
//             // The underlying HTTP connection is still held by the response object
//             // to allow the response content to be streamed directly from the network socket.
//             // In order to ensure correct deallocation of system resources
//             // the user MUST call CloseableHttpResponse#close() from a finally clause.
//             // Please note that if response content is not fully consumed the underlying
//             // connection cannot be safely re-used and will be shut down and discarded
//             // by the connection manager.
//             try {
//                 System.out.println(response1.getStatusLine());
//                 HttpEntity entity1 = response1.getEntity();
//                 System.out.println("HTTP Entity : " );

//                 InputStream  is = entity1.getContent();
//                 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//                 String linestr = null;
//                 while((linestr = reader.readLine())!=null){
//                 	System.out.println("line = > " + linestr);

//                 }
//                 System.out.println("结束了！");
//                 // do something useful with the response body
//                 // and ensure it is fully consumed
//                 // EntityUtils.consume(entity1);
//             } finally {
//                 response1.close();
//             }
//         }catch(Exception ex){

//         }
// 	}

	// @Test
	// public void testMethod(){
 //    	// params有点怪怪的
 //    	// 
 //    	System.out.println("开始了");
 //        HttpParams params = new BasicHttpParams();
 //        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
 //        HttpProtocolParams.setContentCharset(params, "UTF-8");
 //        HttpProtocolParams.setUserAgent(params, "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
 //        HttpProtocolParams.setUseExpectContinue(params, true);


 //        // 可以添加自定义的处理器
 //        BasicHttpProcessor httpproc = new BasicHttpProcessor();
 //        // Required protocol interceptors
 //        httpproc.addInterceptor(new RequestContent());
 //        httpproc.addInterceptor(new RequestTargetHost());
 //        // Recommended protocol interceptors
 //        httpproc.addInterceptor(new RequestConnControl());
 //        // httpproc.addInterceptor(new RequestUserAgent());
 //        httpproc.addInterceptor(new RequestExpectContinue());



 //        HttpRequestExecutor httpexecutor = new HttpRequestExecutor();
        
 //        HttpContext context = new BasicHttpContext(null);
 //        // HttpHost host = new HttpHost("http://51voa.com", 80);
 //        HttpHost host = new HttpHost("110.76.43.78",80);

 //        DefaultHttpClientConnection conn = new DefaultHttpClientConnection();
 //        ConnectionReuseStrategy connStrategy = new DefaultConnectionReuseStrategy();

 //        context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
 //        context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, host);

 //        try {
            
 //            // String[] targets = {
 //            //         "/",
 //            //         "/servlets-examples/servlet/RequestInfoExample", 
 //            //         "/somewhere%20in%20pampa"};
            
 //            // String[] targets = {
 //            //         "/",
 //            //         "/VOA_Standard_English/microscope-brings-tiny-worlds-closer-researchers-66090.html", 
 //            //         "/Technology_Report_1.html"};


 //            String[] targets = {
 //                    "http://www.51voa.com/VOA_Standard_1.html",
 //                    "http://www.51voa.com/VOA_Standard_English/microscope-brings-tiny-worlds-closer-researchers-66090.html", 
 //                    "http://www.51voa.com/Technology_Report_1.html"};


 //            for (int i = 0; i < targets.length; i++) {
                
 //                if (!conn.isOpen()) {
 //                	// 
 //                    Socket socket = new Socket(host.getHostName(), host.getPort());
 //                    // Socket socket = new Socket("110.76.43.78",80);
 //                    conn.bind(socket, params);

 //                }

 //                BasicHttpRequest request = new BasicHttpRequest("GET", targets[i]);
 //                System.out.println(">> Request URI: " + request.getRequestLine().getUri());
                
 //                request.setParams(params);

 //                httpexecutor.preProcess(request, httpproc, context);
                
 //                HttpResponse response = httpexecutor.execute(request, conn, context);

 //                response.setParams(params);
 //                // httpexecutor.postProcess(response, httpproc, context);
                
                
 //                System.out.println("<< Response: " + response.getStatusLine());
 //                // 这里需要进行处理
 //                BufferedReader reader = new  BufferedReader(new InputStreamReader(response.getEntity().getContent()));
 //                String str = null;
 //                while((str=reader.readLine())!=null){
 //                    System.out.println(" line => " + str);
 //                }
 //                // 使用这个方法会导致 乱码
 //                // System.out.println(EntityUtils.toString(response.getEntity()));

 //                System.out.println("==============");
 //                if (!connStrategy.keepAlive(response, context)) {
 //                    conn.close();
 //                } else {
 //                    System.out.println("Connection kept alive...");
 //                }
 //            }
 //        }catch(Exception ex){

 //        }finally {
 //        	System.out.println("结束");
 //        	try{
 //            	conn.close();
 //        	}catch(Exception e){

 //        	}
 //        }
	// }

// }