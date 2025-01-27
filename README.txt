GRIME: Graphical Image Manipulation and Enhancement

This project submission builds upon our simple image processing application developed in Java, following the model-view-controller (MVC) architecture for improved code organization and management of complexity. We have now added a graphical user interface (GUI) that enables users to easily apply various image processing operations, such as loading, saving, and manipulating images, making the application more user-friendly and accessible for image enhancement.

OVERVIEW:

KEY COMPONENTS INCLUDED:
- Main: The entry point of the application that initializes the controller and view.
- model Interface: Defines the methods for image operations such as loading, saving, and pixel manipulation (blur, sharpen, brighten etc.).
- Image: Implements the model interface, providing functionalities for image processing.
- ImageProcessor: Contains methods for various image processing operations like brightness adjustment, image flipping, greyscale conversion, and more.
- view Interface: Defines methods for executing scripts that interact with the Image controller.
- ImageView: The concrete implementation of the view interface that handles user interactions with the controller.
- ImageGUI: Provides the graphical user interface (GUI) for the application, allowing users to interact with the image processing features visually. It includes components like buttons and menus to apply image operations and view the 	    results in real-time. The GUI acts as the interface between the User and the ImageController, sending user actions as commands to be processed by the controller.
- controller Interface: Defines the methods for image processing operations.
- ImageController: The implementation of the `controller` interface, coordinating the interaction between the model and the view, processing commands from scripts, and managing images.


FEATURES:

- Load image(s) from specified file paths through an intuitive GUI or via command line.
- Save processed image(s) to a designated directory using the GUI or command line operations.
- Adjust brightness (brighten and/or darken) with interactive sliders in the GUI or by using command line parameters.
- Flip images (horizontally and/or vertically) with a simple click of a button in the GUI or through command line commands.
- Extract individual color components (red, green, blue) via the GUI or command line.
- Apply various visualizations (luma, value, intensity) for better image analysis, available both in the GUI and through command line options.
- Combine and split RGB components easily with GUI controls or through command line operations.
- Apply image effects such as blur, sharpen, and sepia tone via the GUI or using command line commands.
- Apply image compression to reduce file size and quality with adjustable settings, supported both through the GUI and command line.
- Generate a histogram to analyze the image's tonal distribution through the GUI or by executing a command line script.
- Adjust the image levels for optimal contrast and brightness with interactive controls in the GUI or by specifying parameters in the command line.
- Create a split view for a side-by-side comparison of the image, allowing for easy before-and-after visualizations in the GUI, or by invoking a command from the command line.

JUSTIFICATION OF ADDITIONS / CHANGES MADE:
In this iteration, Based on the updated requirements and feedback received we made changes to mainly 3 classes: Main, ImageController and ImageProcessor.

Main.java:
We have added the ability to execute commands from script files provided via the command line, as well as the option to enter commands directly into the command line.

Image.java:
- The toBufferedImage() method converts the Image object to a BufferedImage by iterating through its pixels, extracting and clamping the RGB values to ensure they are within the valid range. These values are then set into a new BufferedImage, allowing the image to be displayed in the Graphical User Interface.


ImageGUI.java:
- The ImageGUI class was introduced to provide a graphical user interface (GUI) that facilitates user interactions with the application. The GUI enables users to load images, apply processing operations, and view the results in a more intuitive and visual manner.
- Through the GUI, users can adjust image properties such as brightness, apply effects like blur and sharpen, and perform transformations like flipping or cropping, all through simple, interactive controls (buttons, menus, etc.).

In summary, ImageGUI.java enhances user experience by providing a visual interface for image manipulation, while still maintaining support for more advanced users who prefer command-line operations.

ImageController.java:
- In the initial version of the ImageController class, we used a switch-case structure for command processing. This approach became less scalable as the number of commands increased. In the current version, we have refactored the code to implement the Command Pattern. This change allows for greater flexibility and scalability, as each operation is encapsulated in its own command object. This improves maintainability, readability, and makes it easier to add or modify commands in the future without affecting the core logic.

- Additionally, we have added support for an image downscaling operation as required. This feature allows users to reduce the resolution of images, making them more suitable for scenarios where smaller file sizes or lower resolutions are required.

- We also added a getImage() Method that returns the Image object into a BufferedImage format to be displayed in the GUI. If the image is found in the map, it is converted to a BufferedImage, which is then returned. If the image is not found, an IOException is thrown, and an error message is displayed. This method helps efficiently retrieve and process images stored in the hashmap.

In summary, the Current Version is more organized, easier to extend, and has added functionality.

ImageProcessor.java:
- In the current version, we have added the functionality for image downscaling. This new feature allows users to reduce the resolution of an image, making it more suitable for scenarios requiring smaller file sizes or lower image resolutions. The downscaling logic has been implemented directly within the ImageProcessor class, accessible by the ImageController.


CITATION / IMAGE SOURCES:
- PNG & JPG : Image of a Beach captured at Spectacle Island by us.
- PPM: https://people.sc.fsu.edu/~jburkardt/data/ppma/ppma.html (FSU SC PPMA Files explanation).

CONCLUSION:
The Image Manipulation and Enhancement project, built on the MVC architecture, offers a range of image processing operations and a user-friendly GUI, with command-line support for advanced users. Refactoring with the Command Pattern enhances scalability and maintainability. The project is now more flexible, accessible, and ready for scalability.