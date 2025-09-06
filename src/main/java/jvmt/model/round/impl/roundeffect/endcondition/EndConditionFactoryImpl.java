package jvmt.model.round.impl.roundeffect.endcondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import jvmt.model.round.api.RoundState;
import jvmt.model.round.api.roundeffect.endcondition.EndCondition;
import jvmt.model.round.api.roundeffect.endcondition.EndConditionFactory;
import jvmt.model.card.impl.TrapCard;

/**
 * Concrete implementation of {@link EndConditionFactory}.
 * 
 * @see EndCondition
 * @see EndConditionFactory
 * 
 * @author Emir Wanes Aouioua
 */
public class EndConditionFactoryImpl implements EndConditionFactory {

    /**
     * Default constructor for EndConditionFactoryImpl.
     */
    public EndConditionFactoryImpl() {
        // This constructor is intentionally empty.
    }

    /**
     * Returns an {@link EndCondition} that concatenates the specified predicate
     * and description with the default round end conditions:
     * <ol>
     * <li>
     * End of the round when all players have exited.
     * </li>
     * <li>
     * End of the round when all cards are finished.
     * </li>
     * </ol>
     * 
     * @param extraCondition the extra end condition, modeled as a
     *                       {@code Predicate<RoundState>}, to decide whether a
     *                       round has ended.
     * @param description    the textual description of how the extra condition is
     *                       met.
     * @return nn {@code EndCondition} that determines that the round is over if
     *         there are no more cards to draw, if there are no more active players,
     *         or if the extra end condition is met.
     */
    private EndCondition genericEndCondition(
            final Predicate<RoundState> extraCondition,
            final String description) {
        return new EndConditionImpl(
                state -> !state.getRoundPlayersManager().hasNext()
                        || !state.getDeck().hasNext()
                        || extraCondition.test(state),
                "The round ends when the deck is over, if all players leave, or if " + description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EndCondition firstTrapEnds() {
        return genericEndCondition(
                state -> !state.getDrawnTraps().isEmpty(),
                "a trap card is drawn");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EndCondition standard() {
        return genericEndCondition(
                state -> computeTrapsOccurrences(state.getDrawnTraps())
                        .values()
                        .stream()
                        .anyMatch(occ -> occ >= 2),
                "two identical trap cards are drawn");
    }

    /**
     * Calculates the number of occurrences of trap cards in a list using a map that
     * associates each trap with the number of times it was drawn.
     * 
     * @param traps the list of drawn traps.
     * @return a map that associates each trap card in the list with the number of
     *         times it appears.
     */
    private Map<TrapCard, Integer> computeTrapsOccurrences(final List<TrapCard> traps) {
        final Map<TrapCard, Integer> occurrences = new HashMap<>();
        for (final TrapCard trap : traps) {
            occurrences.put(trap, occurrences.getOrDefault(trap, 0) + 1);
        }
        return occurrences;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EndCondition threeRelicsDrawn() {
        return genericEndCondition(
                state -> state.getDrawnRelics().size() >= 3,
                "three relics are drawn");
    }
}
