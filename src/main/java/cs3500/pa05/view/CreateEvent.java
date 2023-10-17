package cs3500.pa05.view;

import cs3500.pa05.controller.ControllerImp;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.model.json.EventJson;
import java.time.LocalTime;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * class CreateEvent
 */
public class CreateEvent extends CreatePlannedItem {
  private final TextArea descriptionContent = new TextArea();
  private final TextField hour = new TextField();
  private final TextField minute = new TextField();
  private final TextField durationTime = new TextField();
  private EventView view;
  private Event event;

  /**
   * constructor for CreateEvent
   *
   * @param controller the controller
   * @param weekday    which weekday to create event
   * @param categories the category of the event being created
   */
  public CreateEvent(ControllerImp controller, Weekday weekday, List<String> categories) {
    super(controller, weekday, categories);
    HBox description = new HBox();
    description.getChildren().add(new Text("Description: "));
    descriptionContent.setPromptText("Optional");
    description.getChildren().add(descriptionContent);
    hour.setAccessibleRoleDescription("hour");
    hour.setTextFormatter(this.limitInput());
    minute.setAccessibleRoleDescription("minute");
    minute.setTextFormatter(this.limitInput());
    HBox startTime = new HBox();
    startTime.getChildren().add(new Text("Start Time : "));
    startTime.getChildren().add(hour);
    startTime.getChildren().add(minute);
    HBox duration = new HBox();
    duration.getChildren().add(new Text("Duration: "));
    this.durationTime.setPromptText("In Minutes");
    duration.getChildren().add(durationTime);
    this.vbox.getChildren().add(duration);
    this.vbox.getChildren().add(startTime);
    this.vbox.getChildren().add(description);
    this.save.setOnAction(e -> create());
    this.vbox.getChildren().add(save);
  }

  /**
   * This method gets called when the save button is clicked.
   * It creates an event based on the user input and adds it to the model via the controller.
   */
  private void create() {
    if (this.createHelper()) {
      this.controller.addEvent(event, this.weekday, this.view);
    }
  }

  /**
   * A helper method that is used by the create() method.
   * It handles the actual creation of the Event object and validates the user input.
   *
   * @return true if the event is successfully created; false otherwise.
   */
  private boolean createHelper() {
    try {
      LocalTime time = LocalTime.of(Integer.parseInt(hour.getText()),
          Integer.parseInt(minute.getText()));
      int duration1 = Integer.parseInt(durationTime.getText());
      String name = this.nameFile.getText();
      if (name.length() == 0) {
        nameFile.setBorder(Border.stroke(Paint.valueOf("RED")));
        board.setText("Name cannot be empty");
      } else {
        this.event = new Event(name, duration1, time);
        event.setDescription(descriptionContent.getText());
        event.setCategory(this.category);
        this.view = new EventView(this.event, controller);
        this.hide();
        return true;
      }
    } catch (Exception e) {
      hour.setBorder(Border.stroke(Paint.valueOf("RED")));
      minute.setBorder(Border.stroke(Paint.valueOf("RED")));
      board.setText("Invalid start time");
      return false;
    }
    return false;
  }

  /**
   * @return the text formatter that only allow number with two digit
   */
  private TextFormatter<String> limitInput() {
    return new TextFormatter<>((TextFormatter.Change change) -> {
      String newText = change.getControlNewText();
      if (newText.length() > 2) {
        return null;
      } else {
        try {
          Integer.parseInt(newText);
          return change;
        } catch (Exception e) {
          return null;
        }
      }
    });
  }

  /**
   * This method allows the user to edit an existing event.
   * It updates the CreateEvent interface with the details of the event to be edited.
   *
   * @param json the JSON representation of the event to be edited.
   * @param original the EventView object representing the event to be edited.
   * @param old is the old event
   * @return the updated CreateEvent object.
   */
  public CreateEvent edit(EventJson json, EventView original, Event old) {
    this.descriptionContent.setText(json.description());
    this.nameFile.setText(json.name());
    this.category = json.category();
    LocalTime startTime = LocalTime.parse(json.startTime());
    this.durationTime.setText(String.valueOf(json.duration()));
    this.hour.setText(String.valueOf(startTime.getHour()));
    this.minute.setText(String.valueOf(startTime.getMinute()));
    this.vbox.getChildren().remove(save);
    this.save = new Button("save");
    this.save.setOnAction(e -> {
      if (createHelper()) {
        this.controller.replaceEvent(this.view, original, this.event, old);
      }
    });
    this.vbox.getChildren().add(save);
    return this;
  }
}