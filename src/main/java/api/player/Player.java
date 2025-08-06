package api.player;

/**
 * Rapresents a generic player.
 * <p>
 * This interface provides the name of a player
 * and the gems inside their chest.
 * </p>
 * 
 * @see PlayerInRound
 * 
 * @author Filippo Gaggi
 */
public interface Player {

    /**
     * @return the name of a player.
     */
    String getName();

    /**
     * @return the gems inside a player's chest.
     */
    int getChestGems();
}
