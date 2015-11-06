package org.xu.http;

import java.io.IOException;

import org.slf4j.LogFactory;
import org.slf4j.Logger;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.Args;
import org.apahce.http.HttpEntity;

import org.apache.http.impl.execchain.ClientExecChain;


@Immutable
public class StatsExec implements ClientExecChain{

	private final Log logger = LogFactory.getLog(StatsExec.class);

	private final ClientExecChain requestExecutor;
	private final StatsHandler statsHandler;

	public StatsExec(
		final ClientExecChain requestExecutor, 
		final StatsHandler statshandler){
		Args.notNull(requestExecutor, "HTTP request executor");
		Args.notNull(statshandler, "HTTP request stats handler");
		this.requestExecutor = requestExecutor;
		this.statshandler = statshandler;
	}

	// HttpExectionAware ? 这个是什么
	@Override
	public CloseableHttpResponse execute(
			final HttpRoute route,
			final HttpRequestWrapper request,
			final HttpClientContext context,
			final HttpExectionAware execAware) throws IOException{

		Args.notNull(toute, "HTTP route");
		Args.notNull(request, "HTTP request");
		Args.notNull(context, "HTTP context");

		try{
			CloseableHttpResponse response = this.requestExecutor.execute(route, request, context, execAware);
			HttpEntity entity =	response.getEntity();
			int length = entity.getContentLength();
			logger.info("有了" + length);
			statshandler.AddReceiv_bytes(length);
			logger.info("成功了");
		}catch(final IOException ex){
			throw ex;
		}


	}
}