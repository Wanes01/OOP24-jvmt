package jvmt.model.round.impl.roundeffect;

import jvmt.model.round.api.roundeffect.RoundEffect;
import jvmt.model.round.api.roundeffect.endcondition.EndCondition;
import jvmt.model.round.api.roundeffect.gemmodifier.GemModifier;
import jvmt.utils.CommonUtils;

import java.util.Objects;

import jvmt.model.round.api.RoundState;

/**
 * Concrete implementation of {@link RoundEffect}.
 * <p>
 * This implementation uses {@link EndCondition} to define the condition
 * under which a round ends and {@link GemModifier} to apply an alteration to
 * the number of base gems earned by players.
 * </p>
 * 
 * @see RoundEffect
 * @see GemModifier
 * @see EndCondition
 * 
 * @author Emir Wanes Aouioua
 */
public class RoundEffectImpl implements RoundEffect {

    private final EndCondition endCondition;
    private final GemModifier gemModifier;

    /**
     * Constructor for the round effect. This implementation delegates control of
     * the end condition of the round and the calculation of modified gems to
     * the {@code EndCondition} and {@code GemModifier} objects.
     * 
     * @param endCondition the end condition used to determine whether the
     *                     round has come to an end.
     * @param gemModifier  the gem modifier used to compute the number of gems
     *                     earned by players.
     * @throws NullPointerException if {@code endCondition} or {@code gemModifier}
     *                              is null.
     */
    public RoundEffectImpl(
            final EndCondition endCondition,
            final GemModifier gemModifier) {
        CommonUtils.requireNonNulls(endCondition, gemModifier);
        this.endCondition = endCondition;
        this.gemModifier = gemModifier;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Note: this method delegates the decision to the {@code EndCondition} with
     * which this object is created.
     * </p>
     * 
     * @throws NullPointerException if {@code state} is null.
     */
    @Override
    public boolean isEndConditionMet(final RoundState state) {
        Objects.requireNonNull(state);
        return this.endCondition.getEndCondition()
                .test(state);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Note: this method delegates the gems transformation to the
     * {@code GemModifier} with which this object is created.
     * </p>
     * 
     * @throws NullPointerException if {@code state} is null.
     */
    @Override
    public int applyGemModifier(final RoundState state, final int gems) {
        Objects.requireNonNull(state);
        return this.gemModifier.getGemModifier()
                .apply(state, gems);
    }

    /**
     * Returns the text description of the effects applied by this RoundEffect. The
     * resulting description is the union of the {@code EndCondition} and
     * {@code GemModifier} descriptions with which the object was created.
     * 
     * @return the description of the rules custom rules of this round effect.
     */
    @Override
    public String getDescription() {
        return this.endCondition.getDescription() + "\n"
                + this.gemModifier.getDescription();
    }
}
