package api.round.roundeffect.gemmodifier;

// import for javadoc
import api.round.roundeffect.RoundEffect;

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
     * Given a {@link api.round.RoundState} and 5 base gems, the modifier returns 5
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
     * Given 5 base gems and a {@link api.round.RoundState} in which 3 trap cards
     * have been drawn, and assuming a fixed bonus of 3 gems per trap, the modifier
     * returns: 5 + (3 * 3) = 14 gems.
     * </p>
     *
     * @return a {@link GemModifier} that rewards more gems for each trap card drawn
     */
    GemModifier riskyReward();

    /**
     * Returns a gem modifier that multiplies the base gems by a randomly chosen
     * fixed factor.
     * 
     * <p>
     * Example:
     * Given 5 base gems and a {@link api.round.RoundState}, if a random multiplier
     * of x1.5 is chosen, the modifier returns: 5 * 1.5 = 7 gems.
     * </p>
     *
     * @return a {@link GemModifier} that applies a random multiplier to the base
     *         gems
     */
    GemModifier chaosModifier();
}
