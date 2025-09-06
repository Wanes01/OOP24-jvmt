package jvmt.round;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvmt.model.round.api.roundeffect.endcondition.EndCondition;
import jvmt.model.round.api.roundeffect.endcondition.EndConditionFactory;
import jvmt.model.card.api.Card;
import jvmt.model.card.api.Deck;
import jvmt.model.player.impl.PlayerInRound;
import jvmt.model.card.impl.TrapCard;
import jvmt.model.card.impl.DeckFactoryImpl;
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
        final List<PlayerInRound> players = CommonUtils.generatePlayerInRoundList(numberOfPlayers);
        this.state = new RoundStateImpl(players, deck);
    }

    @Test
    void testStandardEndCondition() {
        final EndCondition standard = this.factory.standard();
        final Deck deck = this.state.getDeck();
        final Map<TrapCard, Integer> occurrences = new HashMap<>();

        while (deck.hasNext()) {
            final Card card = deck.next();
            this.state.addCardToPath(card);
            if (card instanceof TrapCard) {
                final TrapCard trap = (TrapCard) card;
                occurrences.put(trap, occurrences.getOrDefault(trap, 0) + 1);
                if (occurrences.get(trap) > 1) {
                    break;
                }
                assertFalse(standard.getEndCondition().test(this.state));
            }
        }

        assertTrue(standard.getEndCondition().test(this.state));
    }

    @Test
    void testFirstTrapEnds() {
        final EndCondition firstTrap = this.factory.firstTrapEnds();
        final Deck deck = this.state.getDeck();

        while (deck.hasNext()) {
            final Card card = deck.next();
            this.state.addCardToPath(card);
            if (card instanceof TrapCard) {
                break;
            }
            assertFalse(firstTrap.getEndCondition().test(this.state));
        }

        assertTrue(firstTrap.getEndCondition().test(this.state));
    }
}
