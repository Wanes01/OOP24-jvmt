package jvmt.round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvmt.model.round.api.roundeffect.endcondition.EndCondition;
import jvmt.model.round.api.roundeffect.endcondition.EndConditionFactory;
import jvmt.model.card.api.Card;
import jvmt.model.card.api.Deck;
import jvmt.model.player.api.Player;
import jvmt.model.card.impl.TrapCard;
import jvmt.model.card.impl.DeckFactoryImpl;
import jvmt.model.card.impl.RelicCard;
import jvmt.model.round.api.RoundState;
import jvmt.model.round.impl.RoundStateImpl;
import jvmt.model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import jvmt.utils.CommonUtils;

/**
 * Tests for {@link EndConditionFactoryImpl} ({@link EndConditionFactory}
 * implementation).
 * 
 * @author Emir Wanes Aouioua
 */
class EndConditionFactoryImplTest {

    private final EndConditionFactory factory = new EndConditionFactoryImpl();
    private RoundState state;

    @BeforeEach
    void setUp() {
        final int numberOfPlayers = 3;
        final Deck deck = new DeckFactoryImpl().standardDeck();
        final List<Player> players = CommonUtils.generatePlayerList(numberOfPlayers);
        this.state = new RoundStateImpl(players, deck);
    }

    @Test
    void testStandardEndCondition() {
        final EndCondition standard = this.factory.standard();
        final Map<TrapCard, Integer> occurrences = new HashMap<>();

        this.drawTillComplies(standard, card -> {
            if (card instanceof TrapCard) {
                final TrapCard trap = (TrapCard) card;
                occurrences.put(trap, occurrences.getOrDefault(trap, 0) + 1);
                return occurrences.get(trap) > 1;
            }
            return false;
        });
    }

    @Test
    void testFirstTrapEnds() {
        final EndCondition firstTrap = this.factory.firstTrapEnds();
        this.drawTillComplies(firstTrap, card -> card instanceof TrapCard);
    }

    @Test
    void testThreeRelicsEnds() {
        final EndCondition threeRelics = this.factory.threeRelicsDrawn();
        final int target = 3;
        /*
         * using AtomicInteger as a counter to access it in
         * the anonymous class.
         */
        final AtomicInteger relics = new AtomicInteger();
        this.drawTillComplies(threeRelics, card -> {
            if (card instanceof RelicCard) {
                relics.incrementAndGet();
                return relics.get() >= target;
            }
            return false;
        });

        assertEquals(target, relics.get());
        assertEquals(target, this.state.getDrawnRelics().size());
    }

    /**
     * Draws cards from this round's deck until {@code toComply}
     * condition is satisfied. Each card gets added to the round state
     * and it is asserted that {@code endCondition} is not yet satisfied
     * before {@code toComply} is met.
     * Once {@code toComply} returns true, it is asserted that {@code endCondition}
     * is satisfied.
     * 
     * @param endCondition the end condition to test.
     * @param toComply     a predicate that defines when to stop drawing from the
     *                     deck.
     */
    private void drawTillComplies(
            final EndCondition endCondition,
            final Predicate<Card> toComply) {
        final Deck deck = this.state.getDeck();
        while (deck.hasNext()) {
            final Card card = deck.next();
            this.state.addCardToPath(card);
            if (toComply.test(card)) {
                break;
            }
            assertFalse(endCondition.getEndCondition().test(this.state));
        }
        assertTrue(endCondition.getEndCondition().test(this.state));
    }
}
