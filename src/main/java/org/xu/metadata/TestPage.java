package org.xu.metadata;

import org.xu.http.Request;
import org.xu.http.Response;


@Page(order=1)
public class TestPage{

	public TestPage(){
		
	}

	// 还需要对方法进行检测，是否符合要求
	public void handle(Request request, Response response){
		System.out.println("Hello  girl");
	}

}