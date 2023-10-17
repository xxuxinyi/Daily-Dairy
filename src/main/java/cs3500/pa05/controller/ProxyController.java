package cs3500.pa05.controller;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.model.json.DayJson;
import cs3500.pa05.model.json.EventJson;
import cs3500.pa05.model.json.TaskJson;
import cs3500.pa05.model.json.WeekJson;
import cs3500.pa05.view.EventView;
import cs3500.pa05.view.TaskView;
import cs3500.pa05.view.View;
import cs3500.pa05.view.ViewLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Class controller
 */
public class ProxyController implements Controller {
  private final Week week;
  private final ObjectMapper mapper = new ObjectMapper();
  private final Stage stage;
  private final ControllerImp controller;
  private InputStream in;
  private File file;
  @FXML
  private TextField filePath;
  @FXML
  private TextField filename;
  @FXML
  private Button existing;
  @FXML
  private Button newFile;
  @FXML
  private Pane board;
  @FXML
  private Button template;

  private String name;
  @FXML
  private TextField filePath2;


  /**
   * Constructs a ProxyController instance with the provided Stage.
   * It initializes a new Week and ControllerImp.
   *
   * @param stage The stage where the controller operates.
   */
  public ProxyController(Stage stage) {
    this.week = new Week("UNTITLED", new Day[] {
        new Day(Weekday.SUN),
        new Day(Weekday.MON),
        new Day(Weekday.TUE),
        new Day(Weekday.WED),
        new Day(Weekday.THU),
        new Day(Weekday.FRI),
        new Day(Weekday.SAT)});
    this.controller = new ControllerImp(week, stage, this);
    this.stage = stage;

  }

