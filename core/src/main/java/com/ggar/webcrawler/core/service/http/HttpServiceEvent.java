package com.ggar.webcrawler.core.service.http;

import java.net.URL;
import java.util.Map;

// TODO: get relevant information from request and response instead of returning both
public interface HttpServiceEvent {

	URL getRequestURL();
	Map<String, String> getRequestHeaders();
	Map<String, String> getResponseHeaders();
	Map<String, String> getCookies();
	String getHttpMethod();
	String getResponseBody();
	Exception getError();
	Long getTimestamp();

}
