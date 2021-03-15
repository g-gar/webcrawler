package com.ggar.webcrawler.http.playwright;

import com.ggar.webcrawler.core.model.LambdaWithException;
import com.ggar.webcrawler.core.service.http.HttpService;
import com.ggar.webcrawler.core.service.http.HttpServiceEvent;
import com.ggar.webcrawler.core.service.http.HttpServiceMonitor;
import com.ggar.webcrawler.core.service.http.Interceptor;
import com.microsoft.playwright.Page;

import java.net.URL;
import java.util.Collection;

public class PlaywrightHttpService implements HttpService<Page> {

	private final HttpServiceMonitor monitor;

	public PlaywrightHttpService(HttpServiceMonitor monitor) {
		this.monitor = monitor;
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
		return null;
	}
}
