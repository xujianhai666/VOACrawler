package reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

import org.xu.reader.UrlReader;
import org.xu.frontier.DBFrontier;
import org.xu.configuration.Config;


public class TestUrlReader{

	// @Test
	// public void testUrlRunner(){
	// 	// UrlReader exmp = new UrlReader(new StringBuilder(
	// 	// 		"http://www.51voa.com/VOA_Standard_1.html"));
		
	// 	try{
	// 		Config config = Config.getInstance();
 //        	config.parseConfig(new FileInputStream(new File(
 //             	"/Users/snow_young/tech/VOACrawler/src/main/resources/properties.yml")));
 //        }catch(IOException e){
            
 //        }

	// 	UrlReader exmp = new UrlReader(
	// 		new StringBuilder("http://www.51voa.com/VOA_Standard_1_archiver.html"));

	// 	exmp.setFrontier(new DBFrontier("Queue"));
	// 	Thread t = new Thread(exmp);
	// 	t.start();
	// 	try{
	// 		t.join();
	// 	}catch(java.lang.InterruptedException e){

	// 	}
	// }
}


