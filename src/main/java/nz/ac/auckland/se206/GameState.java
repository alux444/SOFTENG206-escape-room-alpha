package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.controllers.ChatController;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Represents the state of the game. */
public class GameState {

  // static reference to itself
  private static GameState instance;

  private TextToSpeech textToSpeech;

  private Scene currentScene;
  private ChatController chatController;

  // button and images
  private Button timeBtnChat;
  private Button timeBtnCupboard;
  private Button timeBtnRoom;
  private Button timeBtnSafe;
  private Button timeBtnTable;
  private ImageView backgroundImage;
  private int backgroundId;
  private Image currentImage;
  private ImageView gameoverImageView;

  /** Boolean values to indicate the progress of the escape room. */
  private boolean isRiddleGenerated;

  private boolean isRiddleResolved;
  private String itemToFind;
  private boolean isSafeFound;
  private boolean isKeyFound;
  private boolean isGameWon;

  /** Timer related fields */
  private Timer timer;

  private int time;
  private boolean timerStarted;

  /** Initial gamestate */
  private GameState() {
    this.textToSpeech = new TextToSpeech();
    this.backgroundId = 1;
    this.isRiddleGenerated = false;
    this.isRiddleResolved = false;
    this.itemToFind = null;
    this.isSafeFound = false;
    this.isKeyFound = false;
    this.time = 120;
    this.timerStarted = false;
  }

  // returns the current instance of the gamestate. Only one will exist
  public static GameState getInstance() {
    if (instance == null) {
      instance = new GameState();
    }
    return instance;
  }

  /**
   * Resets gamestate to initial state.
   *
   * @throws ApiProxyException
   * @throws IOException
   */
  public void resetGameState() throws ApiProxyException, IOException {
    // Reset all the game state variables to their initial values
    this.backgroundId = 1;
    this.isRiddleGenerated = false;
    this.isRiddleResolved = false;
    this.itemToFind = null;
    this.isSafeFound = false;
    this.isKeyFound = false;
    this.time = 120;
    this.timerStarted = false;
    this.isGameWon = false;
    chatController.initialize();
    startCountdown();
    updateImage("room0");
  }

  /**
   * Sets the current scene of the game.
   *
   * @param scene main scene of the game
   */
  public void setCurrentScene(Scene scene) {
    this.currentScene = scene;
  }

  /**
   * Sets the current game's chat controller.
   *
   * @param controller instance of chat controller.
   */
  public void setChatController(ChatController controller) {
    this.chatController = controller;
  }

  /**
   * Sets the game's current time button.
   *
   * @param button instance of button
   * @param room string ofroom name
   */
  public void setTimeButton(Button button, String room) {
    if (room.equals("room")) {
      this.timeBtnRoom = button;
    } else if (room.equals("chat")) {
      this.timeBtnChat = button;
    } else if (room.equals("cupboard")) {
      this.timeBtnCupboard = button;
    } else if (room.equals("table")) {
      this.timeBtnTable = button;
    } else if (room.equals("safe")) {
      this.timeBtnSafe = button;
    } else {
      System.out.println("Invalid room input");
    }
  }

  /**
   * Sets the game's current background imageview instance.
   *
   * @param image instance of imageview.
   */
  public void setBackgroundImage(ImageView image) {
    this.backgroundImage = image;
  }

  /**
   * Getter for current image of the game.
   *
   * @return image instance of currentimage
   */
  public Image getCurrentImage() {
    return this.currentImage;
  }

  /**
   * Sets the game's current gameover imageview instance.
   *
   * @param image instance of imageview.
   */
  public void setEndImageView(ImageView image) {
    this.gameoverImageView = image;
  }

  /**
   * Sets the game's current item to be found.
   *
   * @param item string name for item
   */
  public void setItem(String item) {
    this.itemToFind = item;
  }

  /**
   * Getter for riddle resolved status.
   *
   * @return boolean of if riddle is resolveed
   */
  public boolean getRiddleResolved() {
    return this.isRiddleResolved;
  }

  /**
   * Setter for riddle resolved status.
   *
   * @param status boolean status to change riddleResolved to.
   */
  public void setRiddleResolved(boolean status) {
    this.isRiddleResolved = status;
  }

  public boolean getSafeFound() {
    return this.isSafeFound;
  }

  /**
   * Returns a boolean value based on if key is found.
   *
   * @return boolean of if the key is found
   */
  public boolean getKeyFound() {
    return this.isKeyFound;
  }

  /**
   * Returns the status of the riddle (if it is generated)
   *
   * @return boolean of if riddle is generted
   */
  public boolean getRiddleGenerated() {
    return this.isRiddleGenerated;
  }

