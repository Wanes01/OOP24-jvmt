package controller;

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

    /**
     * Sets the page for which this controller
     * must handle interactions.
     * 
     * @param page the page that this controller handles.
     */
    protected PageController(final Page page) {
        this.page = page;
    }

    /**
     * Returns the page that this controller handles.
     * 
     * @return the page handled by this controller.
     */
    protected Page getPage() {
        return this.page;
    }
}
