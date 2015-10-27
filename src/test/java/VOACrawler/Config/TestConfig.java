package Config;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;
import org.yaml.snakeyaml.Yaml;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
// import com.google.gson.Gson;

import org.xu.Configuration.MongoConfig;


public class TestConfig{

	@Test
	public void testYaml(){
		Yaml yaml = new Yaml();
		// 通过空格缩进来控制层级关系
		// String document = "  a: 1\n  b:\n    c: 3\n    d: 4\n";
		// String document = "  a: 1\n  b:\n    c: 3\n    d: 4\n";
		String document = "  a: 1\n  b:\n    c: 3\n    d: 4\n  e: 5\n";
		System.out.println(document);
		System.out.println(yaml.dump(yaml.load(document)));

		// Yaml yaml = new Yaml();
		String document2 = "\n- Hesperiidae\n- Papilionidae\n- Apatelodidae\n- Epiplemidae";
		List<String> list = (List<String>) yaml.load(document2);
		System.out.println(list);

		// Yaml yaml = new Yaml();
    	String document3 = "hello: 25";
    	Map map = (Map) yaml.load(document3);
    	assertEquals("{hello=25}", map.toString());
    	assertEquals(25, map.get("hello"));

    	// 这个异常使用java junit解决
    	InputStream input = null;
    	try{
    		// System.out.println("path => " + TestConfig.class.getClassLoader().getResourceAsStream("time.yaml"));
    		// input = new FileInputStream(new File(
             // TestConfig.class.getClassLoader().getResourceAsStream("time.yaml")));
    		input = new FileInputStream(new File(
             "/Users/snow_young/tech/VOACrawler/src/test/resources/time.yml"));
    		int counter = 0;
    		for (Object data : yaml.loadAll(input)) {
        		System.out.println(data);
        		counter++;
    		}
    		assertEquals(3, counter);
    	}catch(java.io.FileNotFoundException e){
    		assertTrue(false);
    	}
	}

	@Test
	public void testJedis(){
		Yaml yaml = new Yaml();
    	// 这个异常使用java junit解决
    	InputStream input = null;
    	try{
    		input = new FileInputStream(new File(
             "/Users/snow_young/tech/VOACrawler/src/main/resources/properties.yml"));
    		int counter = 0;
    		// 如何遍历获取数据呢？
    		for (Object data : yaml.loadAll(input)) {
        		System.out.println(data);

        		counter++;
                if(counter==2){
                    LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>) data;
                    // for(LinkedHashMap.Entry<String,Object> entry : map.entrySet()){
                    //     System.out.println("key->" + entry.getKey() + "value->" + entry.getValue());
                    // }
                    for (String key : map.keySet()) {  
                        System.out.println("Key = " + key);
                        System.out.println("values = " + map.get(key));
                        String values = String.valueOf(map.get(key));                   
                        values = values.replace("=",":");
                        System.out.println("String values = " + values);

                        // 这里肯定有玄机
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();   
                        System.out.println("gson stream" + gson.fromJson(values, MongoConfig.class));
                        // LinkedHashMap detialmongo = (LinkedHashMap)map.get(key);
                        // for(Map.Entry<String,Object> entry : detialmongo.entrySet()){
                        //     System.out.prinltn("key->" + entry.getKey() + "value" + entry.getValue());
                        // }
                    }
                }
    		}
    		System.out.println("一共有 " + counter);
    		// assertEquals(3, counter);
    	}catch(java.io.FileNotFoundException e){
    		// 不应该报错的！
    		assertTrue(false);
    	}
	}
}