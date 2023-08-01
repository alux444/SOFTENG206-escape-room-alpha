package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class TableController {
  @FXML private Rectangle rubberDuck;
  @FXML private Rectangle apple;
  @FXML private Rectangle pencil;
  @FXML private Rectangle scissors;
  @FXML private Polygon watch;
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
   * Handles the click event on the duck.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickDuck(MouseEvent event) {
    System.out.println("duck clicked");
  }

  /**
   * Handles the click event on the apple.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickApple(MouseEvent event) {
    System.out.println("apple clicked");
  }

  /**
   * Handles the click event on the pencil.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickPencil(MouseEvent event) {
    System.out.println("pencil clicked");
  }

  /**
   * Handles the click event on the scissors.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickScissors(MouseEvent event) {
    System.out.println("scissors clicked");
  }

  /**
   * Handles the click event on the watch,
   *
   * @param event the mouse event
   */
  @FXML
  public void clickWatch(MouseEvent event) {
    System.out.println("watch clicked");
  }

  @FXML
  public void returnRoom() throws IOException {}
}
