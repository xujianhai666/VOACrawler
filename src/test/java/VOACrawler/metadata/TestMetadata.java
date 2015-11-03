package metadata;

import java.lang.reflect.*;
// import java.lang.annotation.Annotation;

import static org.junit.Assert.*;
import org.junit.Test;

import org.xu.http.Request;
import org.xu.http.Response;
import org.xu.metadata.Page;
import org.xu.metadata.TestPage;


public class TestMetadata{

	@Test
	public void testPageAnnotation(){
		//  读取类文件，判断注解,获取注解，判断方法合法，进行调用
		//  优化
		System.out.println("获取顺序");
		Page pageannotation = TestPage.class.getAnnotation(Page.class);
		System.out.println(" order = " + pageannotation.order());

		try{
			System.out.println("获取方法");
			// new Class[] {int.class}
			Method method = TestPage.class.getMethod("handle", new Class[]{Request.class, Response.class});
			Request req = new Request();
			Response res = new Response();
			// TestPage page = new TestPage();
			// TestPage page = TestPage.class.newInstance() ;
			Class cls = Class.forName("org.xu.metadata.TestPage");
			// TestPage page = cls.newInstance() ;
			// method.invoke(page ,new Object[]{req ,res});
			method.invoke(cls.newInstance(),new Object[]{req ,res});

		}catch(java.lang.NoSuchMethodException e){
			// 这个需要报错,停止运行，记录错误
			System.out.println("方法不存在");
		}catch(java.lang.IllegalAccessException ex){
			// 这个需要报错,停止运行，记录错误
			System.out.println("参数有问题");
		}catch(java.lang.reflect.InvocationTargetException es){
			// 这个需要报错,停止运行，记录错误
			System.out.println("调用有问题");
		}catch(java.lang.InstantiationException eex){
			// 这个需要报错,停止运行，记录错误
			System.out.println("实例化异常！");
		}catch(java.lang.ClassNotFoundException eex){
			// 这个需要报错,停止运行，记录错误
			System.out.println("类找不到！");
		}

	}

}






