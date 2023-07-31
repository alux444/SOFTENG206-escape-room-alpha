package nz.ac.auckland.se206;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;

/** Represents the state of the game. */
public class GameState {

  private static GameState instance;

  // time tracker button
  private Button checkTimeBtn;

  /** Indicates whether the riddle has been resolved. */
  private boolean isRiddleResolved;

  /** Indicates whether the key has been found. */
  private boolean isKeyFound;

  /** Indication of time remaining */
  private int time;

  private Timer timer;
  private boolean timerStarted;

  private GameState() {
    this.isRiddleResolved = false;
    this.isKeyFound = false;
    this.time = 120;
    this.timerStarted = false;
  }

  public static GameState getInstance() {
    if (instance == null) {
      instance = new GameState();
    }
    return instance;
  }

  public void setTimeButton(Button button) {
    this.checkTimeBtn = button;
  }

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

  public void startCountdown() {
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
                    System.out.println(time);
                    Platform.runLater(
                        () -> {
                          checkTimeBtn.setText(Integer.toString(time));
                        });

                    if (time <= 0) {
                      System.out.println("out of time");
                      timer.cancel();
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
}
