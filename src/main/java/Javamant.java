import controller.MainControllerImpl;
import controller.api.MainController;

/**
 * Entry point of hthe Javamant application.
 * Bootstraps an instance of {@link MainController}.
 * 
 * @see MainController
 */
public class Javamant {
    /**
     * Starts the Javamant application.
     * 
     * @param args command line arguments. Not used in this application.
     */
    public static void main(String[] args) {
        final MainController ctrl = new MainControllerImpl();
        ctrl.startApp();
    }
}
