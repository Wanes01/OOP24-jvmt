package model.game.api;

import java.util.Iterator;

import model.game.impl.GameImpl;
import model.leaderboard.api.Leaderboard;
import model.player.api.LogicCpu;
import model.round.api.Round;

/**
 * Iterator that manages the game's rounds
 * and has all of the game's informations.
 * 
 * @see GameImpl
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
