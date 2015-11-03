package org.xu.DownloadMethods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xu.frontier.Frontier;
import org.xu.utils.TestLog;

/**
 * 没有实现具体的下载方法, 将下载方法隔离出去
 */
public abstract class Downloader implements Runnable{

	//使用桥接模型，定义与实现 相分离
	Frontier  frontier = null;
	//日志信息
	private static  Logger logger = LoggerFactory.getLogger(Downloader.class);
	
	public Downloader() {

	}

	public Downloader(Frontier frontier) {
		this.frontier = frontier;
	}



	public Frontier getFrontier() {
		return frontier;
	}

	public void setFrontier(Frontier frontier) {
		this.frontier = frontier;
	}
	
	
	public abstract void download(String Url);

	public void run() {
		while(true)
		{
			//获取可用的url 进行下载
			String  url  = frontier.getNext();
 			logger.info("需要下载的URL---->"+url);
 			
			if(url!=null)
			{
				download(url);
			}else{
				//轮询策略:查看数据库数据是否存在,应该 设置一定的时间长度，
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					logger.error("数据库为空，等待轮询,Thread 失败:"+e.toString());				
				}
			}
		}
	}
}
