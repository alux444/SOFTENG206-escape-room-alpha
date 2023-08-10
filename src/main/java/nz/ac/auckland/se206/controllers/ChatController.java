package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
  @FXML private TextField helperTextArea;
  @FXML private Button sendButton;
  @FXML private Button timeBtnChat;

  private GameState gamestate = GameState.getInstance();

  /** Array of possible words taken from objects in the room. */
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
   * Initialises the chat controller. Clears the chat (if there was any previous chats) and sets the
   * chat controller of gamestate. Generates a welcome message for the user.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    chatTextArea.clear();
    gamestate.setChatController(this);
    gamestate.setTimeButton(timeBtnChat, "chat");
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.7).setTopP(0.6).setMaxTokens(100);

    // task to set the completion request for future GPT responses, and generates the welcome
    // message for the user.
    Task<Void> welcomeUserTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            Platform.runLater(
                () -> {
                  helperTextArea.setText("The gamemaster is thinking...");
                });
            runGpt(new ChatMessage("user", GptPromptEngineering.welcomeUser()), true);

            Platform.runLater(
                () -> {
                  helperTextArea.setText("The gamemaster greets you.");
                });

            return null;
          }
        };

    Thread gptWelcomeThread = new Thread(welcomeUserTask, "GptWelcomeThread");
    gptWelcomeThread.start();
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  public void onKeyPressed(KeyEvent event) throws ApiProxyException, IOException {
    if (event.getCode() == KeyCode.ENTER) {
      onSendMessage(new ActionEvent());
    }
  }

  /**
   * Generates a random riddle based in a randomly selected value from the words. Calls GPT to
   * create a riddle with the prompt as the answer, and sends the message to the user.
   */
  public void generateRiddle() {
    Random random = new Random();
    int randNum = random.nextInt(11);
    System.out.println(words[randNum]);
    gamestate.setItem(words[randNum]);

    // task for concurrency when calling GPT to generate the user the riddle.
    Task<Void> completionTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            Platform.runLater(
                () -> {
                  helperTextArea.setText("The gamemaster is thinking...");
                });
            runGpt(
                new ChatMessage(
                    "user", GptPromptEngineering.getRiddleWithGivenWord(words[randNum])),
                true);

            Platform.runLater(
                () -> {
                  helperTextArea.setText("The gamemaster is waiting for your reply.");
                  ;
                });

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
    String speaker = msg.getRole().equals("assistant") ? "???" : "You";
    chatTextArea.appendText(speaker + ": " + msg.getContent() + "\n\n");
    if (speaker.equals("???")) {
      gamestate.runTextToSpeech(msg.getContent());
    }
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg, boolean addToChat) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);

    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      if (addToChat) {
        chatCompletionRequest.addMessage(result.getChatMessage());
        appendChatMessage(result.getChatMessage());
      } else {
        System.out.println(result.getChatMessage().getContent());
      }
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      System.out.println("bug");
      return null;
    }
  }

  /**
   * Adds the users typed message and clears the input. Sends a message to the GPT model. Generates
   * a response from GPT to the users message.
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

    // a task for concurrency when sending a message - updates the player on what the gamemaster is
    // doing.
    // calls gpt api to call for a response from the gamemaster.
    Task<Void> sendMessageTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            Platform.runLater(
                () -> {
                  helperTextArea.setText("The gamemaster is thinking...");
                });

            ChatMessage msg = new ChatMessage("user", message);
            appendChatMessage(msg);
            ChatMessage lastMsg = runGpt(msg, true);
            if (lastMsg.getRole().equals("assistant")
                && lastMsg.getContent().startsWith("Correct")) {
              gamestate.setRiddleResolved(true);
              Platform.runLater(
                  () -> {
                    helperTextArea.setText(
                        "Nice work! Maybe the answer can help you get out of here.");
                    ;
                  });
              return null;
            }

            Platform.runLater(
                () -> {
                  helperTextArea.setText("The gamemaster is waiting.");
                });
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
  private void switchToRoom(ActionEvent event) throws ApiProxyException, IOException {
    Button clickedButton = (Button) event.getSource();
    Scene currentScene = clickedButton.getScene();
    currentScene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }

  /**
   * Public function to add message to the chat text area.
   *
   * @param message the message content to append
   */
  public void addGamemasterMessage(String message) {
    ChatMessage msg = new ChatMessage("assistant", message);
    appendChatMessage(msg);
  }

  /** Calls gpt to generate a taunt for the user. */
  public void addTauntMessage() {
    Task<Void> sendTauntTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            runGpt(new ChatMessage("user", GptPromptEngineering.tauntUser()), true);
            return null;
          }
        };

    Thread sendTauntThread = new Thread(sendTauntTask, "SendMessageThread");
    sendTauntThread.start();
  }

  /** Switches to hinting about the safe */
  public void setToSafeTaunts() {
    Task<Void> switchToSafeTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            runGpt(new ChatMessage("user", GptPromptEngineering.setToSafeTaunts()), true);
            return null;
          }
        };

    Thread safeMsgThread = new Thread(switchToSafeTask, "SafeMessageThread");
    safeMsgThread.start();
  }
}
