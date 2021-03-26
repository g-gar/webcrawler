package com.ggar.webcrawler.core.implementations;

import com.ggar.webcrawler.core.definitions.http.HttpService;
import com.ggar.webcrawler.core.definitions.rep.RepService;
import com.ggar.webcrawler.core.utils.UrlUtils;
import com.google.search.robotstxt.Matcher;
import com.google.search.robotstxt.Parser;
import com.google.search.robotstxt.RobotsParseHandler;
import com.google.search.robotstxt.RobotsParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class BasicRepService implements RepService {

	private final Logger logger = Logger.getLogger(BasicRepService.class.getName());
	private final HttpService httpService;
	private final Map<String, Matcher> matchers;

	public BasicRepService(HttpService httpService) {
		this.httpService = httpService;
		matchers = new ConcurrentHashMap<>();
	}

	@Override
	public Matcher loadRepForDomain(URL url) {
		Matcher result = null;
		String basePath = null;
		Parser parser = new RobotsParser(new RobotsParseHandler());
		try {
			basePath = UrlUtils.getBasePath(url);
			String robotsTxtPath = String.format("%s/robots.txt", basePath);
			String robotsTextContent = httpService.get(new URL(robotsTxtPath));
			result = parser.parse(robotsTextContent.getBytes(StandardCharsets.UTF_8));
		} catch (MalformedURLException e) {
			logger.info(String.format("Couldn't find a robots.txt file under %s... populating with an empty ruleset", basePath));
			result = parser.parse("User-agent: *\nAllow: /".getBytes(StandardCharsets.UTF_8));
		} finally {
			matchers.put(basePath, result);
		}
		return result;
	}

	@Override
	public Boolean canAccessUrl(URL url, String userAgent) {
		Boolean result = null;
		String basePath = UrlUtils.getBasePath(url);
		if (matchers.containsKey(basePath)) {
			result = matchers.get(basePath).singleAgentAllowedByRobots(userAgent, url.toString());
			int a = 0;
		} else {
			logger.info(String.format("No matcher registered for url %s. Trying to retrieve...", basePath));
			result = loadRepForDomain(url).singleAgentAllowedByRobots(userAgent, url.toString());
		}
		return result;
	}
}
