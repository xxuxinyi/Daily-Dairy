package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * class WeekTest
 */
class WeekTest {

  private Week week;
  private Task task1;
  private Task task2;
  private Event event1;
  private Event event2;

  /**
   * set up for WeekTest
   */
  @BeforeEach
  void setUp() {
    this.week = new Week("UNTITLED", new Day[] {
        new Day(Weekday.SUN),
        new Day(Weekday.MON),
        new Day(Weekday.TUE),
        new Day(Weekday.WED),
        new Day(Weekday.THU),
        new Day(Weekday.FRI),
        new Day(Weekday.SAT)});
    this.event1 = new Event("Meeting", 60, LocalTime.of(9, 21));
    this.event2 = new Event("Conference", 90, LocalTime.of(13, 30));
    this.task1 = new Task("Homework");
    this.task2 = new Task("Assignment");
  }

  /**
   * testConstructor and getters
   */
  @Test
  void testConstructor() {
    assertNotNull(week.getCategories());
    assertEquals(Theme.DAYTIME, week.getTheme());
  }

  /**
   * testSetMax
   */
  @Test
  void testSetMax() {
    assertTrue(this.week.setMax(1, 0));
    assertFalse(this.week.addTask(Weekday.FRI, this.task1));
    assertTrue(this.week.setMax(1, 1));
    assertTrue(this.week.addTask(Weekday.FRI, this.task2));
    assertTrue(this.week.setMax(0, 1));
    assertFalse(this.week.addEvent(Weekday.FRI, this.event1));
    assertTrue(this.week.setMax(1, 1));
    assertTrue(this.week.addEvent(Weekday.FRI, this.event2));
  }

  /**
   * testSetTheme
   */
  @Test
  void testSetTheme() {
    week.setTheme(Theme.NIGHTTIME);
    assertEquals(Theme.NIGHTTIME, week.getTheme());
  }

  /**
   * testAddTask
   */
  @Test
  void testAddTask() {
    Weekday day = Weekday.MON;
    Task task = new Task("Task 1");
    assertTrue(week.addTask(day, task));
  }

  /**
   * testAddEvent
   */
  @Test
  void testAddEvent() {
    Weekday day = Weekday.MON;

    Event event = new Event("Event 1", 60, LocalTime.of(9, 0));
    assertTrue(week.addEvent(day, event));
  }

  /**
   * testToJson
   */
  @Test
  void testToJson() {
    week.addCategory("Work");
    week.setDate(LocalDate.of(2022, 11, 10));
    assertEquals(week.toJson().toString(), "WeekJson[events=5, tasks=5, name=UNTITLED, "
        +  "theme=DAYTIME, days=[DayJson[day=SUN, date=2022-11-10, events=[], tasks=[]], "
        +  "DayJson[day=MON, "
        +  "date=2022-11-10, events=[], tasks=[]], DayJson[day=TUE, date=2022-11-10, events=[],"
        +  " tasks=[]],"
        +  " DayJson[day=WED, date=2022-11-10, events=[], tasks=[]], DayJson[day=THU, "
        +  "date=2022-11-10,"
        +  " events=[], tasks=[]], DayJson[day=FRI, date=2022-11-10, events=[], tasks=[]], "
        +  "DayJso"
        +  "n[day=SAT, date=2022-11-10, events=[], tasks=[]]], startDate=2022-11-10, "
        +  "categories=[Work]]");
  }

  /**
   * testDeleteTask
   */
  @Test
  void testDeleteTask() {
    Weekday day = Weekday.MON;
    Task task = new Task("Task 1");
    week.addTask(day, task);
    assertFalse(week.setMax(1, 0));
    week.deleteTask(task, day);
    assertTrue(week.setMax(1, 1));
  }

  /**
   * testDeleteEvent
   */
  @Test
  void testDeleteEvent() {
    Weekday day = Weekday.MON;
    Event event = new Event("Event 1", 60, LocalTime.of(9, 0));
    week.addEvent(day, event);
    assertFalse(week.setMax(0, 1));
    week.deleteEvent(event, day);
    assertTrue(week.setMax(1, 1));
  }

  /**
   * testAddCategory
   */
  @Test
  void testAddCategory() {
    String category = "Work";
    week.addCategory(category);
    assertTrue(week.getCategories().contains(category));
  }

  /**
   * testSetDate
   */
  @Test
  void testSetDate() {
    LocalDate date = LocalDate.of(2023, 6, 1);
    week.setDate(date);
    assertEquals(week.toJson().startDate(), date.toString());
  }
}