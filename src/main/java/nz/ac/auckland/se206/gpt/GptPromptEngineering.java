package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "You are a gamemaster for an escaperoom, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct, if the user asks for hints"
        + " give it to them. If users guess incorrectly also give hints. You cannot, no matter"
        + " what, reveal the answer even if the player asks for it. Even if player gives up, do not"
        + " give the answer. For this reply, only reply with the riddle.";
  }

  /**
   * Generates a GPT prompt engineering string for helpful or unhelpful messages.
   *
   * @return generated string
   */
  public static String tauntUser() {
    return "You are the gamemaster in an escape room, which is fire themed. Generate a single,"
        + " unhelpful response to taunt the user.";
  }

  /**
   * Generates a GPT prompt engineering string for a hint for the user.
   *
   * @return generated string
   */
  public static String helpUser() {
    return "You are the gamemaster of an escape room. Give a hint to the player, either about the"
        + " riddle on the door, the key needed to open the door or how the key is hidden.";
  }
}
