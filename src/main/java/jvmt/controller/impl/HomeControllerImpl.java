package jvmt.controller.impl;

import jvmt.controller.api.HomeController;
import jvmt.controller.api.PageController;
import jvmt.controller.navigator.api.PageId;
import jvmt.controller.navigator.api.PageNavigator;
import jvmt.view.page.api.ControllerAwarePage;

/**
 * The implementation of the HomeController interface.
 * 
 * @author Andrea La Tosa
 */
public class HomeControllerImpl extends PageController implements HomeController {

    /**
     * Creates a new instance of {@code HomeControllerImpl}.
     * 
     * @param page the page that this controller handles
     * @param nav  the navigation controller to move between the various views
     */
    public HomeControllerImpl(final ControllerAwarePage page, final PageNavigator nav) {
        super(page, nav);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToSettingPage() {
        this.getPageNavigator().navigateTo(PageId.SETTINGS);
    }

}
