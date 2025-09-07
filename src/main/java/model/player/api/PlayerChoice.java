package model.player.api;

import model.player.impl.PlayerInRound;

/**
 * Enum containing the player's choices that
 * can be made at the end of the turn.
 * 
 * @see PlayerInRound
 * 
 * @param STAY  choice that represents if a player is staying.
 * @param EXIT  choice that represents if a player is exited.
 * 
 * @author Filippo Gaggi
 */
public enum PlayerChoice { STAY, EXIT }
