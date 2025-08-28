package model.round.impl.roundeffect.gemmodifier;

import model.round.api.roundeffect.gemmodifier.GemModifier;
import model.round.api.roundeffect.gemmodifier.GemModifierFactory;

/**
 * Concrete implementation of {@link GemModifierFactory}.
 * 
 * @see GemModifier
 * @see GemModifierFactory
 * @author Emir Wanes Aouioua
 */
public class GemModifierFactoryImpl implements GemModifierFactory {

    /**
     * Default constructor for GemModifierFactoryImpl.
     */
    public GemModifierFactoryImpl() {
        // This constructor is intentionally empty.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemModifier standard() {
        return new GemModifierImpl(
                (state, gems) -> gems,
                "no modifiers applied to gems");
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * <strong>Note:</strong> in this implementation {@code bonus} can also be
     * a negative value so that this modifier becomes a penalty.
     * </p>
     */
    @Override
    public GemModifier riskyReward(final int bonus) {
        return new GemModifierImpl(
                (state, gems) -> gems + (state.getDrawnTraps().size() * bonus),
                (bonus >= 0 ? "+" : "") + bonus + " gems for each trap card already drawn");
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * <strong>Note:</strong> in this implementation {@code multiplier} can also be
     * a negative value so that this modifier becomes a penalty.
     * </p>
     */
    @Override
    public GemModifier gemMultiplier(final double multiplier) {
        return new GemModifierImpl(
                (state, gems) -> (int) (gems * multiplier),
                "multiplier applied to gems [x" + multiplier + "]");
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * <strong>Note:</strong> in this implementation {@code leftBonus} can also be
     * a negative value so that this modifier becomes a penalty.
     * </p>
     */
    @Override
    public GemModifier leftReward(final int leftBonus) {
        return new GemModifierImpl(
                (state, gems) -> gems + (leftBonus * state.getRoundPlayersManager().getExitedPlayers().size()),
                (leftBonus >= 0 ? "+" : "") + leftBonus + " gems for each player who exits the round");
    }

}
