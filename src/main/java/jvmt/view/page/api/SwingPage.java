package jvmt.view.page.api;

import javax.swing.JPanel;
import jvmt.controller.api.PageController;
import jvmt.view.window.impl.SwingWindow;

/**
 * Abstract class for {@link Page}s that use the Swing library.
 * <p>
 * A {@code SwingPage} extends {@link JPanel} and implements the {@link Page}
 * interface. This class is designed to be extended by concrete pages of the
 * application.
 * </p>
 * <p>
 * Each page is associated with a {@link PageController}, which is set via
 * {@link #setController(PageController)}. Subclasses must implement
 * {@link #setHandlers()} to configure event listeners, binding GUI interactions
 * to the controller.
 * </p>
 * <p>
 * Note: the subclasses can retrieve the specific controller for the page
 * they represent by using the {@link #getController(ControllerClass)} method.
 * This design choice was made to avoid propagating the use of generics to
 * pages.
 * </p>
 * 
 * @see SwingWindow
 * @see Page
 * @see JPanel
 * 
 * @author Emir Wanes Aouioua
 */
public abstract class SwingPage extends JPanel implements Page {

    private static final long serialVersionUID = 1L;
    private PageController controller;

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        this.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dismiss() {
        this.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        this.revalidate();
        this.repaint();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Initilizes event binding as soon as the controller is set.
     * </p>
     * <p>
     * <strong>Note:</strong>
     * the specific controller for a concrete page can
     * be retrieved using {@link #getController(ControllerClass)}.
     * </p>
     */
    @Override
    public void setController(final PageController controller) {
        this.controller = controller;
        this.setHandlers();
    }

    /**
     * Configures the event handlers that binds the GUI components
     * of this page with the controller logic.
     * <p>
     * Sublasses <strong>must</strong> implement this method to define how user
     * interactions are handled.
     * </p>
     */
    protected abstract void setHandlers();

    /**
     * Returns the controller associated with this page casted to a specific type.
     * <p>
     * This method ensures that the access to the controller is safe with a runtime
     * check.
     * This method was created to avoid propagating the use of generics to pages.
     * </p>
     * 
     * @param <T>             the expected type of the controller, namely a
     *                        controller that extends {@link PageController}.
     * @param controllerClass the class object of the expected controller.
     * @return the controller casted to the specified type.
     * @throws IllegalArgumentException if the controller class is not an extension
     *                                  of {@link PageController}.
     */
    protected <T extends PageController> T getController(final Class<T> controllerClass) {
        if (controllerClass.isInstance(this.controller)) {
            return controllerClass.cast(this.controller);
        }
        throw new IllegalArgumentException("The controller must extend PageController");
    }
}
