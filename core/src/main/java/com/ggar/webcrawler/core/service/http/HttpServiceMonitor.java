package com.ggar.webcrawler.core.service.http;

import java.util.Collection;

public interface HttpServiceMonitor {

	HttpServiceEvent registerEvent(HttpServiceEvent event);
	/**
	 * @return All registered events
	 */
	Collection<HttpServiceEvent> getRegisteredEvents();

	/**
	 * @param start Time in milliseconds where to start retrieving registered events. Inclusive.
	 * @param end Time in milliseconds where to start retrieving registered events. Exclusive.
	 * @return A java.util.Collection containing all registered events associated to a certain time window
	 */
	Collection<HttpServiceEvent> getRegisteredEvents(Long start, Long end);

}
