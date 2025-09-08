package jvmt.model.round.api.roundeffect.endcondition;

import jvmt.model.common.api.Describable;
import jvmt.model.round.api.RoundState;

/**
 * Models the variable end condition of the round. A round can continue as long
 * as there are still players, cards to draw, and if other rules allows it.
 * The end condition checks the status of the round through
 * {@link RoundState} to decide whether the round must end or not.
 * 
 * @see RoundState
 * 
 * @author Emir Wanes Aouioua.
 */
public interface EndCondition extends Describable {
    /**
     * Returns whether this {@code EndCondition} is met based on the current
     * {@link RoundState}.
     * 
     * @param state the {@link RoundState} that is used to determine if the round
     *              must end.
     * @return true if the round must end, false otherwise.
     */
    boolean isEndConditionMet(RoundState state);
}
