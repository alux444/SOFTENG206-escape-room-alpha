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
import javafx.scene.shape.Circle;
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
  @FXML private Circle digitOne;
  @FXML private Circle digitTwo;
  @FXML private Circle digitThree;
  @FXML private Circle digitFour;

  private GameState gamestate = GameState.getInstance();
  private String code = "";
  private String solution = "";

  /* Initialises the safe. Sets the background image and generates the code riddle and displays it to the GUI.
   *
   */
  @FXML
  private void initialize() throws IOException {
    digitOne.setFill(Color.RED);
    digitTwo.setFill(Color.RED);
    digitThree.setFill(Color.RED);
    digitFour.setFill(Color.RED);

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
   * the respective button function being run. Handles using backspace to delete and enter as
   * submit. Otherwise, any other key will do nothing.
   *
   * @param event the key event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  private void onKeyPressed(KeyEvent event) throws ApiProxyException, IOException {
    KeyCode keyCode = event.getCode();
    switch (keyCode) {
      case BACK_SPACE:
        handleClickDel(new ActionEvent());
        break;
      case ENTER:
        handleClickSub(new ActionEvent());
        break;
      case DIGIT0:
      case NUMPAD0:
        handleClickZero(new ActionEvent());
        break;
      case DIGIT1:
      case NUMPAD1:
        handleClickOne(new ActionEvent());
        break;
      case DIGIT2:
      case NUMPAD2:
        handleClickTwo(new ActionEvent());
        break;
      case DIGIT3:
      case NUMPAD3:
        handleClickThree(new ActionEvent());
        break;
      case DIGIT4:
      case NUMPAD4:
        handleClickFour(new ActionEvent());
        break;
      case DIGIT5:
      case NUMPAD5:
        handleClickFive(new ActionEvent());
        break;
      case DIGIT6:
      case NUMPAD6:
        handleClickSix(new ActionEvent());
        break;
      case DIGIT7:
      case NUMPAD7:
        handleClickSeven(new ActionEvent());
        break;
      case DIGIT8:
      case NUMPAD8:
        handleClickEight(new ActionEvent());
        break;
      case DIGIT9:
      case NUMPAD9:
        handleClickNine(new ActionEvent());
        break;
      default:
        break;
    }
  }

  // handles clicking of the 0
  @FXML
  private void handleClickZero(ActionEvent action) {
    pressNumber(0);
  }

  // handles clicking of the 1
  @FXML
  private void handleClickOne(ActionEvent action) {
    pressNumber(1);
  }

  // handles clicking of the 2
  @FXML
  private void handleClickTwo(ActionEvent action) {
    pressNumber(2);
  }

  // handles clicking of the 3
  @FXML
  private void handleClickThree(ActionEvent action) {
    pressNumber(3);
  }

  // handles clicking of the 4
  @FXML
  private void handleClickFour(ActionEvent action) {
    pressNumber(4);
  }

  // handles clicking of the 5
  @FXML
  private void handleClickFive(ActionEvent action) {
    pressNumber(5);
  }

  // handles clicking of the 6
  @FXML
  private void handleClickSix(ActionEvent action) {
    pressNumber(6);
  }

  // handles clicking of the 7
  @FXML
  private void handleClickSeven(ActionEvent action) {
    pressNumber(7);
  }

  // handles clicking of the 8
  @FXML
  private void handleClickEight(ActionEvent action) {
    pressNumber(8);
  }

  // handles clicking of the 9
  @FXML
  private void handleClickNine(ActionEvent action) {
    pressNumber(9);
  }

  // handles clicking of the delete
  @FXML
  private void handleClickDel(ActionEvent action) {
    if (this.code.length() > 0) {
      this.code = this.code.substring(0, this.code.length() - 1);
    }
    codeText.setText(code);
  }

  // handles clicking of the submit
  // checks for the correct numbers at each relative digit to green or red based on the correctness
  // of the input.
  // this way hinting for the player the correctness of the inputs that they
  @FXML
  private void handleClickSub(ActionEvent action) throws IOException {
    System.out.println("sub");
    if (this.code.length() == 4) {
      for (int i = 0; i < 4; i++) {
        if (code.charAt(i) == (solution.charAt(i))) {
          // switch case for setting correct values to the solution to green.
          switch (i) {
            case 0:
              digitOne.setFill(Color.GREEN);
              break;
            case 1:
              digitTwo.setFill(Color.GREEN);
              break;
            case 2:
              digitThree.setFill(Color.GREEN);
              break;
            case 3:
              digitFour.setFill(Color.GREEN);
              break;
          }
        } else {
          // switch case for setting incorrect values to red.
          switch (i) {
            case 0:
              digitOne.setFill(Color.RED);
              break;
            case 1:
              digitTwo.setFill(Color.RED);
              break;
            case 2:
              digitThree.setFill(Color.RED);
              break;
            case 3:
              digitFour.setFill(Color.RED);
              break;
          }
        }
      }

      // if the solution is correct, set the gamestate status of the key to true, and change the
      // safe background image to the open safe.
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
  }

  // switches to the room root
  @FXML
  private void handleSwitchToBefore(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }

  // switches to the chat room
  @FXML
  private void handleSwitchToChat(ActionEvent event) {
    Button source = (Button) event.getSource();
    Scene currentScene = source.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.CHAT));
  }
}
