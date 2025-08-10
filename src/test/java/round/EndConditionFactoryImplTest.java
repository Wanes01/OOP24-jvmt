package round;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.endcondition.EndConditionFactory;
import model.others.api.Card;
import model.others.api.Deck;
import model.player.impl.PlayerInRound;
import model.others.api.TrapCard;
import model.others.impl.DeckImpl;
import model.round.api.RoundPlayersManager;
import model.round.api.RoundState;
import model.round.impl.RoundStateImpl;
import model.round.impl.roundeffect.endcondition.EndConditionFactoryImpl;
import utils.CommonUtils;

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
        final Deck deck = new DeckImpl();
        final List<PlayerInRound> players = CommonUtils.generatePlayerInRoundList(numberOfPlayers);
        this.state = new RoundStateImpl(players, deck);
    }

    @Test
    void testStandardEndCondition() {
        final EndCondition standard = this.factory.standard();
        final Deck deck = this.state.getDeck();
        final Map<TrapCard, Integer> occurrences = new HashMap<>();

        while (deck.hasNext()) {
            final Card card = deck.drawCard();
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
        this.testAllPlayersExit(standard);
    }

    /**
     * Resets the round state and test if making all players
     * leave triggers the {@code EndCondition}.
     * 
     * @param condition the EndCondition to test in case all players exit.
     */
    private void testAllPlayersExit(final EndCondition condition) {
        this.setUp();
        final RoundPlayersManager pm = this.state.getRoundPlayersManager();

        while (pm.hasNext()) {
            final PlayerInRound player = pm.next();
            player.exit();
            if (pm.hasNext()) {
                assertFalse(condition.getEndCondition().test(this.state));
            }
        }
        assertTrue(condition.getEndCondition().test(this.state));
    }

    @Test
    void testFirstTrapEnds() {
        final EndCondition firstTrap = this.factory.firstTrapEnds();
        final Deck deck = this.state.getDeck();

        while (deck.hasNext()) {
            final Card card = deck.drawCard();
            this.state.addCardToPath(card);
            if (card instanceof TrapCard) {
                break;
            }
            assertFalse(firstTrap.getEndCondition().test(this.state));
        }

        assertTrue(firstTrap.getEndCondition().test(this.state));
        this.testAllPlayersExit(firstTrap);
    }
}
