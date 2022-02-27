package ch.qos.logback.core.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import antlr.collections.List;
import ch.qos.logback.core.model.dao.EventData;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventDataRepositiryTest {

	@Autowired
	private EventDataRepositiry repository;

	@Test
	public void findByIDTest() {
		EventData event = new EventData();
		event.setId("1");
		event.setType("Application_Log");
		event.setFlag(true);
		event.setHost("host");
		event.setDuration(5);
		repository.save(event);
		assertThat(repository.findById("1")).isNotEmpty();
	}

	@Test
	public void findAllTest() {
		EventData event1 = new EventData();
		event1.setId("2");
		event1.setType("Application_Log");
		event1.setFlag(true);
		event1.setHost("host");
		event1.setDuration(5);
		repository.save(event1);

		EventData event2 = new EventData();
		event2.setId("3");
		event2.setType("Application_Log");
		event2.setFlag(true);
		event2.setHost("host");
		event2.setDuration(5);
		repository.save(event2);

		assertThat(repository.findAll()).hasSize(2);
	}

}
