package model;

import java.io.IOException;

/**
 * The Model interface defines methods for manipulating and managing images, including loading,
 * saving, retrieving pixel values, and displaying the image.
 */
public interface Model {

  /**
   * Loads an image from a specified file.
   *
   * @param filePath The path to the image file to be loaded.
   * @throws IOException if an error occurs while loading the image.
   */
  void load(String filePath) throws IOException;

  /**
   * Saves the current image to a specified file.
   *
   * @param filename The name of the file where the image should be saved.
   * @throws IOException if an error occurs while saving the image.
   */
  void save(String filename) throws IOException;

  /**
   * Sets the pixel value at a specific coordinate (x, y).
   *
   * @param x   The x-coordinate of the pixel to be set.
   * @param y   The y-coordinate of the pixel to be set.
   * @param rgb An array containing the RGB values to set (0-255 for each channel).
   */
  void setPixel(int x, int y, int[] rgb);

  /**
   * Gets the pixel value at a specific coordinate (x, y).
   *
   * @param x The x-coordinate of the pixel to be retrieved.
   * @param y The y-coordinate of the pixel to be retrieved.
   * @return An array containing the RGB values of the pixel (0-255 for each channel).
   */
  int[] getPixel(int x, int y);

  /**
   * Displays the image in the console. The representation may be text-based or a summary of the
   * image.
   */
  void display();

  /**
   * Gets the width of the image.
   *
   * @return The width of the image in pixels.
   */
  int getWidth();

  /**
   * Gets the height of the image.
   *
   * @return The height of the image in pixels.
   */
  int getHeight();
}
