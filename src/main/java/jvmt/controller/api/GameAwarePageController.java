package jvmt.controller.api;

import jvmt.model.game.api.Game;
import jvmt.controller.navigator.api.PageNavigator;
import jvmt.view.page.api.ControllerAwarePage;
import jvmt.view.page.api.Page;

/**
 * Base class for page controllers that require information
 * about the current game.
 * <p>
 * A {@code GameAwarePageController} is a {@link PageController}
 * that is aware of the current {@link Game}. This class can only
 * be extended and allows concrete controllers to access game data
 * to handle page interaction.
 * </p>
 * 
 * @see Game
 * @see PageController
 * @see Page
 * 
 * @author Emir Wanes Aouioua
 */
public class GameAwarePageController extends PageController {

    private final Game game;

    /**
     * Constructs a new {@code GameAwarePageController} for a specific
     * page.
     * 
     * @param page      the page that this controller handles.
     * @param navigator the navigator used to go to other pages.
     * @param game      the game from which this controller can retrieve
     *                  information.
     */
    protected GameAwarePageController(
            final ControllerAwarePage page,
            final PageNavigator navigator,
            final Game game) {
        super(page, navigator);
        this.game = game;
    }

    /**
     * Returns to the {@link Game} from which this controller gets its information.
     * 
     * @return the game object associated with this controller.
     */
    protected Game getGame() {
        return this.game;
    }

}
