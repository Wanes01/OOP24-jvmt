package round;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.api.others.Card;
import model.api.others.CardWithGems;
import model.api.others.Deck;
import model.api.others.PlayerInRound;
import model.api.round.RoundPlayersManager;
import model.api.round.RoundState;
import model.api.round.roundeffect.gemmodifier.GemModifier;
import model.api.round.roundeffect.gemmodifier.GemModifierFactory;
import model.impl.others.DeckImpl;
import model.impl.others.PlayerInRoundImpl;
import model.impl.round.RoundStateImpl;
import model.impl.round.roundeffect.gemmodifier.GemModifierFactoryImpl;

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
        final Deck deck = new DeckImpl();
        final List<PlayerInRound> players = new ArrayList<>();
        List.of("P1", "P2", "P3").stream()
                .map(pn -> new PlayerInRoundImpl(pn))
                .forEach(players::add);
        this.state = new RoundStateImpl(players, deck);
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
            final Card card = deck.drawCard();
            if (card instanceof CardWithGems) {
                final CardWithGems gemCard = (CardWithGems) card;
                final int expectedGems = expected.apply(gemCard.getGems());
                final int actualGems = modifier.getGemModifier().apply(state, gemCard.getGems());
                assertEquals(expectedGems, actualGems);
            } else {
                action.accept(card);
            }
        }
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
                final PlayerInRound player = pm.next();
                player.leave();
            }

            this.forEachGemCardCheckExpected(
                    gems -> gems + (pm.getExitedPlayers().size() * bonus),
                    card -> {
                    }, leftReward);
        }
    }
}
