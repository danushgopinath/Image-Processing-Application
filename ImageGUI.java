package view;

import controller.ImageController;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.AbstractButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

/**
 * A graphical user interface (GUI) for displaying and manipulating an image. Provides functionality
 * to load, save, and apply operations on images, including splitting and adjusting levels.
 */
public class ImageGUI {

  /**
   * The main JFrame for the application.
   */
  public JFrame frame;

  /**
   * JLabel to display the loaded image.
   */
  public JLabel imageLabel;

  /**
   * JLabel to display the image histogram.
   */
  public JLabel histogramLabel;

  /**
   * Panel containing the histogram.
   */
  public JPanel histogramPanel;

  /**
   * Path of the current image file.
   */
  public String currentFilePath;

  /**
   * Name of the current image.
   */
  public String imageName;

  /**
   * Name of the previous image for comparison after split operations.
   */
  public String previousImageName;

  /**
   * Flag indicating if the image has been saved.
   */
  public boolean isImageSaved = true;

  /**
   * The ImageController that handles image processing tasks.
   */
  public ImageController controller;

  /**
   * Dropdown menu for selecting image operations.
   */
  public JComboBox<String> operationComboBox;

  /**
   * Dropdown menu for selecting split operations.
   */
  public JComboBox<String> splitOperationComboBox;

  /**
   * Panel containing the split operation dropdown.
   */
  public JPanel splitOperationPanel;

  /**
   * Toggle button for switching between the current and previous image.
   */
  public JToggleButton toggleButton;

  /**
   * Constructs an instance of the ImageGUI, initializing the components and setting up the UI.
   */
  public ImageGUI() {
    controller = new ImageController();
    initialize();
  }

