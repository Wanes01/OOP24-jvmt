package view.navigator.api;

import java.util.Optional;

import view.page.api.Page;

/**
 * Models a navigator to quickly change the {@link Page}
 * displayed. The navigator associates each {@link PageId}
 * to a specific page that will be displayed when
 * its ID is passed to {@link #navigateTo(PageId)}
 * 
 * @see Page
 * @see PageId
 * 
 * @author Emir Wanes Aouioua
 */
public interface PageNavigator {
    /**
     * Changes the displayed page with that associated with {@code id}.
     * 
     * @param id the id of the page to be displayed.
     */
    void navigateTo(PageId id);

    /**
     * Register an association between the {@code id} and the specified page.
     * 
     * @param id   the id to bind to the page.
     * @param page the page that has to been bound to the id.
     */
    void registerView(PageId id, Page page);

    /**
     * Gets the id of the current displayed page
     * (if the current displayed page is registered on this navigator).
     * 
     * @return an {@link Optional} containing the {@link PageId} of the current
     *         page, an empty optional if the displayed page is not registered on
     *         this navigator.
     */
    Optional<PageId> getCurrentPageId();
}
