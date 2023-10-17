package cs3500.pa05.controller;

import cs3500.pa05.view.ViewLoader;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * class mainStage
 */
public class MainStage extends Application {

  /**
   * the start method in mainStage
   *
   * @param stage the primary stage for this application, onto which
   *              the application scene can be set.
   *              Applications may create other stages, if needed, but they will not be
   *              primary stages.
   */
  @Override
  public void start(Stage stage) {
    Controller c1 = new ProxyController(stage);
    ViewLoader view1 = new ViewLoader(c1, "welcomePage.fxml");
    try {
      stage.setScene(view1.load());
      c1.run();
      stage.show();
    } catch (IllegalStateException exc) {
      System.err.println("Unable to load GUI.");
    }
  }
}
