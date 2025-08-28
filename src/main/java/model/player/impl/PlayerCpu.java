package model.player.impl;

import java.util.Objects;

/**
 * Represents a CPU player during a round.
 * <p>
 * This class extends {@link PlayerInRound}.
 * </p>
 * 
 * @see PlayerInRound
 * 
 * @author Filippo Gaggi
 */
public class PlayerCpu extends PlayerInRound {

    /**
     * Initializes the CPU player's informations.
     * 
     * @throws NullPointerException if {@link name} is null.
     * 
     * @param name a string representing the CPU player's
     *             name
     */
    public PlayerCpu(final String name) {
        super(Objects.requireNonNull(name));
    }
}
