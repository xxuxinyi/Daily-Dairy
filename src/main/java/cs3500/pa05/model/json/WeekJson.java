package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Theme;
import java.util.List;


/**
 * This record represents a JSON object for a week's data.
 *
 * @param events The maximum number of events for the week.
 * @param tasks The maximum number of tasks for the week.
 * @param name The name of the week.
 * @param theme The theme of the week.
 * @param days The list of DayJson objects representing the days in the week.
 * @param startDate The start date of the week.
 * @param categories The list of categories for the week.
 */
public record WeekJson(
    @JsonProperty("maxEvents") Integer events,
    @JsonProperty("maxTask") Integer tasks,
    @JsonProperty("name") String name,
    @JsonProperty("Theme") Theme theme,
    @JsonProperty("Days") List<DayJson> days,
    @JsonProperty("startDate") String startDate,
    @JsonProperty("Categories") List<String> categories) {
}
