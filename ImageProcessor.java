package model;

import java.util.Arrays;

/**
 * The ImageProcessor class provides various methods to manipulate images, including adjusting
 * brightness, flipping, converting to greyscale, applying sepia tone, and more.
 */
public class ImageProcessor {

  /**
   * Adjusts the brightness of the given image.
   *
   * @param image  the original image to be adjusted
   * @param factor the factor by which to adjust the brightness
   * @return a new image with adjusted brightness
   */
  public static Image adjustBrightness(Image image, float factor) {
    int width = image.getWidth();
    int height = image.getHeight();
    Image adjustedImage = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] pixelColor = image.getPixel(x, y);

        // Adjust RGB values
        int r = pixelColor[0] + (int) factor;
        int g = pixelColor[1] + (int) factor;
        int b = pixelColor[2] + (int) factor;

        // Ensure RGB values are within the range [0, 255]
        r = (r < 0) ? 0 : Math.min(r, 255);
        g = (g < 0) ? 0 : Math.min(g, 255);
        b = (b < 0) ? 0 : Math.min(b, 255);

        adjustedImage.setPixel(x, y, new int[]{r, g, b});
      }
    }
    return adjustedImage; // Return the adjusted image
  }

  /**
   * Flips the given image horizontally.
   *
   * @param originalImage the image to be flipped
   * @return a new image that is flipped horizontally
   */
  public static Image flipHorizontally(Image originalImage) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Image flippedImage = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Get the pixel from the original image
        int[] pixel = originalImage.getPixel(x, y);
        // Set the pixel in the flipped image
        flippedImage.setPixel(width - 1 - x, y, pixel);
      }
    }
    return flippedImage; // Return the flipped image
  }

  /**
   * Flips the given image vertically.
   *
   * @param originalImage the image to be flipped
   * @return a new image that is flipped vertically
   */
  public static Image flipVertically(Image originalImage) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Image flippedImage = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Get the pixel from the original image
        int[] pixel = originalImage.getPixel(x, y);
        // Set the pixel in the flipped image
        flippedImage.setPixel(x, height - 1 - y, pixel);
      }
    }
    return flippedImage; // Return the flipped image
  }

  /**
   * Abstracts the process of applying a pixel transformation.
   *
   * @param rgb                the RGB values of the pixel (in the form of an array [R, G, B])
   * @param transformationType the type of transformation to apply ("greyscale", "sepia", "value",
   *                           "intensity", "luma")
   * @return the transformed pixel (new RGB values)
   */
  private static int[] transformPixel(int[] rgb, String transformationType) {
    int r = rgb[0];
    int g = rgb[1];
    int b = rgb[2];

    switch (transformationType.toLowerCase()) {
      case "greyscale":
        int greyValue = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
        return new int[]{greyValue, greyValue, greyValue};

      case "sepia":
        // Apply the sepia tone formula
        int newRed = (int) (0.393 * r + 0.769 * g + 0.189 * b);
        int newGreen = (int) (0.349 * r + 0.686 * g + 0.168 * b);
        int newBlue = (int) (0.272 * r + 0.534 * g + 0.131 * b);

        // Clamp values between 0 and 255
        newRed = Math.min(255, newRed);
        newGreen = Math.min(255, newGreen);
        newBlue = Math.min(255, newBlue);

        return new int[]{newRed, newGreen, newBlue};

      case "value":
        // Calculate the Value component (maximum of R, G, B)
        int value = Math.max(r, Math.max(g, b));
        return new int[]{value, value, value};

      case "intensity":
        // Calculate the Intensity component (average of R, G, B)
        int intensity = (r + g + b) / 3;
        return new int[]{intensity, intensity, intensity};

      default:
        throw new IllegalArgumentException("Unknown transformation type: " + transformationType);
    }
  }

  /**
   * Applies a transformation to each pixel of the image based on the given transformation type.
   *
   * @param originalImage      the image to be transformed
   * @param transformationType the type of transformation to apply
   * @return a new image with the transformation applied
   */
  private static Image applyImageTransformation(Image originalImage, String transformationType) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Image transformedImage = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] pixel = originalImage.getPixel(x, y); // Get the original pixel (RGB)
        int[] transformedPixel = transformPixel(pixel,
            transformationType); // Apply the transformation
        transformedImage.setPixel(x, y, transformedPixel);
      }
    }

    return transformedImage; // Return the transformed image
  }

  /**
   * Converts the given image to greyscale using the Luma formula.
   *
   * @param originalImage the image to be converted
   * @return a new greyscale image
   */
  public static Image greyscaleImage(Image originalImage) {
    return applyImageTransformation(originalImage, "greyscale");
  }

  /**
   * Converts the given image to sepia tone.
   *
   * @param originalImage the image to be converted
   * @return a new sepia tone image
   */
  public static Image sepiaToneImage(Image originalImage) {
    return applyImageTransformation(originalImage, "sepia");
  }

  /**
   * Converts the given image to its Value component.
   *
   * @param originalImage the image to be converted
   * @return a new image containing the Value component
   */
  public static Image valueImage(Image originalImage) {
    return applyImageTransformation(originalImage, "value");
  }

  /**
   * Converts the given image to its Intensity component.
   *
   * @param originalImage the image to be converted
   * @return a new image containing the Intensity component
   */
  public static Image intensityImage(Image originalImage) {
    return applyImageTransformation(originalImage, "intensity");
  }

  /**
   * Extracts a specific color component (Red, Green, or Blue) of the given image.
   *
   * @param sourceImage the image from which to extract the color component
   * @param color       the color component to extract ("red", "green", or "blue")
   * @return a new image containing only the specified color component
   */
  public static Image extractColorComponent(Image sourceImage, String color) {
    int width = sourceImage.getWidth();
    int height = sourceImage.getHeight();
    Image colorImage = new Image(width, height);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] rgb = sourceImage.getPixel(j, i);
        int[] newPixel = new int[3]; // Initialize a new pixel array for RGB

        switch (color.toLowerCase()) {
          case "red":
            newPixel[0] = newPixel[1] = newPixel[2] = rgb[0];
            break;
          case "green":
            newPixel[0] = newPixel[1] = newPixel[2] = rgb[1];
            break;
          case "blue":
            newPixel[0] = newPixel[1] = newPixel[2] = rgb[2];
            break;
          default:
            throw new IllegalArgumentException("Invalid color specified: " + color);
        }

        colorImage.setPixel(j, i, newPixel);
      }
    }

    return colorImage; // Return the image with the specified color component
  }

  /**
   * Combines the given RGB component images into a single image.
   *
   * @param redImage   the image containing the red component
   * @param greenImage the image containing the green component
   * @param blueImage  the image containing the blue component
   * @return a new image that combines the RGB components
   */
  public static Image rgbCombine(Image redImage, Image greenImage, Image blueImage) {
    int width = redImage.getWidth();
    int height = redImage.getHeight();
    Image combinedImage = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] redPixel = redImage.getPixel(x, y);
        int[] greenPixel = greenImage.getPixel(x, y);
        int[] bluePixel = blueImage.getPixel(x, y);

        combinedImage.setPixel(x, y, new int[]{redPixel[0], greenPixel[1], bluePixel[2]});
      }
    }
    return combinedImage; // Return combined image
  }

  /**
   * Applies a blur effect to the given image using a blur kernel.
   *
   * @param originalImage the image to be blurred
   * @return a new image with the blur effect applied
   */
  public static Image blurImage(Image originalImage) {
    // Define a blur kernel
    float[][] blurKernel = {
        {1f / 16, 1f / 8, 1f / 16},
        {1f / 8, 1f / 4, 1f / 8},
        {1f / 16, 1f / 8, 1f / 16}
    };

    // Call applyKernel with the blur kernel
    return applyKernel(originalImage, blurKernel);
  }

  /**
   * Sharpens the given image using a sharpening kernel.
   *
   * @param originalImage the image to be sharpened
   * @return a new image with the sharpening effect applied
   */
  public static Image sharpenImage(Image originalImage) {
    // Define a sharpening kernel
    float[][] sharpenKernel = {
        {-1f / 8, -1f / 8, -1f / 8, -1f / 8, -1f / 8},
        {-1f / 8, 1f / 4, 1f / 4, 1f / 4, -1f / 8},
        {-1f / 8, 1f / 4, 1f / 4, 1f / 4, -1f / 8},
        {-1f / 8, 1f / 4, 1f / 4, 1f / 4, -1f / 8},
        {-1f / 8, -1f / 8, -1f / 8, -1f / 8, -1f / 8}
    };

    // Call applyKernel with the sharpening kernel
    return applyKernel(originalImage, sharpenKernel);
  }

  /**
   * Applies a custom kernel to the given image.
   *
   * @param originalImage the image to be processed
   * @param kernel        the kernel to apply
   * @return a new image after applying the kernel
   */
  public static Image applyKernel(Image originalImage, float[][] kernel) {
    int kernelHeight = kernel.length;
    int kernelWidth = kernel[0].length;
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int kernelOffsetY = kernelHeight / 2;
    int kernelOffsetX = kernelWidth / 2;
    Image processedImage = new Image(width, height);

    // Apply the kernel
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        float[] newColor = new float[3]; // Use float to avoid early clamping
        float kernelSum = 0; // Initialize kernel sum for normalization

        // Apply the kernel to the current pixel
        for (int ky = 0; ky < kernelHeight; ky++) {
          for (int kx = 0; kx < kernelWidth; kx++) {
            int newY = y + ky - kernelOffsetY;
            int newX = x + kx - kernelOffsetX;

            // Check bounds
            if (newY >= 0 && newY < height && newX >= 0 && newX < width) {
              int[] pixel = originalImage.getPixel(newX, newY);
              for (int channel = 0; channel < 3; channel++) {
                newColor[channel] += pixel[channel] * kernel[ky][kx]; // Accumulate new color
              }
              kernelSum += kernel[ky][kx]; // Accumulate kernel sum
            }
          }
        }

        // Normalize and clamp values before setting the new pixel
        for (int channel = 0; channel < 3; channel++) {
          newColor[channel] = Math.min(Math.max(newColor[channel] / kernelSum, 0), 255);
        }
        processedImage.setPixel(x, y,
            new int[]{(int) newColor[0], (int) newColor[1], (int) newColor[2]});
      }
    }
    return processedImage;
  }

  /**
   * Compresses an image using Haar wavelet transform and thresholding.
   *
   * @param sourceImage The source image to be compressed.
   * @param quality     The desired quality level of the compressed image (between 0 and 100).
   * @return The compressed image.
   */
  public static Image compressImage(Image sourceImage, int quality) {
    int width = sourceImage.getWidth();
    int height = sourceImage.getHeight();

    // Calculate padded width and height to the next power of 2
    int paddedWidth = nextPowerOf2(width);
    int paddedHeight = nextPowerOf2(height);

    // Initialize padded pixel array with zeros
    int[][][] paddedPixels = new int[paddedHeight][paddedWidth][3];

    // Copy pixels from the source image to the padded array
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] pixel = sourceImage.getPixel(x, y);
        paddedPixels[y][x] = pixel;
      }
    }

    // Apply Haar wavelet transform and thresholding on each color channel
    double compressionRatio = (100 - quality) / 1000.0; // Convert quality to compression ratio
    for (int c = 0; c < 3; c++) {
      int[][] channel = extractChannel(paddedPixels, c, paddedWidth, paddedHeight);
      double[][] doubleChannel = toDoubleArray(channel);
      doubleChannel = compress(doubleChannel, paddedWidth, compressionRatio);
      int[][] compressedChannel = toIntArray(doubleChannel);
      insertChannel(paddedPixels, compressedChannel, c, paddedWidth, paddedHeight);
    }

    // Create and return the compressed image from the padded pixels
    Image compressedImage = new Image(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        compressedImage.setPixel(x, y, paddedPixels[y][x]);
      }
    }

    return compressedImage;
  }

  /**
   * Applies compression to a channel using Haar wavelet transform and thresholding.
   *
   * @param channel          The image channel to compress.
   * @param size             The size of the channel (width or height).
   * @param compressionRatio The ratio used to apply the thresholding.
   * @return The compressed channel.
   */
  public static double[][] compress(double[][] channel, int size, double compressionRatio) {
    channel = haarTransform(channel, size);
    channel = applyThreshold(channel, compressionRatio); // Updated thresholding approach
    channel = inverseHaarTransform(channel, size);
    return channel;
  }

  /**
   * Applies a thresholding technique to compress the image by zeroing out small values.
   *
   * @param channel          The image channel to apply thresholding on.
   * @param compressionRatio The compression ratio to decide the threshold.
   * @return The thresholded channel.
   */
  private static double[][] applyThreshold(double[][] channel, double compressionRatio) {
    int totalElements = channel.length * channel[0].length;
    int thresholdIndex = (int) (totalElements * compressionRatio);

    // Flatten, sort, and calculate threshold
    double[] flatChannel = Arrays.stream(channel).flatMapToDouble(Arrays::stream).toArray();
    Arrays.sort(flatChannel);

    // Improved threshold calculation
    double thresholdValue = flatChannel[totalElements - thresholdIndex - 1];

    // Apply threshold
    for (int i = 0; i < channel.length; i++) {
      for (int j = 0; j < channel[0].length; j++) {
        if (Math.abs(channel[i][j]) < thresholdValue) {
          channel[i][j] = 0; // Zero out values below threshold
        }
      }
    }
    return channel;
  }

  /**
   * Performs the Haar wavelet transform on a given matrix.
   *
   * @param matrix The matrix to be transformed.
   * @param size   The size of the matrix (dimension).
   * @return The Haar-transformed matrix.
   */
  public static double[][] haarTransform(double[][] matrix, int size) {
    double[][] transformedMatrix = new double[size][size];

    for (int i = 0; i < size; i++) {
      transformedMatrix[i] = matrix[i].clone(); // Copy the original matrix
    }

    int c = size;
    while (c > 1) {
      // Transform rows
      for (int i = 0; i < c; i++) {
        transformedMatrix[i] = transformRow(transformedMatrix[i], c);
      }

      // Transform columns
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = transformedMatrix[i][j];
        }
        column = transformRow(column, c);
        for (int i = 0; i < c; i++) {
          transformedMatrix[i][j] = column[i];
        }
      }

      c /= 2;
    }

    return transformedMatrix;
  }

  /**
   * Performs the inverse Haar wavelet transform on a given matrix.
   *
   * @param matrix The matrix to be inverse-transformed.
   * @param size   The size of the matrix (dimension).
   * @return The inverse Haar-transformed matrix.
   */
  public static double[][] inverseHaarTransform(double[][] matrix, int size) {
    double[][] invertedMatrix = new double[size][size];

    // Clone the original matrix into the invertedMatrix
    for (int i = 0; i < size; i++) {
      invertedMatrix[i] = matrix[i].clone();
    }

    int c = 2;
    while (c <= size) {
      // Inverse transform on columns first
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = invertedMatrix[i][j];
        }
        column = inverseTransformRow(column, c);  // Inverse Haar transform on the column
        for (int i = 0; i < c; i++) {
          invertedMatrix[i][j] = column[i];
        }
      }

      // Then inverse transform on rows
      for (int i = 0; i < c; i++) {
        invertedMatrix[i] = inverseTransformRow(invertedMatrix[i], c);  // Inverse Haar on each row
      }

      c *= 2;  // Move to the next level (larger region)
    }

    return invertedMatrix;
  }

  /**
   * Extracts a single color channel from the image pixel data.
   *
   * @param pixels       The 3D pixel array of the image.
   * @param channelIndex The index of the color channel (0 for red, 1 for green, 2 for blue).
   * @param width        The width of the image.
   * @param height       The height of the image.
   * @return A 2D array representing the extracted color channel.
   */
  private static int[][] extractChannel(int[][][] pixels, int channelIndex, int width, int height) {
    int[][] channel = new int[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        channel[y][x] = pixels[y][x][channelIndex];
      }
    }
    return channel;
  }

  /**
   * Inserts a color channel back into the image pixel data.
   *
   * @param pixels       The 3D pixel array of the image.
   * @param channel      The 2D array of the color channel to insert.
   * @param channelIndex The index of the color channel (0 for red, 1 for green, 2 for blue).
   * @param width        The width of the image.
   * @param height       The height of the image.
   */
  private static void insertChannel(int[][][] pixels, int[][] channel,
      int channelIndex, int width, int height) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        pixels[y][x][channelIndex] = clamp(channel[y][x], 0, 255);
      }
    }
  }

  /**
   * Clamps an integer to the range [min, max].
   *
   * @param value The value to be clamped.
   * @param min   The minimum allowed value.
   * @param max   The maximum allowed value.
   * @return The clamped value.
   */
  private static int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }

  /**
   * Converts a 2D int array to a 2D double array.
   *
   * @param intArray The double array to convert.
   * @return The resulting double array.
   */
  private static double[][] toDoubleArray(int[][] intArray) {
    double[][] doubleArray = new double[intArray.length][intArray[0].length];
    for (int i = 0; i < intArray.length; i++) {
      for (int j = 0; j < intArray[0].length; j++) {
        doubleArray[i][j] = intArray[i][j];
      }
    }
    return doubleArray;
  }

  /**
   * Converts a 2D double array to a 2D int array.
   *
   * @param doubleArray The double array to convert.
   * @return The resulting int array.
   */
  private static int[][] toIntArray(double[][] doubleArray) {
    int[][] intArray = new int[doubleArray.length][doubleArray[0].length];
    for (int i = 0; i < doubleArray.length; i++) {
      for (int j = 0; j < doubleArray[0].length; j++) {
        intArray[i][j] = (int) Math.round(doubleArray[i][j]);
      }
    }
    return intArray;
  }

  /**
   * Calculates the smallest power of 2 that is greater than or equal to the given number.
   *
   * @param n The number for which to find the next power of 2.
   * @return The smallest power of 2 greater than or equal to n.
   */
  public static int nextPowerOf2(int n) {
    if (n <= 0) {
      return 1;
    }
    int power = 1;
    while (power < n) {
      power *= 2;
    }
    return power;
  }

  /**
   * Transforms a single row of a matrix during the Haar wavelet transform.
   *
   * @param row    The row to transform.
   * @param length The size of the row (dimension).
   * @return The transformed row.
   */
  public static double[] transformRow(double[] row, int length) {
    double[] temp = new double[length];
    int half = length / 2;

    for (int i = 0; i < half; i++) {
      temp[i] = (row[2 * i] + row[2 * i + 1]) / Math.sqrt(2);
      temp[half + i] = (row[2 * i] - row[2 * i + 1]) / Math.sqrt(2);
    }

    System.arraycopy(temp, 0, row, 0, length);
    return row;
  }

  /**
   * Inversely transforms a single row of a matrix during the inverse Haar wavelet transform.
   *
   * @param row    The row to inversely transform.
   * @param length The size of the row (dimension).
   * @return The inversely transformed row.
   */
  public static double[] inverseTransformRow(double[] row, int length) {
    double[] temp = new double[length];
    int half = length / 2;

    for (int i = 0; i < half; i++) {
      temp[2 * i] = (row[i] + row[half + i]) / Math.sqrt(2);
      temp[2 * i + 1] = (row[i] - row[half + i]) / Math.sqrt(2);
    }

    System.arraycopy(temp, 0, row, 0, length);
    return row;
  }


  /**
   * Generates a histogram image from the given source image, showing the frequency of color
   * intensities for the Red, Green, and Blue channels.
   *
   * @param sourceImage The input image to generate the histogram from.
   * @return A new image representing the histogram of the source image.
   */
  public static Model generateHistogram(Model sourceImage) {
    int width = sourceImage.getWidth();
    int height = sourceImage.getHeight();

    // Arrays to store frequency of each color intensity (0-255) for R, G, B
    int[] redChannel = new int[256];
    int[] greenChannel = new int[256];
    int[] blueChannel = new int[256];

    // Populate the color channels with intensity frequencies
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] rgb = sourceImage.getPixel(x, y);
        redChannel[rgb[0]]++;
        greenChannel[rgb[1]]++;
        blueChannel[rgb[2]]++;
      }
    }

    // Find the maximum frequency for scaling the histogram
    int maxFrequency = Math.max(Arrays.stream(redChannel).max().orElse(0),
        Math.max(Arrays.stream(greenChannel).max().orElse(0),
            Arrays.stream(blueChannel).max().orElse(0)));

    // Adjusted width and height for a smoother histogram
    int histWidth = 256; // Increase width for better visualization
    int histHeight = 256;
    Model histogramImage = new Image(histWidth, histHeight);

    // Fill the background with white
    int[] white = {255, 255, 255};
    for (int y = 0; y < histHeight; y++) {
      for (int x = 0; x < histWidth; x++) {
        histogramImage.setPixel(x, y, white);
      }
    }

    // Draw grid pattern (light gray color)
    int[] gray = {200, 200, 200};
    int gridSpacing = 32; // Space between grid lines

    // Vertical grid lines
    for (int x = 0; x < histWidth; x += gridSpacing) {
      for (int y = 0; y < histHeight; y++) {
        histogramImage.setPixel(x, y, gray);
      }
    }

    // Horizontal grid lines
    for (int y = 0; y < histHeight; y += gridSpacing) {
      for (int x = 0; x < histWidth; x++) {
        histogramImage.setPixel(x, y, gray);
      }
    }

    // Draw smooth line histogram for each channel
    for (int i = 1; i < 256; i++) {
      int x1 = (i - 1) * (histWidth / 256);
      int x2 = i * (histWidth / 256);

      int prevRedHeight = histHeight
          - (int) ((redChannel[i - 1] / (double) maxFrequency) * histHeight);
      int currRedHeight = histHeight
          - (int) ((redChannel[i] / (double) maxFrequency) * histHeight);
      drawLine(histogramImage, x1, clamphist(prevRedHeight, 0, histHeight - 1),
          x2, clamphist(currRedHeight, 0, histHeight - 1), new int[]{255, 0, 0});

      int prevGreenHeight = histHeight
          - (int) ((greenChannel[i - 1] / (double) maxFrequency) * histHeight);
      int currGreenHeight = histHeight
          - (int) ((greenChannel[i] / (double) maxFrequency) * histHeight);
      drawLine(histogramImage, x1, clamphist(prevGreenHeight, 0, histHeight - 1),
          x2, clamphist(currGreenHeight, 0, histHeight - 1), new int[]{0, 255, 0});

      int prevBlueHeight = histHeight
          - (int) ((blueChannel[i - 1] / (double) maxFrequency) * histHeight);
      int currBlueHeight = histHeight
          - (int) ((blueChannel[i] / (double) maxFrequency) * histHeight);
      drawLine(histogramImage, x1, clamphist(prevBlueHeight, 0, histHeight - 1),
          x2, clamphist(currBlueHeight, 0, histHeight - 1), new int[]{0, 0, 255});
    }

    return histogramImage;
  }

  private static void drawLine(Model image, int x1, int y1, int x2, int y2, int[] color) {
    int dx = Math.abs(x2 - x1);
    int dy = Math.abs(y2 - y1);
    int sx = x1 < x2 ? 1 : -1;
    int sy = y1 < y2 ? 1 : -1;
    int err = dx - dy;

    while (true) {
      if (x1 >= 0 && x1 < image.getWidth() && y1 >= 0 && y1 < image.getHeight()) {
        image.setPixel(x1, y1, color); // Draw the pixel if within bounds
      }

      if (x1 == x2 && y1 == y2) {
        break;
      }
      int e2 = 2 * err;
      if (e2 > -dy) {
        err -= dy;
        x1 += sx;
      }
      if (e2 < dx) {
        err += dx;
        y1 += sy;
      }
    }
  }

  /**
   * Applies color correction to the given image by adjusting the color channels (Red, Green, Blue)
   * based on the histogram peaks to balance the overall color intensity.
   *
   * @param image The input image to be color-corrected.
   * @return A new image with the corrected color balance.
   */
  public static Image colorCorrectImage(Image image) {
    int width = image.getWidth();
    int height = image.getHeight();
    Image correctedImage = new Image(width, height);

    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] pixel = image.getPixel(x, y);
        redHistogram[pixel[0]]++;
        greenHistogram[pixel[1]]++;
        blueHistogram[pixel[2]]++;
      }
    }

    int redPeak = findHistogramPeak(redHistogram, 10, 245);
    int greenPeak = findHistogramPeak(greenHistogram, 10, 245);
    int bluePeak = findHistogramPeak(blueHistogram, 10, 245);

    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    int redOffset = averagePeak - redPeak;
    int greenOffset = averagePeak - greenPeak;
    int blueOffset = averagePeak - bluePeak;

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] pixel = image.getPixel(x, y);

        int r = clamphist(pixel[0] + redOffset, 0, 255);
        int g = clamphist(pixel[1] + greenOffset, 0, 255);
        int b = clamphist(pixel[2] + blueOffset, 0, 255);

        correctedImage.setPixel(x, y, new int[]{r, g, b});
      }
    }

    return correctedImage;
  }

  private static int findHistogramPeak(int[] histogram, int min, int max) {
    int peakValue = min;
    int peakCount = histogram[min];
    for (int i = min + 1; i <= max; i++) {
      if (histogram[i] > peakCount) {
        peakValue = i;
        peakCount = histogram[i];
      }
    }
    return peakValue;
  }

  private static int clamphist(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }

  /**
   * Adjusts the levels of the given image by remapping the pixel values for each color channel
   * (Red, Green, Blue) based on the specified black, mid, and white level values.
   *
   * @param image The input image to be adjusted.
   * @param black The black point (minimum value), must be between 0 and 255.
   * @param mid   The mid-point (gamma), must be between black and white.
   * @param white The white point (maximum value), must be between mid and 255.
   * @return A new image with the adjusted color levels.
   * @throws IllegalArgumentException If the provided black, mid, or white values are invalid.
   */
  public static Image levelsAdjust(Image image, int black, int mid, int white) {
    if (black < 0 || mid < black || white < mid || white > 255) {
      throw new IllegalArgumentException("Invalid black, mid, or white values.");
    }

    int width = image.getWidth();
    int height = image.getHeight();
    Image adjustedImage = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] pixel = image.getPixel(x, y);
        int r = adjustChannel(pixel[0], black, mid, white);
        int g = adjustChannel(pixel[1], black, mid, white);
        int b = adjustChannel(pixel[2], black, mid, white);
        adjustedImage.setPixel(x, y, new int[]{r, g, b});
      }
    }
    return adjustedImage;
  }

  private static int adjustChannel(int value, int black, int mid, int white) {
    double a =
        black * black * (mid - white) - black * (mid * mid - white * white) + white * mid * mid
            - mid * white * white;
    double a_a = -black * (128 - 255) + 128 * white - 255 * mid;
    double a_b = black * black * (128 - 255) + 255 * mid * mid - 128 * white * white;
    double a_c =
        black * black * (255 * mid - 128 * white)
            - black * (255 * mid * mid - 128 * white * white);

    double aCoefficient = a_a / a;
    double bCoefficient = a_b / a;
    double cCoefficient = a_c / a;

    int newValue = (int) (aCoefficient * value * value + bCoefficient * value + cCoefficient);
    return Math.max(0, Math.min(255, newValue));
  }

  /**
   * Applies a split view on the image, processing the left part with the specified operation and
   * leaving the right part unchanged.
   *
   * @param viewType      The processing type: "blur", "sharpen", "greyscale", "sepia",
   *                      "color-correct", or "levels-adjust".
   * @param splitPercent  The percentage (0 to 100) to split the image.
   * @param originalImage The image to apply the split view to.
   * @return A new image with the left part processed and the right part unchanged.
   */
  public static Image applySplitView(String viewType, int splitPercent, Image originalImage) {
    if (splitPercent < 0 || splitPercent > 100) {
      throw new IllegalArgumentException("Split percentage must be between 0 and 100.");
    }

    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int splitWidth = (int) (width * (splitPercent / 100.0));
    Image splitImage = new Image(width, height);

    Image processedPart;
    switch (viewType.toLowerCase()) {
      case "blur":
        processedPart = blurImage(originalImage);
        break;
      case "sharpen":
        processedPart = sharpenImage(originalImage);
        break;
      case "greyscale":
        processedPart = greyscaleImage(originalImage);
        break;
      case "sepia":
        processedPart = sepiaToneImage(originalImage);
        break;
      case "color-correct":
        processedPart = colorCorrectImage(originalImage);
        break;
      case "levels-adjust":
        processedPart = levelsAdjust(originalImage, 0, 128, 255);
        break;
      default:
        throw new IllegalArgumentException("Invalid operation specified.");
    }

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (x < splitWidth) {
          splitImage.setPixel(x, y, processedPart.getPixel(x, y));
        } else {
          splitImage.setPixel(x, y, originalImage.getPixel(x, y));
        }
      }
    }
    return splitImage;
  }

  /**
   * Downscales the given image to a specified width and height using bi-linear interpolation.
   *
   * @param image        the original image to be downscaled
   * @param targetWidth  the target width for the downscaled image
   * @param targetHeight the target height for the downscaled image
   * @return a new image that has been downscaled to the target dimensions
   */
  public static Image downscaleImage(Image image, int targetWidth, int targetHeight) {
    int originalWidth = image.getWidth();
    int originalHeight = image.getHeight();
    Image downscaledImage = new Image(targetWidth, targetHeight);

    for (int y = 0; y < targetHeight; y++) {
      for (int x = 0; x < targetWidth; x++) {
        // Map the target pixel (x, y) back to the original image coordinates
        float srcX = ((float) x / targetWidth) * originalWidth;
        float srcY = ((float) y / targetHeight) * originalHeight;

        // Calculate the four surrounding pixels in the original image
        int x0 = (int) Math.floor(srcX);
        int x1 = Math.min(x0 + 1, originalWidth - 1);
        int y0 = (int) Math.floor(srcY);
        int y1 = Math.min(y0 + 1, originalHeight - 1);

        // Get colors of the surrounding pixels
        int[] color00 = image.getPixel(x0, y0);
        int[] color01 = image.getPixel(x0, y1);
        int[] color10 = image.getPixel(x1, y0);
        int[] color11 = image.getPixel(x1, y1);

        // Calculate the weights for interpolation
        float xWeight = srcX - x0;
        float yWeight = srcY - y0;

        // Perform bi-linear interpolation for each color component
        int red = (int) ((1 - xWeight) * (1 - yWeight) * color00[0]
            + xWeight * (1 - yWeight) * color10[0]
            + (1 - xWeight) * yWeight * color01[0]
            + xWeight * yWeight * color11[0]);

        int green = (int) ((1 - xWeight) * (1 - yWeight) * color00[1]
            + xWeight * (1 - yWeight) * color10[1]
            + (1 - xWeight) * yWeight * color01[1]
            + xWeight * yWeight * color11[1]);

        int blue = (int) ((1 - xWeight) * (1 - yWeight) * color00[2]
            + xWeight * (1 - yWeight) * color10[2]
            + (1 - xWeight) * yWeight * color01[2]
            + xWeight * yWeight * color11[2]);

        // Ensure RGB values are within the range [0, 255]
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        // Set the computed color to the downscaled image
        downscaledImage.setPixel(x, y, new int[]{red, green, blue});
      }
    }
    return downscaledImage; // Return the downscaled image
  }

}