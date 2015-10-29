package org.xu.SaveMethods;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xu.configuration.Config;
import org.xu.configuration.MongoConfig;

/**
 * mongoclient 内部维护了一个线程池，所以代码端并不需要进行池的维护
 * MongoClient把connection的默认值从以前的10个变成了现在的100个
 * 去看一下源代码的实现, 弄清楚是否可以进行 并发数量的控制 以及 连接的如何控制
 */
public class MongoPool {
	
	private static  final Logger logger = LoggerFactory.getLogger(MongoPool.class);

	private static MongoClient mongoClient = null;
	private static DB db = null;
	
	// ip port dbname maxSeconds maxConn
	// 后面去掉 static 
	private static MongoConfig config = Config.getInstance().getMongoConfig();

	private static String dbName = config.getDbname();
	private static final int maxSeconds = config.getMaxSeconds();
	private static final int maxConn = config.getMaxConn();
	
	// 后面得改，根据需求配置决定是否进行实例化 
	static{
		logger.info("实例化mongo数据库");

		try {
			String host = config.getIp();
			int port = config.getPort();
			mongoClient = new MongoClient(host, port);
		} catch (UnknownHostException e) {
			// stop 如何停止？,想直接停止，向上抛出吧
			logger.error("主机地址不存在");
		}
	}

	public static  DB  getConn() {
		return mongoClient.getDB(dbName);
	}
}
