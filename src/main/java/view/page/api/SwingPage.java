package view.page.api;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Component;
import java.awt.Font;

import controller.PageController;
import view.window.impl.SwingWindow;

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

    private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 23);
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

    /**
     * Helper method that tries to apply the default font to a component
     * and then adds it through the {@link Runnable} {@code addComponent} to this
     * page.
     * This method helps reduce code duplication: to set a default font, each
     * overload of the JPanel's "add" method must be overridden. A Runnable is used
     * to specify which overload to use.
     * 
     * @param component    the component on which the font will be applied, if
     *                     possible, and which will be added to the page.
     * @param addOperation the operation to run in order to add the component to
     *                     this page.
     * @return the given {@code component} with, possibly, the default font
     *         applied.
     */
    private Component applyDefaultFontAndAddComponent(
            final Component component,
            final Runnable addOperation) {
        this.applyDefaultFont(component);
        addOperation.run();
        return component;
    }

    /**
     * Tries to apply the default font to the {@code component}.
     * The default font is applied to the component only if no custom font
     * is already set on it.
     * 
     * @param component the component to which to apply the font.
     */
    private void applyDefaultFont(final Component component) {
        if (component instanceof JComponent) {
            final JComponent jComponent = (JComponent) component;
            final Font currentFont = jComponent.getFont();
            if (currentFont == null || this.isDefaultSystemFont(currentFont)) {
                jComponent.setFont(DEFAULT_FONT);
            }
        }
    }

    /**
     * Returns true if the specified font is the default system font.
     * 
     * @param font the font to check if it is che default system font.
     * @return true if the specified font is the system's default font, false
     *         otherwise.
     */
    private boolean isDefaultSystemFont(final Font font) {
        return font.equals(UIManager.getDefaults().getFont("Label.font"));
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Tries to apply the default font to the component before adding it to this
     * page.
     * </p>
     */
    @Override
    public Component add(final Component component) {
        return this.applyDefaultFontAndAddComponent(
                component,
                () -> super.add(component));
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Tries to apply the default font to the component before adding it to this
     * page.
     * </p>
     */
    @Override
    public Component add(final String name, final Component component) {
        return this.applyDefaultFontAndAddComponent(
                component,
                () -> super.add(name, component));
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Tries to apply the default font to the component before adding it to this
     * page.
     * </p>
     */
    @Override
    public Component add(final Component component, final int index) {
        return this.applyDefaultFontAndAddComponent(
                component,
                () -> super.add(component, index));
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Tries to apply the default font to the component before adding it to this
     * page.
     * </p>
     */
    @Override
    public void add(final Component component, final Object obj) {
        this.applyDefaultFontAndAddComponent(
                component,
                () -> super.add(component, obj));
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Tries to apply the default font to the component before adding it to this
     * page.
     * </p>
     */
    @Override
    public void add(
            final Component component,
            final Object obj,
            final int index) {
        this.applyDefaultFontAndAddComponent(
                component,
                () -> super.add(component, obj, index));
    }
}
