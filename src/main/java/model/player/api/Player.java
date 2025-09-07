package model.player.api;

import model.player.impl.PlayerInRound;

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
     * Getter for the player's name.
     * 
     * @return  the name of a player.
     */
    String getName();

    /**
     * Getter for the player's chest gems.
     * 
     * @return  the gems inside a player's chest.
     */
    int getChestGems();

    /**
     * Getter for the player's sack gems.
     * 
     * @return  the quantity of gems inside the player's sack.
     */
    int getSackGems();

    /**
     * Getter for the player's choice.
     * 
     * @return  the player's choice.
     */
    PlayerChoice getChoice();

    /**
     * Adds a certain amount of gems to the player's sack.
     * 
     * @param gems  the number of gems to add to the player's sack.
     * 
     * @throws IllegalArgumentException if the amount of gems to add to the sack is negative.
     */
    void addSackGems(final int gems);

    /**
     * Substracts a certain amount of gems from the player's sack.
     * The sack's amount of gems can't be negative.
     * 
     * @param gems  the number of gems to substract from the player's sack.
     * 
     * @throws IllegalArgumentException if the amount of gems to substract from the sack is negative.
     */
    void subSackGems(final int gems);

    /**
     * Resets to zero the amount of gems inside the player's sack.
     */
    void resetSack();

    /**
     * Adds the amount of gems inside the player's sack to their chest.
     */
    void addSackToChest();

    /**
     * Substracts a certain amount of gems from the player's chest.
     * The chest's amount of gems can't be negative.
     * 
     * @param gems  the number of gems to substract from the player's chest.
     * 
     * @throws IllegalArgumentException if the amount of gems to substract from the chest is negative.
     */
    void subChestGems(final int gems);

    /**
     * Updates the player's choice.
     * 
     * @param choice    the choice that'll be overwitten as the player's choice.
     * 
     * @throws NullPointerException if @param choice is null.
     */
    void choose(final PlayerChoice choice);

    /**
     * Updates the player's choice as EXIT.
     * 
     * @throws IllegalStateException if the player's choice is already EXIT.
     */
    void exit();

    /**
     * Resets to STAY the player's choice.
     */
    void resetChoice();

    /**
     * Resets the player's sack gems to zero and their choice.
     */
    void resetRoundPlayer();
}
