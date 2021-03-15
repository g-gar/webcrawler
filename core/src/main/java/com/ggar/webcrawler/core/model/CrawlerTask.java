package com.ggar.webcrawler.core.model;

import java.net.URL;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.Function;

public interface CrawlerTask extends Function<URL, Collection<URL>> {



}
