package org.xu.http;

public interface StatsHandler{

	void OneRequest();

	void OneResponse();

	void AddSend_bytes(long bytes);

	void AddReceiv_bytes(long bytes);

}