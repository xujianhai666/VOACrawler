package org.xu.configuration;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

// 能不能写一个专门的解析
// 如果配置文件仅仅设置了enable,那么，采用默认配置，具体的参数应该是什么呢？
public class JedisConfig{

	public String ip;
	public int port;
	public String pass;
	public int timeout;

	Map pool = new HashMap<String, String>();

	// gson不需要显示的构造函数也行
	public JedisConfig(){

	}

	// gson不需要显示的构造函数也行
	public JedisConfig(JedisConfig config){
		this.ip = config.getIp();
		this.port = config.getPort();
		this.pass = config.getPass();
		this.timeout = config.getTimeout(); 
		this.pool = Collections.unmodifiableMap(config.getPool());
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

	public Map getPool(){
		return pool;
	}

	public void setMap(Map<String,String> map){
		this.pool = map;
	} 
}