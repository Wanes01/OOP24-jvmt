package jvmt.controller.api;

import jvmt.view.navigator.api.PageNavigator;
import jvmt.view.page.api.ControllerAwarePage;
import jvmt.view.page.api.Page;

/**
 * Base class for page controllers.
 * <p>
 * A {@code PageController} is associated with a specific
 * {@link ControllerAwarePage}. This
 * class is designed to be extented by concrete controllers for
 * invididual pages.
 * </p>
 * 
 * @see Page
 * @see ControllerAwarePage
 * 
 * @author Emir Wanes Aouioua
 */
public class PageController {
    private final ControllerAwarePage page;
    private final PageNavigator navigator;

    /**
     * Sets the page for which this controller
     * must handle interactions.
     * 
     * @param page      the page that this controller handles.
     * @param navigator the navigator used to go to other pages.
     */
    protected PageController(final ControllerAwarePage page, final PageNavigator navigator) {
        this.page = page;
        this.navigator = navigator;
    }

    /**
     * Returns the page that this controller handles.
     * 
     * @return the page handled by this controller.
     */
    protected ControllerAwarePage getPage() {
        return this.page;
    }

    /**
     * Returns the navigator used to navigate to other pages.
     * 
     * @return the navigator used to switch page.
     */
    protected PageNavigator getPageNavigator() {
        return this.navigator;
    }
}
