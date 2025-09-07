package model.player.impl;

import java.util.Objects;

import model.game.api.GameSettings;
import model.player.api.LogicCpu;
import model.round.api.RoundState;

/**
 * Represents a CPU player during a round.
 * This class extends {@link PlayerInRound}.
 * 
 * @see PlayerInRound
 * @see LogicCpu
 * 
 * @author Filippo Gaggi
 */
public class PlayerCpu extends PlayerInRound {

    private final LogicCpu logic;

    /**
     * Initializes the CPU player's informations.
     * 
     * @param name      a string representing the CPU player's name.
     * @param settings  the game settings.
     * 
     * @throws NullPointerException if @param name is null.
     * @throws NullPointerException if @param settings is null.
     */
    public PlayerCpu(final String name, final GameSettings settings) {
        super(Objects.requireNonNull(name));
        Objects.requireNonNull(settings);
        this.logic = new LogicCpuImpl(settings);
    }

    /**
     * Initializes the CPU player's informations, second constructor that gives in input
     * the seed of the Random object on top of other fields in order to
     * facilitate testing.
     * 
     * @param name      a string representing the CPU player's name.
     * @param settings  the game settings.
     * @param seed      seed for the Random object.
     * 
     * @throws NullPointerException if @param name is null.
     * @throws NullPointerException if @param settings is null.
     */
    public PlayerCpu(final String name, final GameSettings settings, final int seed) {
        super(Objects.requireNonNull(name));
        Objects.requireNonNull(settings);
        this.logic = new LogicCpuImpl(settings, seed);
    }

    /**
     * Method for making the CPU player take a choice through the CPU logic.
     * 
     * @param state the current game state.
     * 
     * @throws NullPointerException if @param state is null.
     */
    public void chooseCpu(final RoundState state) {
        Objects.requireNonNull(state);
        choose(logic.cpuChoice(state));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }
}
