package org.xu.entrance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.xu.entrance.Controller;

import org.xu.configuration.Config;


public class Bootstrap{


	public static void main(String[] args){

		// 读取自定义配置文件
		// 
		// 扫描项目，获取注解
		// 
		// 
		// 装配配置文件
		try{
			// 应该获取当前目录的文件
			Config config = Config.getInstance();
        	config.parseConfig(new FileInputStream(new File(
             		"/Users/snow_young/tech/VOACrawler/src/main/resources/properties.yml")));
        }catch(IOException e){
            	// 异常抛出
        }

        // 运行
        Controller.bootstrap();

	} 
}