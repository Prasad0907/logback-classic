package ch.qos.logback.core.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.model.Event;
import ch.qos.logback.core.model.dao.EventData;
import ch.qos.logback.core.util.UtilityService;

@Service
public class LogbackServiceImpl implements LogbackService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogbackServiceImpl.class);

	@Autowired
	UtilityService utility;

	@Value("${app.eventThresholdTimems}")
	private int eventThresholdTimems;

	@Value("${app.commitBatchSize}")
	private int commitBatchSize;

	public void analyse(String filePath) {
		Map<String, Event> eventMap = new HashMap<>();
		Map<String, EventData> eventsData = new HashMap<>();
		File file = null;
		try {
			file = new ClassPathResource("logdirectory/" + filePath).getFile();
			if (!file.exists()) {
				file = new ClassPathResource(filePath).getFile();
				if (!file.exists()) {
					file = new File(filePath);
				}
			}
			if (!file.exists())
				throw new FileNotFoundException("File Not found at " + filePath);
		} catch (IOException e) {
			LOGGER.error("Not able to locate file '{}'", filePath);
		}

		LOGGER.info("Please wait file processing is in progress...");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				Event event;
				try {
					event = new ObjectMapper().readValue(line, Event.class);

					if (eventMap.containsKey(event.getId())) {
						Event e1 = eventMap.get(event.getId());
						long executionTime = utility.getEventExecutionTime(event, e1);

						EventData eventData = new EventData(event, Math.toIntExact(executionTime));

						if (executionTime > eventThresholdTimems) {
							eventData.setFlag(Boolean.TRUE);
							LOGGER.debug("Execution time for the event {} is {} ms", event.getId(), executionTime);
						}
						eventsData.put(event.getId(), eventData);
						eventMap.remove(event.getId());
					} else {
						eventMap.put(event.getId(), event);
					}
				} catch (JsonProcessingException e) {
					LOGGER.error("Unable to parse the event! {}", e.getMessage());
				}

				if (eventsData.size() > commitBatchSize) {
					utility.persistEventData(eventsData.values());
					eventsData = new HashMap<>();
				}
			}
			if (eventsData.size() != 0) {
				utility.persistEventData(eventsData.values());
			}
			LOGGER.info("All done!!! File processing finished...");
		} catch (IOException e) {
			LOGGER.error("Unable to open the file: {}", e.getMessage());
		}

	}

}
