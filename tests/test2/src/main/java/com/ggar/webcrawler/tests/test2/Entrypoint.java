package com.ggar.webcrawler.tests.test2;

import com.ggar.webcrawler.core.Crawler;
import com.ggar.webcrawler.core.logic.BasicBlacklistService;
import com.ggar.webcrawler.core.logic.BasicHttpServiceMonitor;
import com.ggar.webcrawler.core.logic.BasicRepService;
import com.ggar.webcrawler.core.service.blacklist.BlacklistService;
import com.ggar.webcrawler.core.service.http.HttpService;
import com.ggar.webcrawler.core.service.http.HttpServiceEvent;
import com.ggar.webcrawler.core.service.http.HttpServiceMonitor;
import com.ggar.webcrawler.core.service.http.Interceptor;
import com.ggar.webcrawler.core.service.rep.RepService;
import com.ggar.webcrawler.http.playwright.PlaywrightHttpService;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;

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
	private static final HttpService<Page> httpService = new PlaywrightHttpService(httpServiceMonitor);
	private static final RepService repService = new BasicRepService(httpService);
	private static final Collection<Interceptor> interceptors = Collections.unmodifiableCollection(new LinkedList<Interceptor<Browser.NewContextOptions>>() {{
		add(context -> context.setUserAgent(Configuration.USER_AGENT));
	}});
	private static final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Configuration.THREAD_POOL_SIZE);

	public static void main(String[] args) throws MalformedURLException {

		Crawler crawler = new Crawler(
			() -> new BasicPlaywrightCrawlerTask(httpService, interceptors),
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
				logger.info(String.format("Queue size: %s, Active workers: %s", crawler.getPendingTasks(), crawler.getActiveThreadsCount()));
			}
		}, 0, 100);
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if (crawler.getActiveThreadsCount() == 0 && crawler.getPendingTasks() == 0) {
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
