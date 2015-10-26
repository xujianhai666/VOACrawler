package org.xu.entrance;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xu.DownloadMethods.DownloadStrategy;
import org.xu.DownloadMethods.Downloader;
import org.xu.DownloadMethods.MP3;
import org.xu.DownloadMethods.Mp3Downloader;
import org.xu.SaveMethods.MongoSave;
import org.xu.frontier.Frontier;
import org.xu.frontier.DBFrontier;
import org.xu.reader.UrlReader;

// 总控的入口
public class Controller {

	private static final Logger logger = LoggerFactory.getLogger(Controller.class);

		static List<Thread> pagedownloader = new ArrayList<Thread>();
		static List<Thread> mp3downloader = new ArrayList<Thread>();

		static Frontier  frontier  = new DBFrontier("Queue");
		static Frontier  mp3_front = new DBFrontier("mp3");

		// 启动下载线程，并发树木从配置文件中读取
		private  static void startDownn(){
			int down_thread_count=5;
			for(int i=0;i<down_thread_count ;i++)
			{
				Downloader down =new Downloader() {
					@Override
					public void download(String url) {
						DownloadStrategy.download_all(url, new MongoSave(), mp3_front,"/Users/snow_young/tech/VoaHelper/mp3dir/");
					}
				};
				// 有必要每一个线程一个吗？
				down.setFrontier(frontier);
				Thread t = new Thread(down);
				pagedownloader.add(t);
				logger.info("下载器启动, index = " + i);
				t.start();
				//因该最后一起 join一下，结构有问题
			}
		}
		
		private static void startMP3()
		{
			/*
			 * 同时启动10个线程 存在易失性
			 * 一旦线程停，线程中残留的 几个 未完全下载的 url 会被 抛弃，
			 *new DBFrontier("mp3"),"G://eclipseVOA//crawler//downloader//"
			 */
			//下载MP3的线程数量
			int mp3_thread_count = 10;
			//下载mp3的县曾启动，一旦有数据（mp3URL，就进行下载）
			//这个应该使用 mp3downloader 进行操作
			for(int i=0;i<mp3_thread_count;i++)
			{
				Downloader down = new Downloader(mp3_front){
					@Override
					public void download(String url) {
						// TODO Auto-generated method stub
						MP3 mus = new MP3(url, "/Users/snow_young/tech/VoaHelper/mp3dir/");
						mus.run();
					}	
				};
				Thread t = new Thread(down);
				pagedownloader.add(t);
				logger.info("mp3 下载器启动 , index = " + i);
				t.start();
			}
		}
		
		private static void startUrl(){
			(new Thread(new UrlReader(new StringBuilder("http://www.51voa.com/VOA_Standard_1_archiver.html"),  frontier))).start();
		}

		// 使用countdownlatch 或者 xxx 进行调度
		// 怎么算是爬完了
		public static  void bootstrap()
		{
			logger.info("开启下载总开关");
			startUrl();
			try{
				Thread.sleep(5000);
			}catch(java.lang.InterruptedException e){
				logger.warn("睡眠延迟被打断");
			}
			
			startDownn();
			startMP3();

			for(Thread t : pagedownloader){
				try{
					t.join();
				}catch(java.lang.InterruptedException e){
					logger.warn("被打断了");
				}
			}

			for(Thread t : mp3downloader){
				try{
					t.join();
				}catch(java.lang.InterruptedException e){
					logger.warn("被打断了");
				}
			}
			logger.info("关闭下载总开关");	
		}

		public static  void main(String[]args)
		{

			Controller.bootstrap();
			// logger.info("开启下载总开关");
			// startMP3();
			// startDownn();
			// startUrl();
			// logger.info("关闭下载总开关");	
		}
}
