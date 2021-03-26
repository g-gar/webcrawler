package com.ggar.webcrawler.core.definitions.crawler;

import java.net.URL;
import java.util.Collection;
import java.util.function.Function;

public interface CrawlerTask extends Function<URL, Collection<URL>> {



}
