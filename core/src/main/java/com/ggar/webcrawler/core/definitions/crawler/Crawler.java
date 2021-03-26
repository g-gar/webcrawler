package com.ggar.webcrawler.core.definitions.crawler;

import java.net.URL;
import java.util.Collection;

public interface Crawler {

	void process(URL url);
	void stop();
	Collection<URL> getCollectedUrls();

	Integer getPendingTasksCount();
	Integer getActiveThreadsCount();
}
