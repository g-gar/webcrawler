package com.ggar.webcrawler.core.definitions.rep;

import com.google.search.robotstxt.Matcher;

import java.net.URL;

/**
 * The Robots Exclusion Protocol (REP) is a standard that enables website owners to control which URLs may be accessed
 * by automated clients (i.e. crawlers) through a simple text file with a specific syntax. It's one of the basic
 * building blocks of the internet as we know it and what allows search engines to operate.
 */
public interface RepService {

	Matcher loadRepForDomain(URL url);
	Boolean canAccessUrl(URL url, String userAgent);

}
