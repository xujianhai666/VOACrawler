package org.xu.Configuration;

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

import org.xu.Configuration.MongoConfig;
import org.xu.Configuration.RedisConfig;

// 对配置的有效性进行检查
// 需要寻找最佳实践
// 应该是单例的
public class Config{

	private static final Logger logger = LoggerFactory.getLogger(Config.class);

	// 对象存储层(以存储为主，一半不涉及)
	private boolean mongoenabled = false;
	private boolean mysqlenabled = false;

	// url 存储层 : 存取速度要快，永久性(防止程序异常中断后数据丢失)
	// 应该避免在程序中修改，所以,get操作返回一个不可变的集合
	// 那么，parser只能尽快的
	private boolean redisenabled = false;
	private boolean kestrelenabled = false;

	private MongoConfig mongo = new MongoConfig();
	private RedisConfig redis = new RedisConfig();

	public Config(){

	}

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
	 	Yaml yaml = new Yaml();
	 	GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();  
	 	for(Object data : yaml.loadAll(is)){
	 		LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>) data;
            for(Map.Entry<String,Object> entry : map.entrySet()){
                System.out.println("key = " + entry.getKey());
                //  == 这个是引用一致性
                //  equals
                //  yaml解析就没有异常吗？ 进行测试一下
                if(entry.getKey().equals("jedis")){
                    System.out.println("匹配的 jedis key = " + entry.getKey());                        
                    redis = gson.fromJson(String.valueOf(entry.getValue()), RedisConfig.class);
                    redisenabled = true;
                }else if(entry.getKey().equals("mongo")){
                    System.out.println("匹配的 mongo key = " + entry.getKey());                        
                    mongo = gson.fromJson(String.valueOf(entry.getValue()), MongoConfig.class);
                    mongoenabled = true;
               	}
	 		}
	 	}
	}	

	// 给redis队列进行装配的
	// 配置不可修改
	public RedisConfig getRedisConfig(){
		return (new RedisConfig(redis));
	}

		// 给redis队列进行装配的
	// 配置不可修改
	public MongoConfig getMongoConfig(){
		return (new MongoConfig(mongo));
		// return this.mongo
	}
}