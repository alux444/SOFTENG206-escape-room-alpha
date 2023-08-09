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
  private String code = "";

  @FXML
  private void initialize() {}

  @FXML
  private void pressNumber(int number) {
    if (this.code.length() < 4) {
      this.code += Integer.toString(number);
      codeText.setText(code);
    }
  }

  @FXML
  public void clickZero() {
    pressNumber(0);
  }

  @FXML
  public void clickOne() {
    pressNumber(1);
  }

  @FXML
  public void clickTwo() {
    pressNumber(2);
  }

  @FXML
  public void clickThree() {
    pressNumber(3);
  }

  @FXML
  public void clickFour() {
    pressNumber(4);
  }

  @FXML
  public void clickFive() {
    pressNumber(1);
  }

  @FXML
  public void clickSix() {
    pressNumber(6);
  }

  @FXML
  public void clickSeven() {
    pressNumber(7);
  }

  @FXML
  public void clickEight() {
    pressNumber(8);
  }

  @FXML
  public void clickNine() {
    pressNumber(9);
  }

  @FXML
  public void clickDel() {
    if (this.code.length() > 0) {
      this.code = this.code.substring(0, this.code.length() - 1);
    }
    codeText.setText(code);
  }

  @FXML
  public void clickSub() {
    System.out.println("sub");
  }

  @FXML
  public void switchToBefore(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }
}
