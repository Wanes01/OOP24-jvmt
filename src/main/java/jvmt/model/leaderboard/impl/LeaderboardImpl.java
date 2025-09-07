package jvmt.model.leaderboard.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import jvmt.model.leaderboard.api.Leaderboard;
import jvmt.model.player.api.Player;

/**
 * Implementation of the Leaderboard interface.
 * 
 * @see Leaderboard
 * 
 * @author Filippo Gaggi
 */
public class LeaderboardImpl implements Leaderboard {

    private final List<Player> listPlayers;

    /**
     * Constructor of the class.
     * 
     * @throws NullPointerException if {@link listPlayers} is null.
     * 
     * @param listPlayers list of the players that played the game.
     */
    public LeaderboardImpl(final List<Player> listPlayers) {
        this.listPlayers = new ArrayList<>(Objects.requireNonNull(listPlayers));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayersSortedByScore() {
        this.listPlayers.sort(Comparator.comparing(Player::getChestGems).reversed());
        return Collections.unmodifiableList(this.listPlayers);
    }
}
