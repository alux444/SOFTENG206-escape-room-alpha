package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManager {
  public enum AppUi {
    ROOM,
    CHAT,
    CUPBOARD,
    TABLE,
    GAMEOVER,
    SAFE
  }

  private static HashMap<AppUi, Parent> sceneMap = new HashMap<AppUi, Parent>();
  private static AppUi previousScene;

  public static void addUi(AppUi appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  public static Parent getUiRoot(AppUi appUi) {
    return sceneMap.get(appUi);
  }

  public static void setPrevious(AppUi appUi) {
    previousScene = appUi;
  }

  public static AppUi getPrevious() {
    return previousScene;
  }
}
