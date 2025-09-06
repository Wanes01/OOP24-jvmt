package jvmt.model.round.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jvmt.model.card.api.Deck;
import jvmt.model.player.impl.PlayerInRound;
import jvmt.model.round.api.Round;
import jvmt.model.round.api.RoundState;
import jvmt.model.round.api.roundeffect.RoundEffect;
import jvmt.model.round.api.turn.Turn;
import jvmt.model.round.impl.turn.TurnImpl;
import jvmt.utils.CommonUtils;

/**
 * Implementation of the {@link Round} interface that represents a single round
 * of a game. A round consists of a sequence of {@link Turn}s for the players,
 * and ends based on a condition defined by a {@link RoundEffect}.
 * <p>
 * At the beginning of the round all players are reset using
 * {@link PlayerInRound#resetRoundPlayer()} and the round state is initialized
 * with the given {@link Deck} and list of players.
 * </p>
 * <p>
 * This class is also iterable, providing an {@link Iterator} over {@link Turn}s
 * until the round is end condition is reached or there are no more players
 * that can play.
 * </p>
 * <p>
 * When the round ends, the {@code endRound()} method can be called to transfer
 * gems
 * from the players' sacks to their chests.
 * </p>
 * 
 * @see Round
 * @see RoundState
 * @see RoundEffect
 * @see Turn
 * @see PlayerInRound
 * @see Deck
 * 
 * @author Emir Wanes Aouioua
 */

@SuppressFBWarnings(value = { "EI_EXPOSE_REP",
        "EI_EXPOSE_REP2" }, justification = "Internal mutable objects are part of the game logic and shared by design")
public class RoundImpl implements Round {

    private final RoundState state;
    private final RoundEffect effect;
    private int currentTurn = 0;

    /**
     * Creates a RoundImpl object, starting a new round.
     * <p>
     * Upon creation of a RoundImpl the status of all players is reset and the
     * shared round state is created.
     * </p>
     * 
     * @see PlayerInRound#resetRoundPlayer()
     * @see RoundEffect
     * 
     * @param players the players who will play in this round
     * @param deck    the deck that will be used during this round
     * @param effect  the effect that is applied to this round that will determine
     *                its modifier for gems and end condition
     */
    public RoundImpl(
            final List<PlayerInRound> players,
            final Deck deck,
            final RoundEffect effect) {
        CommonUtils.requireNonNulls(players, deck, effect);

        players.forEach(PlayerInRound::resetRoundPlayer);
        this.state = new RoundStateImpl(players, deck);
        this.effect = effect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return this.effect.getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoundState getState() {
        return this.state;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException if this method is called but the round has not
     *                               ended yet.
     */
    @Override
    public void endRound() {
        if (!this.isRoundOver()) {
            throw new IllegalStateException("Gems can be transfered from the sack to the chest only on round end.");
        }

        /*
         * Only players who are not active when the round ends can put their gems in the
         * chest
         */
        final List<PlayerInRound> players = this.state.getRoundPlayersManager().getExitedPlayers();
        players.forEach(PlayerInRound::addSackToChest);
    }

    /**
     * Return whether the current round is over or not.
     * The round is over if there are no active players, if there are no
     * cards to draw or if the extra end condition of the round is met.
     * 
     * @return true if the round is over, false otherwise.
     */
    private boolean isRoundOver() {
        return !this.state.getRoundPlayersManager().hasNext()
                || !this.state.getDeck().hasNext()
                || this.effect.isEndConditionMet(state);
    }

    @Override
    public boolean hasNext() {
        return !this.isRoundOver();
    }

    @Override
    public Turn next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("The round has ended. No more turns can be played.");
        }
        this.currentTurn++;
        final PlayerInRound player = state.getRoundPlayersManager().next();
        return new TurnImpl(player, state, effect);
    }

    @Override
    public int getTurnNumber() {
        return this.currentTurn;
    }

}
