package org.xu.reader;

import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xu.DownloadMethods.Downloader;
import org.xu.frontier.DBFrontier;
import org.xu.frontier.Frontier;
import org.xu.utils.Strategy;
import org.xu.utils.WebClient;

public class UrlReader implements Runnable{

	//记录当前 的 记录条数，共享队列中的数量
	private static int count = 0;
	
	//日志记录
	private static Logger logger = LoggerFactory.getLogger(UrlReader.class);
	
	//起始的Url
	//read from the doc
	private StringBuilder startUrl = null;

	//预读取到的url的队列, 这个存放的是 1,2,3,4 等这些页面
	private Queue<StringBuilder> queues = new ConcurrentLinkedQueue<StringBuilder>();

	//工作队列，共享数据  url 的 保存区域
	Frontier frontier = null;
	
	public Frontier getFrontier() {
		return frontier;
	}

	//桥接模式
	public void setFrontier(Frontier frontier) {
		this.frontier = frontier;
	}

	/*
	 * 构造函数  提供起始url
	 */
	public UrlReader(StringBuilder startUrl) {
		this.startUrl = startUrl;
	}

	public UrlReader(StringBuilder startUrl, Frontier frontier) {
		this.startUrl = startUrl;
		this.frontier = frontier;
	}
	
	
	public void startReader() {
		preReader();
		produce();
		System.out.println("结束---->"+count);
	}

	/*
	 * 将网页 转化成 stream 流 进行处理
	 * 获取url列表的块
	 */
	private void produce(){
		while(!queues.isEmpty())
		{
			logger.info("预队列解析的url:"+queues.peek());
			
			InputStream  stream = null;
			stream = WebClient.url2sTream(queues.poll());
			if(stream==null){
				logger.warn("url解析的stream 为null");
				continue;
			}else
				logger.debug("toString--->"+stream.toString());
			// 这个应该是 util的工具类
			StringBuilder builder = Strategy.getList(stream); 
			logger.debug("builder--->"+builder.toString());
			try{
				in_Produce(builder);
			}catch(Exception e){
				logger.error("异常发生");
			}
		}
	}
	
	// 获取页面文章的超链接放入frontier
	private void in_Produce(StringBuilder str){
		
		Parser  parser = null;
		NodeFilter filter = null;
		NodeList nodeList = null;
		
		try{
			parser = new Parser(str.toString());
			filter = new NodeClassFilter(LinkTag.class);
			nodeList = parser.extractAllNodesThatMatch(filter);
		}catch(ParserException e){
			// 应该抛出
			logger.error("解析异常");
		}
		if(nodeList!=null || nodeList.size() >0){
			for(int i=0;i<nodeList.size();i++)
			{
				LinkTag node = (LinkTag) nodeList.elementAt(i);
				logger.info("link index("+i+") : " + node.extractLink()); 
				//没有及时回收，导致  8个 就死掉了
				
				if(node.extractLink().contains("/VOA_Standard_English/") && 
					!node.extractLink().trim().equals("/VOA_Standard_English/"))
					frontier.putUrl(node.extractLink());
				else{
					logger.debug("无效的link : " + node.extractLink());
				}
				count++;
			}

		}	
	}
	/*
	 * 通过起始 url 得到 待读取 的 url 队列
	 */
	private void preReader() {
		in_Queue(Strategy.getPageList(WebClient.url2sTream(startUrl),
				"pagelist"));
	}

	/*
	 * 将待读的的 url  提取出来，并放进 队列中
	 * 这个事读取什么的？
	 */
	protected void in_Queue(StringBuilder str) {

		// 正则表达式 匹配
		Parser parser = null;
		NodeFilter filter = null;
		NodeList nodeList = null;

		try {
			// 第一种方案
			logger.info("获取的字符串 => " + str);
			parser = new Parser(str.toString()); 
			filter = new NodeClassFilter(LinkTag.class);
			
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList == null || nodeList.size() <= 0) {
				logger.warn("这里为空");
				return;
			}
			for (int i = 0; i < nodeList.size(); i++) {
				LinkTag node = (LinkTag) nodeList.elementAt(i);
				logger.info("Link is--->" + node.extractLink());
				queues.add(new StringBuilder(node.extractLink()));
			}
			parser.reset();
		} catch (ParserException pe) {
			logger.error("预读取队列操作解析出错:"+pe.toString());
		}
	}

	@Override
	public void run() {
		startReader();
	}

}
