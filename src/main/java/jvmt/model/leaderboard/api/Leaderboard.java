package jvmt.model.leaderboard.api;

import java.util.List;

import jvmt.model.leaderboard.impl.LeaderboardImpl;
import jvmt.model.player.impl.PlayerInRound;

/**
 * Rapresents the leaderboard at the end of the game.
 * <p>
 * This interface provides methods for ordering the
 * list of players by their final score.
 * </p>
 * 
 * @see LeaderboardImpl
 * 
 * @author Filippo Gaggi
 */
public interface Leaderboard {

    /**
     * Orders the list of players by their final scores.
     * 
     * @return the list of the players ordered by
     *         their final score.
     */
    List<PlayerInRound> getPlayersSortedByScore();
}
