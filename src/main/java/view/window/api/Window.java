package view.window.api;

import java.util.Optional;

import view.page.api.Page;

/**
 * Represents a generic application window, indipendent of the
 * GUI library used.
 * <p>
 * An {@code Window} serves as the main container of the application's
 * {@link Page} instances. It provides common operations such as display,
 * dismiss (i.e. hide window), refreshing content and so on.
 * </p>
 * <p>
 * This interface does not reference specific UI components, ensuring
 * flexibility in the choise of the underlying graphical library.
 * </p>
 * 
 * @see Page
 * 
 * @author Emir Wanes Aouioua
 */
public interface Window {

    /**
     * Opens the window (makes it visible to the user).
     */
    void display();

    /**
     * Hides the window (makes it not visible to the user).
     */
    void dismiss();

    /**
     * Refreshes/updates the current content of the view.
     */
    void refresh();

    /**
     * Disables user interaction on this window.
     */
    void disableInteraction();

    /**
     * Enables user interaction on this window.
     */
    void enableInteraction();

    /**
     * Sets the size of the window.
     * <p>
     * <strong>Note:</strong>
     * The unit of measures used by {@code width} and {@code height}
     * are determined by the implementation.
     * </p>
     * 
     * @param width  the width of this window.
     * @param height the height of this window.
     */
    void setSize(int width, int height);

    /**
     * Closes the window.
     * <p>
     * <strong>Note:</strong>
     * While the {@link #dismiss()} method hides the window, this method should be
     * used to
     * delete the window (which may also result in the termination of the entire
     * app).
     * </p>
     */
    void close();

    /**
     * Sets the current {@link Page} to display in the window.
     * 
     * @param page the page to show.
     */
    void setCurrentPage(Page page);

    /**
     * Returns the currently displayed page.
     * 
     * @return an optional containing the page displayed on this window.
     *         An empty optional if no page is set.
     */
    Optional<Page> getCurrentPage();
}
