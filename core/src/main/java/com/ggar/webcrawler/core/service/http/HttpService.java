package com.ggar.webcrawler.core.service.http;

import com.ggar.webcrawler.core.model.LambdaWithException;

import java.net.URL;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

// TODO: contains an implementation of BlacklistService
public interface HttpService<T> {

	String get(URL url);
	String get(URL url, Collection<Interceptor> requestInterceptors);
	String get(URL url, Collection<Interceptor> requestInterceptors, LambdaWithException<HttpServiceEvent> eventDigest);

	T parse(String html);
}
