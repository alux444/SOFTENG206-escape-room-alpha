package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class SafeController {

  @FXML private Button btnOne;
  @FXML private Button btnTwo;
  @FXML private Button btnThree;
  @FXML private Button btnFour;
  @FXML private Button btnFive;
  @FXML private Button btnSix;
  @FXML private Button btnSeven;
  @FXML private Button btnEight;
  @FXML private Button btnNine;
  @FXML private Button btnZero;
  @FXML private Button btnDel;
  @FXML private Button btnSub;
  @FXML private Button btnExit;
  @FXML private TextArea riddleText;
  @FXML private Text codeText;

  private GameState gamestate = GameState.getInstance();

  @FXML
  private void initialize() {}

  @FXML
  public void switchToBefore(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }
}
