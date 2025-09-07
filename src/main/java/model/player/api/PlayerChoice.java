package model.player.api;

import model.player.impl.PlayerInRound;

/**
 * Enum containing the player's choices that
 * can be made at the end of the turn.
 * 
 * @see PlayerInRound
 * 
 * @author Filippo Gaggi
 */
public enum PlayerChoice {
    /** choice that represents if a player is staying. */
    STAY,
    /** choice that represents if a player is exited. */
    EXIT }
