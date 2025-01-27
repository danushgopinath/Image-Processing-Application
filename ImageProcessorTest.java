import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import model.Image;
import model.ImageProcessor;
import org.junit.Before;
import org.junit.Test;

/**
 * This class provides unit tests for the ImageProcessor class, verifying functionality of various
 * image operations including brightness adjustment, flipping, color component extraction, and image
 * transformations.
 */
public class ImageProcessorTest {

  private Image originalImage;
  private Map<String, Image> testImages;

  /**
   * Sets up the initial conditions for the test cases. Loads the original image and initializes a
   * map of expected test images.
   *
   * @throws IOException if the image file cannot be read.
   */
  @Before
  public void setUp() throws IOException {
    BufferedImage tempImage = ImageIO.read(new File("resources/images/beach.jpg"));
    int width = tempImage.getWidth();
    int height = tempImage.getHeight();

    originalImage = new Image(width, height);
    originalImage.load("resources/images/beach.jpg");

    testImages = loadTestImages();
  }

  /**
   * Tests the brightness adjustment functionality to brighten an image.
   */
  @Test
  public void testAdjustBrightness() {
    Image brightenedImage = ImageProcessor.adjustBrightness(originalImage, 20);
    assertImages(brightenedImage, testImages.get("beach-brighten"));
  }

  /**
   * Tests the brightness adjustment functionality to darken an image.
   */
  @Test
  public void testDarkenImage() {
    Image darkenedImage = ImageProcessor.adjustBrightness(originalImage, -20);
    assertImages(darkenedImage, testImages.get("beach-darken"));
  }

  /**
   * Tests flipping an image horizontally.
   */
  @Test
  public void testFlipHorizontally() {
    Image flippedImage = ImageProcessor.flipHorizontally(originalImage);
    assertImages(flippedImage, testImages.get("beach-horizontal"));
  }

  /**
   * Tests flipping an image vertically.
   */
  @Test
  public void testFlipVertically() {
    Image flippedImage = ImageProcessor.flipVertically(originalImage);
    assertImages(flippedImage, testImages.get("beach-vertical"));
  }

  /**
   * Tests converting an image to greyscale.
   */
  @Test
  public void testGreyscaleImage() {
    Image greyscaleImage = ImageProcessor.greyscaleImage(originalImage);
    assertImages(greyscaleImage, testImages.get("beach-greyscale"));
  }

  /**
   * Tests applying a sepia tone to an image.
   */
  @Test
  public void testSepiaToneImage() {
    Image sepiaImage = ImageProcessor.sepiaToneImage(originalImage);
    assertImages(sepiaImage, testImages.get("beach-sepia"));
  }

  /**
   * Tests extracting the red component from an image.
   */
  @Test
  public void testExtractRedComponent() {
    Image redImage = ImageProcessor.extractColorComponent(originalImage, "red");
    assertImages(redImage, testImages.get("beach-red"));
  }

  /**
   * Tests extracting the green component from an image.
   */
  @Test
  public void testExtractGreenComponent() {
    Image greenImage = ImageProcessor.extractColorComponent(originalImage, "green");
    assertImages(greenImage, testImages.get("beach-green"));
  }

  /**
   * Tests extracting the blue component from an image.
   */
  @Test
  public void testExtractBlueComponent() {
    Image blueImage = ImageProcessor.extractColorComponent(originalImage, "blue");
    assertImages(blueImage, testImages.get("beach-blue"));
  }

  /**
   * Tests blurring an image.
   */
  @Test
  public void testBlurImage() {
    Image blurredImage = ImageProcessor.blurImage(originalImage);
    assertImages(blurredImage, testImages.get("beach-blur"));
  }

  /**
   * Tests sharpening an image.
   */
  @Test
  public void testSharpenImage() {
    Image sharpenedImage = ImageProcessor.sharpenImage(originalImage);
    assertImages(sharpenedImage, testImages.get("beach-sharpen"));
  }

  /**
   * Tests creating a value-based greyscale image.
   */
  @Test
  public void testValueImage() {
    Image valueImage = ImageProcessor.valueImage(originalImage);
    assertImages(valueImage, testImages.get("beach-value"));
  }

  /**
   * Tests creating a luma-based greyscale image.
   */
  @Test
  public void testLumaImage() {
    Image lumaImage = ImageProcessor.greyscaleImage(originalImage);
    assertImages(lumaImage, testImages.get("beach-luma"));
  }

  /**
   * Tests creating an intensity-based greyscale image.
   */
  @Test
  public void testIntensityImage() {
    Image intensityImage = ImageProcessor.intensityImage(originalImage);
    assertImages(intensityImage, testImages.get("beach-intensity"));
  }

  /**
   * Tests creating a compressed version of an image.
   */
  @Test
  public void testCompressedImage() throws Exception {
    Image compressedImage = ImageProcessor.compressImage(originalImage, 90);
    assertImages(compressedImage, testImages.get("beach-90-compress"));
  }

  /**
   * Tests creating a histogram for an image.
   */
  @Test
  public void testHistogramImage() {
    Image histogramImage = (Image) ImageProcessor.generateHistogram(originalImage);
    assertImages(histogramImage, testImages.get("beach-histogram"));
  }

