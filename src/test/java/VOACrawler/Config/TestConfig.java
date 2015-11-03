package Config;

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

import org.xu.configuration.MongoConfig;
import org.xu.configuration.JedisConfig;
import org.xu.configuration.Config; 


public class TestConfig{

    @Test
    public void testConfig(){
        // Config config = new Config();
        Config config = Config.getInstance();
        try{
        config.parseConfig(new FileInputStream(new File(
             "/Users/snow_young/tech/VOACrawler/src/main/resources/properties.yml")));
        }catch(IOException e){
            // logger.in
        }

        JedisConfig jedis = config.getJedisConfig();
        MongoConfig mongo = config.getMongoConfig();
        System.out.println("获取的redis映射");
        System.out.println("redis port => " + jedis.getPort());

        JedisConfig.Pool pool = jedis.getPool();
        System.out.println("redis pool  maxactive => " + pool.getMaxActive());
        System.out.println("redis pool  maxtotla => " + pool.getMaxTotal());
        System.out.println("redis pool  maxidle => " + pool.getMaxIdle());
        System.out.println("redis pool  maxWait => " + pool.getMaxWait());
        System.out.println("redis pool  testOnBorrow => " + pool.getTestOnBorrow());
        System.out.println("redis pool  testOnReturn => " + pool.getTestOnReturn());


        System.out.println("获取的mongo映射");
        System.out.println("mongo port => " + mongo.getPort());
        System.out.println("mongo dbname => " + mongo.getDbname());

    }
}