package controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import controller.api.GameAwarePageController;
import controller.api.LeaderboardController;
import model.game.api.Game;
import model.player.impl.PlayerInRound;
import view.navigator.api.PageId;
import view.navigator.api.PageNavigator;
import view.page.api.Page;

/**
 * The implementation of the {@link LeaderboardController} interface.
 * 
 * @see LeaderboardControllerImpl
 * 
 * @author Filippo Gaggi
 */
public class LeaderboardControllerImpl extends GameAwarePageController implements LeaderboardController {
    private final List<PlayerInRound> players;

    /**
     * Constructor of the class.
     * 
     * @throws NullPointerException if {@link page} is null.
     * @throws NullPointerException if {@link navigator} is null.
     * @throws NullPointerException if {@link game} is null.
     * 
     * @param page      the page that this controller handles.
     * @param navigator the navigator used to go to other pages.
     * @param game      the round iterator of the game.
     */
    public LeaderboardControllerImpl(final Page page, final PageNavigator navigator, final Game game) {
        super(
            Objects.requireNonNull(page),
            Objects.requireNonNull(navigator),
            Objects.requireNonNull(game));
        this.players = Objects.requireNonNull(game).getLeaderboard().getPlayersSortedByScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerInRound> getPlayerList() {
        return new ArrayList<>(this.players);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInRound getWinner() {
        return this.players.getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToHomePage() {
        this.getPageNavigator().navigateTo(PageId.MENU);
    }

}
