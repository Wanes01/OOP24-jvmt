package round;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.others.Card;
import api.others.CardWithGems;
import api.others.Deck;
import api.others.PlayerInRound;
import api.round.RoundPlayersManager;
import api.round.RoundState;
import api.round.roundeffect.gemmodifier.GemModifier;
import api.round.roundeffect.gemmodifier.GemModifierFactory;
import impl.others.DeckImpl;
import impl.others.PlayerInRoundImpl;
import impl.round.RoundStateImpl;
import impl.round.roundeffect.gemmodifier.GemModifierFactoryImpl;
import utils.CommonUtils;

class GemModifierFactoryImplTest {

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
        final int bonusPerTrap = CommonUtils.randomIntBetweenValues(3, 10);
        final GemModifier riskyReward = factory.riskyReward(bonusPerTrap);

        this.forEachGemCardCheckExpected(
                gems -> gems + (bonusPerTrap * this.state.getDrawnTraps().size()),
                this.state::addCardToPath,
                riskyReward);
    }

    @Test
    void testGemMultiplierModifier() {
        final double multiplier = CommonUtils.randomDoubleBetweenValues(3, 10);
        final GemModifier gemMultiplier = this.factory.gemMultiplier(multiplier);

        this.forEachGemCardCheckExpected(
                gems -> (int) (gems * multiplier),
                card -> {
                },
                gemMultiplier);
    }

    @Test
    void testLeftRewardGemModifier() {
        final int bonus = CommonUtils.randomIntBetweenValues(3, 10);
        final GemModifier leftReward = this.factory.leftReward(bonus);

        final RoundPlayersManager pm = this.state.getRoundPlayersManager();

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
