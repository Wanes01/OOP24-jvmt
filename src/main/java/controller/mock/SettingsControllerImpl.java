package controller.mock;

import java.util.function.Consumer;

import controller.api.PageController;
import model.settings.api.GameSettings;
import view.navigator.api.PageNavigator;
import view.page.api.Page;

public class SettingsControllerImpl extends PageController {

    public SettingsControllerImpl(Page page, PageNavigator navigator, Consumer<GameSettings> settingsSetter) {
        super(page, navigator);
    }

}
