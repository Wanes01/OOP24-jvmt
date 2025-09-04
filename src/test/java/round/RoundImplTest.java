package round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.round.api.roundeffect.RoundEffect;
import model.round.api.turn.Turn;
import model.card.api.Card;
import model.card.api.Deck;
import model.card.impl.DeckFactoryImpl;
import model.card.impl.TrapCard;
import model.player.api.PlayerChoice;
import model.player.impl.PlayerInRound;
import model.round.api.Round;
import model.round.api.RoundPlayersManager;
import model.round.api.RoundState;
import model.round.impl.RoundImpl;
import model.round.impl.roundeffect.RoundEffectImpl;
import model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import utils.CommonUtils;

/**
 * Tests for {@link RoundImpl} ({@link Round}
 * implementation).
 * <p>
 * This class tests a single round.
 * See {@link FullGameTest} for a full game simulation
 * (tests on multiple rounds).
 * </p>
 * 
 * @author Emir Wanes Aouioua
 */
class RoundImplTest {

    private static final int PLAYERS_COUNT = 3;
    private final List<PlayerInRound> players = CommonUtils.generatePlayerInRoundList(PLAYERS_COUNT);
    private final RoundEffect effect = new RoundEffectImpl(
            new EndConditionFactoryImpl().standard(),
            new GemModifierFactoryImpl().standard());
    private Round round;

    @BeforeEach
    void setUp() {
        final Deck deck = new DeckFactoryImpl().standardDeck();
        this.round = new RoundImpl(this.players, deck, this.effect);
    }

    @Test
    void testRoundDescription() {
        assertNotNull(this.round.getDescription());
        assertEquals(effect.getDescription(), this.round.getDescription());
    }

    @Test
    void resetPlayersOnConstruct() {
        final int gems = 10;
        this.players.forEach(p -> {
            p.addSackGems(gems);
            p.exit();
        });
        // creates a new RoundImpl. All players should be reset.
        this.setUp();
        this.players.forEach(p -> {
            assertEquals(0, p.getSackGems());
            assertEquals(PlayerChoice.STAY, p.getChoice());
        });
    }

    @Test
    void testTurnIteratorConsumeTurns() {
        final RoundState state = this.round.getState();
        final RoundPlayersManager pm = state.getRoundPlayersManager();
        final Deck deck = state.getDeck();

        while (pm.hasNext()
                && deck.hasNext()
                && !this.effect.isEndConditionMet(state)) {
            assertTrue(this.round.hasNext());
            final Turn turn = this.round.next();
            this.playTurnAndMakePlayerExit(turn);
        }

        assertFalse(this.round.hasNext());
        assertThrows(NoSuchElementException.class, this.round::next);
    }

    @Test
    void testTurnIteratorNoMorePlayers() {
        final RoundState state = this.round.getState();
        final RoundPlayersManager pm = state.getRoundPlayersManager();

        while (pm.hasNext()) {
            assertTrue(this.round.hasNext());
            final PlayerInRound player = pm.next();
            player.exit();
        }

        assertFalse(this.round.hasNext());
    }

    @Test
    void testTurnIteratorNoMoreCards() {
        final RoundState state = this.round.getState();
        final Deck deck = state.getDeck();

        while (deck.hasNext()) {
            assertTrue(this.round.hasNext());
            deck.next();
        }
        assertFalse(this.round.hasNext());
    }

    @Test
    void testTurnIteratorEndConditionMet() {
        final RoundState state = this.round.getState();
        final Deck deck = state.getDeck();
        final Set<TrapCard> traps = new HashSet<>();

        // checks on the standard end condition: must draw 2 equals traps
        while (deck.hasNext()) {
            final Card drawn = deck.next();
            state.addCardToPath(drawn);
            if (drawn instanceof TrapCard) {
                final TrapCard trap = (TrapCard) drawn;
                if (!traps.add(trap)) {
                    break;
                }
            }
            assertTrue(this.round.hasNext());
            assertFalse(this.effect.isEndConditionMet(state));
        }
        assertTrue(this.effect.isEndConditionMet(state));
        assertFalse(this.round.hasNext());
    }

    /**
     * Plays the specified {@code turn} and makes only the player that
     * has drawn the card exit.
     * 
     * @param turn the turn to be played.
     */
    private void playTurnAndMakePlayerExit(final Turn turn) {
        final PlayerInRound turnPlayer = turn.getCurrentPlayer();
        turn.executeDrawPhase();
        turnPlayer.exit();
        turn.endTurn(Set.of(turnPlayer));
    }

    @Test
    void testEndRoundRoundNotEnded() {
        // a round can't be terminated without it being finished.
        while (this.round.hasNext()) {
            assertThrows(IllegalStateException.class, this.round::endRound);
            final Turn turn = this.round.next();
            this.playTurnAndMakePlayerExit(turn);
        }

        // a round can be terminated if it has finished.
        try {
            this.round.endRound();
        } catch (final IllegalStateException e) {
            fail("A round must be able to be termined if it's done: "
                    + e.getMessage());
        }
    }

    @Test
    void testRoundEndMovesGemsToChest() {
        final Map<PlayerInRound, Integer> playerSacks = new HashMap<>();

        // plays a full round
        while (this.round.hasNext()) {
            this.playTurnAndMakePlayerExit(this.round.next());
        }

        final RoundState state = this.round.getState();
        final RoundPlayersManager pm = state.getRoundPlayersManager();

        // saves the gems that each player holds
        pm.getExitedPlayers().forEach(p -> playerSacks.put(p, p.getSackGems()));

        // the round has ended so terminating it must not throw an exeption
        try {
            this.round.endRound();
        } catch (final IllegalStateException e) {
            fail("A round must be able to be termined if it's done: "
                    + e.getMessage());
        }

        // players' sacks should have been moved to their chest
        pm.getExitedPlayers().forEach(p -> {
            assertEquals(0, p.getSackGems());
            assertEquals(playerSacks.get(p), p.getChestGems());
        });
    }
}
