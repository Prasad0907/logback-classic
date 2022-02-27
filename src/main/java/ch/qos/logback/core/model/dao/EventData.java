package ch.qos.logback.core.model.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.qos.logback.core.model.Event;

@Entity
@Table(name = "EVENT_DATA")
public class EventData {
	@Id
	@JsonProperty("id")
	private String id;

	@JsonProperty("duration")
	private int duration;

	@JsonProperty("type")
	private String type;

	@JsonProperty("host")
	private String host;

	@JsonProperty("flag")
	private Boolean flag;

	public EventData() {
	}

	public EventData(Event event, int duration) {
		this.id = event.getId();
		this.type = event.getType();
		this.host = event.getHost();
		this.duration = duration;
		this.flag = Boolean.FALSE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean alert) {
		this.flag = alert;
	}

	public boolean equals(EventData eventData) {
		return this.getId().equals(eventData.getId());
	}

}
