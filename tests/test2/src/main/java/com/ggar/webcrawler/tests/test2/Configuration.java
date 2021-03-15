package com.ggar.webcrawler.tests.test2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Configuration {

	public static final Long SLEEP_DURATION = 500L;
	public static final String USER_AGENT = "Funny WebCrawling service (maybe Google's search engine v2)";
	public static final Integer THREAD_POOL_SIZE = 10;

	public static final Collection<URL> URLS = Collections.unmodifiableCollection(new ArrayList<>(){{
		try {
			add(new URL("https://asdf.com/"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}});
}
