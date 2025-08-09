package round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.api.others.PlayerInRound;
import model.api.others.RelicCard;
import model.api.others.TreasureCard;
import model.api.round.RoundPlayersManager;
import model.api.round.RoundState;
import model.api.round.roundeffect.RoundEffect;
import model.api.round.turn.Turn;
import model.api.others.Card;
import model.api.others.Deck;

import model.impl.others.DeckImpl;
import model.impl.others.PlayerInRoundImpl;
import model.impl.round.RoundStateImpl;
import model.impl.round.roundeffect.RoundEffectImpl;
import model.impl.round.roundeffect.endcondition.EndConditionFactoryImpl;
import model.impl.round.roundeffect.gemmodifier.GemModifierFactoryImpl;
import model.impl.round.turn.TurnImpl;
import utils.CommonUtils;

/**
 * Tests for {@link TurnImpl} ({@link Turn}
 * implementation).
 * <p>
 * This class tests a single turn.
 * See {@link RoundImplTest} for the tests on a round
 * (tests on multiple turns).
 * </p>
 * 
 * @author Emir Wanes Aouioua
 */
class TurnImpTest {

    private static final int PLAYER_COUNT = 5;
    private final PlayerInRound turnPlayer = new PlayerInRoundImpl("TURN_PLAYER");
    private RoundEffect effect;
    private RoundState state;
    private Turn turn;

    @BeforeEach
    void setUp() {
        final List<PlayerInRound> players = new ArrayList<>(List.of(this.turnPlayer));
        players.addAll(CommonUtils.generatePlayerInRoundList(PLAYER_COUNT - 1));

        // the standard deck ensures the precence of trasure and relic cards.
        final Deck deck = new DeckImpl();
        this.state = new RoundStateImpl(players, deck);
        this.effect = new RoundEffectImpl(
                new EndConditionFactoryImpl().standard(),
                new GemModifierFactoryImpl().standard());

        this.turn = new TurnImpl(this.turnPlayer, this.state, this.effect);
    }

    @Test
    void testGetDrawnCard() {
        // tries to get a card that has not been drawn yet.
        final int tries = 100;
        for (int t = 0; t < tries; t++) {
            assertEquals(turn.getDrawnCard(), Optional.empty());
        }
        turn.executeDrawPhase();
        final Optional<Card> drawn = turn.getDrawnCard();
        assertTrue(drawn.isPresent());
    }

    @Test
    void testAlreadyDrawnCard() {
        try {
            turn.executeDrawPhase();
        } catch (final IllegalStateException e) {
            fail("A card has not been drawn yet. " + e.getMessage());
        }

        assertThrows(IllegalStateException.class, turn::executeDrawPhase);
    }

    @Test
    void testCardAddedToPathAfterDraw() {
        turn.executeDrawPhase();
        final Card drawnCard = turn.getDrawnCard().get();
        assertTrue(this.state.getDrawCards().contains(drawnCard));
    }

    @Test
    void testTreasureCardDivision() {
        // makes half the players leave the round
        final RoundPlayersManager pm = this.state.getRoundPlayersManager();
        this.makePlayersExit(PLAYER_COUNT / 2);

        // makes so that the next card is guaranted to be a TreasureCard
        this.skipCardsUntilPeekCondition(c -> c instanceof TreasureCard);
        turn.executeDrawPhase();

        final TreasureCard treasure = (TreasureCard) turn.getDrawnCard().get();
        final int actives = pm.getActivePlayers().size();
        /*
         * the expected gems to be put in the active players' sacks and on the round
         * path
         */
        final int expectedReward = this.effect.applyGemModifier(
                state, treasure.getGems() / actives);
        final int expectedOnPath = treasure.getGems() % actives;

        pm.getActivePlayers().stream()
                .forEach(a -> assertEquals(expectedReward, a.getSackGems()));
        assertEquals(expectedOnPath, this.state.getPathGems());
    }

    /**
     * Skips all cards so that the next one that will be drawn
     * respects the given {@code condition}.
     * 
     * @param condition a predicate that specifies the condition the next card that
     *                  will be drawn will comply to after calling this method.
     */
    private void skipCardsUntilPeekCondition(final Predicate<Card> condition) {
        final Deck deck = state.getDeck();
        while (deck.hasNext()) {
            if (condition.test(deck.peekCard())) {
                break;
            }
            deck.drawCard();
        }
    }

