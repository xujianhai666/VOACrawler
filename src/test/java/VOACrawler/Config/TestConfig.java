package Config;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;
import org.yaml.snakeyaml.Yaml;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
// import com.google.gson.Gson;

import org.xu.Configuration.MongoConfig;
import org.xu.Configuration.RedisConfig;
import org.xu.Configuration.Config; 


public class TestConfig{

	// @Test
	// public void testYaml(){
	// 	Yaml yaml = new Yaml();
	// 	// 通过空格缩进来控制层级关系
	// 	String document = "  a: 1\n  b:\n    c: 3\n    d: 4\n  e: 5\n";
	// 	System.out.println(document);
	// 	System.out.println(yaml.dump(yaml.load(document)));

	// 	String document2 = "\n- Hesperiidae\n- Papilionidae\n- Apatelodidae\n- Epiplemidae";
	// 	List<String> list = (List<String>) yaml.load(document2);
	// 	System.out.println(list);

 //    	String document3 = "hello: 25";
 //    	Map map = (Map) yaml.load(document3);
 //    	assertEquals("{hello=25}", map.toString());
 //    	assertEquals(25, map.get("hello"));

 //    	// 这个异常使用java junit解决
 //    	InputStream input = null;
 //    	try{
 //    		input = new FileInputStream(new File(
 //             "/Users/snow_young/tech/VOACrawler/src/test/resources/time.yml"));
 //    		int counter = 0;
 //    		for (Object data : yaml.loadAll(input)) {
 //        		System.out.println(data);
 //        		counter++;
 //    		}
 //    		assertEquals(3, counter);
 //    	}catch(java.io.FileNotFoundException e){
 //    		assertTrue(false);
 //    	}
	// }

    @Test
    public void testConfig(){
        Config config = new Config();
        try{
        config.parseConfig(new FileInputStream(new File(
             "/Users/snow_young/tech/VOACrawler/src/main/resources/properties.yml")));
        }catch(IOException e){
            // logger.in
        }
        RedisConfig redis = config.getRedisConfig();
        MongoConfig mongo = config.getMongoConfig();
        System.out.println("获取的redis映射");
        System.out.println("redis port => " + redis.getPort());
        System.out.println("redis pool => " + redis.getPool());
        System.out.println("获取的mongo映射");
        System.out.println("mongo port => " + mongo.getPort());
        System.out.println("mongo dbname => " + mongo.getDbname());

    }

	// @Test
	// public void testJedis(){
	// 	Yaml yaml = new Yaml();
 //    	// 这个异常使用java junit解决
 //    	InputStream input = null;
 //    	try{
 //    		input = new FileInputStream(new File(
 //             "/Users/snow_young/tech/VOACrawler/src/main/resources/properties.yml"));
 //    		int counter = 0;
 //    		// 如何遍历获取数据呢？
 //            // 如果使用了 --- , 就是多个 object进行循环 
 //    		RedisConfig redis = null;
 //            MongoConfig mongo = null;
 //            GsonBuilder builder = new GsonBuilder();
 //            Gson gson = builder.create();  
 //            for (Object data : yaml.loadAll(input)) {
        		
 //                System.out.println(data);
                
 //                LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>) data;
 //                for(Map.Entry<String,Object> entry : map.entrySet()){
 //                    System.out.println("key = " + entry.getKey());
 //                    //  == 这个是引用一致性
 //                    //  equals
 //                    if(entry.getKey().equals("jedis")){
 //                        System.out.println("匹配的 jedis key = " + entry.getKey());                        
 //                        redis = gson.fromJson(String.valueOf(entry.getValue()), RedisConfig.class);
 //                    }
 //                    if(entry.getKey().equals("mongo")){
 //                        System.out.println("匹配的 mongo key = " + entry.getKey());                        
 //                        mongo = gson.fromJson(String.valueOf(entry.getValue()), MongoConfig.class);
 //                    }
 //                }
 //    		}

 //            System.out.println("获取的redis映射");
 //            System.out.println("redis port" + redis.getPort());
 //            System.out.println("redis pool" + redis.getPool());
 //            System.out.println("获取的mongo映射");
 //            System.out.println("mongo port" + mongo.getPort());
 //            System.out.println("mongo dbname" + mongo.getDbname());
 //    	}catch(java.io.FileNotFoundException e){
 //    		// 不应该报错的！
 //    		assertTrue(false);
 //    	}
	// }
    
    // 可用，测试jedis
    // @Test
    // public void testJedis(){
    //     Yaml yaml = new Yaml();
    //     // 这个异常使用java junit解决
    //     InputStream input = null;
    //     try{
    //         input = new FileInputStream(new File(
    //          "/Users/snow_young/tech/VOACrawler/src/main/resources/properties.yml"));
            


    //         System.out.println("获取的redis映射");
    //         System.out.println("redis port" + redis.getPort());
    //         System.out.println("redis pool" + redis.getPool());
    //         System.out.println("获取的mongo映射");
    //         System.out.println("mongo port" + mongo.getPort());
    //         System.out.println("mongo dbname" + mongo.getDbname());
    //     }catch(java.io.FileNotFoundException e){
    //         // 不应该报错的！
    //         assertTrue(false);
    //     }
    // }




}