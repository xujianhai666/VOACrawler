package org.xu.frontier;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xu.configuration.Config;
import org.xu.configuration.JedisConfig;


/**
 * java usage of redis
 */
public abstract class DBPool {
	
	private static final Logger logger = LoggerFactory.getLogger(DBPool.class);
	/*
	 * from the document, one JedisPool is enough
	 */
	private static JedisPool pool = null;
	// 后面去掉这两个 static, 其实这里就有一个依赖，实例 依赖 static 的实现
	private static JedisConfig config = Config.getInstance().getJedisConfig();
	private static Map<String, String> poolmap = config.getPool();

	/*
	 * the name of the queue
	 * Currently, just support one queuename
	 * considering more queuename 
	 */
	protected String queue;

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public DBPool (){
		
	}
	
	// 后面得抽取出来，专门进行实例化
	static{

		// 获取源代码进行改造
		JedisPoolConfig poolconfig = new JedisPoolConfig();


		// 传值的时候，必须使用String.valueOf()操作
		// 不然, 报错 ： String  和 Double类型的异常 ，stackoverflow进行提问 
		// 这个应该封转在自定义的map里面
		int value = GsonInt(String.valueOf(poolmap.get("maxIdle")));

		poolconfig.setMaxIdle(value);

			// config.setMaxTotal(500) //整个池最大值
			// 取代了这个  : config.setMaxActive(Integer.valueOf(props.getProperty("jedis.pool.maxActive")));
			//config.setMaxTotalPerKey(5) //每个key的最大
		
		poolconfig.setBlockWhenExhausted(true);	//资源耗尽 进行堵塞
	
		// poolconfig.setMaxWaitMillis(Long.valueOf(poolmap.get("maxWait")));
		value = GsonInt(String.valueOf(poolmap.get("maxWait")));
		poolconfig.setMaxWaitMillis(value);

		// poolconfig.setTestOnBorrow(Boolean.valueOf(poolmap.get("testOnBorrow")));
		poolconfig.setTestOnBorrow(true);

		// poolconfig.setTestOnReturn(Boolean.valueOf(poolmap.get("testOnReturn")));
		poolconfig.setTestOnReturn(true);


			pool = new JedisPool(poolconfig, config.getIp(), config.getPort(), config.getTimeout(),config.getPass());					
			

	}

	// 这个是gson解析导致的
	public static int GsonInt(String d){
        String value = String.valueOf(d);
        value = value.substring(0,value.indexOf("."));
        return Integer.valueOf(value);
	}


	// public static boolean GsonBool(String d){
 //        String value = String.valueOf(d);
 //        value = value.substring(0,value.indexOf("."));
 //        return Integer.valueOf(value);
	// }

	public static Jedis getJedisObject() {
		return pool.getResource();
	}

	public static void recycleJedisOjbect(Jedis jedis) {
		pool.returnResource(jedis);
	}
}