    /**
     * Makes {@code count} players exit the round.
     * 
     * @param count the number of players that have to exit the round. If
     *              {@code count} is greater then the total number of players, all
     *              players will exit.
     */
    private void makePlayersExit(final int count) {
        final RoundPlayersManager pm = state.getRoundPlayersManager();
        for (int i = 0; i < count && pm.hasNext(); i++) {
            pm.next().leave();
        }
    }

    @Test
    void testEndTurnNoCardDrawn() {
        assertThrows(
                IllegalStateException.class,
                () -> this.turn.endTurn(Set.of()));

        turn.executeDrawPhase();

        try {
            this.turn.endTurn(Set.of());
        } catch (final IllegalStateException e) {
            fail("The card has been drawn and the turn can end."
                    + e.getMessage());
        }
    }

    @Test
    void testEndTurnActivePlayerNotExpected() {
        // makes all players exit but one
        final RoundPlayersManager pm = this.state.getRoundPlayersManager();
        this.makePlayersExit(PLAYER_COUNT - 1);

        assertEquals(1, pm.getActivePlayers().size());
        final Set<PlayerInRound> players = new HashSet<>(pm.getExitedPlayers());
        players.add(pm.getActivePlayers().getFirst());

        // one active player is in the players set. This behavior is not allowed.
        this.turn.executeDrawPhase();
        assertThrows(IllegalArgumentException.class, () -> turn.endTurn(players));

        // all players in the players set have left the round.
        try {
            turn.endTurn(new HashSet<>(pm.getExitedPlayers()));
        } catch (final IllegalArgumentException e) {
            fail("All players given to the endTurn function have left the round. "
                    + e.getMessage());
        }
    }

    @Test
    void testEndTurnRelicsAssignment() {
        // draws one relic card
        this.skipCardsUntilPeekCondition(c -> c instanceof RelicCard);
        this.turn.executeDrawPhase();
        assertTrue(turn.getDrawnCard().get() instanceof RelicCard);

        final RelicCard relic = (RelicCard) turn.getDrawnCard().get();
        final RoundPlayersManager pm = this.state.getRoundPlayersManager();

        this.makePlayersExit(PLAYER_COUNT);
        this.turn.endTurn(new HashSet<>(pm.getExitedPlayers()));

        /*
         * the relics must not be assigned because more than one player have left the
         * round this turn
         */
        assertFalse(relic.isAlreadyTaken());
        for (final PlayerInRound exited : pm.getExitedPlayers()) {
            assertEquals(0, exited.getSackGems());
        }

        // only one player have left the round. The relics can be assigned.
        this.turn.endTurn(Set.of(this.turnPlayer));
        assertTrue(relic.isAlreadyTaken());
        assertEquals(relic.getGems(), this.turnPlayer.getSackGems());
    }

    @Test
    void testEndRoundAlreadyTakenRelics() {
        this.skipCardsUntilPeekCondition(c -> c instanceof RelicCard);
        this.turn.executeDrawPhase();

        final RelicCard relic = (RelicCard) turn.getDrawnCard().get();
        assertFalse(relic.isAlreadyTaken());

        // simulates already taken relic
        relic.setAsTaken();
        assertTrue(relic.isAlreadyTaken());

        this.turnPlayer.leave();
        this.turn.endTurn(Set.of(this.turnPlayer));

        assertEquals(0, this.turnPlayer.getSackGems());
        assertTrue(relic.isAlreadyTaken());
    }

    @Test
    void testEndRoundPathGemsDistribution() {
        final int pathGems = 100;
        this.state.setPathGems(pathGems);

        this.makePlayersExit(PLAYER_COUNT / 2);
        this.turn.executeDrawPhase();

        final List<PlayerInRound> exiting = this.state.getRoundPlayersManager()
                .getExitedPlayers();
        /*
         * the first card drawn may be a trasure card, which could cause the sack's gems
         * to not be 0
         */
        final int basePlayersSack = exiting.getFirst().getSackGems();
        final int expectedSackGems = this.effect.applyGemModifier(
                state, this.state.getPathGems() / exiting.size()) + basePlayersSack;
        final int expectedPathGems = this.state.getPathGems() % exiting.size();

        this.turn.endTurn(new HashSet<>(exiting));

        exiting.forEach(e -> assertEquals(expectedSackGems, e.getSackGems()));
        assertEquals(expectedPathGems, this.state.getPathGems());
    }

    @Test
    void testGetCurrentPlayer() {
        assertEquals(this.turnPlayer, turn.getCurrentPlayer());
    }
}
