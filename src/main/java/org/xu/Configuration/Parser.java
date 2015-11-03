package org.xu.configuration;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.LinkedHashMap;

import org.yaml.snakeyaml.Yaml;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



// 目前支持 yaml类型的解析
// 为了避免引用的问题，暂时合并到Config文件中
// 但是不可避免会有多种实现
// 所以还是将parser抽象成接口，然后桥接实现吧 
public class Parser{

	private static final Logger logger = LoggerFactory.getLogger(Parser.class);
	// private Configuration config;

	 // 解析文件，获取配置
	 // 直接报错就行了，运行的时候停止
	 public void parseConfig(String filename){
	 	parseConfig(new File(filename));
	 }

	 public void parseConfig(File file){
	 	try{
	 		parseConfig(new FileInputStream(file));
	 	}catch(FileNotFoundException e){
	 		logger.warn("配置文件没有找到");
	 		// 这个时候应用程序应该退出的，这是一个致命的错误 fatal
	 	}
	 }

	 public void parseConfig(InputStream is){

	 }
	 // 检查文件是否存在
}