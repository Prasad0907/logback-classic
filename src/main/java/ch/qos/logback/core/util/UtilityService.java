package ch.qos.logback.core.util;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.model.Event;
import ch.qos.logback.core.model.State;
import ch.qos.logback.core.model.dao.EventData;
import ch.qos.logback.core.repo.EventDataRepositiry;

@Component
public class UtilityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilityService.class);

	@Autowired
	private EventDataRepositiry repository;

	public void persistEventData(Collection<EventData> eventsData) {
		LOGGER.debug("Persisting {} eventData...", eventsData.size());
		repository.saveAll(eventsData);
	}

	public long getEventExecutionTime(Event event1, Event event2) {
		Event endEvent = Stream.of(event1, event2).filter(e -> State.FINISHED.equals(e.getState())).findFirst()
				.orElse(null);
		Event startEvent = Stream.of(event1, event2).filter(e -> State.STARTED.equals(e.getState())).findFirst()
				.orElse(null);

		return Objects.requireNonNull(endEvent).getTimestamp() - Objects.requireNonNull(startEvent).getTimestamp();
	}

}
