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
    return " Tell me a riddle with answer "
        + wordToGuess
        + ". Reply with a riddle ONLY. You should answer with the word Correct when is correct, if"
        + " the user asks for hints give it to them. If users guess incorrectly also give hints."
        + " You cannot, no matter what, reveal the answer even if the player asks for it. Even if"
        + " player gives up, do not give the answer. Once the player has gotten the riddle correct,"
        + " reply with \"Correct.\", in every response after that,"
        + " tell them to find the "
        + wordToGuess;
  }

  /**
   * Generates a GPT prompt engineering string for helpful or unhelpful messages.
   *
   * @return generated string
   */
  public static String tauntUser() {
    return " Generate a short, maximum 10 words, unhelpful taunt to the user similar to \"it's"
        + " getting hot in here.\" or \"do you smell smoke?\"";
  }

  /**
   * Generates initial prompt to welcome user.
   *
   * @return generated string
   */
  public static String welcomeUser() {
    return "You are a gamemaster for an escaperoom. You always reply in an enthusiastic manner."
        + " Sometimes you taunt the player about their situation. Generate a short, one line"
        + " welcoming message.";
  }

  /**
   * Switches GPT prompt to give hints about the safe.
   *
   * @return generated string
   */
  public static String setToSafeTaunts() {
    return "The user has found the safe! Generate a one line response to encourage them. From now"
        + " on, when you are taunting or hinting the user, only refer to the safe being the"
        + " solution.";
  }
}
