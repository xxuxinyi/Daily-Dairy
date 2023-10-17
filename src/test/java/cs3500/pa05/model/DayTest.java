package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.json.DayJson;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * class DayTest
 */
class DayTest {

  private Day day;

  /**
   * set up for DayTest
   */
  @BeforeEach
  void setUp() {
    day = new Day(Weekday.MON);
    this.day.setMaxTask(2);
    this.day.setMaxEvent(2);
  }

  /**
   * testSetDate
   */
  @Test
  void testSetDate() {
    LocalDate date = LocalDate.of(2013, 4, 15);
    day.setDate(date);
    assertEquals(date.toString(), day.toJson().date());
  }

  /**
   * testSetMaxEvent
   */
  @Test
  void testSetMaxEvent() {
    assertTrue(day.setMaxEvent(2));
    assertTrue(day.setMaxEvent(5));
    assertTrue(day.setMaxEvent(0));
  }

  /**
   * testSetMaxTask
   */
  @Test
  void testSetMaxTask() {
    assertTrue(day.setMaxTask(4));
    assertTrue(day.setMaxTask(7));
    assertTrue(day.setMaxTask(0));
  }

  /**
   * testAddEvent
   */
  @Test
  void testAddEvent() {
    Event event = new Event("Orientation", 60, LocalTime.of(9, 0));
    assertTrue(day.addEvent(event, Weekday.MON));  // Max events: 3, Weekday: MON
    assertFalse(day.addEvent(event, Weekday.TUE)); // Weekday: TUE
    assertFalse(day.addEvent(event, Weekday.WED)); // Weekday: WED
  }

  /**
   * testAddTask
   */
  @Test
  void testAddTask() {
    Task task = new Task("Homework");
    assertTrue(day.addTask(task, Weekday.MON));  // Max tasks: 5, Weekday: MON
    assertFalse(day.addTask(task, Weekday.TUE)); // Weekday: TUE
    assertTrue(day.addTask(task, Weekday.MON));  // Max tasks: 5, Weekday: MON
    assertFalse(day.addTask(task, Weekday.MON)); // Weekday: WED
  }

  /**
   * testDeleteEvent
   */
  @Test
  void testDeleteEvent() {
    Event event1 = new Event("Meeting", 60, LocalTime.of(9, 0));
    Event event2 = new Event("Conference", 90, LocalTime.of(13, 30));
    day.addEvent(event1, Weekday.MON);
    day.addEvent(event2, Weekday.MON);
    assertFalse(day.setMaxEvent(1));
    day.deleteEvent(event1, Weekday.MON);
    assertTrue(day.setMaxEvent(1));
  }

  /**
   * testDeleteTask
   */
  @Test
  void testDeleteTask() {
    Task task1 = new Task("Homework");
    Task task2 = new Task("Assignment");
    day.addTask(task1, Weekday.MON);
    day.addTask(task2, Weekday.MON);
    assertFalse(day.setMaxTask(1));
    day.deleteTask(task1, Weekday.MON);
    assertTrue(day.setMaxEvent(1));
  }

  /**
   * testToJson
   */
  @Test
  void testToJson() {
    Event event1 = new Event("Meeting", 60, LocalTime.of(9, 0));
    Event event2 = new Event("Conference", 90, LocalTime.of(13, 30));
    Task task1 = new Task("Homework");
    Task task2 = new Task("Assignment");
    day.addEvent(event1, Weekday.MON);
    day.addEvent(event2, Weekday.MON);
    day.addTask(task1, Weekday.MON);
    day.addTask(task2, Weekday.MON);
    DayJson dayJson = day.toJson();
    assertEquals(dayJson.toString(), "DayJson[day=MON, date=0001-01-01, "
        +  "events=[EventJson[name=Meeting, description=, category=, "
        +  "startTime=09:00, duration=60], EventJson[name=Conference, "
        +  "description=, category=, startTime=13:30, duration=90]], "
        +  "tasks=[TaskJson[name=Homework, description=, category=, complete=false],"
        +  " TaskJson[name=Assignment, description=, category=, complete=false]]]");
  }
}