package jvmt.model.round.impl.roundeffect.endcondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * {@inheritDoc}
     */
    @Override
    public EndCondition firstTrapEnds() {
        return new EndConditionImpl(
                state -> !state.getDrawnTraps().isEmpty(),
                "a trap card is drawn");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EndCondition standard() {
        return new EndConditionImpl(
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
}
