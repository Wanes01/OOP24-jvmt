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
public abstract class SwingPage extends ControllerAwarePage {

    private final JPanel panel = new JPanel();

    /**
     * This class is designed to be extended and
     * must not be instanciated directly.
     */
    protected SwingPage() {
        // this constructor is empty on purpose.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        this.panel.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dismiss() {
        this.panel.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        this.panel.revalidate();
        this.panel.repaint();
    }

    /**
     * Returns the {@link JPanel} used by this Swing implementation of {@link Page}.
     * 
     * @return the {@code JPanel} used by this page implementation.
     */
    public JPanel getPanel() {
        return this.panel;
    }
}
