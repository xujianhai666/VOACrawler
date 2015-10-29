package org.xu.configuration;

public class MongoConfig{

	private String ip;
	private int port;
	private String dbname;
	private int maxSeconds;
	private int maxConn;

	public MongoConfig(){

	}

	public MongoConfig(MongoConfig config){
		this.ip = config.getIp();
		this.port = config.getPort();
		this.dbname = config.getDbname();
		this.maxSeconds = config.getMaxSeconds();
		this.maxConn = config.getMaxConn();
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

	public String getDbname(){
		return this.dbname;
	}

	public void setDbname(String dbname){
		this.dbname = dbname;
	}

	public void setMaxSeconds(int maxSeconds){
		this.maxSeconds = maxSeconds;
	}

	public int getMaxSeconds(){
		return this.maxSeconds;
	}

	public void setMaxConn(int maxConn){
		this.maxConn = maxConn;
	}

	public int getMaxConn(){
		return this.maxConn;
	}
}