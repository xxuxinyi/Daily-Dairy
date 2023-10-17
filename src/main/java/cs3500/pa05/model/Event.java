package cs3500.pa05.model;

import cs3500.pa05.model.json.EventJson;
import java.time.LocalTime;

/**
 * class Event
 */
public class Event extends PlannedItem {

  private final LocalTime startTime;
  private final int duration;


  /**
   * Constructs an instance of an Event with a name, start time, and duration.
   *
   * @param name The name of the event.
   * @param duration The duration of the event.
   * @param startTime The start time of the event.
   */
  public Event(String name, int duration, LocalTime startTime) {
    super(name);
    this.duration = duration;
    this.startTime = startTime;
  }

  /**
   * Returns the start time of the event.
   *
   * @return The start time of the event.
   */
  public LocalTime getStartTime() {
    return startTime;
  }

  /**
   * Returns the duration of the event.
   *
   * @return The duration of the event.
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Returns a JSON representation of the Event object.
   *
   * @return A EventJson object representing the Event.
   */
  public EventJson toJson() {
    return new EventJson(this.name, this.description.toString(), this.category,
        this.startTime.toString(),
        this.duration);
  }
}
