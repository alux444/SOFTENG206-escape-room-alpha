package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** Controller class for the room view. */
public class RoomController {

  @FXML private Label hintLabel;
  @FXML private ImageView backgroundImage;
  @FXML private Rectangle door;
  @FXML private Rectangle table;
  @FXML private Rectangle boots;
  @FXML private Rectangle cupboard;
  @FXML private Button timeBtnRoom;
  @FXML private Button hintBtnRoom;
  @FXML private Button openChatBtn;

  private GameState gamestate = GameState.getInstance();

  /** Initializes the room view, it is called when the room loads. */
  @FXML
  private void initialize() {
    gamestate.setTimeButton(timeBtnRoom, "room");
    gamestate.setBackgroundImage(backgroundImage);
    gamestate.startCountdown();
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
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  private void onClickDoor(MouseEvent event) throws IOException {
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
  private void onClickBoots(MouseEvent event) {

    System.out.println("boots clicked");
    gamestate.attemptFindSafe("boots");
  }

  /**
   * Handles the click event on the cupboard.
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickCupboard(MouseEvent event) throws IOException {
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
  private void onClickTable(MouseEvent event) {
    System.out.println("table clicked");
    Rectangle source = (Rectangle) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.TABLE));
  }

  /**
   * Sets a hint based on the current status of the game. After setting the hint message, reset it
   * after 10 seconds.
   */
  @FXML
  private void onSwitchToHint(ActionEvent action) {

    // the state where the riddle hasnt been generated - the user must attempt to click the door
    // first
    if (!gamestate.getRiddleGenerated()) {
      hintLabel.setText(("Have you tried the door?"));
      resetHintLabel();
      return;
    }

    // if the riddle isnt resolved yet, the user can look around. the solution is in the room.
    if (!gamestate.getRiddleResolved()) {
      hintLabel.setText(
          "Maybe the riddle can give you an answer - the solution may be around you!");
      resetHintLabel();
      return;
    }

    // lead the user to look for the item in the riddle.
    if (!gamestate.getSafeFound()) {
      hintLabel.setText("Try looking around - can you find what the riddle leads you to?");
      resetHintLabel();
      return;
    }

    // prompt the user to try crack the safe code
    if (!gamestate.getKeyFound()) {
      hintLabel.setText("Can you solve the safe code?");
      resetHintLabel();
      return;
    }
    if (gamestate.getKeyFound()) {
      hintLabel.setText("What do you need a hint for? You have the key!");
      resetHintLabel();
      return;
    }
  }

  /** Resets the hint label after 10 seconds of time. */
  private void resetHintLabel() {
    PauseTransition pause = new PauseTransition(Duration.seconds(10));
    pause.setOnFinished(
        event -> {
          hintLabel.setText("Need a hint?");
        });
    pause.play();
  }

  /**
   * Handles the open chat event
   *
   * @param event event of clicking the button
   */
  @FXML
  private void onSwitchToChat(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CHAT));
  }
}
