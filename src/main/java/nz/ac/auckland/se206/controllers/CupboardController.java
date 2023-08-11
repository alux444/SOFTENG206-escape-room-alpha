package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class CupboardController {

  @FXML private Rectangle sunglasses;
  @FXML private Rectangle teapot;
  @FXML private Rectangle lightbulb;
  @FXML private Rectangle teddyBear;
  @FXML private Rectangle coffee;
  @FXML private Button returnBtn;
  @FXML private Button timeBtnCupboard;

  /** instance of gamestate */
  private GameState gamestate = GameState.getInstance();

  @FXML
  private void initialize() {
    gamestate.setTimeButton(timeBtnCupboard, "cupboard");
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
   * Handles the click event on the sunglasses,
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickSunglasses(MouseEvent event) {
    System.out.println("sunglasses clicked");
    gamestate.attemptFindSafe("sunglasses");
  }

  /**
   * Handles the click event on the teapot,
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickTeapot(MouseEvent event) {
    System.out.println("teapot clicked");
    gamestate.attemptFindSafe("teapot");
  }

  /**
   * Handles the click event on the lightbulb,
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickLightbulb(MouseEvent event) {
    System.out.println("bulb clicked");
    gamestate.attemptFindSafe("lightbulb");
  }

  /**
   * Handles the click event on the teddy,
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickTeddy(MouseEvent event) {
    System.out.println("teddy clicked");
    gamestate.attemptFindSafe("teddy bear");
  }

  /**
   * Handles the click event on the coffee,
   *
   * @param event the mouse event
   */
  @FXML
  private void onClickCoffee(MouseEvent event) {
    System.out.println("coffee clicked");
    gamestate.attemptFindSafe("coffee");
  }

  /**
   * Handles returning for the room controller.
   *
   * @param event event of returning using button
   * @throws IOException
   */
  @FXML
  private void onSwitchToRoom(ActionEvent event) throws IOException {
    Button currentButton = (Button) event.getSource();
    Scene currentScene = currentButton.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }
}
