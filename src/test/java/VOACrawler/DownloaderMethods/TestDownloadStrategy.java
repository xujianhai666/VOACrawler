package DownloadMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

import org.xu.DownloadMethods.DownloadStrategy;
import org.xu.SaveMethods.MongoSave;
import org.xu.frontier.DBFrontier;
import org.xu.configuration.Config;


public class TestDownloadStrategy {
	
	// 配置都从配置文件中获取
	@Test
	public void testDownload_success() {

		// 保证配置的一致性
		// 后面使用 junit的 注解吧
		// 这里需要优化
		try{
			Config config = Config.getInstance();
        	config.parseConfig(new FileInputStream(new File(
             	"/Users/snow_young/tech/VOACrawler/src/main/resources/properties.yml")));
        }catch(IOException e){
            
        }


		System.out.println("开始了");
		// DownloadStrategy strategy = new DownloadStrategy();
		// 得支持文件的下载
		// 这个需要到redis中塞数据的
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
