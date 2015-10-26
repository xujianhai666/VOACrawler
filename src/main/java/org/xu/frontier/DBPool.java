package org.xu.frontier;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * java usage of redis
 */
public abstract class DBPool {
	
	private static final Logger logger = LoggerFactory.getLogger(DBPool.class);
	/*
	 * from the document, one JedisPool is enough
	 */
	private static JedisPool pool = null;
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
	
	//
	static{
		try {
			// in case of intialized too many times
			//目录地址
			//检查目录地址是否存在
			//这个文件地址应该放在 正规的resources目录中
			InputStream in = new BufferedInputStream(
					new FileInputStream(
						System.getProperty("user.dir")
							+ "//resources//redis.properties"));
			logger.info("文件地址" + System.getProperty("user.dir")
							+ "/resources/redis.properties");

			Properties props = new Properties();
			props.load(in);

			JedisPoolConfig config = new JedisPoolConfig();

			config.setMaxIdle(Integer.valueOf(props
					.getProperty("jedis.pool.maxIdle")));
			// config.setMaxTotal(500) //整个池最大值
			// 取代了这个  : config.setMaxActive(Integer.valueOf(props.getProperty("jedis.pool.maxActive")));
			//config.setMaxTotalPerKey(5) //每个key的最大
				
			config.setBlockWhenExhausted(true);	//资源耗尽 进行堵塞
	
			config.setMaxWaitMillis(Long.valueOf(props
					.getProperty("jedis.pool.maxWait")));

			config.setTestOnBorrow(Boolean.valueOf(props
					.getProperty("jedis.pool.testOnBorrow")));

			config.setTestOnReturn(Boolean.valueOf(props
					.getProperty("jedis.pool.testOnReturn")));

			pool = new JedisPool(config, props.getProperty("redis.ip"),
					Integer.valueOf(props.getProperty("redis.port")), 
					Integer.valueOf(props.getProperty("timeout")),
					"xujianhai");
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Jedis getJedisObject() {
		return pool.getResource();
	}

	public static void recycleJedisOjbect(Jedis jedis) {
		pool.returnResource(jedis);
	}
}
