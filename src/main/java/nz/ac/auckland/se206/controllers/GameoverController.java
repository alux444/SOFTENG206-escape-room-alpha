package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class GameoverController {
  private GameState gamestate = GameState.getInstance();

  @FXML private Button playAgainBtn;
  @FXML private ImageView backgroundImage;

  @FXML
  private void initialize() {
    gamestate.setEndImageView(backgroundImage);
  }

  @FXML
  private void playAgain(ActionEvent event) throws ApiProxyException, IOException {
    System.out.println("test");
    GameState.getInstance().resetGameState();
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }
}
