package DownloadMethods;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;

import org.xu.DownloadMethods.DownloadStrategy;
import org.xu.SaveMethods.MongoSave;
import org.xu.frontier.DBFrontier;

public class TestDownloadStrategy {
	
	// 配置都从配置文件中获取
	@Test
	public void testDownload_success() {
		System.out.println("开始了");
		// DownloadStrategy strategy = new DownloadStrategy();
		// 得支持文件的下载
		DownloadStrategy.download_all(
			"/VOA_Standard_English/obama-to-visit-malaysia-amid-mh-criticisms-55971.html",
			new MongoSave(),new DBFrontier("mp3"),"/Users/snow_young/tech/VoaHelper/mp3dir/");

		File mp3file = new File("/Users/snow_young/tech/VoaHelper/mp3dir/");
		// 在java中，java路径的检查，使用securitymanager
		assertTrue("文件应该存在", mp3file.exists()); 

		// 到mp3文件夹中进行检查
		// 
		// 到mongo中进行检查
		System.out.println("结束了");
	}
}
