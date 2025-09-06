package jvmt.model.round.api.roundeffect.endcondition;

import java.util.function.Predicate;

import jvmt.model.round.api.Round;
import jvmt.model.common.api.Describable;
import jvmt.model.round.api.RoundState;

/**
 * Models the variable end condition of the round. A round can continue as long
 * as there are still players, cards to draw, and if other rules allows it.
 * The end condition to be verified is modeled
 * through a {@link Predicate} that checks the status of the round through the
 * {@link RoundState} object associated with it.
 * 
 * @see Predicate
 * @see RoundState
 * @see Round
 * @author Emir Wanes Aouioua.
 */
public interface EndCondition extends Describable {
    /**
     * Returns the predicate that determines whether this
     * {@code EndCondition} is met based on the current {@link RoundState}.
     * 
     * @return a predicate that evaluates to {@code true} when the condition
     *         to end the round is satisfied.
     */
    Predicate<RoundState> getEndCondition();
}
