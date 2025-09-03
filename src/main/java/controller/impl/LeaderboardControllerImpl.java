package controller.impl;

import java.util.List;
import java.util.Objects;

import controller.api.GameAwarePageController;
import controller.api.LeaderboardController;
import model.game.api.Game;
import model.leaderboard.api.Leaderboard;
import view.navigator.api.PageId;
import view.navigator.api.PageNavigator;
import view.page.api.Page;
import view.page.utility.Pair;

/**
 * The implementation of the {@link LeaderboardController} interface.
 * 
 * @see LeaderboardControllerImpl
 * 
 * @author Filippo Gaggi
 */
public class LeaderboardControllerImpl extends GameAwarePageController implements LeaderboardController {

    private final Leaderboard leaderboard;

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
        super(Objects.requireNonNull(page),
            Objects.requireNonNull(navigator),
            Objects.requireNonNull(game));
        this.leaderboard = game.getLeaderboard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pair<String, Integer>> getSortedPlayerScores() {
        return this.leaderboard.getPlayersSortedByScore()
                .stream()
                .map(p -> new Pair<>(p.getName(), p.getChestGems()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWinner() {
        return this.getSortedPlayerScores().getFirst().first();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToHomePage() {
        this.getPageNavigator().navigateTo(PageId.MENU);
    }
}
