package jvmt;

import jvmt.controller.MainControllerImpl;
import jvmt.controller.api.MainController;

/**
 * Entry point of the Javamant application.
 * Bootstraps an instance of {@link MainController}.
 * 
 * @see MainController
 */
public final class Javamant {

    /**
     * This class can not have instances.
     * This class bootstraps the application through {@link Javamant#main()}.
     */
    private Javamant() {
    }

    /**
     * Starts the Javamant application by creating a {@link MainController}
     * instance and calling {@link MainController#startApp()}.
     * 
     * @param args command line arguments. Not used in this application.
     */
    public static void main(String[] args) {
        final MainController ctrl = new MainControllerImpl();
        ctrl.startApp();
    }
}
