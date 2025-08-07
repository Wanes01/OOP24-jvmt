package model.impl.round.roundeffect.gemmodifier;

import model.api.round.roundeffect.gemmodifier.GemModifier;
import model.api.round.roundeffect.gemmodifier.GemModifierFactory;

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
                "Nessun modificatore applicato alle gemme.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemModifier riskyReward(final int bonus) {
        return new GemModifierImpl(
                (state, gems) -> gems + (state.getDrawnTraps().size() * bonus),
                "+" + bonus + " gemme per ogni carta trappola già pescata.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemModifier gemMultiplier(final double multiplier) {
        return new GemModifierImpl(
                (state, gems) -> (int) (gems * multiplier),
                "Moltiplicatore applicato alle gemme [x" + multiplier + "]");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemModifier leftReward(final int leftBonus) {
        return new GemModifierImpl(
                (state, gems) -> gems + (leftBonus * state.getRoundPlayersManager().getExitedPlayers().size()),
                "+" + leftBonus + " gemme per ogni giocatore uscito dal round.");
    }

}
