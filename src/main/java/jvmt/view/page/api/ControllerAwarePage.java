package jvmt.view.page.api;

import java.util.Optional;

import jvmt.controller.api.PageController;

/**
 * Abstract base class for {@link Page}s that are associated with
 * a {@link PageController}.
 * <p>
 * This class provides a way to bind a controller to a page
 * and to retrieve it in a type-safe way, without propagating the use
 * of generics into implementations.
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
 * @see Page
 * 
 * @author Emir Wanes Aouioua
 */
public abstract class ControllerAwarePage implements Page {
    /**
     * the controller that will handle the interaction with this page.
     */
    private Optional<PageController> controller;

    /**
     * Initializes this page with no controller bound to it.
     */
    protected ControllerAwarePage() {
        this.controller = Optional.empty();
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
        this.controller = Optional.of(controller);
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
     * @throws IllegalStateException    if the controller has not been set yet.
     */
    protected <T extends PageController> T getController(final Class<T> controllerClass) {
        if (this.controller.isEmpty()) {
            throw new IllegalStateException("A controller has not been set yet.");
        }
        if (controllerClass.isInstance(this.controller.get())) {
            return controllerClass.cast(this.controller.get());
        }
        throw new IllegalArgumentException("The controller must extend PageController");
    }
}
