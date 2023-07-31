package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** Represents the state of the game. */
public class GameState {

  private static GameState instance;

  // time tracker button
  private Button checkTimeBtn;
  private ImageView backgroundImage;
  private Image currentImage;

  /** Indicates whether the riddle has been resolved. */
  private boolean isRiddleResolved;

  /** Indicates whether the key has been found. */
  private boolean isKeyFound;

  /** Indication of time remaining */
  private int time;

  private Timer timer;
  private boolean timerStarted;

  /** Initial gamestate */
  private GameState() {
    this.isRiddleResolved = false;
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

  // set reference for time tracking button
  public void setTimeButton(Button button) {
    this.checkTimeBtn = button;
  }

  // set reference for background image
  public void setBackgroundImage(ImageView image) {
    this.backgroundImage = image;
  }

  // get if riddle is resolved
  public boolean getRiddleResolved() {
    return this.isRiddleResolved;
  }

  public void setRiddeResolved(boolean status) {
    this.isRiddleResolved = status;
  }

  public boolean getKeyFound() {
    return this.isKeyFound;
  }

  public void setKeyFound(boolean status) {
    this.isKeyFound = status;
  }

  public int getTime() {
    return this.time;
  }

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
    if (time == 105) {
      updateImage("room1");
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
