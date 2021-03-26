package com.ggar.webcrawler.http.playwright;

import com.ggar.webcrawler.core.definitions.http.LambdaWithException;
import com.ggar.webcrawler.core.definitions.http.HttpService;
import com.ggar.webcrawler.core.definitions.http.HttpServiceEvent;
import com.ggar.webcrawler.core.definitions.http.HttpServiceMonitor;
import com.ggar.webcrawler.core.definitions.http.Interceptor;
import com.microsoft.playwright.*;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PlaywrightHttpService implements HttpService<Page> {

	private final HttpServiceMonitor monitor;
	private final Browser browser;

	public PlaywrightHttpService(HttpServiceMonitor monitor) {
		this.monitor = monitor;
		try (Playwright playwright = Playwright.create()) {
			List<BrowserType> browserTypes = Arrays.asList(
				playwright.chromium(), playwright.webkit(), playwright.firefox()
			);
			Browser temp = null;
			for (BrowserType browserType : browserTypes) {
				try (Browser browser = browserType.launch()) {
					BrowserContext context = browser.newContext();
					temp = browser;
				}
			}
			browser = temp;
		}
	}

	@Override
	public String get(URL url) {
		return null;
	}

	@Override
	public String get(URL url, Collection<Interceptor> requestInterceptors) {
		return null;
	}

	@Override
	public String get(URL url, Collection<Interceptor> requestInterceptors, LambdaWithException<HttpServiceEvent> eventDigest) {
		return null;
	}

	@Override
	public Page parse(String html) {
		Page result = this.browser.newPage();
		result.setContent(html);
		return result;
	}
}
