package controller.api;

import view.navigator.api.PageNavigator;
import view.page.api.Page;

/**
 * Base class for page controllers.
 * <p>
 * A {@code PageController} is associated with a specific {@link Page}. This
 * class is designed to be extented by concrete controllers for
 * invididual pages.
 * </p>
 * 
 * @see Page
 * 
 * @author Emir Wanes Aouioua
 */
public class PageController {
    private final Page page;
    private final PageNavigator navigator;

    /**
     * Sets the page for which this controller
     * must handle interactions.
     * 
     * @param page      the page that this controller handles.
     * @param navigator the navigator used to go to other pages.
     */
    protected PageController(final Page page, final PageNavigator navigator) {
        this.page = page;
        this.navigator = navigator;
    }

    /**
     * Returns the page that this controller handles.
     * 
     * @return the page handled by this controller.
     */
    protected Page getPage() {
        return this.page;
    }

    /**
     * Returns the navigator used to to to other pages.
     * 
     * @return the navigator to switch page.
     */
    protected PageNavigator getPageNavigator() {
        return this.navigator;
    }
}
