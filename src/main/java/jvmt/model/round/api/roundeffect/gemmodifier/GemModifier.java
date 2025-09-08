package jvmt.model.round.api.roundeffect.gemmodifier;

import jvmt.model.common.api.Describable;
import jvmt.model.round.api.RoundState;

/**
 * Represents a modifier applied to gems based on the state of the round.
 * <p>
 * This abstraction allows different implementations that modify gems,
 * either through fixed multipliers, conditional bonuses or dynamic rules
 * based on the round state.
 * </p>
 * <p>
 * This modifer extends {@link Describable} because each modifier
 * should be followed by a human-readable description explaining
 * how the gems are modified.
 * </p>
 * 
 * @see RoundState
 * 
 * @author Emir Wanes Aouioua
 */
public interface GemModifier extends Describable {

    /**
     * Returns the modifies amount of gems based on the current round state.
     * 
     * @param state the current {@link RoundState}.
     * @param gems  the base amount of gems to modify.
     * @return the modified amount of gems after applying the modifier's logic.
     */
    int applyGemModifier(RoundState state, int gems);
}
