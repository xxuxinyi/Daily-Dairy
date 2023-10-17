package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents a Journal GUI view with basic elements.
 */
public class ViewLoader implements View {
  FXMLLoader loader;

  /**
   * constructor for ViewLoader
   *
   * @param controller the controller
   * @param fxml the fxml file name
   */
  public ViewLoader(Controller controller, String fxml) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource(fxml));
    this.loader.setController(controller);
  }

  /**
   * Loads the view and returns it as a JavaFX Scene object.
   *
   * @return the loaded Scene object representing the view.
   * @throws IllegalStateException if an error occurs while loading the layout.
   */
  @Override
  public Scene load()  {
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout." + exc.getCause()
          + exc.getMessage());
    }
  }
}