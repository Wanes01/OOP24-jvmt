package impl.player;

import api.player.PlayerInRound;

/**
 * Represents a real player during a round.
 * <p>
 * This class extends {@link PlayerInRound},
 * it provides a method that .
 * </p>
 * 
 * @see PlayerInRound
 * 
 * @author Filippo Gaggi
 */
public class RealPlayer extends PlayerInRound {

    /**
     * Initializes the real player's informations.
     * 
     * @param name a string representing the real player's
     *             name
     */
    public RealPlayer(final String name) {
        super(name);
    }
}
