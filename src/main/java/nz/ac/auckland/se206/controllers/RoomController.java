package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

/** Controller class for the room view. */
public class RoomController {

  @FXML private ImageView backgroundImage;
  @FXML private Rectangle door;
  @FXML private Rectangle table;
  @FXML private Rectangle boots;
  @FXML private Rectangle cupboard;
  @FXML private Button checkTimeBtn;

  private GameState gamestate = GameState.getInstance();

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    // Initialization code goes here
    gamestate.setTimeButton(checkTimeBtn);
    gamestate.setBackgroundImage(backgroundImage);
    gamestate.startCountdown();
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void clickDoor(MouseEvent event) throws IOException {
    System.out.println("door clicked");

    if (!gamestate.getRiddleResolved()) {
      showDialog("Info", "Riddle", "You need to resolve the riddle!");
      App.setRoot("chat");
      return;
    }

    if (!gamestate.getKeyFound()) {
      showDialog(
          "Info", "Find the key!", "You resolved the riddle, now you know where the key is.");
    } else {
      showDialog("Info", "Second Riddle", "You're not done yet...");
      App.setRoot("chat");
      return;
    }
  }

  /**
   * Handles the click event on the bots.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickBoots(MouseEvent event) {
    System.out.println("boots clicked");
    if (gamestate.getRiddleResolved() && !gamestate.getKeyFound()) {
      showDialog("Info", "Key Found", "You found a key in the boots!");
      gamestate.setKeyFound(true);
      ;
    }
  }

  /**
   * Handles the click event on the cupboard.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickCupboard(MouseEvent event) throws IOException {
    System.out.println("cupboard clicked");
    App.setRoot("cupboard");
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickTable(MouseEvent event) throws IOException {
    System.out.println("table clicked");
    App.setRoot("table");
  }

  @FXML
  public void checkTime() {
    System.out.println(gamestate.getTime());
  }
}
