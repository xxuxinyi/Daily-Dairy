package cs3500.pa05.model;

import cs3500.pa05.model.json.TaskJson;

/**
 * class Task
 */
public class Task extends PlannedItem {

  private boolean complete;

  /**
   * Constructs a new Task with a specified name.
   * The completion status is initialized as false.
   *
   * @param name The name of the task.
   */
  public Task(String name) {
    super(name);
    this.complete = false;
  }

  /**
   * Marks this task as finished by setting the completion status to true.
   */
  public void markAsFinished() {
    this.complete = true;
  }


  /**
   * Converts this Task to a TaskJson object.
   *
   * @return A new TaskJson object representing this Task.
   */
  public TaskJson toJson() {
    return new TaskJson(name, description.toString(), category, complete);
  }

}
