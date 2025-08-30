package controller.api;

import model.game.api.Game;
import view.navigator.api.PageNavigator;
import view.page.api.Page;

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
