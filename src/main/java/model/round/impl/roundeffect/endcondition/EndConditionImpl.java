package model.round.impl.roundeffect.endcondition;

import java.util.function.Predicate;

import model.common.api.Describable;
import model.round.api.RoundState;
import model.round.api.roundeffect.endcondition.EndCondition;
import utils.CommonUtils;

/**
 * Simple implementation of {@link EndCondition}.
 * 
 * <p>
 * This class is implemented as a {@code record} because it represents a
 * simple data holder.
 * </p>
 * 
 * @param condition   the predicate defining when the round should end.
 * @param description a human readable explanation of the effect end condition.
 * 
 * @see RoundState
 * @see Describable
 * 
 * @author Emir Wanes Aouioua
 */
public record EndConditionImpl(
        Predicate<RoundState> condition,
        String description) implements EndCondition {

    private static final String BASE_END_CONDITION = "The round ends when the deck is over, if all players leave, or if ";

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
        return BASE_END_CONDITION + description();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Predicate<RoundState> getEndCondition() {
        return condition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.getDescription();
    }
}
