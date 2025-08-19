package model.round.api.roundeffect.gemmodifier;

import java.util.function.BiFunction;

import model.common.api.Describable;
import model.round.api.RoundState;

/**
 * Represents modifier applied to gems.
 * The modifier is modeled through a ${BiFunction} that takes the current
 * {@link RoundState} and the base number of gems, and returns the modified
 * amount.
 * 
 * <p>
 * This abstraction allows different implementations that modify gems,
 * either through fixed multipliers, conditional bonuses or dynamic rules
 * based on the round state.
 * </p>
 * 
 * @author Emir Wanes Aouioua
 */
public interface GemModifier extends Describable {

    /**
     * Returns a function that modifies the number of gems obtained,
     * based on the round state.
     * 
     * The {@link BiFunction} takes two arguments:
     * <ul>
     * <li>The current {@link RoundState}</li>
     * <li>The base amount of gems to modify</li>
     * </ul>
     * and returns the modified amount of gems after applying the modifier's logic.
     * 
     * @return a {@code BiFunction} that computed the modified gem amount
     */
    BiFunction<RoundState, Integer, Integer> getGemModifier();
}