  /**
   * Writes the provided Week instance data to a file in JSON format.
   *
   * @param week The Week instance to write.
   */
  public void writeFile(Week week) {

    try {
      in.close();
      String jsonString =
          mapper.writerWithDefaultPrettyPrinter().writeValueAsString(week.toJson());
      Files.writeString(this.file.toPath(), jsonString);
      renameFile(name);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Renames the file to a new name.
   *
   * @param newName the new name for the file
   * @throws IllegalArgumentException if the file or new name is invalid
   */
  public void renameFile(String newName) {
    Path oldPath = file.toPath();
    Path newPath = oldPath.resolveSibling(newName + ".bujo");
    try {
      Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      System.err.println("Error renaming file: " + e.getMessage());
    }
  }

  /**
   * Runs the operations for existing or new file buttons.
   */
  @FXML
  public void run() {
    existing.setOnAction(this::createExist);
    newFile.setOnAction(this::createNew);
    template.setOnAction(this::template);
  }

  /**
   * Creates and runs an existing file.
   *
   * @param event The action event that triggered the method.
   */
  private void createExist(ActionEvent event) {
    Path p = Path.of(filePath.getText());
    this.name = String.valueOf(p.getFileName());
    this.file = p.toFile();
    if (this.checkFile()) {
      this.runActual();
    } else {
      board.getChildren().add(new Text("Invalid filepath"));
    }
  }

  /**
   * Creates a new file.
   *
   * @param event The action event that triggered the method.
   */
  private void createNew(ActionEvent event) {
    try {
      Path path = Path.of("src/main/resources/" + filename.getText() + ".bujo");
      this.name = filename.getText();
      this.file = Files.createFile(path).toFile();
      if (this.checkFile()) {
        View view = new ViewLoader(controller, "week.fxml");
        stage.setScene(view.load());
        controller.run();
      }
    } catch (Exception e) {
      board.getChildren().add(new Text("Invalid file name"));
    }
  }

  /**
   * Modifies the week based on the given WeekJson.
   *
   * @param week The WeekJson instance to modify the week.
   */
  public void modifyWeek(WeekJson week) {
    this.week.setTheme(week.theme());
    this.week.setMax(week.tasks(), week.events());
    this.week.setDate(LocalDate.parse(week.startDate()));
    this.controller.setDate(LocalDate.parse(week.startDate()));
    this.controller.setName(week.name());
    if (week.categories() != null) {
      for (String c : week.categories()) {
        this.week.addCategory(c);
      }
    }
    for (int i = 0; i < 7; i++) {
      this.delegateDay(week.days().get(i));
    }
  }

  /**
   * Delegates the tasks and events for the day based on the given DayJson and Theme.
   *
   * @param dayJson The DayJson instance for the day.
   */
  private void delegateDay(DayJson dayJson) {
    ArrayNode listEvent = this.mapper.convertValue(dayJson.events(), ArrayNode.class);
    ArrayNode listTask = this.mapper.convertValue(dayJson.tasks(), ArrayNode.class);
    for (JsonNode j : listEvent) {
      EventJson
          item = this.mapper.convertValue(j, EventJson.class);
      LocalTime start = LocalTime.parse(item.startTime());
      Event event = new Event(item.name(), item.duration(), start);
      event.setDescription(item.description());
      String c = item.category();
      event.setCategory(c);
      this.controller.addEvent(event, dayJson.day(),
          new EventView(event, this.controller));
    }
    for (JsonNode j : listTask) {
      TaskJson
          item = this.mapper.convertValue(j, TaskJson.class);
      Task task = new Task(item.name());
      if (item.complete()) {
        task.markAsFinished();
      }
      String c = item.category();
      task.setCategory(c);
      task.setDescription(item.description());
      this.controller.addTask(task, dayJson.day(),
          new TaskView(task, this.controller));
    }
  }


  /**
   * Runs the operations for an actual (existing or newly created) file.
   */
  private void runActual() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      View v = new ViewLoader(this.controller, "week.fxml");
      Scene s = v.load();
      stage.setScene(s);
      stage.setTitle("Journal");
      stage.fullScreenProperty();
      WeekJson message = parser.readValueAs(WeekJson.class);
      switch (message.theme()) {
        case NIGHTTIME ->
            s.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("nightTime/nightTime.css")).toString());
        case HOLIDAY ->
            s.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("holiday/holiday.css")).toString());
        default ->
            s.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("default/dayTime.css")).toString());
      }
      modifyWeek(message);
      controller.run();
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
      //board.getChildren().add(new Text("Error: " + e.getMessage()));
    }
  }

  /**
   * Checks if the file exists and is readable.
   *
   * @return true if the file exists and is readable, false otherwise.
   */
  private boolean checkFile() {
    try {
      this.in = new FileInputStream(file);
      return true;
    } catch (FileNotFoundException e) {
      board.getChildren().add(new Text("Invalid File please re enter"));
      return false;
    }
  }


  private void template(ActionEvent event) {
    Path p = Path.of(filePath2.getText());
    try {
      Path newFilePath = Path.of("src/main/resources/" + filename.getText() + ".bujo");
      Files.copy(p, newFilePath, StandardCopyOption.REPLACE_EXISTING);
      this.file = newFilePath.toFile();
      if (this.checkFile()) {
        this.inputName();
      } else {
        board.getChildren().add(new Text("Invalid filepath"));
      }
    } catch (IOException e) {
      board.getChildren().add(new Text("Error in copying the file: " + e.getMessage()));
    }
  }


  private void inputName() {
    Popup inputName = new Popup();
    VBox v = new VBox();
    inputName.getContent().add(v);
    Button enter = new Button("Enter");
    TextField text = new TextField();
    enter.setOnAction(e -> {
          inputName.hide();
          name = text.getText();

          this.runTemplate();
        }
    );
    v.getChildren().add(new Text("Please enter a new name for your week:"));
    v.getChildren().add(text);
    v.getChildren().add(enter);
    inputName.show(stage);
  }

  private void runTemplate() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      WeekJson message = parser.readValueAs(WeekJson.class);
      View v = new ViewLoader(this.controller, "week.fxml");
      Scene s = v.load();
      stage.setScene(s);
      switch (message.theme()) {
        case NIGHTTIME ->
            s.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("nightTime/nightTime.css")).toString());
        case HOLIDAY ->
            s.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("holiday/holiday.css")).toString());
        default ->
            s.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("default/dayTime.css")).toString());
      }

      this.week.setTheme(message.theme());
      this.week.setMax(message.tasks(), message.events());
      this.week.setDate(LocalDate.parse(message.startDate()));
      this.controller.setDate(LocalDate.parse(message.startDate()));
      this.controller.setName(this.name);
      if (message.categories() != null) {
        for (String c : message.categories()) {
          this.week.addCategory(c);
        }
      }
      controller.run();
    } catch (IOException e) {
      board.getChildren().add(new Text("Error: " + e.getMessage()));
    }
  }
}

