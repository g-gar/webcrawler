package com.ggar.webcrawler.tests.test1;

import com.ggar.webcrawler.core.model.CrawlerTask;
import com.ggar.webcrawler.core.service.http.HttpService;
import com.ggar.webcrawler.core.service.http.Interceptor;
import com.ggar.webcrawler.core.util.UrlUtils;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BasicJsoupCrawlerTask implements CrawlerTask {

	private final HttpService<Document> httpService;
	private final Collection<Interceptor> interceptors;
	private final Logger logger = Logger.getLogger(BasicJsoupCrawlerTask.class.getName());

	public BasicJsoupCrawlerTask(HttpService<Document> httpService, Collection<Interceptor> interceptors) {
		this.httpService = httpService;
		this.interceptors = interceptors;
	}

	@Override
	public Collection<URL> apply(URL url) {
		Collection result = Collections.emptyList();

		try {
			String html = httpService.get(url, interceptors);
			Document doc = httpService.parse(html);
			result = doc.getElementsByTag("a").stream()
				.filter(e -> e.hasAttr("href"))
				.map(e -> UrlUtils.resolve(url, e.attr("href")))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
