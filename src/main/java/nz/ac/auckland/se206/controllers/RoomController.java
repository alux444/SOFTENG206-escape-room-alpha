package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** Controller class for the room view. */
public class RoomController {

  @FXML private ImageView backgroundImage;
  @FXML private Rectangle door;
  @FXML private Rectangle table;
  @FXML private Rectangle boots;
  @FXML private Rectangle cupboard;
  @FXML private Button checkTimeBtn;
  @FXML private Button openChatBtn;

  private GameState gamestate = GameState.getInstance();

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
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
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void clickDoor(MouseEvent event) throws IOException {
    System.out.println("door clicked");

    if (!gamestate.getRiddleResolved() && !gamestate.getRiddleGenerated()) {
      gamestate.showDialog("Info", "Riddle", "You need to resolve the riddle!");
      gamestate.runGenerateRiddle();
      Rectangle clickedRectange = (Rectangle) event.getSource();
      Scene currentScene = clickedRectange.getScene();
      currentScene.setRoot(SceneManager.getUiRoot(AppUi.CHAT));
      return;
    } else if (!gamestate.getKeyFound()) {
      gamestate.showDialog(
          "Key",
          "Can't open the door.",
          "You need to find the key to open the door! Where could it be?");
    } else if (gamestate.getKeyFound()) {
      gamestate.winGame();
      gamestate.endGame();
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
    gamestate.attemptFindKey("boots");
  }

  /**
   * Handles the click event on the cupboard.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickCupboard(MouseEvent event) throws IOException {
    System.out.println("cupboard clicked");
    Rectangle source = (Rectangle) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CUPBOARD));
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickTable(MouseEvent event) {
    System.out.println("table clicked");
    Rectangle source = (Rectangle) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.TABLE));
  }

  /**
   * Handles the open chat event
   *
   * @param event event of clicking the button
   */
  @FXML
  public void openChat(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CHAT));
  }
}
