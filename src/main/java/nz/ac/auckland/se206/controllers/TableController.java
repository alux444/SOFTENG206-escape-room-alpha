package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class TableController {
  @FXML private Rectangle rubberDuck;
  @FXML private Rectangle apple;
  @FXML private Rectangle pencil;
  @FXML private Rectangle scissors;
  @FXML private Polygon watch;
  @FXML private Button returnBtn;
  @FXML private Button timeBtnTable;

  private GameState gamestate = GameState.getInstance();

  @FXML
  private void initialize() {
    gamestate.setTimeButton(timeBtnTable, "table");
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  private void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  private void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  /**
   * Handles the click event on the duck.
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickDuck(MouseEvent event) {
    System.out.println("duck clicked");
    gamestate.attemptFindSafe("rubber duck");
  }

  /**
   * Handles the click event on the apple.
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickApple(MouseEvent event) {
    System.out.println("apple clicked");
    gamestate.attemptFindSafe("apple");
  }

  /**
   * Handles the click event on the pencil.
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickPencil(MouseEvent event) {
    System.out.println("pencil clicked");
    gamestate.attemptFindSafe("pencil");
  }

  /**
   * Handles the click event on the scissors.
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickScissors(MouseEvent event) {
    System.out.println("scissors clicked");
    gamestate.attemptFindSafe("scissors");
  }

  /**
   * Handles the click event on the watch,
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickWatch(MouseEvent event) {
    System.out.println("watch clicked");
    gamestate.attemptFindSafe("watch");
  }

  /**
   * Returns the user to the room controller.
   *
   * @param event
   * @throws IOException
   */
  @FXML
  private void onSwitchToRoom(ActionEvent event) throws IOException {
    Button currentButton = (Button) event.getSource();
    Scene currentScene = currentButton.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }
}
