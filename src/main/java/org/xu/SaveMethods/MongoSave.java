package org.xu.SaveMethods;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xu.POJO.Page;

/**
 * 设计一个pages的池子
 */
public class MongoSave implements UrlSave{
	
	private static final Logger logger = LoggerFactory.getLogger(MongoSave.class);   

	public static DB db  = null;

	public MongoSave(){

	}

	/**
	 * [save need to add a cache using jdk collections]
	 * @param obj [description]
	 */
	@Override
	public void save(Object obj) {
		// TODO Auto-generated method stub
		if(obj==null){
			logger.error("存入了一个 null对象");
			return;
		}
		Page  page = (Page) obj;
		db = MongoPool.getConn();

		if(db!=null)
		{
			logger.info("正在存储mongo对象");
			// 这里会抛出异常吗？
			db.getCollection("pages").insert(new BasicDBObject("title",page.getTitle().toString()).append("mp3", page.getMp3().toString()).append("content",page.getContent().toString()));
		}
		MongoPool.backConn(db);
	}
}
