package jvmt.model.round.api.roundeffect.gemmodifier;

import jvmt.model.round.api.roundeffect.RoundEffect;
import jvmt.model.round.api.Round;
import jvmt.model.round.api.RoundState;

/**
 * A factory interface for creating {@link GemModifier} instances used during a
 * round.
 * 
 * @see Round
 * @see RoundEffect
 * 
 * @author Emir Wanes Aouioua
 */
public interface GemModifierFactory {

    /**
     * Returns a gem modifier that acts as the identity function:
     * the number of gems returned is exactly the same as the input.
     * 
     * <p>
     * Example:
     * Given a {@link model.round.api.RoundState} and 5 base gems, the modifier
     * returns 5
     * gems.
     * </p>
     *
     * @return the standard {@link GemModifier} with no effect
     */
    GemModifier standard();

    /**
     * Returns a gem modifier that adds bonus gems based on the number of trap cards
     * drawn so far in the round.
     * 
     * <p>
     * Example:
     * Given 5 base gems and a {@link RoundState} in which 3 trap cards
     * have been drawn, and assuming a {@code trapBonus} of 3 gems per trap, the
     * modifier
     * returns: 5 + (3 * 3) = 14 gems.
     * </p>
     *
     * @param trapBonus the bonus gems that each drawn trap card adds.
     * @return a {@link GemModifier} that rewards more gems for each trap card
     *         drawn.
     */
    GemModifier riskyReward(int trapBonus);

    /**
     * Returns a gem modifier that multiplies the base gems by a specified
     * fixed factor.
     * 
     * <p>
     * Example:
     * Given 5 base gems and a {@link RoundState}, if the
     * {@code multiplier} is set to x1.5 then the modifier returns: 5 * 1.5 = 7
     * gems.
     * </p>
     *
     * @param multiplier the multiplier applied to the base gems.
     * @return a {@link GemModifier} that applies a multiplier to the base
     *         gems based on the number of drawn traps
     */
    GemModifier gemMultiplier(double multiplier);

    /**
     * Returns a gem modifier that adds a bonus to the base gems based on the
     * number players that have left so far.
     * 
     * <p>
     * Example:
     * Given 5 base gems and a {@link RoundState}, if 2 players
     * have left and a {@code leftBonus} of 3 gems is set, then the modifier returns
     * 5 + (2 * 3) = 11 gems.
     * </p>
     * 
     * @param leftBonus the bonus gems that each left player adds.
     * @return a {@link GemModifier} that applies a bonus to the base
     *         gems based on the number of exited players.
     */
    GemModifier leftReward(int leftBonus);
}
