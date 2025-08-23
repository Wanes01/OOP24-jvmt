package view.window.impl;

import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import view.page.api.Page;
import view.page.api.SwingPage;
import view.window.api.Window;

/**
 * Swing based implementation of the {@link Window} interface.
 * <p>
 * {@code SwingWindow} extends {@link JFrame} and serves as a container
 * for {@link SwingPage}s. This implementation manages a single
 * {@link SwingPage} at a time as its current content.
 * </p>
 * <p>
 * The window can not be displayed or refreshed unless a page
 * has been set via {@link #setCurrentPage(Page)}, an
 * {@link IllegalStateException} will be thrown otherwise.
 * </p>
 * <p>
 * The window created occupies a fixed percentage of the user's screen. When the
 * first SwingWindow is created, a default font is applied to all graphic
 * components that support it, and the font size is scaled according to the DPI
 * of the reference screen.
 * </p>
 * 
 * @see Window
 * @see SwingPage
 * @see Page
 * 
 * @author Emir Wanes Aouioua
 */
public class SwingWindow extends JFrame implements Window {

    private static final long serialVersionUID = 1L;
    // how much of the screen this window will occupy.
    private static final double WINDOW_SCREEN_RATIO = 0.8;
    private static final String DEFAULT_FONT = Font.SANS_SERIF;
    private static final int BASE_FONT_SIZE = 23;
    private static boolean swingDpiConfigured;

    private transient Optional<SwingPage> currentPage = Optional.empty();

    /**
     * Creates a new swing window that occupies a fixed factor
     * of the screen size.
     */
    public SwingWindow() {
        final Dimension screen = Toolkit.getDefaultToolkit()
                .getScreenSize();
        final int width = (int) (WINDOW_SCREEN_RATIO * screen.width);
        final int height = (int) (WINDOW_SCREEN_RATIO * screen.height);
        super.setSize(width, height);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setResizable(false);

        initSwingDpiConfiguration();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException if a {@link SwingPage} is not set.
     */
    @Override
    public void display() {
        this.checkEmptyAndThrowStateException("Can't display an empty window. Set the current page first.");
        this.setVisible(true);
    }

    /**
     * Utility method that checks if the current {@link SwingPage} is set.
     * 
     * @param message the message to use in the exception if a page is not set.
     * @throws IllegalStateException if a {@link SwingPage} is not set.
     */
    private void checkEmptyAndThrowStateException(final String message) {
        if (this.currentPage.isEmpty()) {
            throw new IllegalStateException(message);
        }
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
     * 
     * @throws IllegalStateException if a {@link SwingPage} is not set.
     */
    @Override
    public void refresh() {
        this.checkEmptyAndThrowStateException("Can't refresh an empty window. Set the current page first.");
        this.revalidate(); // computes the new components position and sizes.
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableInteraction() {
        this.setEnabled(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enableInteraction() {
        this.setEnabled(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.dispose();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Only {@link SwingPage} instances are supported.
     * </p>
     * 
     * @param page the SwingPage to display.
     * @throws IllegalArgumentException if the page is not a {@link SwingPage}.
     */
    @Override
    public void setCurrentPage(final Page page) {
        if (!(page instanceof SwingPage)) {
            throw new IllegalArgumentException("This class supports SwingPage only as Page implementation.");
        }
        final SwingPage swingPage = (SwingPage) page;
        this.currentPage = Optional.of(swingPage);
        this.setContentPane(swingPage);
        this.refresh();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException if a {@link SwingPage} is not set.
     */
    @Override
    public Optional<Page> getCurrentPage() {
        return this.currentPage.isPresent()
                ? Optional.of((Page) this.currentPage.get())
                : Optional.empty();
    }

    /**
     * This method sets the default font, making it proportional on any screen. This
     * method applies the default font to all graphical components that support it
     * and calculates the font size based on the actual DPI of the user's display.
     * The body of this method is executed only once when the first SwingWindow is
     * created, in order to ensure that the default font is applied to all
     * swing-based components.
     */
    private static void initSwingDpiConfiguration() {
        if (swingDpiConfigured) {
            return;
        }

        final int actualDpi = Toolkit.getDefaultToolkit()
                .getScreenResolution();
        /*
         * Basic DPI used as a reference by Swing when the default scale
         * factor (100%) is used in the operative system.
         */
        final double baseDpi = 96.0;
        final double zoom = actualDpi / baseDpi;

        final Font defaultFont = new Font(DEFAULT_FONT, Font.PLAIN, (int) (BASE_FONT_SIZE * zoom));

        /*
         * Sets the default font to every UI components that
         * supports a font.
         */
        for (final var entry : UIManager.getDefaults().entrySet()) {
            final Object item = entry.getKey();
            if (item instanceof String
                    && ((String) item).endsWith(".font")) {
                UIManager.put(item, defaultFont);
            }
        }
        swingDpiConfigured = true;
    }

}
