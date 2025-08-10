package model.player.impl;

/**
 * Represents a real player during a round.
 * <p>
 * This class extends {@link PlayerInRound}.
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
