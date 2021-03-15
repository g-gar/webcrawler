package com.ggar.webcrawler.core.model;

public interface LambdaWithException<T> {

	void call(T param) throws Exception;

}
