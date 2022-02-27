package ch.qos.logback.core.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.model.dao.EventData;

@Repository
public interface EventDataRepositiry extends CrudRepository<EventData, String>{

}
