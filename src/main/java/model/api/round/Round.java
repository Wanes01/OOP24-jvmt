package model.api.round;

import java.util.Iterator;

import model.api.common.Describable;
import model.api.round.turn.Turn;

/**
 * Represents a single round within a game.
 * <p>
 * A round is composed of a sequence of {@link Turn} objects and
 * defines how players progress, gain gems and when the rould ends.
 * </p>
 * <p>
 * A round is an {@link Iterable}, and provides and iterator over
 * the turns to be played. The iteration is intended to be performed
 * only once: multiple iterations are not supported and will result
 * in an {@link IllegalStateException}
 * </p>
 * 
 * @see Turn
 * @see RoundState
 * @see Iterable
 * @see Describable
 * 
 * @author Emir Wanes Aouioua
 */
public interface Round extends Iterable<Turn>, Describable {

    /**
     * Returns an iterator of {@link Turn}s to be played in this round.
     * <p>
     * This iterator allows consuming the turns in order. The round is
     * designed to be iterated only once. Calling this method more than
     * once will result in an {@link IllegalStateException}
     * </p>
     * 
     * @return an iterator over the turns to played in this round.
     * 
     * @throws IllegalStateException if this method is called after an iterator has
     *                               already been created or after the iteration has
     *                               been completed (i.e., no more turns can be
     *                               played in this round).
     */
    @Override
    Iterator<Turn> iterator();

    /**
     * {@inheritDoc}
     * 
     * Returns a description about the current round rules. To be more specific:
     * <ul>
     * <li>When the round ends.</li>
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
}
