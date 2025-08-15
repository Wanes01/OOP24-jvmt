package model.round.impl.roundeffect.endcondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.roundeffect.endcondition.EndConditionFactory;
import model.card.impl.TrapCard;

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
                state -> !state.getDrawnTraps().isEmpty()
                        || !state.getRoundPlayersManager().hasNext(),
                "Il round termina alla prima carta trappola pescata o se tutti i giocatori escono.");
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
                        .anyMatch(occ -> occ >= 2)
                        || !state.getRoundPlayersManager().hasNext(),
                "Il round termina se vengono pescate due carte trappola uguali o se tutti i giocatori escono.");
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
