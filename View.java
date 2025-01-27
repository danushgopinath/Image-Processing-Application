package view;

import java.io.IOException;

/**
 * The View interface defines methods for interacting with the user and running image processing
 * scripts.
 */
public interface View {

  /**
   * Executes a script file containing a series of image processing commands.
   *
   * @param scriptFilePath The path to the script file to be executed.
   * @throws IOException if an error occurs while reading the script file.
   */
  void runScript(String scriptFilePath) throws IOException;
}
