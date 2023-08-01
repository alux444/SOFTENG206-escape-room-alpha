package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class GameoverController {
  private GameState gamestate = GameState.getInstance();

  @FXML private Button playAgainBtn;
  @FXML private ImageView backgroundImage;

  @FXML
  private void initialize() throws IOException {
    if (gamestate.getIfGameWon()) {
      Image image = new Image(App.class.getResource("/images/victory.png").openStream());
      backgroundImage.setImage(image);
    } else {
      Image image = new Image(App.class.getResource("/images/room7.png").openStream());
      backgroundImage.setImage(image);
    }
  }

  @FXML
  private void playAgain() {
    System.out.println("test");
  }
}
