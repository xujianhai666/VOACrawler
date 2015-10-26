package entrance;

import org.junit.Test;

import org.xu.entrance.Controller;


public class TestEntrance{

	@Test
	public void testBootstrap(){
		System.out.println("启动了总开关");
		Controller.bootstrap();
		System.out.println("关闭了总开关");
	}
}