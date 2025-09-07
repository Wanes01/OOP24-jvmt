package jvmt.model.player.impl;

import java.util.Objects;

import jvmt.model.game.api.GameSettings;
import jvmt.model.player.api.LogicCpu;
import jvmt.model.round.api.RoundState;

/**
 * Represents a CPU player during a round.
 * <p>
 * This class extends {@link PlayerInRound}.
 * </p>
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
     * @param name a string representing the CPU player's name.
     * @param settings the game settings.
     */
    public PlayerCpu(final String name, final GameSettings settings) {
        super(Objects.requireNonNull(name));
        this.logic = new LogicCpuImpl(Objects.requireNonNull(settings));
    }

    /**
     * Initializes the CPU player's informations, second constructor that gives in input
     * the seed of the Random object on top of other fields in order to
     * facilitate testing.
     * 
     * @throws NullPointerException if {@link name} is null.
     * @throws NullPointerException if {@link settings} is null.
     * 
     * @param name a string representing the CPU player's name.
     * @param settings the game settings.
     * @param seed seed for the Random object.
     */
    public PlayerCpu(final String name, final GameSettings settings, final int seed) {
        super(Objects.requireNonNull(name));
        this.logic = new LogicCpuImpl(Objects.requireNonNull(settings), seed);
    }

    /**
     * Method for making the CPU player take a choice through the CPU logic.
     * 
     * @throws NullPointerException if {@link state} is null.
     * 
     * @param state the current game state.
     */
    public void choose(final RoundState state) {
        choose(logic.cpuChoice(Objects.requireNonNull(state)));
    }
}
