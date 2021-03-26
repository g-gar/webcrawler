package com.ggar.webcrawler.core.definitions.blacklist;

import java.util.regex.Pattern;

public interface BlacklistService<T> {

	BlacklistService add(Pattern pattern);
	boolean isValid(T item);

}
