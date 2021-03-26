package com.ggar.webcrawler.core.definitions.http;

public interface LambdaWithException<T> {

	void call(T param) throws Exception;

}
