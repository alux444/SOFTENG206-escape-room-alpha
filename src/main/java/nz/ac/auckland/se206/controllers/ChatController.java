package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

/** Controller class for the chat view. */
public class ChatController {
  @FXML private TextArea chatTextArea;
  @FXML private TextField inputText;
  @FXML private Button sendButton;
  @FXML private Text timerDisplay;

  private GameState gamestate = GameState.getInstance();

  private String[] words = {
    "teapot",
    "sunglasses",
    "lightbulb",
    "teddy bear",
    "coffee",
    "rubber duck",
    "apple",
    "pencil",
    "scissors",
    "watch",
    "boots"
  };

  private ChatCompletionRequest chatCompletionRequest;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    gamestate.setChatController(this);
  }

  public void generateRiddle() {
    Random random = new Random();
    int randNum = random.nextInt(11);
    System.out.println(words[randNum]);
    gamestate.setItem(words[randNum]);

    Task<Void> completionTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.2)
                    .setTopP(0.5)
                    .setMaxTokens(100);
            runGpt(
                new ChatMessage(
                    "user", GptPromptEngineering.getRiddleWithGivenWord(words[randNum])));
            return null;
          }
        };

    Thread gptThread = new Thread(completionTask, "GptSearchThread");
    gptThread.start();
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  private void appendChatMessage(ChatMessage msg) {
    chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      appendChatMessage(result.getChatMessage());
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Sends a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onSendMessage(ActionEvent event) throws ApiProxyException, IOException {
    String message = inputText.getText();
    if (message.trim().isEmpty()) {
      return;
    }
    inputText.clear();

    Task<Void> sendMessageTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            ChatMessage msg = new ChatMessage("user", message);
            appendChatMessage(msg);
            ChatMessage lastMsg = runGpt(msg);
            if (lastMsg.getRole().equals("assistant")
                && lastMsg.getContent().startsWith("Correct")) {
              gamestate.setRiddleResolved(true);
            }
            return null;
          }
        };

    Thread sendMessageThread = new Thread(sendMessageTask, "SendMessageThread");
    sendMessageThread.start();
  }

  /**
   * Navigates back to the previous view.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onGoBack(ActionEvent event) throws ApiProxyException, IOException {
    Button clickedButton = (Button) event.getSource();
    Scene currentScene = clickedButton.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }

  /**
   * Public function to add message to the chat text area.
   *
   * @param role the role of the sender (e.g., "user", "assistant")
   * @param message the message content to append
   */
  public void addGamemasterMessage(String message) {
    ChatMessage msg = new ChatMessage("???", message);
    appendChatMessage(msg);
  }
}
