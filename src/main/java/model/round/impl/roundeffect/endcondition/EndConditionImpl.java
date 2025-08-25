package model.round.impl.roundeffect.endcondition;

import java.util.function.Predicate;

import model.round.api.roundeffect.endcondition.EndCondition;
import model.round.api.RoundState;

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
 * @see model.common.api.Describable
 * 
 * @author Emir Wanes Aouioua
 */
public record EndConditionImpl(
        Predicate<RoundState> condition,
        String description) implements EndCondition {

    private static final String BASE_END_CONDITION = "The round ends when the deck is over, if all players leave, or if ";

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
}
