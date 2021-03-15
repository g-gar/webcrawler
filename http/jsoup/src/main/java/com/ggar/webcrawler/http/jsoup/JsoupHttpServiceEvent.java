package com.ggar.webcrawler.http.jsoup;

import com.ggar.webcrawler.core.service.http.HttpServiceEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jsoup.Connection;

import java.net.URL;
import java.util.Map;

@ToString
@Builder
@EqualsAndHashCode
public class JsoupHttpServiceEvent implements HttpServiceEvent {

	@Getter private final Connection.Request request;
	@Getter private final Connection.Response response;
	@Getter private final Exception error;
	@Getter private final Long timestamp;

	public JsoupHttpServiceEvent(Connection.Request request, Connection.Response response, Exception error, Long timestamp) {
		this.request = request;
		this.response = response;
		this.error = error;
		this.timestamp = timestamp;
	}

	@Override
	public URL getRequestURL() {
		return request.url();
	}

	@Override
	public Map<String, String> getRequestHeaders() {
		return request.headers();
	}

	@Override
	public Map<String, String> getResponseHeaders() {
		return response.headers();
	}

	@Override
	public Map<String, String> getCookies() {
		return request.cookies();
	}

	@Override
	public String getHttpMethod() {
		return request.method().name();
	}

	@Override
	public String getResponseBody() {
		return response.body();
	}
}
