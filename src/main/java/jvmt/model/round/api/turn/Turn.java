package jvmt.model.round.api.turn;

import java.util.Optional;
import java.util.Set;

import jvmt.model.card.api.Card;
import jvmt.model.player.impl.PlayerInRound;
import jvmt.model.round.api.Round;

/**
 * Represents a single turn in a round of the game. Each turn
 * is associated with a specific player who must draw a card, and may
 * result in the distribution of gems depending on the card drawn and
 * player choices.
 * 
 * <p>
 * This interface provides methods to manage the draw phase and the
 * successive gem distribution phase of a turn.
 * </p>
 * 
 * <p>
 * Implementations are expected to enforce the correct order of operations:
 * {@link #executeDrawPhase()} must be called before {@link #endTurn(Set)}.
 * </p>
 * 
 * @see Round
 * @see PlayerInRound
 * @see Card
 * @see Optional
 * 
 * @author Emir Wanes Aouioua
 */
public interface Turn {

    /**
     * Returns the player whose turn it is to draw a card.
     *
     * @return the player who is currently responsible for drawing a card.
     */

    PlayerInRound getCurrentPlayer();

    /**
     * Returns the card drawn during this turn, if any.
     *
     * @return an {@link Optional} containing the drawn card, or an empty Optional
     *         if no card have been drawn yet.
     */
    Optional<Card> getDrawnCard();

    /**
     * Executes the draw phase for this turn:
     * The current player draws a card from the shared deck and
     * if a treasure card is drawn, gems are distributed among the active players.
     *
     * @throws IllegalStateException if a card has already been drawn during this
     *                               turn.
     */
    void executeDrawPhase();

    /**
     * Represents the stage after the players decide whether or not to stay in the
     * round. It divides the gems left in the round among the players who have
     * decided to exit and assigns relics in case only one player exits.
     * 
     * <p>
     * Note: This method must be called after calling
     * {@link #executeDrawPhase()} and after the
     * interaction with the users to collect their decisions on whether or not to
     * exit the round.
     * </p>
     * 
     * @param playersExitingThisTurn the players who decided to leave the round
     *                               during this turn, in no particular order.
     * @throws IllegalStateException    if the {@link #executeDrawPhase()}
     *                                  has not yet been executed.
     * @throws IllegalArgumentException if at least one player in
     *                                  {@code playersExitingThisTurn} is still
     *                                  active in the round.
     */
    void endTurn(Set<PlayerInRound> playersExitingThisTurn);
}
