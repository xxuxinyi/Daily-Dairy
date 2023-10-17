package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * class PlannedItemTest
 */
class PlannedItemTest {

  private PlannedItem task;
  private String taskName;

  /**
   * set up for PlannedItemTest
   */
  @BeforeEach
  void setUp() {
    taskName = "Task";
    task = new Task(taskName);
  }

  /**
   * testConstructor and getters
   */
  @Test
  void testConstructor() {
    assertEquals(taskName, task.getName());
    assertEquals("", task.getDescription());
    assertEquals("", task.getCategory());
  }

  /**
   * testSetDescription
   */
  @Test
  void testSetDescription() {
    String description = "This is an task";
    task.setDescription(description);
    assertEquals(description, task.getDescription());
  }

  /**
   * testSetCategory
   */
  @Test
  void testSetCategory() {
    String category = "Category";
    task.setCategory(category);
    assertEquals(category, task.getCategory());
  }

  /**
   * testInCategory
   */
  @Test
  void testInCategory() {
    String category = "Category";
    task.setCategory(category);
    assertTrue(task.inCategory(category));
    assertFalse(task.inCategory("Other Category"));
  }

  /**
   * testCompareName
   */
  @Test
  void testCompareName() {
    PlannedItem task1 = new Task("a");
    PlannedItem task2 = new Task("ab");
    assertEquals(task1.compareName(task2), 1);
    assertEquals(task2.compareName(task1), -1);
    assertEquals(0, task1.compareName(task1));
  }
}