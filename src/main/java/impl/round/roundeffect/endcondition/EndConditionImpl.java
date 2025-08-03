package impl.round.roundeffect.endcondition;

import java.util.function.Predicate;

import api.round.RoundState;
import api.round.roundeffect.endcondition.EndCondition;

/**
 * Simple implementation of {@link EndCondition}.
 * 
 * <p>
 * This class is implemented as a {@code record} because it represents a
 * simple data holder.
 * </p>
 * 
 * @param condition   the predicate defining when the round should end
 * @param description a human readable explanation of the condition
 * 
 * @see RoundState
 * @see api.common.Describable
 * 
 * @author Emir Wanes Aouioua
 */
public record EndConditionImpl(
        Predicate<RoundState> condition,
        String description) implements EndCondition {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Predicate<RoundState> getEndCondition() {
        return condition();
    }
}
