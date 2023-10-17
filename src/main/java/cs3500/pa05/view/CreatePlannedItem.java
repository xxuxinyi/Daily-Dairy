package cs3500.pa05.view;

import cs3500.pa05.controller.ControllerImp;
import cs3500.pa05.model.Weekday;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 * class CreatePlannedItem
 */
public abstract class CreatePlannedItem extends Popup {
  protected TextField nameFile = new TextField();
  protected Button save = new Button("save");
  protected VBox vbox = new VBox();
  protected HBox name = new HBox();
  protected Weekday weekday;
  protected ControllerImp controller;
  protected Text board = new Text();

  protected MenuButton categories = new MenuButton("Categories");
  protected String category = "";

  /**
   * constructor for CreatePlannedItem
   *
   * @param controller the controller
   * @param weekday which weekday the day is
   * @param categories the category of the task being created
   */
  public CreatePlannedItem(ControllerImp controller, Weekday weekday, List<String> categories) {
    this.controller = controller;
    this.weekday = weekday;
    this.setHeight(400);
    this.setWidth(400);
    this.getContent().add(vbox);
    vbox.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-border-width:2;"
        + "-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;");
    this.name.getChildren().add(new Text("Name: "));
    this.name.getChildren().add(nameFile);
    this.vbox.getChildren().add(name);
    this.categories.setText("Choose Category");
    for (String c : categories) {
      MenuItem item = new MenuItem(c);
      this.categories.getItems().add(item);
      item.setOnAction(e -> this.category = item.getText());
    }
    this.vbox.getChildren().add(this.categories);
    this.vbox.getChildren().add(board);
  }
}