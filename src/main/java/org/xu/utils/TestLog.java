package org.xu.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class TestLog {

	private static Logger logger = null;
	@Before
	public void setup()
	{
		logger = LoggerFactory.getLogger(TestLog.class);
	}
	
	@Test
	public void testStatus()
	{
		 LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		 // print logback's internal status
		 StatusPrinter.print(lc);
	}
	@Test
	public void testout() 
	{
		logger.debug("debug---->");
		logger.info("info---->");
		logger.warn("warning---->");
		logger.error("error--->");
	}

}
