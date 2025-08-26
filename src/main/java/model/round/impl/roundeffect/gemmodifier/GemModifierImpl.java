package model.round.impl.roundeffect.gemmodifier;

import java.util.function.BiFunction;

import model.round.api.roundeffect.gemmodifier.GemModifier;
import utils.CommonUtils;
import model.round.api.RoundState;
import model.common.api.Describable;

/**
 * Simple implementation of {@link GemModifier}.
 * 
 * <p>
 * This class is implemented as a {@code record} because it represents a
 * simple data holder.
 * </p>
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
     */
    @Override
    public String getDescription() {
        return description();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BiFunction<RoundState, Integer, Integer> getGemModifier() {
        return modifier();
    }

}
