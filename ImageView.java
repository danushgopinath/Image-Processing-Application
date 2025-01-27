package view;

import controller.ImageController;
import java.io.IOException;

/**
 * Represents the view component for interacting with the image processing controller. Responsible
 * for handling user input and output, particularly command execution.
 */
public class ImageView implements View {

  private final ImageController controller; // The controller that handles image processing

  /**
   * Constructor to initialize the ImageView with a specified controller.
   *
   * @param controller the ImageController to be associated with this view.
   */
  public ImageView(ImageController controller) {
    this.controller = controller; // Set the controller for this view
  }

  /**
   * Processes commands received from the main class and forwards them to the controller.
   *
   * @param commands the command-line arguments to be processed.
   */
  public void processCommand(String[] commands) {
    // Convert the command array to a single string for parsing
    String commandString = String.join(" ", commands);
    System.out.println(commandString);

    // Pass the command string to the controller for processing
    controller.processCommand(commandString);
  }

  /**
   * Executes a script using the associated controller.
   *
   * @param scriptFilePath the path to the script file to be executed.
   */
  public void runScript(String scriptFilePath) {
    try {
      controller.runScript(scriptFilePath); // Call the runScript method from ImageController
    } catch (IOException e) {
      // Handle exceptions related to script execution
      System.out.println("Error executing script: " + e.getMessage());
    }
  }
}