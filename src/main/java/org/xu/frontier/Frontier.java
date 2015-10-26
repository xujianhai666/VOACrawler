package org.xu.frontier;

/**
 * behavior interface to get the url from store
 * whether it is threadsage depend its implementation
 * u can achieve the interface by jdk collections(java.util or java.util.concurrent) or 
 * nosql(redis、kestrel) or so on 
 */
public interface Frontier {
	
	public String getNext();
	
	public void putUrl(String url);
}
