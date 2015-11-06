package org.xu.http;

import java.util.AtomicInteger;

// 设计一个 统计的 请求和发送
// 应该统计哪些东西？ 统计模块，应该放到日志中，然后由日志消费者机型统计
// 所以放弃这个想法
public class SimpleStatsHandler implements StatsHandler{

	// 对于溢出的内容怎么处理！
	private AtomicInteger send_requests;
	private AtomicInteger receive_responses;
	private AtomicLong send_bytes;
	private AtomicLong receiv_bytes;

	public void OneRequest(){
		this.send_requests.incrementAndGet();
	}

	public void OneResponse(){
		this.receive_responses.incrementAndGet();
	}

	public void AddSend_bytes(long bytes){
		this.send_bytes.addAndGet(bytes);
	}

	public void AddReceiv_bytes(long bytes){
		this.receiv_bytes.addAndGet(bytes);
	}

}