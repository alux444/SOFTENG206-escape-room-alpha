package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.controllers.ChatController;

/** Represents the state of the game. */
public class GameState {

  // static reference to itself
  private static GameState instance;

  // chat controller reference to add messages from gamemaster
  private ChatController chatController;

  // button and images
  private Button checkTimeBtn;
  private ImageView backgroundImage;

  // image type to update image
  private Image currentImage;

  /** Indicates whether the riddle has been generated yet. */
  private boolean isRiddleGenerated;

  /** Indicates whether the riddle has been resolved. */
  private boolean isRiddleResolved;

  /** Indicated the item for which must be found after riddle is resolved. */
  private String itemToFind;

  /** Indicates whether the key has been found. */
  private boolean isKeyFound;

  /** Indication of time remaining */
  private int time;

  /** Timer type and boolean for if timer has started */
  private Timer timer;

  private boolean timerStarted;

  /** boolean if game is won */
  private boolean isGameWon;

  /** Initial gamestate */
  private GameState() {
    this.isRiddleResolved = false;
    this.isKeyFound = false;
    this.time = 120;
    this.timerStarted = false;
    this.isRiddleGenerated = false;
    this.itemToFind = null;
  }

  // returns the current instance of the gamestate. Only one will exist
  public static GameState getInstance() {
    if (instance == null) {
      instance = new GameState();
    }
    return instance;
  }

  public void setChatController(ChatController controller) {
    this.chatController = controller;
  }

  // set reference for time tracking button
  public void setTimeButton(Button button) {
    this.checkTimeBtn = button;
  }

  // set reference for background image
  public void setBackgroundImage(ImageView image) {
    this.backgroundImage = image;
  }

  // set correct item to find
  public void setItem(String item) {
    this.itemToFind = item;
  }

  // getter for background image
  public ImageView getBackgroundImage() {
    return this.backgroundImage;
  }

  // getter for current image
  public Image getCurrentImage() {
    return this.currentImage;
  }

  // get if riddle is resolved
  public boolean getRiddleResolved() {
    return this.isRiddleResolved;
  }

  public void setRiddleResolved(boolean status) {
    this.isRiddleResolved = status;
  }

  public boolean getKeyFound() {
    return this.isKeyFound;
  }

  public void runGenerateRiddle() {
    chatController.generateRiddle();
    this.isRiddleGenerated = true;
  }

  public boolean getRiddleGenerated() {
    return this.isRiddleGenerated;
  }

  public void attemptFindKey(String item) {
    if (item == itemToFind && this.isRiddleResolved == true) {
      this.isKeyFound = true;
      showDialog("Key", "Key Found!", "You found the key under the " + item);
      chatController.addGamemasterMessage("That looks useful.");
    }
  }

  public void winGame() {
    this.isGameWon = true;
    timer.cancel();
  }

  public boolean getIfGameWon() {
    return this.isGameWon;
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

  // function for starting countdown.
  public void startCountdown() {
    // will not start the countdown if it has already started.
    if (this.timerStarted) {
      return;
    }

    timer = new Timer();
    System.out.println("timer started");
    this.timerStarted = true;

    Task<Void> timerTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {

            timer.scheduleAtFixedRate(
                new TimerTask() {
                  @Override
                  public void run() {
                    time--;
                    Platform.runLater(
                        () -> {
                          checkTimeBtn.setText(Integer.toString(time));
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

  private void checkTimeStatus() throws IOException {
    if (time % 30 == 0) {
      if (isRiddleResolved) {
        chatController.addTauntMessage();
      }
    }

    if (time == 105) {
      updateImage("room1");
      chatController.addGamemasterMessage("Is that a fire?");
    }

    if (time == 85) {
      updateImage("room2");
    }

    if (time == 65) {
      updateImage("room3");
    }
    if (time == 45) {
      updateImage("room4");
    }
    if (time == 30) {
      updateImage("room5");
    }
    if (time == 15) {
      updateImage("room6");
    }

    if (time <= 0) {
      System.out.println("out of time");
      timer.cancel();
      updateImage("room7");
    }
  }
}
