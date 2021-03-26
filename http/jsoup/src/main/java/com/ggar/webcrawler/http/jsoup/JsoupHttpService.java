package com.ggar.webcrawler.http.jsoup;

import com.ggar.webcrawler.core.definitions.http.LambdaWithException;
import com.ggar.webcrawler.core.definitions.http.HttpService;
import com.ggar.webcrawler.core.definitions.http.HttpServiceEvent;
import com.ggar.webcrawler.core.definitions.http.HttpServiceMonitor;
import com.ggar.webcrawler.core.definitions.http.Interceptor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;

public class JsoupHttpService implements HttpService<Document> {

	private final HttpServiceMonitor monitor;

	public JsoupHttpService(HttpServiceMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public String get(URL url) {
		return this.get(url, new HashSet<>());
	}

	@Override
	public String get(URL url, Collection<Interceptor> connectionInterceptors) {
		return get(url, connectionInterceptors, event -> {});
	}

	@Override
	public String get(URL url, Collection<Interceptor> connectionInterceptors, LambdaWithException<HttpServiceEvent> eventDigest) {
		String result = null;
		Connection connection = null;
		Connection.Response response = null;
		JsoupHttpServiceEvent.JsoupHttpServiceEventBuilder builder = new JsoupHttpServiceEvent.JsoupHttpServiceEventBuilder();

		try {

			connection = Jsoup.connect(url.toString());
			for (Interceptor<Connection> interceptor : connectionInterceptors) {
				interceptor.accept(connection);
			}
			builder.request(connection.request());

			if ((response = connection.execute()) != null) {
				builder.timestamp(System.currentTimeMillis());
				builder.response(response);
				result = response.body();
			}
		} catch (Exception e) {
			builder.timestamp(System.currentTimeMillis());
			builder.error(e);
			throw new RuntimeException(e);
		} finally {
			HttpServiceEvent event = builder.build();
			try {
				monitor.registerEvent(event);
				eventDigest.call(event);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return result;
	}

	@Override
	public Document parse(String html) {
		return Jsoup.parse(html);
	}

}
