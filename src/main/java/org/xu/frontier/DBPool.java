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
 * 所有的配置可以设计成 管道模式
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
	
	// 后面得抽取出来，专门进行实例化
	static{

		// 获取源代码进行改造
		JedisConfig config = Config.getInstance().getJedisConfig();
		JedisConfig.Pool configpool = config.getPool();
		// 命名优点冲突，需要修改源代码
		JedisPoolConfig poolconfig = new JedisPoolConfig();

		poolconfig.setMaxIdle(configpool.getMaxIdle());
		// config.setMaxTotal(500) //整个池最大值
		// 取代了这个  : config.setMaxActive(Integer.valueOf(props.getProperty("jedis.pool.maxActive")));
		//config.setMaxTotalPerKey(5) //每个key的最大
		poolconfig.setBlockWhenExhausted(true);	//资源耗尽 进行堵塞
		poolconfig.setMaxWaitMillis(configpool.getMaxWait());
		poolconfig.setTestOnBorrow(configpool.getTestOnBorrow());
		poolconfig.setTestOnReturn(configpool.getTestOnReturn());
		pool = new JedisPool(poolconfig, config.getIp(), config.getPort(), config.getTimeout(),config.getPass());						
	
	}


	public static Jedis getJedisObject() {
		return pool.getResource();
	}

	public static void recycleJedisOjbect(Jedis jedis) {
		pool.returnResource(jedis);
	}
}