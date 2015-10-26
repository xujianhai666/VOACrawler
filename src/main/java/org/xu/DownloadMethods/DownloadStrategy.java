package org.xu.DownloadMethods;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Text;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xu.POJO.Page;
import org.xu.SaveMethods.MongoSave;
import org.xu.SaveMethods.UrlSave;
import org.xu.frontier.DBFrontier;
import org.xu.frontier.Frontier;
import org.xu.utils.TestLog;


public class DownloadStrategy {

	private static Logger logger = LoggerFactory.getLogger(DownloadStrategy.class);

	public static void download_all(String url, UrlSave saver,Frontier frontier,String directory) {
		logger.debug("存储 mp3 目录:"+directory);
		try {

			Parser parser = new Parser();
			// 自带了 页面的解析，还是网络的版本
			parser.setURL("http://www.51voa.com" + url);

			parser.setEncoding("UTF-8");
			
			logger.info("需要解析的url地址:"+url);
			
			Page page = new Page();

			// 获取听力标题
			NodeFilter filter = new HasAttributeFilter("id", "title");
			NodeList nodeList = parser.extractAllNodesThatMatch(filter);
			// 需要写入到数据库
			if (nodeList != null && nodeList.size() > 0) {
				Node nameNode = nodeList.elementAt(0);
				page.setTitle(nameNode.toPlainTextString());
			}
			parser.reset();

			// 获取听力的音频
			// 有没有类似 lxml的 设置
			filter = new HasAttributeFilter("id", "mp3");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				
				Node nodeName = nodeList.elementAt(0);
				filter = new NodeClassFilter(LinkTag.class);
				parser.setResource(nodeName.toHtml());
				nodeList = parser.extractAllNodesThatMatch(filter);
				if (nodeList == null || nodeList.size() <= 0) {
					//应该写入日志
					logger.warn("解析找不到mp3,返回,不下载处理");
					return;
				}
				String link = null;
				//一般就一个
				LinkTag node = (LinkTag) nodeList.elementAt(0);
				link = node.extractLink();
				// 放入mp3队列
				frontier.putUrl(link);

				page.setMp3(
						directory + link.substring(link.lastIndexOf("/") + 1));	
			}
			parser.reset();
			
			// 获取听力文本
			parser.setURL("http://www.51voa.com" + url);
			parser.setEncoding("UTF-8");
			filter = new HasAttributeFilter("id", "content");
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList != null && nodeList.size() > 0) {
				for (int i = 0; i < nodeList.size(); i++) {
					Node nameNode = nodeList.elementAt(i);
					page.setContent(nameNode.toHtml());	
				}
			}
			parser.reset();

			saver.save(page);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			logger.error("下载前解析错误："+e.toString());
		}

	}
}
