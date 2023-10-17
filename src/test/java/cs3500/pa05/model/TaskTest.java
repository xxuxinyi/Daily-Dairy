package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.json.TaskJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * class TaskTest
 */
class TaskTest {

  private String taskName;
  private Task task;
  private boolean complete;

  /**
   * set up for TaskTest
   */
  @BeforeEach
  void setUp() {
    taskName = "Homework";
    task = new Task(taskName);
    complete = false;
  }

  /**
   * testConstructor
   */
  @Test
  void testConstructor() {
    assertEquals(taskName, task.getName());
    assertFalse(this.complete);
  }

  /**
   * testMarkAsFinished
   */
  @Test
  void testMarkAsFinished() {
    task.markAsFinished();
    assertTrue(task.toJson().complete());
  }

  /**
   * testToJson
   */
  @Test
  void testToJson() {
    task.setDescription("Complete chapter 5 exercises");
    task.setCategory("Study");
    task.markAsFinished();
    TaskJson taskJson = task.toJson();
    assertNotNull(taskJson);
    assertEquals(task.getName(), taskJson.name());
    assertEquals(task.getDescription(), taskJson.description());
    assertEquals(task.getCategory(), taskJson.category());
    assertTrue(taskJson.complete());
  }
}