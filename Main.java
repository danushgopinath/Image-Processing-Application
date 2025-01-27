import controller.ImageController;
import view.ImageView;

import java.util.Scanner;

/**
 * The Main class serves as the entry point for the image processing application.
 * It initializes the necessary components, including the controller and view, and
 * processes commands interactively from the terminal or runs a default script if no
 * arguments are passed.
 */
public class Main {

  /**
   * The main method is the entry point of the application. It initializes the
   * ImageController and ImageView classes and processes commands interactively
   * from the terminal until the user exits, or it runs a default script if no
   * arguments are provided.
   *
   * @param args command-line arguments passed to the program,
   *             which can be commands or a script file path.
   */
  public static void main(String[] args) {
    ImageController controller = new ImageController();
    ImageView view = new ImageView(controller);
    Scanner scanner = new Scanner(System.in);

    // If command-line args are provided
    if (args.length > 0) {
      if (args[0].endsWith(".txt")) {
        // If it's a script file, run it
        String scriptFilePath = args[0];
        view.runScript(scriptFilePath);
      } else {
        view.processCommand(args);
        System.out.println("Interactive mode started. Type 'exit' to quit.");

        while (true) {
          System.out.print("> ");  // Print prompt for user input
          String userInput = scanner.nextLine().trim();

          if (userInput.equalsIgnoreCase("exit")) {
            System.out.println("Exiting...");
            break; // Exit the loop and close the application
          }

          // Process the user's command
          view.processCommand(userInput.split(" "));
        }
      }
    } else {
      // If no arguments are provided, run the default script
      String defaultScriptFilePath = "src/script.txt";
      view.runScript(defaultScriptFilePath);
    }

    scanner.close();  // Close the scanner when done
  }
}
