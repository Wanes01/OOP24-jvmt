package model.player.impl;

import java.util.Objects;

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
     * @throws NullPointerException if {@link name} is null.
     * 
     * @param name a string representing the real player's
     *             name
     */
    public RealPlayer(final String name) {
        super(Objects.requireNonNull(name));
    }
}
