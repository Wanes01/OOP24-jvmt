package model.player.impl;

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
     * @param name a string representing the CPU player's
     *             name
     */
    public PlayerCpu(final String name) {
        super(name);
    }
}
