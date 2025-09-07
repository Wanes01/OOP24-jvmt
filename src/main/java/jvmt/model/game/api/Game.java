package jvmt.model.game.api;

import java.util.Iterator;

import jvmt.model.game.impl.GameImpl;
import jvmt.model.leaderboard.api.Leaderboard;
import jvmt.model.round.api.Round;

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
     * Getter for the game's leaderboard.
     * 
     * @return the end game's leaderboard.
     */
    Leaderboard getLeaderboard();

    /**
     * Getter for the current round number.
     * 
     * @return the current round number.
     */
    int getCurrentRoundNumber();

    /**
     * Getter for the game's settings.
     * 
     * @return the game's settings.
     */
    GameSettings getSettings();
}
