package view.navigator.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import view.navigator.api.PageId;
import view.navigator.api.PageNavigator;
import view.page.api.Page;
import view.window.api.Window;

/**
 * Implementation of the {@link PageNavigator} interface.
 * <p>
 * This class manages the navigation between pages on a {@link Window}.
 * Each page is associated with a unique {@link PageId} which allows
 * to register and switch the currently displayed page.
 * </p>
 * <p>
 * Navigation is performed by selecting the page associated with a specific ID
 * and setting it as the current page of the {@link Window}.
 * </p>
 * 
 * @see Window
 * @see Page
 * @see PageId
 * 
 * @author Emir Wanes Aouioua
 */

public class PageNavigatorImpl implements PageNavigator {

    @SuppressFBWarnings(value = { "EI_EXPOSE_REP",
            "EI_EXPOSE_REP2" }, justification = "Internal mutable objects are part of the game logic and shared by design")
    private final Window window;
    private final Map<PageId, Page> pages = new EnumMap<>(PageId.class);

    /**
     * Creates a new {@code PageNavigatorImpl} that operates on the specified
     * {@code window}.
     * 
     * @param window the {@link Window} where pages will be displayed. This object
     *               is stored directly: changes to it will affect the navigator.
     * @throws NullPointerException if {@code window} is {@code null}.
     */
    public PageNavigatorImpl(final Window window) {
        Objects.requireNonNull(window);
        this.window = window;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException if no page is linked to the specified id.
     */
    @Override
    public void navigateTo(final PageId id) {
        if (!this.isIdRegistered(id)) {
            throw new IllegalArgumentException("No page is bound to " + id.toString() + " id");
        }
        final Page page = pages.get(id);
        this.window.setCurrentPage(page);
    }

    /**
     * Returns true if a page is bound to the specified id, false otherwise.
     * 
     * @param id the id to check that it has an associated page.
     * @return true if a page is bound to the given id, false otherwise.
     */
    private boolean isIdRegistered(final PageId id) {
        return this.pages.keySet().contains(id);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * This implementation allows to override an id to bind it
     * to a different page.
     * </p>
     */
    @Override
    public void registerPage(final PageId id, final Page page) {
        this.pages.put(id, page);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * A page could be set on a window without using this navigator.
     * This implementation attempts to find the ID associated with a given page,
     * returning an optional empty value if that page is not registered.
     * </p>
     */
    @Override
    public Optional<PageId> getCurrentPageId() {
        final Optional<Page> displayed = this.window.getCurrentPage();
        if (displayed.isEmpty()) {
            return Optional.empty();
        }

        final Page page = displayed.get();

        /*
         * Tries to find the ID of the current page.
         * The current page could have been set without using this
         * navigator so a comparison between id and pages must be done.
         */
        return Optional.ofNullable(
                this.pages.entrySet().stream()
                        .filter(e -> e.getValue().equals(page))
                        .map(Entry::getKey)
                        .findFirst()
                        .orElse(null));
    }

}
