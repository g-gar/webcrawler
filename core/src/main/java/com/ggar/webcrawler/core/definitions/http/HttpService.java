package com.ggar.webcrawler.core.definitions.http;

import java.net.URL;
import java.util.Collection;

// TODO: contains an implementation of BlacklistService
public interface HttpService<T> {

	String get(URL url);
	String get(URL url, Collection<Interceptor> requestInterceptors);
	String get(URL url, Collection<Interceptor> requestInterceptors, LambdaWithException<HttpServiceEvent> eventDigest);

	T parse(String html);
}
