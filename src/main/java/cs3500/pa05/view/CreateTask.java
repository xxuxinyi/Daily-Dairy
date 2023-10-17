package cs3500.pa05.view;

import cs3500.pa05.controller.ControllerImp;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.model.json.TaskJson;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * class CreateTask
 */
public class CreateTask extends CreatePlannedItem {

  private final TextArea descriptionContent = new TextArea();
  private final CheckBox isComplete = new CheckBox("Completion status");
  private boolean complete = false;
  private TaskView view;
  private Task task;

  /**
   * constructor CreateTask
   *
   * @param controller the controller
   * @param weekday    which weekday to create task
   * @param categories which category the task is
   */
  public CreateTask(ControllerImp controller, Weekday weekday, List<String> categories) {
    super(controller, weekday, categories);
    HBox description = new HBox();
    description.getChildren().add(new Text("Description: "));
    descriptionContent.setPromptText("Optional");
    description.getChildren().add(descriptionContent);
    this.vbox.getChildren().add(isComplete);
    this.save.setOnAction(e -> create());
    this.vbox.getChildren().add(save);
    this.vbox.getChildren().add(description);
    this.isComplete.setOnAction(e -> complete = true);
  }

  /**
   * This method gets called when the save button is clicked.
   * It creates an event based on the user input and adds it to the model via the controller.
   */
  private void create() {
    if (this.createHelper()) {
      this.controller.addTask(task, this.weekday, this.view);
    }
  }

  /**
   * A helper method that is used by the create() method.
   * It handles the actual creation of the Event object and validates the user input.
   *
   * @return true if the event is successfully created; false otherwise.
   */
  private boolean createHelper() {
    if (nameFile.getText().length() == 0) {
      nameFile.setBorder(Border.stroke(Paint.valueOf("RED")));
      board.setText("Name cannot be empty");
      return false;
    } else {
      this.task = new Task(nameFile.getText());
      task.setDescription(descriptionContent.getText());
      task.setCategory(this.category);
      if (complete) {
        task.markAsFinished();
      }
      this.view = new TaskView(task, this.controller);
      this.hide();
      return true;
    }
  }

  /**
   * This method allows the user to edit an existing event.
   * It updates the CreateEvent interface with the details of the event to be edited.
   *
   * @param json     the JSON representation of the event to be edited.
   * @param original the EventView object representing the event to be edited.
   * @param old      is the old task
   * @return the updated CreateEvent object.
   */
  public CreateTask edit(TaskJson json, TaskView original, Task old) {
    this.descriptionContent.setText(json.description());
    this.nameFile.setText(json.name());
    this.category = json.category();
    this.isComplete.setSelected(complete);
    this.vbox.getChildren().remove(save);
    this.save = new Button("save");
    this.save.setOnAction(e -> {
      if (createHelper()) {
        this.controller.replaceTask(this.view, original, this.task, old);
      }
    });
    this.vbox.getChildren().add(save);
    return this;
  }
}