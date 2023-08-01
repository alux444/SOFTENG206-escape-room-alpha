package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.GameState;

public class GameoverController {
  private GameState gamestate = GameState.getInstance();

  @FXML private Button playAgainBtn;
  @FXML private ImageView backgroundImage;

  @FXML
  private void initialize() {
    gamestate.setEndImageView(backgroundImage);
  }

  @FXML
  private void playAgain() {
    System.out.println("test");
  }
}
