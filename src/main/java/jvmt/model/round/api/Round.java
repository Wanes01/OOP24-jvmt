package jvmt.model.round.api;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jvmt.model.common.api.Describable;
import jvmt.model.round.api.turn.Turn;

/**
 * Represents a single round within a game.
 * <p>
 * A round is composed of a sequence of {@link Turn} objects and
 * defines how players progress, gain gems and when the round ends.
 * </p>
 * <p>
 * A round is an {@link Iterator} over the turns to be played.
 * </p>
 * <p>
 * While {@code Round} specifies how a round should behave,
 * {@link RoundState} captures the current information known about the
 * round, like drawn cards, remaining gems, and player status.
 * </p>
 * 
 * @see Turn
 * @see RoundState
 * @see Iterator
 * @see Describable
 * 
 * @author Emir Wanes Aouioua
 */
public interface Round extends Iterator<Turn>, Describable {

    /**
     * Return whether the current round is over or not (i.e. another
     * turn can be played).
     * 
     * @return true if another turn can be played, false otherwise.
     */
    @Override
    boolean hasNext();

    /**
     * Returns the next {@link Turn} to be played.
     * 
     * @throws NoSuchElementException if the round is over and no more turns can be
     *                                played.
     */
    @Override
    Turn next();

    /**
     * {@inheritDoc}
     * 
     * Returns a description about the current round rules. To be more specific:
     * <ul>
     * <li>What makes the round ends.</li>
     * <li>How the gems gained are modified.</li>
     * </ul>
     * 
     * @return the description of this round rules.
     */
    @Override
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
     * whose left during this round from their sack to their chest.
     * 
     * <p>
     * Note: this method must be called when the round has ended,
     * namely after all turns have been consumed.
     * </p>
     * 
     * @throws IllegalStateException if the round has not ended yet.
     */
    void endRound();

    /**
     * Returns the number of the current turn in this round.
     * 
     * @return the number of the turn currently being played.
     */
    int getTurnNumber();
}
