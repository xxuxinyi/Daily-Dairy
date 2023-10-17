package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import cs3500.pa05.model.json.EventJson;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * class EventTest
 */
class EventTest {

  private String eventName;
  private int duration;
  private LocalTime startTime;
  private Event event;

  /**
   * set up for EventTest
   */
  @BeforeEach
  void setUp() {
    eventName = "Meeting";
    duration = 60;
    startTime = LocalTime.of(9, 0);
    event = new Event(eventName, duration, startTime);
  }

  /**
   * testConstructor and getters
   */
  @Test
  void testConstructor() {
    assertEquals(eventName, event.getName());
    assertEquals(duration, event.getDuration());
    assertEquals(startTime, event.getStartTime());
  }

  /**
   * testToJson
   */
  @Test
  void testToJson() {
    Event event = new Event("Meeting", 60, LocalTime.of(9, 0));
    event.setDescription("Discuss project updates");
    event.setCategory("Work");
    EventJson eventJson = event.toJson();
    assertNotNull(eventJson);
    assertEquals(event.getName(), eventJson.name());
    assertEquals(event.getDescription(), eventJson.description());
    assertEquals(event.getCategory(), eventJson.category());
    assertEquals(event.getStartTime().toString(), eventJson.startTime());
    assertEquals(event.getDuration(), eventJson.duration());
  }
}