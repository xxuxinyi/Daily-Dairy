package cs3500.pa05.view;

import cs3500.pa05.controller.ControllerImp;
import cs3500.pa05.model.PlannedItem;
import cs3500.pa05.model.Theme;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * class PlannedItemView
 */
public abstract class PlannedItemView extends VBox {
  private final String name;

  /**
   * the theme for this PlannedItemView
   */
  protected Theme theme;
  /**
   * the label for this PlannedItemView
   */
  protected Label label;
  /**
   * the icon for this PlannedItemView
   */
  protected ImageView icon = new ImageView();
  /**
   * the edit button for this PlannedItemView
   */
  protected Button edit = new Button("edit");
  /**
   * the detail button for this PlannedItemView
   */
  protected Button detail = new Button("detail");
  /**
   * the controller for this view
   */
  protected ControllerImp controllerImp;
  /**
   * the duration for this event, can be 0
   */
  protected int duration;

  /**
   * Constructs a new PlannedItemView object with the specified planned item and controller.
   *
   * @param item          the planned item to be displayed.
   * @param controllerImp the controller object responsible for handling user interactions.
   */
  PlannedItemView(PlannedItem item, ControllerImp controllerImp) {
    this.controllerImp = controllerImp;
    this.duration = 0;
    this.theme = controllerImp.getTheme();
    this.setBorder(Border.stroke(Color.BLACK));
    this.name = item.getName();
    this.label = new Label(name);
    this.label.setFont(new Font(18));
    this.label.setStyle("-fx-text-fill: black");
  }

  /**
   * Changes the icon of the planned item view based on the specified theme.
   *
   * @param theme the theme to be applied to the planned item view.
   */
  public abstract void changeTheme(Theme theme);

  /**
   * Compares the name of the current planned item view with another planned item view.
   *
   * @param view the other planned item view to compare with.
   * @return a negative integer, zero, or a positive integer as the name
   *         the current view is less than, equal to, or greater than the name of the other view.
   */
  public int compareByName(PlannedItemView view) {
    return this.name.compareTo(view.name);
  }

  /**
   * Compares the duration of the current planned item view with another planned item view.
   *
   * @param view2 the other planned item view to compare with.
   * @return a negative integer, zero, or a positive integer
   */
  public int compareByDuration(PlannedItemView view2) {
    return Integer.compare(this.duration, view2.duration);
  }

  /**
   * Returns the name of the planned item view.
   *
   * @return the name of the planned item view.
   */
  public String getName() {
    return this.name;
  }
}
