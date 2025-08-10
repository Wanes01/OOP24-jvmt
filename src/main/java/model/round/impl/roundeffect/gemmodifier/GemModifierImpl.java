package model.round.impl.roundeffect.gemmodifier;

import java.util.function.BiFunction;

import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.round.api.RoundState;

/**
 * Simple implementation of {@link GemModifier}.
 * 
 * <p>
 * This class is implemented as a {@code record} because it represents a
 * simple data holder.
 * </p>
 * 
 * @param modifier    the function that alters the amount of gems based on the
 *                    current game state
 * @param description a human readable explanation of the modifier
 * 
 * @see RoundState
 * @see model.common.api.Describable
 * 
 * @author Emir Wanes Aouioua
 */
public record GemModifierImpl(
        BiFunction<RoundState, Integer, Integer> modifier,
        String description) implements GemModifier {

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
