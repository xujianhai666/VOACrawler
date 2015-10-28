package org.xu.Configuration;

public class MongoConfig{

	private String ip;
	private String port;
	private String dbname;
	private String maxSeconds;
	private String maxConn;

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

	public String getPort(){
		return this.port;
	}

	public void setPort(String port){
		this.port = port;
	} 

	public String getDbname(){
		return this.dbname;
	}

	public void setDbname(String dbname){
		this.dbname = dbname;
	}

	public void setMaxSeconds(String maxSeconds){
		this.maxSeconds = maxSeconds;
	}

	public String getMaxSeconds(){
		return this.maxSeconds;
	}

	public void setMaxConn(String maxConn){
		this.maxConn = maxConn;
	}

	public String getMaxConn(){
		return this.maxConn;
	}
}