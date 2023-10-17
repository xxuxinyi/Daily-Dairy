package cs3500.pa05.model;

import cs3500.pa05.model.json.DayJson;
import cs3500.pa05.model.json.WeekJson;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * class Week
 */
public class Week {
  private String name;
  private final List<String> categories;
  private final Day[] days;

  private Theme theme;
  private int maxEvents;
  private int maxTask;
  private LocalDate startDate;

  /**
   * constructor for Week
   *
   * @param name week name
   * @param days week day
   */
  public Week(String name, Day[] days) {
    this.name = name;
    this.days = days;
    this.theme = Theme.DAYTIME;
    this.maxTask = 5;
    this.maxEvents = 5;
    this.categories = new LinkedList<>();
    this.startDate = LocalDate.now();
    this.setMax(this.maxTask, this.maxEvents);
    this.setDate(this.startDate);
  }

  /**
   * Sets the maximum number of tasks and events for each day in the week.
   *
   * @param maxEvents The maximum number of events per day.
   * @param maxTask The maximum number of tasks per day.
   * @return Returns true if the operation was successful for all days, false otherwise.
   */
  public boolean setMax(int maxEvents, int maxTask) {
    for (Day d : this.days) {
      if (!(d.setMaxEvent(maxEvents)
          && d.setMaxTask(maxTask))) {
        return false;
      }
    }
    this.maxEvents = maxEvents;
    this.maxTask = maxTask;
    return true;
  }

  /**
   * Retrieves the theme of the week.
   *
   * @return the current theme.
   */
  public Theme getTheme() {
    return this.theme;
  }

  /**
   * Sets the theme for the week.
   *
   * @param theme the theme to be set.
   */
  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  /**
   * Adds a task to the specified day.
   *
   * @param day the day to add the task.
   * @param task the task to be added.
   * @return true if the task is successfully added; false otherwise.
   */
  public boolean addTask(Weekday day, Task task) {
    for (Day d : days) {
      if (d.addTask(task, day)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Adds an event to the specified day.
   *
   * @param day the day to add the event.
   * @param event the event to be added.
   * @return true if the event is successfully added; false otherwise.
   */
  public boolean addEvent(Weekday day, Event event) {
    for (Day d : days) {
      if (d.addEvent(event, day)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Converts this Week object to its JSON representation.
   *
   * @return The WeekJson representation of this Week object.
   */
  public WeekJson toJson() {
    ArrayList<DayJson> days = new ArrayList<>();
    for (Day d : this.days) {
      days.add(d.toJson());
    }
    return new WeekJson(this.maxEvents, this.maxTask, this.name, this.theme, days,
        this.startDate.toString(), this.categories);
  }

  /**
   * Deletes a task from a specific day of the week.
   *
   * @param t The task to be deleted.
   * @param day The day from which to delete the task.
   */
  public void deleteTask(Task t, Weekday day) {
    for (Day d : days) {
      d.deleteTask(t, day);
    }
  }

  /**
   * Deletes an event from the specified day.
   *
   * @param e the event to be deleted.
   * @param day the day from which to delete the event.
   */
  public void deleteEvent(Event e, Weekday day) {
    for (Day d : days) {
      d.deleteEvent(e, day);
    }
  }

  /**
   * Adds a new category.
   *
   * @param category the category to be added.
   */
  public void addCategory(String category) {
    this.categories.add(category);
  }

  /**
   * Sets the start date of the week.
   *
   * @param date the start date to set.
   */
  public void setDate(LocalDate date) {
    this.startDate = date;
    for (Day day : this.days) {
      day.setDate(date);
    }
  }

  /**
   * Retrieves the list of categories.
   *
   * @return the list of categories.
   */
  public List<String> getCategories() {
    return this.categories;
  }

  /**
   * Changes the name of the week.
   *
   * @param name the new name to set.
   */
  public void changeName(String name) {
    this.name = name;
  }

  /**
   * Sets the name of the week.
   *
   * @param name the name to set.
   */
  public void setName(String name) {
    this.name = name;
  }
}
