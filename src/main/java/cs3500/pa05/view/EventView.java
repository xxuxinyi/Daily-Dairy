package cs3500.pa05.view;

import cs3500.pa05.controller.ControllerImp;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Theme;
import cs3500.pa05.model.json.EventJson;
import java.time.LocalTime;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * class EventView
 */
public class EventView extends PlannedItemView {

  /**
   * constructor for EventView
   *
   * @param event         event
   * @param controllerImp imp
   */
  public EventView(Event event, ControllerImp controllerImp) {
    super(event, controllerImp);
    EventJson json = event.toJson();
    duration = json.duration();
    final LocalTime startTime = LocalTime.parse(json.startTime());
    final String description = json.description();
    HBox hbox = new HBox();
    this.changeTheme(theme);
    hbox.getChildren().add(icon);
    this.label.setFont(new Font(18));
    hbox.getChildren().add(label);
    this.getChildren().add(hbox);
    this.getChildren().add(new Text("Duration time : " + duration));
    this.getChildren().add(new Text("startTime : " + startTime));
    this.getChildren().add(new Text("Description : " + description));
    String category = json.category();
    this.getChildren().add(new Text("Category : " + category));
    this.edit.setOnAction(e -> controllerImp.editEvent(this, json));
    this.getChildren().add(edit);
    this.detail.setOnAction(e -> this.controllerImp.miniViewerEvent(this, event));
    this.getChildren().add(detail);
  }

  /**
   * Returns the duration of the event.
   *
   * @return the duration of the event.
   */
  public int getDuration() {
    return
        duration;
  }

  /**
   * Changes the theme of the event view by updating the icon image based on the specified theme.
   *
   * @param theme the theme to be applied to the event view.
   */
  public void changeTheme(Theme theme) {
    switch (theme) {
      case NIGHTTIME -> icon.setImage(new Image("nightTime/nightTimeEvent.png"));
      case HOLIDAY -> icon.setImage(new Image("holiday/holidayEvent.png"));
      default -> icon.setImage(new Image("default/dayTimeEvent.png"));
    }
  }
}
