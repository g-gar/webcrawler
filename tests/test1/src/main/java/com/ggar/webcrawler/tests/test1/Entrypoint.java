package com.ggar.webcrawler.tests.test1;

import com.ggar.webcrawler.core.implementations.BasicCrawler;
import com.ggar.webcrawler.core.implementations.BasicRepService;
import com.ggar.webcrawler.core.definitions.crawler.Crawler;
import com.ggar.webcrawler.core.definitions.blacklist.BlacklistService;
import com.ggar.webcrawler.core.definitions.http.HttpService;
import com.ggar.webcrawler.core.definitions.http.HttpServiceEvent;
import com.ggar.webcrawler.core.definitions.http.HttpServiceMonitor;
import com.ggar.webcrawler.core.definitions.http.Interceptor;
import com.ggar.webcrawler.core.definitions.rep.RepService;
import com.ggar.webcrawler.core.implementations.BasicHttpServiceMonitor;
import com.ggar.webcrawler.http.jsoup.JsoupHttpService;
import com.ggar.webcrawler.core.implementations.BasicBlacklistService;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

public class Entrypoint {

	private static final Logger logger = Logger.getLogger(Entrypoint.class.getName());
	private static final HttpServiceMonitor httpServiceMonitor = new BasicHttpServiceMonitor();
	private static final BlacklistService blacklistService = new BasicBlacklistService(new ArrayList<>(){{}});
	private static final HttpService<Document> httpService = new JsoupHttpService(httpServiceMonitor);
	private static final RepService repService = new BasicRepService(httpService);
	private static final Collection<Interceptor> interceptors = Collections.unmodifiableCollection(new LinkedList<Interceptor<Connection>>() {{
		add(connection -> connection.userAgent(Configuration.USER_AGENT));
	}});
	private static final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Configuration.THREAD_POOL_SIZE);

	public static void main(String[] args) throws MalformedURLException {

		Crawler crawler = new BasicCrawler(
			() -> new BasicJsoupCrawlerTask(httpService, interceptors),
			threadPoolExecutor,
			blacklistService,
			repService,
			Configuration.SLEEP_DURATION,
			Configuration.USER_AGENT
		);

		for (URL url : Configuration.URLS) {
			crawler.process(url);
		}

		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				logger.info(String.format("Queue size: %s, Active workers: %s", crawler.getPendingTasksCount(), crawler.getActiveThreadsCount()));
			}
		}, 0, 100);
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if (crawler.getActiveThreadsCount() == 0 && crawler.getPendingTasksCount() == 0) {
					crawler.stop();

					Entrypoint.processRegisteredHttpEvents(httpServiceMonitor.getRegisteredEvents());
					Entrypoint.processCollectedUrls(crawler.getCollectedUrls());

					t.cancel();
					t.purge();
				}
			}
		}, 1000, 100);
	}

	public static void processCollectedUrls(Collection<URL> collectedUrls) {

	}

	public static void processRegisteredHttpEvents(Collection<HttpServiceEvent> events) {

	}
}
