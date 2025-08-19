package view.window.impl;

import java.util.Optional;

import javax.swing.JFrame;

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
 * By default, the window is created with a size of {@code 1280x720} pixels,
 * unless a custom size is provided.
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

    private static final int DEFAULT_WIDTH = 1280;
    private static final int DEFAULT_HEIGHT = 720;
    private transient Optional<SwingPage> currentPage = Optional.empty();

    /**
     * Creates a new swing window with the given dimensions.
     * 
     * @param width  the window's width, in pixels.
     * @param height the window's height, in pixels.
     */
    public SwingWindow(final int width, final int height) {
        /*
         * this.setDefaultCloseOperation(EXIT_ON_CLOSE);
         * must be called after the window creation.
         */
        super.setSize(width, height);
    }

    /**
     * Creates a new swing window with a default size of {@code 1280x720} pixels.
     */
    public SwingWindow() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
            throw new IllegalArgumentException("View non compatibile con Swing");
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

}
