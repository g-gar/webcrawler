package com.ggar.webcrawler.core.logic;

import com.ggar.webcrawler.core.service.http.HttpServiceMonitor;
import com.ggar.webcrawler.core.service.http.HttpServiceEvent;

import java.util.*;
import java.util.stream.Collectors;

public class BasicHttpServiceMonitor implements HttpServiceMonitor {

	private final Map<Long, HttpServiceEvent> events;

	public BasicHttpServiceMonitor() {
		this.events = Collections.synchronizedMap(new HashMap<>());
	}

	@Override
	public HttpServiceEvent registerEvent(HttpServiceEvent event) {
		this.events.put(event.getTimestamp(), event);
		return this.events.get(event.getTimestamp());
	}

	@Override
	public Collection<HttpServiceEvent> getRegisteredEvents() {
		return this.getRegisteredEvents(0L, Long.MAX_VALUE);
	}

	@Override
	public Collection<HttpServiceEvent> getRegisteredEvents(Long start, Long end) {
		return events.keySet().stream()
			.filter(timestamp -> timestamp >= start && timestamp <= end)
			.map(events::get)
			.collect(Collectors.toList());
	}
}
