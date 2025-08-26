package controller.mock;

import controller.api.PageController;
import view.navigator.api.PageNavigator;
import view.page.api.Page;

public class HomeControllerImpl extends PageController {

    public HomeControllerImpl(Page page, PageNavigator navigator) {
        super(page, navigator);
    }

}
