package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration eventJson
 *
 * @param name        the name of event
 * @param description the description of event
 * @param startTime   the start time of event
 * @param duration    the duration of event
 */
public record EventJson(

    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("category") String category,
    @JsonProperty("start-time") String startTime,
    @JsonProperty("duration") int duration) {

}
