package controller.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * @throws NullPointerException if {@link players} is null.
     * 
     * @param page      the page that this controller handles.
     * @param navigator the navigator used to go to other pages.
     * @param players   the list of players that are to appear in the leaderboard.
     */
    public LeaderboardControllerImpl(final Page page, final PageNavigator navigator, final Game game) {
        super(
                Objects.requireNonNull(page),
                Objects.requireNonNull(navigator),
                Objects.requireNonNull(game));
        this.players = null;
        /*
         * Objects.requireNonNull(players).stream()
         * .sorted(Comparator.comparingInt(PlayerInRound::getChestGems).reversed())
         * .collect(Collectors.toList());
         */
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