  /**
   * Initializes the GUI components, sets up layout, and adds event listeners.
   */
  public void initialize() {
    frame = new JFrame("Image Processing GUI Application");

    // Add Window Listener for Close Confirmation
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        confirmExit();
      }
    });

    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setLayout(new BorderLayout());

    Font paneTitleFont = new Font("Courier New", Font.BOLD, 16);

    // Image viewing pane
    JPanel imagePane = new JPanel(new BorderLayout());
    TitledBorder imagePaneBorder = BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.GRAY, 1), "IMAGE PREVIEW", TitledBorder.CENTER,
        TitledBorder.TOP);
    imagePaneBorder.setTitleFont(paneTitleFont);
    imagePane.setBorder(imagePaneBorder);

    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    JScrollPane imageScrollPane = new JScrollPane(imageLabel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    imageScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    imagePane.add(imageScrollPane, BorderLayout.CENTER);

    //    IF YOU DON'T WANT THE IMAGE TO BE SCROLLABLE
    //    imageLabel = new JLabel();
    //    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    //    imageLabel.setBorder(
    //            BorderFactory.createEmptyBorder(20, 20, 20, 20));
    //    imagePane.add(imageLabel, BorderLayout.CENTER);

    // Histogram pane
    histogramPanel = new JPanel(new BorderLayout());
    TitledBorder histogramPaneBorder = BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.GRAY, 1), "HISTOGRAM", TitledBorder.CENTER,
        TitledBorder.TOP);
    histogramPaneBorder.setTitleFont(paneTitleFont);
    histogramPanel.setBorder(histogramPaneBorder);
    histogramLabel = new JLabel();
    histogramLabel.setHorizontalAlignment(JLabel.CENTER);
    histogramPanel.add(histogramLabel);

    // Controls pane
    JPanel controlsPane = new JPanel(new BorderLayout());
    TitledBorder controlsPaneBorder = BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), "CONTROLS", TitledBorder.CENTER,
        TitledBorder.TOP);
    controlsPaneBorder.setTitleFont(paneTitleFont);
    controlsPane.setBorder(controlsPaneBorder);

    // Top part of controls pane
    JPanel controlsTopPane = new JPanel();
    controlsTopPane.setLayout(new BoxLayout(controlsTopPane, BoxLayout.Y_AXIS));

    // Load and Save Buttons
    JPanel loadSavePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    JButton loadButton = createStyledButton("Load Image");
    loadButton.setPreferredSize(new Dimension(620, 25));
    loadButton.addActionListener(e -> loadImageAction());
    loadSavePanel.add(loadButton);

    JButton saveButton = createStyledButton("Save Image");
    saveButton.setPreferredSize(new Dimension(620, 25));
    saveButton.addActionListener(e -> saveImageAction());
    loadSavePanel.add(saveButton);

    controlsTopPane.add(loadSavePanel);

    // Operation Dropdown and Split Operation
    JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel operationLabel = createStyledLabel("Select Operation:");
    operationPanel.add(operationLabel);

    // Main operations dropdown
    operationComboBox = createStyledComboBox(
        new String[]{"No Selection", "Flip Horizontal", "Flip Vertical", "Red Component",
            "Green Component", "Blue Component", "Blur", "Sharpen", "Greyscale", "Sepia",
            "Compress", "Color-Correct", "Adjust Levels", "Split View"});
    operationComboBox.addActionListener(e -> handleOperationSelection());
    operationPanel.add(operationComboBox);

    // Split operation dropdown and toggle button
    splitOperationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    splitOperationPanel.setVisible(false); // Initially hidden
    JLabel splitLabel = createStyledLabel("Split Operation:");
    splitOperationPanel.add(splitLabel);

    splitOperationComboBox = createStyledComboBox(
        new String[]{"Blur", "Sharpen", "Greyscale", "Sepia", "Color-Correct", "Adjust Levels"});
    splitOperationPanel.add(splitOperationComboBox);

    // Toggle button
    toggleButton = new JToggleButton("Toggle Previous Image");
    createStyledButton(toggleButton); // Use the same styling method
    toggleButton.setPreferredSize(new Dimension(250, 25));
    toggleButton.setVisible(false); // Initially hidden
    toggleButton.addActionListener(e -> toggleImageDisplay());
    splitOperationPanel.add(toggleButton);

    controlsTopPane.add(operationPanel);
    controlsTopPane.add(splitOperationPanel);

    controlsPane.add(controlsTopPane, BorderLayout.CENTER);

    // Execute Button
    JButton executeButton = createStyledButton("Execute");
    executeButton.setPreferredSize(new Dimension(0, 25)); // Keep execute button prominent
    executeButton.addActionListener(e -> executeSelectedOperation());
    controlsPane.add(executeButton, BorderLayout.SOUTH);

    // Split panes for Image and Histogram
    JSplitPane imageHistogramSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePane,
        histogramPanel);
    imageHistogramSplitPane.setDividerLocation(0.70);
    imageHistogramSplitPane.setResizeWeight(0.70);
    imageHistogramSplitPane.setContinuousLayout(true);

    // Main split pane for Image/Histogram and Controls
    JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imageHistogramSplitPane,
        controlsPane);
    mainSplitPane.setDividerLocation(0.85);
    mainSplitPane.setResizeWeight(0.85);
    mainSplitPane.setContinuousLayout(true);

    frame.add(mainSplitPane, BorderLayout.CENTER);
    frame.setVisible(true);
  }

  /**
   * Creates a styled JButton with the given text.
   *
   * @param text The text to be displayed on the button.
   * @return A styled JButton.
   */
  public JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setBackground(new Color(169, 169, 169));
    button.setForeground(Color.BLACK);
    button.setFont(new Font("Courier New", Font.BOLD, 14));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.setOpaque(true);
    return button;
  }

  /**
   * Creates a styled button for an AbstractButton, used for toggle buttons.
   *
   * @param button The button to be styled.
   */
  private void createStyledButton(AbstractButton button) {
    button.setBackground(new Color(169, 169, 169));
    button.setForeground(Color.BLACK);
    button.setFont(new Font("Courier New", Font.BOLD, 14));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.setOpaque(true);
  }

  /**
   * Creates a styled JLabel with the given text.
   *
   * @param text The text to be displayed on the label.
   * @return A styled JLabel.
   */
  public JLabel createStyledLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("Courier New", Font.PLAIN, 14));
    return label;
  }

  /**
   * Creates a JComboBox with the specified items and applies a styled font.
   *
   * @param items An array of strings representing the items to be included in the JComboBox.
   * @return A JComboBox with the given items and styled font.
   */
  public JComboBox<String> createStyledComboBox(String[] items) {
    JComboBox<String> comboBox = new JComboBox<>(items);
    comboBox.setFont(new Font("Courier New", Font.PLAIN, 14));
    return comboBox;
  }

  /**
   * Handles the operation selection from the dropdown menu and toggles visibility of the split
   * operation panel based on the selected operation. If "Split View" is selected, it makes the
   * split operation panel visible and hides the toggle button. For other operations, the split
   * operation panel and toggle button are hidden.
   */
  public void handleOperationSelection() {
    String selectedOperation = (String) operationComboBox.getSelectedItem();
    if ("Split View".equals(selectedOperation)) {
      splitOperationPanel.setVisible(true);
      toggleButton.setVisible(false); // Hide toggle button initially
    } else {
      splitOperationPanel.setVisible(false);
      toggleButton.setVisible(false); // Hide toggle button for other operations
    }
    frame.revalidate();
    frame.repaint();
  }

  /**
   * Opens a file chooser dialog to load an image from the file system. If the user selects an
   * image, it is loaded into the application and displayed. Marks the image as not saved after
   * loading.
   */
  public void loadImageAction() {
    JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
      currentFilePath = fileChooser.getSelectedFile().getAbsolutePath();
      imageName = "image";
      isImageSaved = false; // Mark as not saved after loading a new image
      controller.loadImage(currentFilePath, imageName);
      updateImageDisplay(imageName);
    }
  }

  /**
   * Opens a file chooser dialog to save the current image to the file system. If the image has not
   * been saved previously, it prompts the user to select a file path. Displays an error if no image
   * is loaded.
   */
  public void saveImageAction() {
    if (imageName == null || imageName.isEmpty()) {
      JOptionPane.showMessageDialog(frame, "Image not found! Please load an image before saving.",
          "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
      controller.saveImage(fileChooser.getSelectedFile().getAbsolutePath(), imageName);
      isImageSaved = true; // Mark as saved after saving
    }
  }

  /**
   * Executes the selected operation on the current image based on the dropdown menu selection. If
   * "Split View" is selected, prompts the user for additional input to perform the operation.
   * Otherwise, performs the appropriate image operation based on the selected option.
   */
  public void executeSelectedOperation() {
    if (imageName == null) {
      JOptionPane.showMessageDialog(frame, "No image loaded.");
      return;
    }

    String selectedOperation = (String) operationComboBox.getSelectedItem();

    if ("Split View".equals(selectedOperation)) {
      String selectedSplitOperation = (String) splitOperationComboBox.getSelectedItem();
      if (selectedSplitOperation == null) {
        JOptionPane.showMessageDialog(frame, "No split operation selected.");
        return;
      }

      String splitPercentageInput = JOptionPane.showInputDialog(frame,
          "Enter Split Percentage (0-100):");
      if (splitPercentageInput == null || splitPercentageInput.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Split percentage is required.");
        return;
      }

      try {
        int splitPercentage = Integer.parseInt(splitPercentageInput);
        if (splitPercentage < 0 || splitPercentage > 100) {
          JOptionPane.showMessageDialog(frame, "Split percentage must be between 0 and 100.");
          return;
        }

        previousImageName = imageName; // Save current image as previous
        imageName = "split_" + previousImageName;
        controller.applySplitView(selectedSplitOperation, splitPercentage, previousImageName,
            imageName);
        updateImageDisplay(imageName);
        toggleButton.setVisible(true); // Show toggle button after operation
        isImageSaved = false;
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(frame,
            "Invalid split percentage. Please enter a valid number.");
      }
    } else if (!"No Selection".equals(selectedOperation)) {
      if ("Flip Horizontal".equals(selectedOperation)) {
        controller.flipHorizontally(imageName, imageName);
      } else if ("Flip Vertical".equals(selectedOperation)) {
        controller.flipVertically(imageName, imageName);
      } else if ("Red Component".equals(selectedOperation)) {
        controller.redComponent(imageName, imageName);
      } else if ("Green Component".equals(selectedOperation)) {
        controller.greenComponent(imageName, imageName);
      } else if ("Blue Component".equals(selectedOperation)) {
        controller.blueComponent(imageName, imageName);
      } else if ("Blur".equals(selectedOperation)) {
        controller.blurImage(imageName, imageName);
      } else if ("Sharpen".equals(selectedOperation)) {
        controller.sharpenImage(imageName, imageName);
      } else if ("Greyscale".equals(selectedOperation)) {
        controller.greyScale(imageName, imageName);
      } else if ("Sepia".equals(selectedOperation)) {
        controller.sepiaToneImage(imageName, imageName);
      } else if ("Compress".equals(selectedOperation)) {
        compressImage();
      } else if ("Color-Correct".equals(selectedOperation)) {
        controller.colorCorrect(imageName, imageName);
      } else if ("Adjust Levels".equals(selectedOperation)) {
        adjustLevels();
      } else {
        throw new IllegalStateException("Unexpected value: " + selectedOperation);
      }
      updateImageDisplay(imageName);
      isImageSaved = false;
    }
  }

  /**
   * Toggles between displaying the current image and the previous image. When the toggle button is
   * selected, the previous image is displayed. Otherwise, the current image is displayed.
   */
  public void toggleImageDisplay() {
    if (imageName != null && previousImageName != null) {
      if (toggleButton.isSelected()) {
        updateImageDisplay(previousImageName);
        imageName = previousImageName; // Update imageName to the toggled image
      } else {
        updateImageDisplay(imageName);
      }
    }
  }

  /**
   * Updates the image display by fetching the image using its name and scaling it to fit the
   * display area. Repaints the frame and updates the histogram.
   *
   * @param imageName The name of the image to be displayed.
   */
  public void updateImageDisplay(String imageName) {
    BufferedImage image = controller.getImage(imageName);
    if (image == null) {
      System.out.println("Error: Image not found.");
      return;
    }

    SwingUtilities.invokeLater(() -> {
      ImageIcon imageIcon = new ImageIcon(image);
      imageLabel.setIcon(imageIcon);
      imageLabel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
      imageLabel.revalidate();
      updateHistogram(imageName);
      imageLabel.repaint();
    });
  }

  /**
   * Updates the histogram display for the current image. Generates the histogram for the given
   * image and displays it.
   *
   * @param imageName The name of the image whose histogram is to be displayed.
   */
  public void updateHistogram(String imageName) {
    controller.generateHistogram(imageName, "histogram_" + imageName);
    BufferedImage histogram = controller.getImage("histogram_" + imageName);
    if (histogram != null) {
      SwingUtilities.invokeLater(() -> {
        histogramLabel.setIcon(new ImageIcon(histogram));
        histogramLabel.revalidate();
      });
    } else {
      System.out.println("Error: Histogram image not found.");
    }
  }

  /**
   * Compresses the current image based on a user-provided compression factor. Prompts the user to
   * enter a compression factor between 1 and 100.
   */
  public void compressImage() {
    String input = JOptionPane.showInputDialog(frame, "Enter compression factor (1-100):");
    if (input != null) {
      try {
        int compressionFactor = Integer.parseInt(input);
        if (compressionFactor < 1 || compressionFactor > 100) {
          JOptionPane.showMessageDialog(frame, "Compression factor must be between 1 and 100.");
        } else {
          controller.compressImage(compressionFactor, imageName, imageName);
          isImageSaved = false;
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number.");
      }
    }
  }

  /**
   * Prompts the user to input black, white, and mid-levels to adjust the image. If the input is
   * valid, the levels are updated; otherwise, an error message is shown. The image is marked as
   * unsaved after the operation.
   */
  public void adjustLevels() {
    String blackValue = JOptionPane.showInputDialog(frame, "Enter Black Level:");
    String whiteValue = JOptionPane.showInputDialog(frame, "Enter White Level:");
    String midValue = JOptionPane.showInputDialog(frame, "Enter Mid Level:");
    if (blackValue != null && whiteValue != null && midValue != null) {
      try {
        int black = Integer.parseInt(blackValue);
        int white = Integer.parseInt(whiteValue);
        int mid = Integer.parseInt(midValue);
        controller.levelsAdjust(black, white, mid, imageName, imageName);
        isImageSaved = false;
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid integers.");
      }
    }
  }

  /**
   * Prompts the user with a confirmation dialog to exit the application. If the image has not been
   * saved, the user is asked to confirm whether they are sure they want to exit without saving. If
   * the user selects 'Yes', the application is closed. If the image is already saved, the
   * application is closed immediately.
   */
  public void confirmExit() {
    if (!isImageSaved) {
      int response = JOptionPane.showConfirmDialog(frame,
          "Image not saved. Are you sure you want to close the application?", "Confirm Exit",
          JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

      if (response == JOptionPane.YES_OPTION) {
        frame.dispose(); // Close the application
      }
    } else {
      frame.dispose(); // Close the application directly if the image is saved
    }
  }

  /**
   * The main method which starts the ImageGUI application. This method initializes the GUI and
   * ensures that it is run in the Event Dispatch Thread (EDT) for thread safety in Swing-based
   * applications.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(ImageGUI::new);
  }
}