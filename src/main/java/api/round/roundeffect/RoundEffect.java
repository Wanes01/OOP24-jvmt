package api.round.roundeffect;

import api.common.Describable;
import api.round.RoundState;

// imports for javadoc
import api.round.roundeffect.endcondition.EndCondition;
import api.round.roundeffect.gemmodifier.GemModifier;

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
 * and
 * {@link GemModifier} that abstractly and
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
     * Returns if the round has reached the end, based on the status of the round.
     * 
     * @param state the state of the round, used to determine if the round should
     *              end.
     * @return true if the round has ended, false otherwise.
     */
    boolean isRoundOver(RoundState state);

    /**
     * Returns a quantity of gems after applying a modifier (which can also be the
     * mathematical identity) based on a base number of gems and the status of the
     * round.
     * 
     * @param state the state of the round, used to determine the new gem amount.
     * @param gems  the base gems to be modified.
     * @return the amount of gems given by the gems modifier.
     */
    int applyGemModifier(RoundState state, int gems);
}
