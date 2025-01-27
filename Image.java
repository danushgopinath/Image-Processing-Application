package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Represents an image that can be loaded, manipulated, and saved. Supports loading from standard
 * image formats and PPM (P3) format.
 */
public class Image implements Model {

  private BufferedImage img; // Store the BufferedImage directly
  private int[][][] pixels; // 3D array for RGB values

  /**
   * Constructor to load an image from a file.
   *
   * @param filePath the path to the image file.
   * @throws IOException if there is an error reading the file.
   */
  public Image(String filePath) throws IOException {
    load(filePath); // Call the interface method to load
  }

  /**
   * Constructor for an empty image of given width and height.
   *
   * @param width  the width of the new image.
   * @param height the height of the new image.
   */
  public Image(int width, int height) {
    this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    this.pixels = new int[height][width][3]; // Initialize with 3 for R, G, B
  }

  /**
   * Loads an image from a specified file path.
   *
   * @param filePath the path to the image file.
   * @throws IOException if the file cannot be read.
   */
  @Override
  public void load(String filePath) throws IOException {
    if (filePath.toLowerCase().endsWith(".ppm")) {
      loadPPMImage(filePath); // Load PPM image
    } else {
      loadStandardImage(filePath); // Load standard image
    }
  }

  /**
   * Loads a standard image (e.g., PNG, JPG, etc.) from the specified file path.
   *
   * @param filePath the path to the image file.
   * @throws IOException if the file cannot be read.
   */
  private void loadStandardImage(String filePath) throws IOException {
    img = ImageIO.read(new File(filePath)); // Load the image into BufferedImage
    int width = img.getWidth();
    int height = img.getHeight();
    this.pixels = new int[height][width][3]; // Initialize with 3 for R, G, B

    // Load pixel data into the array
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = img.getRGB(x, y);
        pixels[y][x][0] = (rgb >> 16) & 0xFF; // Red
        pixels[y][x][1] = (rgb >> 8) & 0xFF;  // Green
        pixels[y][x][2] = rgb & 0xFF;         // Blue
      }
    }
  }

  /**
   * Loads a PPM (P3 format) image from the specified file path.
   *
   * @param filePath the path to the PPM file.
   * @throws IOException if the file cannot be read or the format is unsupported.
   */
  private void loadPPMImage(String filePath) throws IOException {
    try (Scanner scanner = new Scanner(new File(filePath))) {
      // Read the PPM header
      String format = scanner.next();
      if (!format.equals("P3")) {
        throw new IOException("Unsupported PPM format: " + format);
      }

      // Skip comments and read width, height
      while (scanner.hasNext()) {
        String next = scanner.next();
        if (next.startsWith("#")) {
          scanner.nextLine(); // Skip the comment line
        } else {
          // Read width and height
          int width = Integer.parseInt(next);
          int height = scanner.nextInt();
          int maxColorValue = scanner.nextInt();

          this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
          this.pixels = new int[height][width][3]; // Initialize with 3 for R, G, B

          // Read the pixel values
          for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
              int r = scanner.nextInt();
              int g = scanner.nextInt();
              int b = scanner.nextInt();
              pixels[y][x][0] = r;
              pixels[y][x][1] = g;
              pixels[y][x][2] = b;
            }
          }
          break; // Exit the loop after reading width and height
        }
      }
    }
  }

  /**
   * Sets the RGB value of a specific pixel.
   *
   * @param x   the x-coordinate of the pixel.
   * @param y   the y-coordinate of the pixel.
   * @param rgb an array containing the RGB values.
   * @throws IllegalArgumentException  if the RGB values are out of range.
   * @throws IndexOutOfBoundsException if the pixel coordinates are out of bounds.
   */
  @Override
  public void setPixel(int x, int y, int[] rgb) {
    if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
      if (rgb.length == 3 && isValidRgb(rgb)) {
        pixels[y][x] = rgb;
      } else {
        throw new IllegalArgumentException("RGB values must be in the range 0-255.");
      }
    } else {
      throw new IndexOutOfBoundsException("Pixel coordinates out of bounds.");
    }
  }

  /**
   * Retrieves the RGB value of a specific pixel.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return an array containing the RGB values.
   * @throws IndexOutOfBoundsException if the pixel coordinates are out of bounds.
   */
  @Override
  public int[] getPixel(int x, int y) {
    if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
      return pixels[y][x];
    } else {
      throw new IndexOutOfBoundsException("Pixel coordinates out of bounds.");
    }
  }

  /**
   * Saves the current image to a file.
   *
   * @param filename the name of the file to save the image to.
   * @throws IOException if there is an error writing to the file.
   */
  @Override
  public void save(String filename) throws IOException {
    if (filename.toLowerCase().endsWith(".ppm")) {
      savePPMImage(filename); // Save as PPM
    } else {
      BufferedImage imgToSave = new BufferedImage(getWidth(), getHeight(),
          BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < getHeight(); y++) {
        for (int x = 0; x < getWidth(); x++) {
          int[] rgb = getPixel(x, y);
          int color = new Color(rgb[0], rgb[1], rgb[2]).getRGB();
          imgToSave.setRGB(x, y, color);
        }
      }
      ImageIO.write(imgToSave, "png", new File(filename)); // Save as PNG (change format as needed)
    }
  }

  /**
   * Saves the current image in PPM format.
   *
   * @param filename the name of the file to save the PPM image to.
   * @throws IOException if there is an error writing to the file.
   */
  private void savePPMImage(String filename) throws IOException {
    try (PrintWriter writer = new PrintWriter(new File(filename))) {
      writer.println("P3");
      writer.println(getWidth() + " " + getHeight());
      writer.println(255); // Max color value (typically 255)

      for (int y = 0; y < getHeight(); y++) {
        for (int x = 0; x < getWidth(); x++) {
          int[] rgb = getPixel(x, y);
          writer.print(rgb[0] + " " + rgb[1] + " " + rgb[2] + " ");
        }
        writer.println();
      }
    }
  }

  /**
   * Displays the pixel values of the image in the console.
   */
  @Override
  public void display() {
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        System.out.println(
            "(" + pixels[y][x][0] + ", " + pixels[y][x][1] + ", " + pixels[y][x][2] + ") ");
      }
      System.out.println();
    }
  }

  /**
   * Returns the width of the image.
   *
   * @return the width of the image.
   */
  @Override
  public int getWidth() {
    return img.getWidth();
  }

  /**
   * Returns the height of the image.
   *
   * @return the height of the image.
   */
  @Override
  public int getHeight() {
    return img.getHeight();
  }

  /**
   * Validates that the provided RGB values are within the range of 0 to 255.
   *
   * @param rgb the array containing RGB values.
   * @return true if the RGB values are valid; false otherwise.
   */
  private boolean isValidRgb(int[] rgb) {
    for (int value : rgb) {
      if (value < 0 || value > 255) {
        return false;
      }
    }
    return true;
  }


  /**
   * Converts the current image represented by pixel data into a BufferedImage.
   *
   * @return a BufferedImage representing the current image, with same dimensions and pixel data.
   */
  public BufferedImage toBufferedImage() {
    // If the img is null, construct it manually from the pixel array.
    int width = getWidth();
    int height = getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB); // Use TYPE_INT_ARGB for alpha support

    // Convert pixel data into the BufferedImage
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] rgb = getPixel(x, y); // Get pixel RGB values

        // Clamp RGB values to be within [0, 255]
        int r = Math.min(255, Math.max(0, rgb[0]));
        int g = Math.min(255, Math.max(0, rgb[1]));
        int b = Math.min(255, Math.max(0, rgb[2]));

        // Convert RGB to a single color value
        int color = new Color(r, g, b).getRGB();
        bufferedImage.setRGB(x, y, color);
      }
    }

    return bufferedImage;
  }


}
