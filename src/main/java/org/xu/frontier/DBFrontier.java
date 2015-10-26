package org.xu.frontier;

import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * use redis implements the Frontier
 */
public class DBFrontier extends DBPool implements Frontier{

	private static final Logger logger = LoggerFactory.getLogger(DBFrontier.class);

	public  DBFrontier(String key)
	{
		queue = key;
	}
	
	@Override
	public  String getNext() {
		// 这里进行检查？返回null的问题
		return getQ();
	}

	@Override
	public void putUrl(String url) {
		// 这里进行检查，放入null的异常
		logger.info("存入url : " + url);
		putQ(url);
	}

	/**
	 * [putQ isolate the detail implementation]
	 * @param val [description]
	 */
	private void putQ(String url) {
		// 保证不能为 null, redis.clients.jedis.exceptions.JedisDataException: value sent to redis cannot be null
		Jedis jedis = getJedisObject();
		jedis.rpush(queue, url);
		recycleJedisOjbect(jedis);
	}

	/**
	 * [getQ isolate the detail implementation]
	 * @return [description]
	 */
	private String getQ() {
		Jedis jedis = getJedisObject();
		String url =  jedis.lpop(queue);
		recycleJedisOjbect(jedis);
		System.out.println("getQ---->" + url);
		return  url;
	}
}
