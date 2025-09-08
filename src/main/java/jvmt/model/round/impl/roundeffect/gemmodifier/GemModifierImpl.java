package jvmt.model.round.impl.roundeffect.gemmodifier;

import java.util.Objects;
import java.util.function.BiFunction;

import jvmt.model.common.api.Describable;
import jvmt.model.round.api.RoundState;
import jvmt.model.round.api.roundeffect.gemmodifier.GemModifier;
import jvmt.utils.CommonUtils;

/**
 * Simple implementation of {@link GemModifier}.
 * 
 * @param modifier    the function that alters the amount of gems based on the
 *                    current game state.
 * @param description a human readable explanation of the modifier.
 * 
 * @see RoundState
 * @see Describable
 * 
 * @author Emir Wanes Aouioua
 */
public record GemModifierImpl(
        BiFunction<RoundState, Integer, Integer> modifier,
        String description) implements GemModifier {

    /**
     * Constructs the {@code GemModifierImpl}.
     * 
     * @param modifier    the function that alters the amount of gems based on the
     *                    current game state.
     * @param description a human readable explanation of the modifier.
     */
    public GemModifierImpl {
        CommonUtils.requireNonNulls(modifier, description);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if {@code state} is null.
     */
    @Override
    public int applyGemModifier(final RoundState state, final int gems) {
        Objects.requireNonNull(state);
        return this.modifier().apply(state, gems);
    }

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
    public String toString() {
        return this.getDescription();
    }
}
