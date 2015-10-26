package org.xu.DownloadMethods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.frontier.Frontier;

// 这个应该继承 download
public class Mp3Downloader implements Runnable {

	//存储mp3来源地址的队列
	private Frontier frontier = null;
	
	//MP3目录，需要屏蔽目录的差异
	// private String direcory = "G://eclipseVOA//crawler//downloader//";
	private String directory = "/Users/snow_young/tech/VoaHelper/mp3dir";
	//日志
	public static Logger logger = LoggerFactory.getLogger(Mp3Downloader.class);
	
	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public Frontier getFrontier() {
		return frontier;
	}

	public void setFrontier(Frontier frontier) {
		this.frontier = frontier;
	}

	public Mp3Downloader() {

	}

	public Mp3Downloader(Frontier frontier) {
		this.frontier = frontier;
	}

	public Mp3Downloader(Frontier frontier,String directory) {
		this.frontier = frontier;
		this.directory = directory;
	}
	
	/**
	 * [run 这样实现了异步，但是资源容易耗竭，需要设置最大并发数量]
	 */
	@Override
	public void run() {
		while (true) {
			String str = frontier.getNext();
			logger.info("需要下载的mp3 URL:" + str);
			// use return null in case of blocking
			if (str != null) {
				MP3 mp3 = new MP3(str, directory);
				mp3.run();
			} else {
				// wait then get
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					logger.error("数据库为空，等待轮询,Thread 失败:"+e.toString());	
				}
			}
		}

	}
}
