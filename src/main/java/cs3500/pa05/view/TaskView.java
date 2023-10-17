package cs3500.pa05.view;

import cs3500.pa05.controller.ControllerImp;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Theme;
import cs3500.pa05.model.json.TaskJson;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * class TaskView
 */
public class TaskView extends PlannedItemView {

  /**
   * constructor for TaskView
   *
   * @param task          task
   * @param controllerImp imp
   */
  public TaskView(Task task, ControllerImp controllerImp) {
    super(task, controllerImp);
    TaskJson json = task.toJson();
    final String description = json.description();
    HBox hbox = new HBox();
    this.changeTheme(theme);
    hbox.getChildren().add(icon);
    this.label.setFont(new Font(18));
    hbox.getChildren().add(label);
    this.getChildren().add(hbox);
    boolean complete = json.complete();
    String category = json.category();
    this.getChildren().add(new Text("Completion Status: " + complete));
    this.getChildren().add(new Text("Description : " + description));
    this.getChildren().add(new Text("Category : " + category));
    this.edit.setOnAction(e -> controllerImp.editTask(this, json));
    this.getChildren().add(edit);
    this.detail.setOnAction(e -> this.controllerImp.miniViewerTask(this, task));
    this.getChildren().add(detail);
  }

  /**
   * Changes the icon of the task view based on the specified theme.
   *
   * @param theme the theme to be applied to the task view.
   */
  public void changeTheme(Theme theme) {
    switch (theme) {
      case NIGHTTIME -> icon.setImage(new Image("nightTime/nightTimeTask.png"));
      case HOLIDAY -> icon.setImage(new Image("holiday/holidayTask.png"));
      default -> icon.setImage(new Image("default/dayTimeTask.png"));
    }
  }
}
