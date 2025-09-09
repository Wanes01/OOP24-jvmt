package jvmt.view.page.api;

import jvmt.view.window.api.Window;
import jvmt.controller.api.PageController;

/**
 * Represents a single page (i.e., a view) withing the application.
 * <p>
 * A {@code Page} can be displayed within a {@link Window} and
 * contains the UI content for a specific part of the app.
 * </p>
 * <p>
 * The user interaction is handled using a {@link PageController} that
 * specifies an action for every possible user interaction with this page.
 * </p>
 * 
 * @see Window
 * @see PageController
 * 
 * @author Emir Wanes Aouioua
 */
public interface Page {

    /**
     * Displays this page on the window.
     */
    void display();

    /**
     * Hides this page from the window.
     */
    void dismiss();

    /**
     * Refreshes/updates the content of this page.
     */
    void refresh();
}
