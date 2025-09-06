package jvmt.model.round.impl;

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
 * of a game. A round consists of a sequence of {@link Turn}s for the players.
 * <p>
 * At the beginning of the round all players are reset using
 * {@link PlayerInRound#resetRoundPlayer()} and the round state is initialized
 * with the given {@link Deck} and list of players.
 * </p>
 * <p>
 * This class is an {@code Iterator} over {@link Turn}s.
 * New turns can be obtained via {@link #next()} until the round's end condition
 * is reached (specified by {@link #hasNext()})
 * </p>
 * <p>
 * <strong>Note:</strong>
 * When the round ends, the {@code endRound()} method should be called to
 * transfer gems from the players' sacks to their chests.
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
    private int currentTurn;

    /**
     * Creates a RoundImpl object, starting a new round.
     * <p>
     * Upon creation of a RoundImpl the sack and choice of all players are reset and
     * the shared round state is created.
     * </p>
     * 
     * @see PlayerInRound#resetRoundPlayer()
     * @see RoundEffect
     * 
     * @param players the players who will play in this round.
     * @param deck    the deck that will be used during this round.
     * @param effect  the effect that is applied to this round that will determine
     *                its modifier for gems and end condition.
     * 
     * @throws NullPointerException if {@code players}, {@code deck} or
     *                              {@code effect} is null.
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
    public boolean hasNext() {
        return !this.effect.isEndConditionMet(this.state);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NoSuchElementException if the round is over and no more turns can be
     *                                played.
     */
    @Override
    public Turn next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("The round has ended. No more turns can be played.");
        }
        this.currentTurn++;
        final PlayerInRound player = state.getRoundPlayersManager().next();
        return new TurnImpl(player, state, effect);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalStateException if the round has not ended yet.
     */
    @Override
    public void endRound() {
        if (this.hasNext()) {
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
     * {@inheritDoc}
     */
    @Override
    public int getTurnNumber() {
        return this.currentTurn;
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
}
