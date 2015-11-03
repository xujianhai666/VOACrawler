package org.xu.configuration;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;


// 由于不需要配置的外部化，这里使用成员内部类
public class JedisConfig{

	private String ip;
	private int port;
	private String pass;
	private int timeout;

	private Pool pool;

	// gson不需要显示的构造函数也行
	public JedisConfig(){

	}

	// gson不需要显式的构造函数也行
	public JedisConfig(JedisConfig config){
		this.ip = config.getIp();
		this.port = config.getPort();
		this.pass = config.getPass();
		this.timeout = config.getTimeout(); 
		this.pool = new Pool(config.getPool());
	}

	public String getIp(){
		return this.ip;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public int getPort(){
		return this.port;
	}

	public void setPort(int port){
		this.port = port;
	} 

	public String getPass(){
		return this.pass;
	}

	public void setPass(String pass){
		this.pass = pass;
	}

	public int getTimeout(){
		return this.timeout;
	}

	public void setTimeout(int timeout){
		this.timeout = timeout;
	} 

	public Pool getPool(){
		return this.pool;
	}

	public void setPool(Pool pool){
		this.pool = pool;
	} 

	// 奇怪，这里的private和public有什么区别,不需要static 类型吗，进行一下测试
	public static class Pool{

		private int maxActive;
    	private int maxTotal;
    	private int maxIdle;
    	private int maxWait;
    	private boolean testOnBorrow;
    	private boolean testOnReturn;


    	public Pool(){

    	}

    	public Pool(Pool pool){
    		this.maxActive = pool.getMaxActive();
    		this.maxTotal = pool.getMaxTotal();
    		this.maxIdle = pool.getMaxIdle();
    		this.maxWait = pool.getMaxWait();
    		this.testOnBorrow = pool.getTestOnBorrow();
    		this.testOnReturn = pool.getTestOnReturn();
    	}

		public int getMaxActive(){
			return this.maxActive;
		}

		public void setMaxActive(int maxActive){
			this.maxActive = maxActive;
		} 

		public int getMaxTotal(){
			return this.maxTotal;
		}

		public void setMaxTotal(int maxTotal){
			this.maxTotal = maxTotal;
		} 		

		public int getMaxIdle(){
			return this.maxIdle;
		}

		public void setMaxIdle(int maxIdle){
			this.maxIdle = maxIdle;
		} 

		public int getMaxWait(){
			return this.maxWait;
		}

		public void setMaxWait(int maxWait){
			this.maxWait = maxWait;
		} 

		public boolean getTestOnBorrow(){
			return this.testOnBorrow;
		}

		public void setTestOnBorrow(boolean testOnBorrow){
			this.testOnBorrow = testOnBorrow;
		} 

		public boolean getTestOnReturn(){
			return this.testOnReturn;
		}

		public void setTestOnReturn(boolean testOnReturn){
			this.testOnReturn = testOnReturn;
		} 

	}
}