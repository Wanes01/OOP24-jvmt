package controller.impl;

import controller.api.HomeController;
import controller.api.PageController;
import view.navigator.api.PageId;
import view.navigator.api.PageNavigator;
import view.page.api.Page;

public class HomeControllerImpl extends PageController implements HomeController {

    public HomeControllerImpl(Page page, PageNavigator nav) {
        super(page, nav);
    } 

    @Override
    public void goToSettingPage() {
        this.getPageNavigator().navigateTo(PageId.SETTINGS);
    }

}
