package jvmt.view.page.api;

import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jvmt.view.window.impl.SwingWindow;

/**
 * Abstract class for {@link Page}s that use the Swing library.
 * <p>
 * A {@code SwingPage} extends {@link ControllerAwarePage} and implements the
 * {@link Page}
 * interface. This class is designed to be extended by concrete pages of the
 * application.
 * </p>
 * <p>
 * This implementation wraps a {@link JPanel} to set the visual components.
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
    @SuppressFBWarnings(value = { "EI_EXPOSE_REP",
            "EI_EXPOSE_REP2" }, justification = "The shared panel is part of the view design")
    public JPanel getPanel() {
        return this.panel;
    }
}
