package ch.qos.logback.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {
		@JsonProperty("id")
	    private String id;

	    @JsonProperty("state")
	    private State state;

	    @JsonProperty("type")
	    private String type;

	    @JsonProperty("host")
	    private String host;

	    @JsonProperty("timestamp")
	    private long timestamp;

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public State getState() {
	        return state;
	    }

	    public void setState(State state) {
	        this.state = state;
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

	    public long getTimestamp() {
	        return timestamp;
	    }

	    public void setTimestamp(long timestamp) {
	        this.timestamp = timestamp;
	    }

}
