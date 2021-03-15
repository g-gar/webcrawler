package com.ggar.webcrawler.tests.test2;

import com.ggar.webcrawler.core.model.CrawlerTask;
import com.ggar.webcrawler.core.service.http.HttpService;
import com.ggar.webcrawler.core.service.http.Interceptor;
import com.ggar.webcrawler.core.util.UrlUtils;
import com.microsoft.playwright.Page;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BasicPlaywrightCrawlerTask implements CrawlerTask {

	private final HttpService<Page> httpService;
	private final Collection<Interceptor> interceptors;
	private final Logger logger = Logger.getLogger(BasicPlaywrightCrawlerTask.class.getName());

	public BasicPlaywrightCrawlerTask(HttpService<Page> httpService, Collection<Interceptor> interceptors) {
		this.httpService = httpService;
		this.interceptors = interceptors;
	}

	@Override
	public Collection<URL> apply(URL url) {
		Collection result = Collections.emptyList();
		try {
			String html = httpService.get(url, interceptors);
			Page page = httpService.parse(html);
			result = page.querySelectorAll("a").stream()
				.map(e -> e.getAttribute("href"))
				.filter(Objects::nonNull)
				.map(e -> UrlUtils.resolve(url, e))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
