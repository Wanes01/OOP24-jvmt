package jvmt.round;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvmt.model.round.api.roundeffect.gemmodifier.GemModifier;
import jvmt.model.round.api.roundeffect.gemmodifier.GemModifierFactory;
import jvmt.model.card.api.Card;
import jvmt.model.card.api.CardWithGem;
import jvmt.model.card.api.Deck;
import jvmt.model.card.impl.DeckFactoryImpl;
import jvmt.model.player.api.Player;
import jvmt.model.round.api.RoundPlayersManager;
import jvmt.model.round.api.RoundState;
import jvmt.model.round.impl.RoundStateImpl;
import jvmt.model.round.impl.roundeffect.gemmodifier.GemModifierFactoryImpl;
import jvmt.utils.CommonUtils;

/**
 * Tests for {@link GemModifierFactoryImpl} ({@link GemModifierFactory}
 * implementation).
 * 
 * @author Emir Wanes Aouioua
 */
class GemModifierFactoryImplTest {

    private static final Set<Integer> BONUS_VALUES = Set.of(-10, -1, 0, 1, 10);
    private final GemModifierFactory factory = new GemModifierFactoryImpl();
    private RoundState state;

    @BeforeEach
    void setUp() {
        final int numberOfPlayers = 3;
        final Deck deck = new DeckFactoryImpl().standardDeck();
        final List<Player> players = CommonUtils.generatePlayerList(numberOfPlayers);
        this.state = new RoundStateImpl(players, deck);
    }

    @Test
    void testStandardGemModifier() {
        final GemModifier standard = factory.standard();
        this.forEachGemCardCheckExpected(gem -> gem, card -> {
        }, standard);
    }

    @Test
    void testRisckyRewardGemModifier() {
        for (final int bonusPerTrap : BONUS_VALUES) {
            this.setUp();
            final GemModifier riskyReward = factory.riskyReward(bonusPerTrap);

            this.forEachGemCardCheckExpected(
                    gems -> gems + (bonusPerTrap * this.state.getDrawnTraps().size()),
                    this.state::addCardToPath,
                    riskyReward);
        }
    }

    @Test
    void testGemMultiplierModifier() {
        for (final int base : BONUS_VALUES) {
            final double multiplier = base + Math.random();
            final GemModifier gemMultiplier = this.factory.gemMultiplier(multiplier);

            this.forEachGemCardCheckExpected(
                    gems -> (int) (gems * multiplier),
                    card -> {
                    },
                    gemMultiplier);
        }
    }

    @Test
    void testLeftRewardGemModifier() {
        for (final int bonus : BONUS_VALUES) {
            this.setUp();
            final RoundPlayersManager pm = this.state.getRoundPlayersManager();
            final GemModifier leftReward = this.factory.leftReward(bonus);

            // makes all players leave the round
            while (pm.hasNext()) {
                final Player player = pm.next();
                player.exit();
            }

            this.forEachGemCardCheckExpected(
                    gems -> gems + (pm.getExitedPlayers().size() * bonus),
                    card -> {
                    }, leftReward);
        }
    }

    /**
     * General method for testing gem modifiers. Draws all cards from the deck. If a
     * drawn card has an associated gem value, applys the {@code expected} function
     * to compute the expected gem value returned by the modifier. Possible
     * operations to be performed on cards without gems can be specified in the
     * consumer {@code action}.
     *
     * @param expected a function that maps an integer to an integer
     *                 ({@link UnaryOperator}), namely the
     *                 function to compute the expected gems returned by the
     *                 {@code modifier}
     * @param action   a {@link Consumer} to specify an action to do if the drawn
     *                 card doesn't have gems.
     * @param modifier the modier to be applied to gem cards.
     */
    private void forEachGemCardCheckExpected(
            final UnaryOperator<Integer> expected,
            final Consumer<Card> action,
            final GemModifier modifier) {
        final Deck deck = this.state.getDeck();
        while (deck.hasNext()) {
            final Card card = deck.next();
            if (card instanceof CardWithGem) {
                final CardWithGem gemCard = (CardWithGem) card;
                final int expectedGems = expected.apply(gemCard.getGemValue());
                final int actualGems = modifier.getGemModifier().apply(state, gemCard.getGemValue());
                assertEquals(expectedGems, actualGems);
            } else {
                action.accept(card);
            }
        }
    }
}
