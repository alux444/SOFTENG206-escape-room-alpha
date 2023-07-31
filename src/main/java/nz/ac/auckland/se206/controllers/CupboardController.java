package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;

public class CupboardController {

  @FXML private Rectangle sunglasses;
  @FXML private Rectangle teapot;
  @FXML private Rectangle lightbulb;
  @FXML private Rectangle teddyBear;
  @FXML private Rectangle coffee;
  @FXML private Button returnBtn;

  public void initialize() {}

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
   * Handles the click event on the sunglasses,
   *
   * @param event the mouse event
   */
  @FXML
  public void clickSunglasses(MouseEvent event) {
    System.out.println("sunglasses clicked");
  }

  /**
   * Handles the click event on the teapot,
   *
   * @param event the mouse event
   */
  @FXML
  public void clickTeapot(MouseEvent event) {
    System.out.println("teapot clicked");
  }

  /**
   * Handles the click event on the lightbulb,
   *
   * @param event the mouse event
   */
  @FXML
  public void clickLightbulb(MouseEvent event) {
    System.out.println("bulb clicked");
  }

  /**
   * Handles the click event on the teddy,
   *
   * @param event the mouse event
   */
  @FXML
  public void clickTeddy(MouseEvent event) {
    System.out.println("teddy clicked");
  }

  /**
   * Handles the click event on the coffee,
   *
   * @param event the mouse event
   */
  @FXML
  public void clickCoffee(MouseEvent event) {
    System.out.println("coffee clicked");
  }

  @FXML
  public void returnRoom() throws IOException {
    App.setRoot("room");
  }
}
