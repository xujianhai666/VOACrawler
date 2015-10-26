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

public class MongoPool {
	
	private static  final Logger logger = LoggerFactory.getLogger(MongoPool.class);

	private static MongoClient mongoClient = null;
	private static DB db = null;
	
	private static String dbName = "test5";
	private static final int maxSeconds = 100;
	private static final int maxConn = 20 ;
	
	private static LinkedList<DB> pools = null;
	
	//修改操作频繁  使用LinkList
	
	// 可以晚点实例化
	static{
		logger.info("实例化数据库");
		int conns = maxConn;

		try {
			mongoClient = new MongoClient("localhost", 27017);
			pools = new LinkedList<DB>();
			

			while(conns!=maxConn)
			{
				conns++;
				pools.add(mongoClient.getDB(dbName));
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			// stop 如何停止？
			if(conns == 0){
				logger.error("non connection has been initialized");
				e.printStackTrace();
			}
			logger.warn("has initialized " +conns +" connections, not achieving " + maxConn);
		}
	}

	public static int getMaxConn() {
		return maxConn;
	}

	// public static void setMaxConn(int maxConn) {		
	// 	MongoPool.maxConn = maxConn;
	// }

	public static String getDbname() {
		return dbName;
	}

	public static void setDBname(String dbName){
		MongoPool.dbName = dbName;
	}

	/**
	 * [getConn 内部实现有问题，应该从缓存中取]
	 * @return [description]
	 */
	public static  DB  getConn() {
		return mongoClient.getDB(dbName);
		/*
		 * mongoClient默认是长连接的
		 */
		// will change the pools to ConcurrentQueue
		// 可能抛出异常
		//     public E getFirst() {
    //     final Node<E> f = first;
    //     if (f == null)
    //         throw new NoSuchElementException();
    //     return f.item;
    // }
    // poll 方法不会抛出异常
    //     public E poll() {
    //     final Node<E> f = first;
    //     return (f == null) ? null : unlinkFirst(f);
    // }
		// return  pools.getFirst();
		// 可能返回null
		// 
		// return pools.poll();
		// return mongoClient.getDB(dbName);
	}
	
	public static void backConn(DB db)
	{
		pools.addLast(db);
	}
}
