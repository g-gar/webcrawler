package com.ggar.webcrawler.core;

import com.ggar.webcrawler.core.service.blacklist.BlacklistService;
import com.ggar.webcrawler.core.model.CrawlerTask;
import com.ggar.webcrawler.core.service.rep.RepService;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Logger;

@EqualsAndHashCode
public class Crawler {

	@Getter private final Supplier<CrawlerTask> workerGenerator;
	private final ThreadPoolExecutor threadPoolExecutor;
	private final BlacklistService<URL> blacklistService;
	private final RepService repService;
	@Getter private final Long sleepDuration;
	@Getter private final String userAgent;
	private final Logger logger;
	private final Map<String, URL> collectedUrls;

	public Crawler(Supplier<CrawlerTask> workerGenerator, ThreadPoolExecutor threadPoolExecutor, BlacklistService<URL> blacklistService, RepService repService, Long sleepDuration, String userAgent) {
		this.workerGenerator = workerGenerator;
		this.threadPoolExecutor = threadPoolExecutor;
		this.blacklistService = blacklistService;
		this.repService = repService;
		this.sleepDuration = sleepDuration;
		this.userAgent = userAgent;
		this.logger = Logger.getLogger(Crawler.class.getName());
		this.collectedUrls = new ConcurrentHashMap<>();
	}

	public synchronized void process(URL url) {
		if (blacklistService.isValid(url)) {
			if (repService.canAccessUrl(url, userAgent)) {
				if (!collectedUrls.containsKey(url.toString())) {
					threadPoolExecutor.submit(() -> {
						logger.info(String.format("Processing %s", url));
						this.collectedUrls.put(url.toString(), url);
						CrawlerTask task = workerGenerator.get();
						Collection<URL> urls = task.apply(url);
						for (URL childUrl : urls) {
							if (!collectedUrls.containsKey(childUrl)) { // to avoid infinite loop
								Crawler.this.process(childUrl);
							}
						}
					});
					try {
						Thread.sleep(sleepDuration);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				logger.info(String.format("%s blocked access to url=[%s] with user-agent=[%s]", repService.getClass().getName(), url, userAgent));
			}
		} else {
			logger.info(String.format("%s blocked access to url=[%s]", blacklistService.getClass().getName(), url));
		}
	}

	public synchronized void stop() {
		try {
			threadPoolExecutor.shutdown();
			threadPoolExecutor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public synchronized Collection<URL> getCollectedUrls() {
		return this.collectedUrls.values();
	}

	public synchronized Integer getPendingTasks() {
		return threadPoolExecutor.getQueue().size();
	}

	public synchronized Integer getActiveThreadsCount() {
		return threadPoolExecutor.getActiveCount();
	}
}
