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
     * @throws NullPointerException if {@link name} is null.
     * @throws NullPointerException if {@link settings} is null.
     * 
     * @param name      a string representing the CPU player's name.
     * @param settings  the game settings.
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
     * @throws NullPointerException if {@link name} is null.
     * @throws NullPointerException if {@link settings} is null.
     * 
     * @param name      a string representing the CPU player's name.
     * @param settings  the game settings.
     * @param seed      seed for the Random object.
     */
    public PlayerCpu(final String name, final GameSettings settings, final int seed) {
        super(Objects.requireNonNull(name));
        Objects.requireNonNull(settings);
        this.logic = new LogicCpuImpl(settings, seed);
    }

    /**
     * Method for making the CPU player take a choice through the CPU logic.
     * 
     * @throws NullPointerException if {@link state} is null.
     * 
     * @param state the current game state.
     */
    public void chooseCpu(final RoundState state) {
        Objects.requireNonNull(state);
        choose(logic.cpuChoice(state));
    }
}
