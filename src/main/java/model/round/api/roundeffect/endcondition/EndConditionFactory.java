package model.round.api.roundeffect.endcondition;

import model.round.api.Round;
import model.round.api.roundeffect.RoundEffect;

/**
 * A factory interface for creating {@link EndCondition} instances used during a
 * round.
 * 
 * @see Round
 * @see RoundEffect
 * 
 * @author Emir Wanes Aouioua
 */
public interface EndConditionFactory {

    /**
     * The standard end condition for a round: the round ends if two identical trap
     * cards are drawn.
     * 
     * @return a {@link EndCondition} following the standard round end condition.
     */
    EndCondition standard();

    /**
     * Returns an end condition that ends the round if a trap card is drawn.
     * 
     * @return a {@link EndCondition} where the first drawn traps ends the round.
     */
    EndCondition firstTrapEnds();
}
