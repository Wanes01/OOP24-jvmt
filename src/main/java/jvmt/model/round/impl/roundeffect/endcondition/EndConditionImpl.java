package jvmt.model.round.impl.roundeffect.endcondition;

import java.util.function.Predicate;

import jvmt.model.common.api.Describable;
import jvmt.model.round.api.RoundState;
import jvmt.model.round.api.roundeffect.endcondition.EndCondition;
import jvmt.utils.CommonUtils;

/**
 * Simple implementation of {@link EndCondition}.
 * 
 * <p>
 * This class is implemented as a {@code record} because it represents a
 * simple data holder.
 * </p>
 * 
 * @param condition   the predicate defining when the end condition is
 *                    met.
 * @param description a human readable explanation of this {@code condition}.
 * 
 * @see RoundState
 * @see Describable
 * 
 * @author Emir Wanes Aouioua
 */
public record EndConditionImpl(
        Predicate<RoundState> condition,
        String description) implements EndCondition {

    /**
     * Constucts the {@code EndConditionImpl}.
     * 
     * @param condition   the predicate defining when the round should end.
     * @param description a human readable explanation of the effect end condition.
     */
    public EndConditionImpl {
        CommonUtils.requireNonNulls(condition, description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return this.description();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Predicate<RoundState> getEndCondition() {
        return this.condition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.getDescription();
    }
}
