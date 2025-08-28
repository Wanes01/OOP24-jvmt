package controller.api;

/**
 * Models the main controller of the application, responsible for creating and
 * maintaining the various views and their assigned controllers. Through this
 * controller, it is possible to initialize the entire application using the
 * {@link #startApp()} method.
 * 
 * @author Emir Wanes Aouioua
 */
public interface MainController {
    /**
     * Initializes the application and displays the GUI to the user.
     */
    void startApp();
}
