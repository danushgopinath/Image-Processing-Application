import controller.ImageController;
import model.Model;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * This class provides unit tests for the ImageController class, verifying functionality of various
 * image operations accessed through the controller and hence accessed by the GUI itself.
 */

public class ImageControllerTest {

  private ImageController imageController;

  @Before
  public void setUp() {
    imageController = new ImageController();  // Create the ImageController instance
  }

  @Test
  public void testLoadImage() {
    // Simulate loading an image from a file
    imageController.loadImage("resources/images/beach.jpg", "sampleImage");

    // Verify that the image was added to the ImageController's images map
    Model image = imageController.getImageFromMap("sampleImage");
    assertNotNull("Image should be loaded successfully.", image);
  }

  @Test
  public void testSaveImage() {
    // Load an image into the controller
    imageController.loadImage("resources/images/beach.jpg", "sampleImage");

    // Try saving the image
    imageController.saveImage("resources/images/beach1.jpg", "sampleImage");

    // Verify that the image was taken to the ImageController's images map
    Model image = imageController.getImageFromMap("sampleImage");
    assertNotNull("Image should be saved successfully.", image);
  }

  @Test
  public void testAdjustBrightness() {
    // Load an image
    imageController.loadImage("resources/images/beach.jpg", "sampleImage");

    // Adjust brightness by +50 and save the result as a new image
    imageController.adjustBrightness(50, "sampleImage", "brightSampleImage");

    // Get the adjusted image and verify it is not null
    Model adjustedImage = imageController.getImageFromMap("brightSampleImage");
    assertNotNull("Brightened image should be created.", adjustedImage);
  }

  @Test
  public void testFlipHorizontally() {
    // Load an image
    imageController.loadImage("resources/images/beach.jpg", "sampleImage");

    // Flip the image horizontally and save the result as a new image
    imageController.flipHorizontally("sampleImage", "flippedHorizontally");

    // Get the flipped image and verify it is not null
    Model flippedImage = imageController.getImageFromMap("flippedHorizontally");
    assertNotNull("Horizontally flipped image should be created.", flippedImage);
  }

  @Test
  public void testGreyscale() {
    // Load an image
    imageController.loadImage("resources/images/beach.jpg", "sampleImage");

    // Apply greyscale and save the result as a new image
    imageController.greyScale("sampleImage", "greyscaleImage");

    // Get the greyscale image and verify it is not null
    Model greyscaleImage = imageController.getImageFromMap("greyscaleImage");
    assertNotNull("Greyscale image should be created.", greyscaleImage);
  }

  @Test
  public void testRgbSplitAndCombine() {
    // Load an image
    imageController.loadImage("resources/images/beach.jpg", "sampleImage");

    // Split the RGB components and save each as a new image
    imageController.rgbSplit("sampleImage", "redComponent", "greenComponent", "blueComponent");

    // Verify each component image
    Model redComponent = imageController.getImageFromMap("redComponent");
    Model greenComponent = imageController.getImageFromMap("greenComponent");
    Model blueComponent = imageController.getImageFromMap("blueComponent");

    imageController.saveImage("resources/images/beach_red.jpg", "redComponent");
    imageController.saveImage("resources/images/beach_green.jpg", "greenComponent");
    imageController.saveImage("resources/images/beach_blue.jpg", "blueComponent");

    assertNotNull("Red component image should be created.", redComponent);
    assertNotNull("Green component image should be created.", greenComponent);
    assertNotNull("Blue component image should be created.", blueComponent);

    // Combine the RGB components back into a single image
    imageController.rgbCombine("combinedImage", "redComponent", "greenComponent", "blueComponent");

    // Verify the combined image
    Model combinedImage = imageController.getImageFromMap("combinedImage");
    assertNotNull("Combined RGB image should be created.", combinedImage);
  }

  @Test
  public void testBlurImage() {
    // Load an image
    imageController.loadImage("resources/images/beach.jpg", "sampleImage");

    // Apply a blur effect and save the result as a new image
    imageController.blurImage("sampleImage", "blurredImage");

    // Get the blurred image and verify it is not null
    Model blurredImage = imageController.getImageFromMap("blurredImage");
    assertNotNull("Blurred image should be created.", blurredImage);
  }

  @Test
  public void testSharpenImage() {
    // Load an image
    imageController.loadImage("resources/images/beach.jpg", "sampleImage");

    // Apply sharpening and save the result as a new image
    imageController.sharpenImage("sampleImage", "sharpenedImage");

    // Get the sharpened image and verify it is not null
    Model sharpenedImage = imageController.getImageFromMap("sharpenedImage");
    assertNotNull("Sharpened image should be created.", sharpenedImage);
  }

  @Test
  public void testDownscaleImage() {
    // Load an image
    imageController.loadImage("resources/images/beach.jpg", "sampleImage");

    // Downscale the image and save the result
    imageController.downscaleImage("sampleImage", "downscaledImage", 100, 100);

    // Get the downscaled image and verify it is not null
    Model downscaledImage = imageController.getImageFromMap("downscaledImage");
    assertNotNull("Downscaled image should be created.", downscaledImage);
  }
}
