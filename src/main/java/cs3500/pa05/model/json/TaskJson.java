package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration taskJson
 *
 * @param name        the name of task
 * @param description the description of task
 * @param complete      the task is complete or incomplete
 */
public record TaskJson(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("category") String category,
    @JsonProperty("complete") Boolean complete) {
}
