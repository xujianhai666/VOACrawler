var http = require("http") ;  

var url = require("url");  
  
var start = function(route,handle){  
    http.createServer(function(request,response){  
        var pathname = url.parse(request.url).pathname;  
  
        if(pathname == "/start"){  
           	response.writeHead(200,{"Content-Type":"text/plain"});  
            response.write("server started.");  
            response.end();  
        }else if(pathname == "/404"){  
            response.writeHead(200,{"Content-Type":"text/plain"});  
            response.write("404 Not found.");  
            response.end();  
        }else if(pathname == "/500"){
        	response.writeHead(500,{"Content-Type":"text/plain"});  
            response.write("500 Server Error.");  
            response.end();  

        }  
    }).listen(8888);  
  
};
exports.start = start  

// 
// simple server 2 : split web
// function start(){  
//     //通过http提供的端口监听方法，来对3000端口进行监听  
//     http.createServer(function(request,response){  
//        //当本地的3000端口被访问时，回调该匿名函数  
//         console.log("hi");  
//     }).listen(3000) ;  
// } 
// exports.start  = start

// 
// 
// simple server : echo 
// http.createServer(function(request, response) { 
 
//     response.writeHead(200, {"Content-Type": "text/plain"}); 
 
//     response.write("Hello World"); 
 
//     response.end(); 
 
// }).listen(8888);