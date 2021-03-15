package com.ggar.webcrawler.core.logic;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BasicBlacklistService extends AbstractBlacklistService<URL> {

	public BasicBlacklistService() {
		this(new ArrayList<>());
	}

	public BasicBlacklistService(ArrayList<Pattern> patterns) {
		super(patterns);
	}

	@Override
	public boolean isValid(URL item) {
		return !patterns.stream().anyMatch(pattern -> pattern.matcher(item.toString()).matches());
	}
}
