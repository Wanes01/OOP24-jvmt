package model.game.api;

import java.util.Iterator;

import model.game.impl.GameImpl;
import model.leaderboard.api.Leaderboard;
import model.player.api.LogicCpu;
import model.round.api.Round;

/**
 * Iterator of {@link Round} elements that saves the
 * informations regarding a game.
 * 
 * @see GameImpl
 * @see Round
 * @see Iterator
 * 
 * @author Filippo Gaggi
 */
public interface Game extends Iterator<Round> {

    /**
     * @return the end game's leaderboard.
     */
    Leaderboard getLeaderboard();

    /**
     * @return the current round number.
     */
    int getCurrentRoundNumber();

    /**
     * @return the game's CPU logic.
     */
    LogicCpu getLogicCpu();

    /**
     * @return the game's settings.
     */
    GameSettings getSettings();
}
