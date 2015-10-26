package reader;

import org.junit.Test;
import static org.junit.Assert.*;

import org.xu.reader.UrlReader;
import org.xu.frontier.DBFrontier;

public class TestUrlReader{

	@Test
	public void testUrlRunner(){
		// UrlReader exmp = new UrlReader(new StringBuilder(
		// 		"http://www.51voa.com/VOA_Standard_1.html"));
		UrlReader exmp = new UrlReader(
			new StringBuilder("http://www.51voa.com/VOA_Standard_1_archiver.html"));

		exmp.setFrontier(new DBFrontier("Queue"));
		Thread t = new Thread(exmp);
		t.start();
		try{
			t.join();
		}catch(java.lang.InterruptedException e){

		}
	}
}


