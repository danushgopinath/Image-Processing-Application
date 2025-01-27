package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import model.Model;
import model.Image;
import model.ImageProcessor;

/**
 * The ImageController class handles the processing of image commands and manages the loading,
 * saving, and manipulation of images.
 */
public class ImageController implements Controller {

  private final Map<String, Model> images = new HashMap<>();
  private final Map<String, Consumer<String[]>> commandMap = new HashMap<>();

  /**
   * Constructor for the ImageController class.
   */
  public ImageController() {
    commandMap.put("load", tokens -> loadImage(tokens[1], tokens[2]));
    commandMap.put("save", tokens -> saveImage(tokens[1], tokens[2]));
    commandMap.put("brighten",
        tokens -> adjustBrightness(Integer.parseInt(tokens[1]), tokens[2], tokens[3]));
    commandMap.put("horizontal-flip", tokens -> flipHorizontally(tokens[1], tokens[2]));
    commandMap.put("vertical-flip", tokens -> flipVertically(tokens[1], tokens[2]));
    commandMap.put("red-component", tokens -> redComponent(tokens[1], tokens[2]));
    commandMap.put("green-component", tokens -> greenComponent(tokens[1], tokens[2]));
    commandMap.put("blue-component", tokens -> blueComponent(tokens[1], tokens[2]));
    commandMap.put("value-component", tokens -> visualizeValue(tokens[1], tokens[2]));
    commandMap.put("intensity-component", tokens -> visualizeIntensity(tokens[1], tokens[2]));
    commandMap.put("luma-component", tokens -> visualizeLuma(tokens[1], tokens[2]));
    commandMap.put("greyscale", tokens -> greyScale(tokens[1], tokens[2]));
    commandMap.put("rgb-combine", tokens -> rgbCombine(tokens[1], tokens[2], tokens[3], tokens[4]));
    commandMap.put("rgb-split", tokens -> rgbSplit(tokens[1], tokens[2], tokens[3], tokens[4]));
    commandMap.put("blur", tokens -> blurImage(tokens[1], tokens[2]));
    commandMap.put("sharpen", tokens -> sharpenImage(tokens[1], tokens[2]));
    commandMap.put("sepia", tokens -> sepiaToneImage(tokens[1], tokens[2]));
    commandMap.put("compress",
        tokens -> compressImage(Integer.parseInt(tokens[1]), tokens[2], tokens[3]));
    commandMap.put("histogram", tokens -> generateHistogram(tokens[1], tokens[2]));
    commandMap.put("color-correct", tokens -> colorCorrect(tokens[1], tokens[2]));
    commandMap.put("levels-adjust",
        tokens -> levelsAdjust(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),
            Integer.parseInt(tokens[3]), tokens[4], tokens[5]));
    commandMap.put("split-view",
        tokens -> applySplitView(tokens[1], Integer.parseInt(tokens[2]), tokens[3], tokens[4]));
    commandMap.put("downscale",
        tokens -> downscaleImage(tokens[1], tokens[2], Integer.parseInt(tokens[3]),
            Integer.parseInt(tokens[4])));
  }

  /**
   * Starts the controller by executing the script located at the provided file path.
   *
   * @param scriptFilePath the path to the script file containing image commands
   */
  public void start(String scriptFilePath) {
    try {
      runScript(scriptFilePath);
    } catch (IOException e) {
      System.out.println("Error reading script: " + e.getMessage());
    }
  }

  /**
   * Reads and processes commands from the specified script file.
   *
   * @param scriptFilePath the path to the script file
   * @throws IOException if an error occurs while reading the file
   */
  public void runScript(String scriptFilePath) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(scriptFilePath));
    String line;
    while ((line = reader.readLine()) != null) {
      line = line.split("#")[0].trim(); // Remove comments and trim whitespace
      if (!line.isEmpty()) {
        processCommand(line);
      }
    }
    reader.close();
  }

  /**
   * Processes a single command by parsing it and executing the corresponding action.
   *
   * @param command the command string to process
   */
  public void processCommand(String command) {
    String[] tokens = command.split(" ");
    String action = tokens[0].toLowerCase();

    // Retrieve the command handler from the map and execute it
    Consumer<String[]> commandHandler = commandMap.get(action);
    if (commandHandler != null) {
      commandHandler.accept(tokens);
    } else {
      System.out.println("Unknown command: " + command);
    }
  }

  @Override
  public void loadImage(String path, String name) {
    try {
      Model image = new Image(path);  // Since Image implements Model, this is valid.
      images.put(name, image);
      System.out.println("Loaded image: " + name);
    } catch (IOException e) {
      System.out.println("Error loading image: " + e.getMessage());
    }
  }

  /**
   * Retrieves an image from the hashmap using the filename and converts it to a BufferedImage.
   *
   * @param filename the name of the image to retrieve.
   * @return a BufferedImage of the image, or null if the image is not found or an error occurs.
   */
  public BufferedImage getImage(String filename) {
    try {
      // Retrieve the image from the hashmap using the filename as the key
      Model modelImage = images.get(filename);

      // Check if the image is found in the map
      if (modelImage == null) {
        throw new IOException("Image not found in the hashmap: " + filename);
      }

      // Assuming modelImage is of type Image, cast it
      Image image = (Image) modelImage;

      // Convert the Image object to a BufferedImage and return it
      return image.toBufferedImage();
    } catch (IOException e) {
      System.out.println("Error retrieving image: " + e.getMessage());
      return null;
    }
  }

  @Override
  public void saveImage(String path, String name) {
    Model image = images.get(name);
    if (image != null) {
      try {
        image.save(path);  // Assuming save() method is defined in Model.
        System.out.println("Saved image to: " + name);
      } catch (IOException e) {
        System.out.println("Error saving image: " + e.getMessage());
      }
    } else {
      System.out.println("Image not found: " + name);
    }
  }

  @Override
  public void adjustBrightness(int increment, String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model brightenedImage = ImageProcessor.adjustBrightness((Image) sourceImage,
            increment);  // Cast to Image where necessary
        images.put(destName, brightenedImage);
        System.out.println("Brightened image: " + destName);
      } catch (Exception e) {
        System.out.println("Error brightening image: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void flipHorizontally(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model flippedImage = ImageProcessor.flipHorizontally((Image) sourceImage);
        images.put(destName, flippedImage);
        System.out.println("Horizontally flipped image: " + destName);
      } catch (Exception e) {
        System.out.println("Error flipping image: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void flipVertically(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model flippedImage = ImageProcessor.flipVertically((Image) sourceImage);
        images.put(destName, flippedImage);
        System.out.println("Vertically flipped image: " + destName);
      } catch (Exception e) {
        System.out.println("Error flipping image: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void greyScale(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model greyImage = ImageProcessor.greyscaleImage((Image) sourceImage);
        images.put(destName, greyImage);
        System.out.println("Greyscale image saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error saving greyscale component: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void redComponent(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model redImage = ImageProcessor.extractColorComponent((Image) sourceImage, "red");
        images.put(destName, redImage);
        System.out.println("Extracted red component to: " + destName);
      } catch (Exception e) {
        System.out.println("Error extracting red component: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void greenComponent(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model greenImage = ImageProcessor.extractColorComponent((Image) sourceImage, "green");
        images.put(destName, greenImage);
        System.out.println("Extracted green component to: " + destName);
      } catch (Exception e) {
        System.out.println("Error extracting green component: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void blueComponent(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model blueImage = ImageProcessor.extractColorComponent((Image) sourceImage, "blue");
        images.put(destName, blueImage);
        System.out.println("Extracted blue component to: " + destName);
      } catch (Exception e) {
        System.out.println("Error extracting blue component: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void visualizeValue(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model valueImage = ImageProcessor.valueImage((Image) sourceImage);
        images.put(destName, valueImage);
        System.out.println("Value component saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error extracting value component: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void visualizeIntensity(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model intensityImage = ImageProcessor.intensityImage((Image) sourceImage);
        images.put(destName, intensityImage);
        System.out.println("Intensity component saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error extracting intensity component: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void visualizeLuma(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model lumaImage = ImageProcessor.greyscaleImage((Image) sourceImage);
        images.put(destName, lumaImage);
        System.out.println("Luma component saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error extracting luma component: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void rgbSplit(String sourceName, String redName, String greenName, String blueName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        // Extract RGB components
        Model redImage = ImageProcessor.extractColorComponent((Image) sourceImage, "red");
        Model greenImage = ImageProcessor.extractColorComponent((Image) sourceImage, "green");
        Model blueImage = ImageProcessor.extractColorComponent((Image) sourceImage, "blue");

        // Store each component
        images.put(redName, redImage);
        images.put(greenName, greenImage);
        images.put(blueName, blueImage);

        System.out.println("RGB components extracted and saved.");
      } catch (Exception e) {
        System.out.println("Error during RGB splitting: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void rgbCombine(String destName, String redName, String greenName, String blueName) {
    Model redImage = images.get(redName);
    Model greenImage = images.get(greenName);
    Model blueImage = images.get(blueName);
    if (redImage != null && greenImage != null && blueImage != null) {
      try {
        Model combinedImage = ImageProcessor.rgbCombine((Image) redImage, (Image) greenImage,
            (Image) blueImage);
        images.put(destName, combinedImage);
        System.out.println("RGB combined image saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error during RGB combining: " + e.getMessage());
      }
    } else {
      System.out.println("One or more source images not found.");
    }
  }

  @Override
  public void blurImage(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model blurredImage = ImageProcessor.blurImage((Image) sourceImage);
        images.put(destName, blurredImage);
        System.out.println("Blurred image saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error during image blurring: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void sharpenImage(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model sharpenedImage = ImageProcessor.sharpenImage((Image) sourceImage);
        images.put(destName, sharpenedImage);
        System.out.println("Sharpened image saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error during image sharpening: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void sepiaToneImage(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model sepiaImage = ImageProcessor.sepiaToneImage((Image) sourceImage);
        images.put(destName, sepiaImage);
        System.out.println("Sepia-toned image saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error applying sepia tone: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void compressImage(int quality, String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model compressedImage = ImageProcessor.compressImage((Image) sourceImage, quality);
        images.put(destName, compressedImage);
        System.out.println("Compressed image saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error compressing image: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void generateHistogram(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model histogramImage = ImageProcessor.generateHistogram((Image) sourceImage);
        images.put(destName, histogramImage);
        System.out.println("Histogram image saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error generating histogram: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void colorCorrect(String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model colorCorrectedImage = ImageProcessor.colorCorrectImage((Image) sourceImage);
        images.put(destName, colorCorrectedImage);
        System.out.println("Color-corrected image saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error during color correction: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void levelsAdjust(int min, int max, int mid, String sourceName, String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model adjustedImage = ImageProcessor.levelsAdjust((Image) sourceImage, min, max, mid);
        images.put(destName, adjustedImage);
        System.out.println("Levels adjusted image saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error adjusting levels: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void applySplitView(String viewType, int componentIndex, String sourceName,
      String destName) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model splitViewImage = ImageProcessor.applySplitView(viewType, componentIndex,
            (Image) sourceImage);
        images.put(destName, splitViewImage);
        System.out.println("Split view image saved as: " + destName);
      } catch (Exception e) {
        System.out.println("Error applying split view: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  @Override
  public void downscaleImage(String sourceName, String destName, int targetWidth,
      int targetHeight) {
    Model sourceImage = images.get(sourceName);
    if (sourceImage != null) {
      try {
        Model downscaledImage = ImageProcessor.downscaleImage((Image) sourceImage, targetWidth,
            targetHeight);
        images.put(destName, downscaledImage);
        System.out.println("Downscaled image: " + destName);
      } catch (Exception e) {
        System.out.println("Error downscaling image: " + e.getMessage());
      }
    } else {
      System.out.println("Source image not found: " + sourceName);
    }
  }

  /**
   * Retrieves an image from the hashmap.
   *
   * @param imageName the name of the image in hashmap.
   * @return the image from the hashmap.
   */
  public Model getImageFromMap(String imageName) {
    // Retrieve the image from the hashmap using the filename as the key
    Model image = images.get(imageName);

    if (image == null) {
      System.out.println("Image not found in the map: " + imageName);
    }

    return image;
  }


}