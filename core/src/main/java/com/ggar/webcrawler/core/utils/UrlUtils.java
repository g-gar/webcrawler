package com.ggar.webcrawler.core.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class UrlUtils {

	public static String getBasePath(URL url) {
		return String.format(url.getPort() > -1 ? "%s://%s:%s" : "%s://%s", url.getProtocol(), url.getHost(), url.getPort());
	}

	public static Boolean isAbsolute(String url) {
		return Pattern.compile("^https?:\\/\\/", Pattern.CASE_INSENSITIVE).matcher(url).matches();
	}

	public static URL resolve(URL base, String relativePath) {
		URL result = null;
		try {
			URL baseUrl = new URL(UrlUtils.getBasePath(base));
			result = new URL(baseUrl, relativePath);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