  /**
   * getting for if this game is won.
   *
   * @return boolean status of victory
   */
  public boolean getIfGameWon() {
    return this.isGameWon;
  }

  /** Runs the generate riddle in the chat controller, and sets riddleGenerated to true. */
  public void runGenerateRiddle() {
    chatController.generateRiddle();
    this.isRiddleGenerated = true;
  }

  /** sets the key status to found and shows a dialog to the user. */
  public void setKeyFound() {
    this.isKeyFound = true;
    showDialog(
        "Key?",
        "Is that a Key?",
        "With a click, the safe opens. Inside you spot the key! Let's get out of here!");
  }

  /** Sets the winning game status to true and cancels the timer. */
  public void winGame() {
    this.isGameWon = true;
    timer.cancel();
  }

  /**
   * Searches for the key under an item. If the item is the item to be searched for, notify user.
   *
   * @param item item attempted to search for key under.
   */
  public void attemptFindSafe(String item) {
    if (item == itemToFind && this.isRiddleResolved == true) {
      if (isSafeFound) {
        currentScene.setRoot(SceneManager.getUiRoot(AppUi.SAFE));
      } else {
        this.isSafeFound = true;
        showDialog("Safe", "Is that a Safe?", "You spot a tiny safe under " + item);
        chatController.addGamemasterMessage("Can you crack the code?");
        chatController.setToSafeTaunts();
        currentScene.setRoot(SceneManager.getUiRoot(AppUi.SAFE));
      }
    }
  }

  /**
   * Ends the current game, setting screen based on result of the game. Switches to the gameover
   * scene.
   *
   * @throws IOException
   */
  public void endGame() throws IOException {
    timer.cancel();
    if (this.isGameWon) {
      Image image = new Image(App.class.getResource("/images/victory.png").openStream());
      gameoverImageView.setImage(image);
      runTextToSpeech("Nice work buddy, you made it. Would you like to try again?");
    } else {
      Image image = new Image(App.class.getResource("/images/room8.png").openStream());
      gameoverImageView.setImage(image);
      runTextToSpeech("Oh, what a shame. You can try again if you like.");
    }
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.GAMEOVER));
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  public void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /** updates image for background */
  public void updateImage(String name) throws IOException {
    this.currentImage = new Image(App.class.getResource("/images/" + name + ".png").openStream());
    Platform.runLater(
        () -> {
          backgroundImage.setImage(currentImage);
        });
  }

  /**
   * Initialises the timer countdown. Updates add timer states as time decreases. Also validates
   * time to check if any events are required to be run.
   */
  public void startCountdown() {
    // will not start the countdown if it has already started.
    if (this.timerStarted) {
      return;
    }

    timer = new Timer();
    System.out.println("timer started");
    this.timerStarted = true;

    // task for the update of the timer, decrements timer every second and updates GUI, aswell as
    // validating for any events that may have to be run.
    Task<Void> timerTask =
        new Task<Void>() {
          // schedules a thread for the continuous decrementing of the timer, and updates all timer
          // value indicators for the GUI
          @Override
          protected Void call() throws Exception {
            timer.scheduleAtFixedRate(
                new TimerTask() {
                  @Override
                  public void run() {
                    time--;
                    Platform.runLater(
                        () -> {
                          timeBtnChat.setText(Integer.toString(time));
                          timeBtnCupboard.setText(Integer.toString(time));
                          timeBtnRoom.setText(Integer.toString(time));
                          timeBtnSafe.setText(Integer.toString(time));
                          timeBtnTable.setText(Integer.toString(time));
                        });
                    try {
                      checkTimeStatus();
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                },
                1000,
                1000);
            return null;
          }
        };

    Thread timerThread = new Thread(timerTask, "TimerThread");
    timerThread.start();
  }

  /**
   * Validates time to check if events need to be run.
   *
   * @throws IOException
   */
  private void checkTimeStatus() throws IOException {

    if (time % 30 == 0 && time > 0) {
      if (isRiddleResolved) {
        chatController.addTauntMessage();
      }
    }

    if (time % 15 == 0) {
      updateImage("room" + this.backgroundId);
      this.backgroundId++;
    }

    if (time <= 0) {
      System.out.println("out of time");
      timer.cancel();
      endGame();
    }
  }

  /**
   * Runs a text to speech message to the user.
   *
   * @param message message to be said
   */
  public void runTextToSpeech(String message) {
    Task<Void> speakingTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            textToSpeech.speak(message);
            return null;
          }
        };

    Thread speakingThread = new Thread(speakingTask, "Speaker Thread");
    speakingThread.start();
  }
}
