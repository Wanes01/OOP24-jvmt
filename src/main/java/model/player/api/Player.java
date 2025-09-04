package model.player.api;

/**
 * Rapresents a generic player.
 * This interface provides the name of a player
 * and the gems inside their chest.
 * 
 * @see PlayerInRound
 * 
 * @author Filippo Gaggi
 */
public interface Player {

    /**
     * @return  the name of a player.
     */
    String getName();

    /**
     * @return  the gems inside a player's chest.
     */
    int getChestGems();
}
