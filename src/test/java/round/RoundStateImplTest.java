package round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.api.others.Deck;
import model.api.others.PlayerInRound;
import model.api.others.RelicCard;
import model.api.others.TrapCard;
import model.api.others.TreasureCard;
import model.api.others.Card;
import model.api.round.RoundState;

import model.impl.others.DeckImpl;
import model.impl.others.PlayerInRoundImpl;
import model.impl.round.RoundStateImpl;

/**
 * Tests for {@link RoundStateImpl} ({@link RoundState} implementation).
 * 
 * @author Emir Wanes Aouioua
 */
class RoundStateImplTest {

    private RoundState state;

    @BeforeEach
    void setUp() {
        final Deck deck = new DeckImpl();
        final List<PlayerInRound> players = new ArrayList<>();

        List.of("Emir", "Andrea", "Filippo", "Rebecca", "Luca", "Flavio")
                .forEach(p -> players.add(new PlayerInRoundImpl(p)));
        this.state = new RoundStateImpl(players, deck);
    }

    /**
     * This method simulates drawing all cards from the deck one by one. For each
     * card, if it satisfies the given {@code cardChecker}, it is added to an
     * expected list.
     * The card is added to the round state's path using {@code addCardToPath}.
     * The current expected list of matching cards is then compared with the actual
     * list provided by the {@code actual} supplier.
     * 
     * <p>
     * This method asserts that the list maintained by the round state
     * (via {@code actual}) matches the expected list of drawn cards filtered by
     * {code cardChecker}.
     * </p>
     * 
     * @param <T>         the type of card to check and compare (must extend / be
     *                    {@link Card}).
     * @param cardChecker a predicate that determines which cards to be included in
     *                    the expected list.
     * @param actual      a supplier that provides the current list of cards from
     *                    the round state.
     */
    private <T extends Card> void testDrawCardsBySupplier(
            final Predicate<Card> cardChecker,
            final Supplier<List<? extends Card>> actual) {

        final List<Card> drawns = new ArrayList<>();
        final Deck deck = this.state.getDeck();

        while (deck.hasNext()) {
            final Card card = deck.drawCard();
            if (cardChecker.test(card)) {
                drawns.add(card);
            }
            this.state.addCardToPath(card);

            assertEquals(drawns, actual.get());
        }
    }

    @Test
    void testCardPath() {
        this.testDrawCardsBySupplier(
                card -> true,
                this.state::getDrawCards);
    }

    @Test
    void testDrawnRelics() {
        this.testDrawCardsBySupplier(
                card -> card instanceof RelicCard,
                this.state::getDrawnRelics);
    }

    @Test
    void testDrawnTreasures() {
        this.testDrawCardsBySupplier(
                card -> card instanceof TreasureCard,
                this.state::getDrawnTreasures);
    }

    @Test
    void testDrawnTraps() {
        this.testDrawCardsBySupplier(
                card -> card instanceof TrapCard,
                this.state::getDrawnTraps);
    }

    @Test
    void testSetPathGems() {
        final int delta = 100;
        assertEquals(0, this.state.getPathGems());

        for (int i = 1; i <= delta; i++) {
            this.state.setPathGems(this.state.getPathGems() + delta);
            assertEquals(delta * i, state.getPathGems());
        }

        assertThrows(IllegalArgumentException.class, () -> this.state.setPathGems(-delta));
    }

}
