package jvmt.model.round.api.roundeffect;

import jvmt.model.round.api.roundeffect.endcondition.EndCondition;
import jvmt.model.round.api.roundeffect.gemmodifier.GemModifier;
import jvmt.model.common.api.Describable;
import jvmt.model.round.api.RoundState;

/**
 * It models the effect applied to the current round. The effect of a round
 * models the following concepts:
 * <ul>
 * <li>
 * The condition that determines whether a round should end, based on the
 * current state of the round.
 * </li>
 * <li>
 * The amount of gems that are given to players (such as when treasure cards are
 * drawn), based on a base number of gems and the current state of the round.
 * </li>
 * </ul>
 * 
 * <p>
 * Note: Although round effects can be implemented directly from this interface,
 * it is recommended to use the appropriate factories for
 * {@link EndCondition}
 * and {@link GemModifier} that abstractly and
 * separately model these concepts.
 * </p>
 * 
 * @see RoundState
 * @see GemModifier
 * @see EndCondition
 * 
 * @author Emir Wanes Aouioua
 */
public interface RoundEffect extends Describable {

    /**
     * Returns if the round's end condition is met, based on the status of the
     * round.
     * 
     * @param state the state of the round, used to determine if the round's
     *              end condition is met.
     * @return true if the round's {@code EndCondition} is satisfied, false
     *         otherwise.
     */
    boolean isEndConditionMet(RoundState state);

    /**
     * Returns a quantity of gems after applying a modifier based on a base number
     * of gems and the status of the round.
     * 
     * @param state the state of the round, used to determine the new gem amount.
     * @param gems  the base gems to be modified.
     * @return the amount of gems given by the gems modifier.
     */
    int applyGemModifier(RoundState state, int gems);
}
