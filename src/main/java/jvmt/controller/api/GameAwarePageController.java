package jvmt.controller.api;

import jvmt.model.game.api.Game;
import jvmt.view.navigator.api.PageNavigator;
import jvmt.view.page.api.Page;

public class GameAwarePageController extends PageController {

    private final Game game;

    protected GameAwarePageController(Page page, PageNavigator navigator, Game game) {
        super(page, navigator);
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

}
