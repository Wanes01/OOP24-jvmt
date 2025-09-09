package jvmt.view.page.api;

import jvmt.controller.api.PageController;

/**
 * Abstract base class for {@link Page}s that are associated with
 * a {@link PageController}.
 * <p>
 * This class provides a way to bind a controller to a page
 * and to retrieve it a type-safe way.
 * </p>
 */
public abstract class ControllerAwarePage implements Page {
    /**
     * the controller that will handle the interaction with this page.
     */
    private PageController controller;

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
    public void setController(PageController controller) {
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
    protected <T extends PageController> T getController(Class<T> controllerClass) {
        if (controllerClass.isInstance(this.controller)) {
            return controllerClass.cast(this.controller);
        }
        throw new IllegalArgumentException("The controller must extend PageController");
    }
}
