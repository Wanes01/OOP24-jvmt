package impl.round.roundeffect.gemmodifier;

import api.round.roundeffect.gemmodifier.GemModifier;
import api.round.roundeffect.gemmodifier.GemModifierFactory;

/**
 * Concrete implementation of {@link GemModifierFactory}.
 * 
 * @see GemModifier
 * @see GemModifierFactory
 * @author Emir Wanes Aouioua
 */
public class GemModifierFactoryImpl implements GemModifierFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public GemModifier standard() {
        return new GemModifierImpl(
                (state, gems) -> gems,
                "Nessun modificatore applicato alle gemme.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemModifier riskyReward() {
        final int extraReward = 3;
        return new GemModifierImpl(
                (state, gems) -> gems + (state.getDrawnTraps().size() * extraReward),
                "+" + extraReward + " gemme per ogni carta trappola già pescata");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemModifier chaosModifier() {
        final double minFactor = 0.5;
        final double maxFactor = 2.5;

        final double factor = minFactor + Math.random() * (maxFactor - minFactor);
        return new GemModifierImpl(
                (state, gems) -> (int) (gems * factor),
                "Moltiplicatore applicato alle gemme [x" + factor + "]");
    }

}
