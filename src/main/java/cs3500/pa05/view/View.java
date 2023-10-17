package cs3500.pa05.view;

import javafx.scene.Scene;

/**
 * The View interface represents a graphical view in the Daily Diary application.
 * It defines the contract for loading the view as a JavaFX Scene.
 */
public interface View {

  /**
   * Loads the view and returns it as a JavaFX Scene object.
   *
   * @return the loaded Scene object representing the view.
   */
  Scene load();
}