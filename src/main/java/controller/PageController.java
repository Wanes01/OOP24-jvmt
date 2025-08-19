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
    protected final Page page;

    /**
     * Sets the page for which this controller
     * must handle interactions.
     * 
     * @param page the page that this controller handles.
     */
    protected PageController(Page page) {
        this.page = page;
    }
}
