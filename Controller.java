package controller;

import java.io.IOException;

/**
 * The Controller interface defines methods for image processing tasks, including loading, saving,
 * manipulating, and visualizing images.
 */
public interface Controller {

  /**
   * Starts the image processing by reading a script file.
   *
   * @param scriptFilePath The path to the script file containing processing instructions.
   */
  void start(String scriptFilePath);

  /**
   * Loads an image from a specified path and associates it with a given name.
   *
   * @param path The path to the image file.
   * @param name The name to associate with the loaded image.
   * @throws IOException if an error occurs while loading the image.
   */
  void loadImage(String path, String name) throws IOException;

  /**
   * Saves the specified image to a given path.
   *
   * @param path The path where the image should be saved.
   * @param name The name of the image to be saved.
   * @throws IOException if an error occurs while saving the image.
   */
  void saveImage(String path, String name) throws IOException;

  /**
   * Adjusts the brightness of the specified image.
   *
   * @param increment  The amount to adjust the brightness (positive to increase, negative to
   *                   decrease).
   * @param sourceName The name of the source image to be adjusted.
   * @param destName   The name to associate with the adjusted image.
   */
  void adjustBrightness(int increment, String sourceName, String destName);

  /**
   * Flips the specified image horizontally.
   *
   * @param sourceName The name of the source image to be flipped.
   * @param destName   The name to associate with the flipped image.
   */
  void flipHorizontally(String sourceName, String destName);

  /**
   * Flips the specified image vertically.
   *
   * @param sourceName The name of the source image to be flipped.
   * @param destName   The name to associate with the flipped image.
   */
  void flipVertically(String sourceName, String destName);

  /**
   * Converts the specified source image to greyscale and stores it under the given destination
   * name.
   *
   * @param sourceName the name of the source image to be converted
   * @param destName   the name under which the greyscale image will be stored
   */
  void greyScale(String sourceName, String destName);

  /**
   * Extracts the red component of the specified image.
   *
   * @param sourceName The name of the source image from which to extract the red component.
   * @param destName   The name to associate with the extracted red component image.
   */
  void redComponent(String sourceName, String destName);

  /**
   * Extracts the green component of the specified image.
   *
   * @param sourceName The name of the source image from which to extract the green component.
   * @param destName   The name to associate with the extracted green component image.
   */
  void greenComponent(String sourceName, String destName);

  /**
   * Extracts the blue component of the specified image.
   *
   * @param sourceName The name of the source image from which to extract the blue component.
   * @param destName   The name to associate with the extracted blue component image.
   */
  void blueComponent(String sourceName, String destName);

  /**
   * Visualizes the luminance of the specified image and extracts its components.
   *
   * @param sourceName   The name of the source image to be visualized.
   * @param destBaseName The base name for the extracted luminance components.
   */
  void visualizeLuma(String sourceName, String destBaseName);

  /**
   * Visualizes the value of the specified image and extracts its components.
   *
   * @param sourceName   The name of the source image to be visualized.
   * @param destBaseName The base name for the extracted value components.
   */
  void visualizeValue(String sourceName, String destBaseName);

  /**
   * Visualizes the intensity of the specified image and extracts its components.
   *
   * @param sourceName   The name of the source image to be visualized.
   * @param destBaseName The base name for the extracted intensity components.
   */
  void visualizeIntensity(String sourceName, String destBaseName);

  /**
   * Splits the RGB components of the specified image.
   *
   * @param sourceName The name of the source image to be split.
   * @param redDest    The name to associate with the extracted red component image.
   * @param greenDest  The name to associate with the extracted green component image.
   * @param blueDest   The name to associate with the extracted blue component image.
   */
  void rgbSplit(String sourceName, String redDest, String greenDest, String blueDest);

  /**
   * Combines the RGB components into a single image.
   *
   * @param destName  The name to associate with the combined image.
   * @param redName   The name of the red component image.
   * @param greenName The name of the green component image.
   * @param blueName  The name of the blue component image.
   */
  void rgbCombine(String destName, String redName, String greenName, String blueName);

  /**
   * Blurs the specified image.
   *
   * @param sourceName The name of the source image to be blurred.
   * @param destName   The name to associate with the blurred image.
   */
  void blurImage(String sourceName, String destName);

  /**
   * Sharpens the specified image.
   *
   * @param sourceName The name of the source image to be sharpened.
   * @param destName   The name to associate with the sharpened image.
   */
  void sharpenImage(String sourceName, String destName);

  /**
   * Applies a sepia tone effect to the specified image.
   *
   * @param sourceName The name of the source image to be processed.
   * @param destName   The name to associate with the sepia-toned image.
   */
  void sepiaToneImage(String sourceName, String destName);

  // New Methods based on ImageProcessor class:

  /**
   * Compresses the specified image to a given quality level and saves it under the given name.
   *
   * @param sourceName The name of the source image to be compressed.
   * @param quality    The quality level to compress the image to.
   * @param destName   The name to associate with the compressed image.
   */
  void compressImage(int quality, String sourceName, String destName);

  /**
   * Generates a histogram of the specified image.
   *
   * @param sourceName The name of the source image to generate the histogram from.
   * @param destName   The name to associate with the histogram image.
   */
  void generateHistogram(String sourceName, String destName);

  /**
   * Applies color correction to the specified image and saves it under the given name.
   *
   * @param sourceName The name of the source image to be color corrected.
   * @param destName   The name to associate with the color corrected image.
   */
  void colorCorrect(String sourceName, String destName);

  /**
   * Adjusts the levels of the specified image and saves it under the given name.
   *
   * @param sourceName The name of the source image to adjust the levels of.
   * @param min        The minimum intensity value to apply.
   * @param max        The maximum intensity value to apply.
   * @param mid        The mid-intensity value to apply.
   * @param destName   The name to associate with the adjusted image.
   */
  void levelsAdjust(int min, int max, int mid, String sourceName, String destName);

  /**
   * Applies a split-view effect to the specified image and saves it under the given name.
   *
   * @param sourceName     The name of the source image to be processed.
   * @param destName       The name to associate with the split-view image.
   * @param viewType       The type of view (e.g., "value", "intensity", etc.) to apply.
   * @param componentIndex The component index (e.g., 0 for red, 1 for green, etc.).
   */
  void applySplitView(String viewType, int componentIndex, String sourceName, String destName);

  void downscaleImage(String sourceName, String destName, int targetWidth, int targetHeight);
}
