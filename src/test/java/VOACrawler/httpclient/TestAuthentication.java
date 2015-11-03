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

// import java.io.IOException;

// import org.apache.http.auth.AuthScope;
// import org.apache.http.auth.UsernamePasswordCredentials;
// import org.apache.http.client.CredentialsProvider;
// import org.apache.http.client.methods.CloseableHttpResponse;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.impl.client.BasicCredentialsProvider;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.util.EntityUtils;

// import org.junit.Test;
// import org.junit.Assert.*;
 

// /**
//  * A simple example that uses HttpClient to execute an HTTP request against
//  * a target site that requires user authentication.
//  */
// public class TestAuthentication {

//     @Test
//     public void testAuth(){

//         System.out.println(" bootstrap :");
//         try{
//         CredentialsProvider credsProvider = new BasicCredentialsProvider();

//         // 这个需要专门做一个登录器
//         credsProvider.setCredentials(
//                 new AuthScope("csdn.net", 80),
//                 new UsernamePasswordCredentials("1723262513@qq.com", "xujianhai"));
//         CloseableHttpClient httpclient = HttpClients.custom()
//                 .setDefaultCredentialsProvider(credsProvider)
//                 .build();
//         try {
//             // HttpGet httpget = new HttpGet("https://passport.csdn.net/account/login?from=http%3A%2F%2Fmy.csdn.net%2Fmy%2Fmycsdn");
//             HttpGet httpget = new HttpGet("http://my.csdn.net/my/mycsdn");

//             System.out.println("Executing request " + httpget.getRequestLine());
//             CloseableHttpResponse response = httpclient.execute(httpget);
//             try {
//                 System.out.println("打印结果----------------------------------------");
//                 System.out.println(response.getStatusLine());
//                 System.out.println(EntityUtils.toString(response.getEntity()));
//             } finally {
//                 response.close();
//             }
//         } finally {
//             httpclient.close();
//         }
//     }catch(IOException exx){

//     }

//     System.out.println("结束了----------------------------------------");

//     }
// }
