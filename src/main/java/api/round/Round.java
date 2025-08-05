package api.round;

import java.util.Iterator;
import java.util.NoSuchElementException;

import api.common.Describable;
import api.round.turn.Turn;

/**
 * Represents a single round within a game.
 * <p>
 * A round is composed of a sequence of {@link Turn} objects and
 * defines how players porgress, gain gems and when the round ends.
 * </p>
 * 
 * @see Turn
 * @see RoundState
 * @see Iterator
 * 
 * @author Emir Wanes Aouioua
 */
public interface Round extends Iterator<Turn>, Describable {

    /**
     * 
     * Returns the next {@link Turn} to be played during this round.
     * 
     * @return the next turn to be played in this round.
     * 
     * @throws NoSuchElementException if no more turns can be created for this
     *                                round.
     */
    Turn next();

    /**
     * 
     * Tells if there is any turn to be played left.
     * 
     * @return true if a new turn can be played, false otherwise.
     */
    boolean hasNext();

    /**
     * {@inheritDoc}
     * 
     * Returns a description about the current round rules. For example:
     * <ul>
     * <li>When the round ends.</li>
     * <li>How the gems gained are modified</li>
     * </ul>
     * 
     * @return the description of this round rules.
     */
    String getDescription();

    /**
     * Returns the {@link RoundState} object created for this round
     * that mantains the round status.
     * 
     * @return the state of the current round.
     */
    RoundState getState();

    /**
     * Ends the current round. Transfers the gems gained by the players
     * during this round from their sack to their chest.
     * 
     * <p>
     * Note: this method must be called when the round has ended,
     * namely when {@link #hasNext()} returns false.
     * </p>
     * 
     * @throws IllegalStateException if the round has not ended yet.
     */
    void endRound();
}
