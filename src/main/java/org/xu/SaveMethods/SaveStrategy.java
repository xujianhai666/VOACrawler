package org.xu.SaveMethods;

// 后面进行扩展
public class SaveStrategy {

	public MongoSave dbSave(){
		
		return new MongoSave();
	}
	
	public void fileSave(){
		
	}
	
}
