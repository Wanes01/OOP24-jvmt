package controller.mock;

import java.util.List;

import controller.api.PageController;
import model.player.impl.PlayerInRound;
import view.navigator.api.PageNavigator;
import view.page.api.Page;

public class LeaderboardControllerImpl extends PageController {

    public LeaderboardControllerImpl(Page page, PageNavigator navigator, List<PlayerInRound> players) {
        super(page, navigator);
    }

}
