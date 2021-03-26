package com.ggar.webcrawler.core.implementations;

import com.ggar.webcrawler.core.definitions.blacklist.BlacklistService;

import java.util.Collection;
import java.util.regex.Pattern;

public abstract class AbstractBlacklistService<T> implements BlacklistService<T> {

	protected final Collection<Pattern> patterns;

	public AbstractBlacklistService(Collection<Pattern> patterns) {
		this.patterns = patterns;
	}

	@Override
	public BlacklistService add(Pattern pattern) {
		if (!patterns.stream().anyMatch(e -> e.equals(pattern))) {
			patterns.add(pattern);
		}
		return this;
	}

}