  /**
   * Tests creating a color-corrected version of an image.
   */
  @Test
  public void testColorCorrectedImage() {
    Image colorCorrectImage = ImageProcessor.colorCorrectImage(originalImage);
    assertImages(colorCorrectImage, testImages.get("beach-corrected"));
  }

  /**
   * Tests creating a level-adjusted version of an image.
   */
  @Test
  public void testLevelAdjustedImage() {
    Image intensityImage = ImageProcessor.levelsAdjust(originalImage, 100, 128, 150);
    assertImages(intensityImage, testImages.get("beach-level"));
  }

  /**
   * Tests creating a split-view of an image (Sepia).
   */
  @Test
  public void testSplitViewImageSepia() {
    Image intensityImage = ImageProcessor.applySplitView("sepia", 50, originalImage);
    assertImages(intensityImage, testImages.get("beach-sepia-split"));
  }


  /**
   * Tests creating a downscaled version of an image.
   */
  @Test
  public void testDownscale() {
    Image downscaleImage = ImageProcessor.downscaleImage(originalImage, 100, 100);
    assertImages(downscaleImage, testImages.get("beach-downscale"));
  }

  /**
   * Loads expected test images into a map, with filenames as keys.
   *
   * @return a map of test images with filenames as keys.
   * @throws IOException if any image file cannot be loaded.
   */
  private Map<String, Image> loadTestImages() throws IOException {
    Map<String, Image> images = new HashMap<>();
    images.put("beach-brighten", loadTestImage("resources/test_images/JPG/beach-brighten.jpg"));
    images.put("beach-darken", loadTestImage("resources/test_images/JPG/beach-darken.jpg"));
    images.put("beach-horizontal", loadTestImage("resources/test_images/JPG/beach-horizontal.jpg"));
    images.put("beach-vertical", loadTestImage("resources/test_images/JPG/beach-vertical.jpg"));
    images.put("beach-red", loadTestImage("resources/test_images/JPG/beach-red.jpg"));
    images.put("beach-green", loadTestImage("resources/test_images/JPG/beach-green.jpg"));
    images.put("beach-blue", loadTestImage("resources/test_images/JPG/beach-blue.jpg"));
    images.put("beach-value", loadTestImage("resources/test_images/JPG/beach-value.jpg"));
    images.put("beach-luma", loadTestImage("resources/test_images/JPG/beach-luma.jpg"));
    images.put("beach-intensity", loadTestImage("resources/test_images/JPG/beach-intensity.jpg"));
    images.put("beach-greyscale", loadTestImage("resources/test_images/JPG/beach-greyscale.jpg"));
    images.put("beach-blur", loadTestImage("resources/test_images/JPG/beach-blur.jpg"));
    images.put("beach-sharpen", loadTestImage("resources/test_images/JPG/beach-sharpen.jpg"));
    images.put("beach-sepia", loadTestImage("resources/test_images/JPG/beach-sepia.jpg"));
    images.put("beach-90-compress",
        loadTestImage("resources/test_images/JPG/beach-90-compress.jpg"));
    images.put("beach-histogram", loadTestImage("resources/test_images/JPG/beach-histogram.jpg"));
    images.put("beach-corrected", loadTestImage("resources/test_images/JPG/beach-corrected.jpg"));
    images.put("beach-level", loadTestImage("resources/test_images/JPG/beach-levels.jpg"));
    images.put("beach-sepia-split",
        loadTestImage("resources/test_images/JPG/beach-sepia-split.jpg"));
    images.put("beach-downscale",
        loadTestImage("resources/test_images/JPG/beach-downscale.jpg"));
    return images;
  }

  /**
   * Loads a test image from the specified path.
   *
   * @param filePath the path to the image file.
   * @return the loaded image.
   * @throws IOException if the image cannot be loaded.
   */
  private Image loadTestImage(String filePath) throws IOException {
    Image img = new Image(100, 100);
    img.load(filePath);
    return img;
  }

  /**
   * Asserts that two images are identical by comparing their pixel data.
   *
   * @param actual   the actual image to compare.
   * @param expected the expected image for comparison.
   */
  private void assertImages(Image expected, Image actual) {
    int width = actual.getWidth();
    int height = actual.getHeight();

    // Assert dimensions
    assertEquals("Image width mismatch", expected.getWidth(), actual.getWidth());
    assertEquals("Image height mismatch", expected.getHeight(), actual.getHeight());

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] expectedPixel = expected.getPixel(x, y);
        int[] actualPixel = actual.getPixel(x, y);

        // Assert pixel values
        assertEquals("Red pixel mismatch at (" + x + ", " + y + ")",
                expectedPixel[0], actualPixel[0]);
        assertEquals("Green pixel mismatch at (" + x + ", " + y + ")",
                expectedPixel[1], actualPixel[1]);
        assertEquals("Blue pixel mismatch at (" + x + ", " + y + ")",
                expectedPixel[2], actualPixel[2]);
      }
    }
  }
}
