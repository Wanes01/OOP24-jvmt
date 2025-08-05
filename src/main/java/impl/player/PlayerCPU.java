package impl.player;

import api.player.PlayerInRound;

/**
 * Represents a CPU player during a round.
 * <p>
 * This class extends {@link PlayerInRound},
 * it provides a method that .
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
