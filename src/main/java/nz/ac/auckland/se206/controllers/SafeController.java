package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class SafeController {

  @FXML private ImageView backgroundImage;
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
  @FXML private Button btnChat;
  @FXML private Button timeBtnSafe;
  @FXML private TextArea riddleText;
  @FXML private Text codeText;
  @FXML private Text statusText;

  private GameState gamestate = GameState.getInstance();
  private String code = "";
  private String solution = "";

  /* Initialises the safe. Sets the background image and generates the code riddle and displays it to the GUI.
   *
   */
  @FXML
  private void initialize() throws IOException {
    gamestate.setTimeButton(timeBtnSafe, "safe");
    backgroundImage.setImage(
        new Image(App.class.getResource("/images/safeClosed.png").openStream()));
    riddleText.setText("");
    riddleText.setOpacity(0.5);

    Random random = new Random();
    for (int i = 0; i < 4; i++) {
      int currentNumber = random.nextInt(10);
      this.solution += Integer.toString(currentNumber);
      for (int j = 0; j < currentNumber; j++) {
        riddleText.setText(riddleText.getText() + Integer.toString(i + 1));
      }
    }
    System.out.println(solution);
  }

  // Handles the event where any number is pressed.
  @FXML
  private void pressNumber(int number) {
    if (this.code.length() < 4) {
      this.code += Integer.toString(number);
      codeText.setText(code);
    }
  }

  /**
   * Handles the key pressed event. Uses a switch case from the keyboard key pressed to result in
   * the respective button function being run
   *
   * @param event the key event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  public void onKeyPressed(KeyEvent event) throws ApiProxyException, IOException {
    KeyCode keyCode = event.getCode();
    switch (keyCode) {
      case BACK_SPACE:
        clickDel();
        break;
      case ENTER:
        clickSub();
        break;
      case DIGIT0:
      case NUMPAD0:
        clickZero();
        break;
      case DIGIT1:
      case NUMPAD1:
        clickOne();
        break;
      case DIGIT2:
      case NUMPAD2:
        clickTwo();
        break;
      case DIGIT3:
      case NUMPAD3:
        clickThree();
        break;
      case DIGIT4:
      case NUMPAD4:
        clickFour();
        break;
      case DIGIT5:
      case NUMPAD5:
        clickFive();
        break;
      case DIGIT6:
      case NUMPAD6:
        clickSix();
        break;
      case DIGIT7:
      case NUMPAD7:
        clickSeven();
        break;
      case DIGIT8:
      case NUMPAD8:
        clickEight();
        break;
      case DIGIT9:
      case NUMPAD9:
        clickNine();
        break;
      default:
        break;
    }
  }

  // handles clicking of the 0
  @FXML
  public void clickZero() {
    pressNumber(0);
  }

  // handles clicking of the 1
  @FXML
  public void clickOne() {
    pressNumber(1);
  }

  // handles clicking of the 2
  @FXML
  public void clickTwo() {
    pressNumber(2);
  }

  // handles clicking of the 3
  @FXML
  public void clickThree() {
    pressNumber(3);
  }

  // handles clicking of the 4
  @FXML
  public void clickFour() {
    pressNumber(4);
  }

  // handles clicking of the 5
  @FXML
  public void clickFive() {
    pressNumber(5);
  }

  // handles clicking of the 6
  @FXML
  public void clickSix() {
    pressNumber(6);
  }

  // handles clicking of the 7
  @FXML
  public void clickSeven() {
    pressNumber(7);
  }

  // handles clicking of the 8
  @FXML
  public void clickEight() {
    pressNumber(8);
  }

  // handles clicking of the 9
  @FXML
  public void clickNine() {
    pressNumber(9);
  }

  // handles clicking of the delete
  @FXML
  public void clickDel() {
    if (this.code.length() > 0) {
      this.code = this.code.substring(0, this.code.length() - 1);
    }
    codeText.setText(code);
  }

  // handles clicking of the submit
  @FXML
  public void clickSub() throws IOException {
    System.out.println("sub");
    if (this.code.equals(this.solution)) {
      System.out.println("correct");
      gamestate.setKeyFound();
      statusText.setFill(Color.GREEN);
      statusText.setText("CORRECT");
      riddleText.setOpacity(0);
      backgroundImage.setImage(
          new Image(App.class.getResource("/images/safeOpen.png").openStream()));
    } else {
      statusText.setFill(Color.RED);
      statusText.setText("INCORRECT");
    }
  }

  // switches to the room root
  @FXML
  public void switchToBefore(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }

  // switches to the chat room
  @FXML
  public void switchToChat(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CHAT));
  }
}
