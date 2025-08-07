package impl.round;

import java.util.Iterator;
import java.util.List;

import api.others.Deck;
import api.others.PlayerInRound;
import api.round.Round;
import api.round.RoundState;
import api.round.roundeffect.RoundEffect;
import api.round.turn.Turn;
import impl.round.turn.TurnImpl;

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
public class RoundImpl implements Round {

    private final RoundState state;
    private final RoundEffect effect;
    private boolean iteratorUsed = false;

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

        players.forEach(p -> p.resetRoundPlayer());
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
     */
    @Override
    public void endRound() {
        if (!this.effect.isRoundOver(state)) {
            throw new IllegalStateException("Gems can be transfered from the sack to the chest only on round end.");
        }

        /*
         * Only players who are not active when the round ends can put their gems in the
         * chest
         */
        final List<PlayerInRound> players = this.state.getRoundPlayersManager().getExitedPlayers();
        players.forEach(e -> e.addSackToChest());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Turn> iterator() {
        if (this.iteratorUsed) {
            throw new IllegalStateException("To play a new round a new RoundImpl object must be created.");
        }
        this.iteratorUsed = true;

        return new Iterator<Turn>() {

            @Override
            public boolean hasNext() {
                return state.getRoundPlayersManager().hasNext()
                        && !effect.isRoundOver(state);
            }

            @Override
            public Turn next() {
                final PlayerInRound player = state.getRoundPlayersManager().next();
                return new TurnImpl(player, state, effect);
            }
        };
    }

}
