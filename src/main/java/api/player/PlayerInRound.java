package api.player;

import impl.player.PlayerCpu;
import impl.player.RealPlayer;

/**
 * Represents a player during a round.
 * <p>
 * This class implements {@link Player},
 * it provides various informations regarding a player during
 * a round, such as their game status, the number of gems
 * inside their sack and their choice at the end of the turn.
 * This class also provides methods for modifying and reset
 * a player's informations.
 * </p>
 * 
 * @see Player
 * @see RealPlayer
 * @see PlayerCpu
 * @see PlayerChoice
 * 
 * @author Filippo Gaggi
 */
public class PlayerInRound implements Player {

    private final String name;
    private int chestGems;
    private int sackGems;
    private PlayerChoice choice;
    private boolean inGame;

    /**
     * Initializes the player's informations.
     * 
     * @param name a string representing the player's name
     */
    protected PlayerInRound(final String name) {
        this.name = name;
        this.chestGems = 0;
        this.sackGems = 0;
        this.choice = PlayerChoice.STAY;
        this.inGame = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getChestGems() {
        return this.chestGems;
    }

    /**
     * @return the player's the player's informations
     * in a String.
     */
    @Override
    public String toString() {
        return "PlayerInRound{"
            + "name ='" + getName()
            + ", chestGems =" + getChestGems()
            + ", sackGems =" + getSackGems()
            + ", choice =" + getChoice()
            + ", inGame =" + hasLeft()
            + '}';
    }

    /**
     * @return the player's game status.
     */
    public boolean hasLeft() {
        return !this.inGame;
    }

    /**
     * @return the player's choice at the end of the turn.
     */
    public PlayerChoice getChoice() {
        return this.choice;
    }

    /**
     * @return the quantity of gems inside the player's sack.
     */
    public int getSackGems() {
        return this.sackGems;
    }

    /**
     * Adds a certain amount of gems to the player's sack.
     * 
     * @throws IllegalArgumentException if the amount of gems
     * to add to the sack is negative.
     * 
     * @param gems the number of gems to add to the player's sack.
     */
    public void addSackGems(final int gems) {
        if (gems < 0) {
            throw new IllegalArgumentException(
                "The amount of gems can't be negative.");
        }
        this.sackGems += gems;
    }

    /**
     * Substracts a certain amount of gems from the player's sack.
     * The sack's amount of gems can't be negative.
     * 
     * @throws IllegalArgumentException if the amount of gems
     * to substract from the sack is negative.
     * 
     * @param gems the number of gems to substract from the player's
     *             sack.
     */
    public void subSackGems(final int gems) {
        if (gems < 0) {
            throw new IllegalArgumentException(
                "The amount of gems can't be negative.");
        }
        if (this.sackGems < gems) {
            this.sackGems = 0;
        }
        this.sackGems -= gems;
    }

    /**
     * Resets to zero the amount of gems inside the player's sack.
     */
    public void resetSack() {
        this.sackGems = 0;
    }

    /**
     * Adds the amount of gems inside the player's sack to their
     * chest.
     */
    public void addSackToChest() {
        this.chestGems += this.sackGems;
        resetSack();
    }

    /**
     * Substracts a certain amount of gems from the player's chest.
     * The chest's amount of gems can't be negative.
     * 
     * @throws IllegalArgumentException if the chest contains a negative
     * amount of gems.
     * 
     * @param gems the number of gems to substract from the player's
     *             chest.
     */
    public void subChestGems(final int gems) {
        if (gems < 0) {
            throw new IllegalArgumentException(
                "The sack's amount of gems can't be negative.");
        }
        if (this.chestGems < gems) {
            this.chestGems = 0;
        }
        this.chestGems -= gems;
    }

    /**
     * Updates the player's choice.
     * 
     * @param choice the choice that'll be overwitten as
     *               the player's choice.
     */
    public void choose(final PlayerChoice choice) {
        this.choice = choice;
    }

    /**
     * Updates the player's game status, it'll be set
     * as false if the player's choice is EXIT.
     * 
     * @throws IllegalStateException if the player's
     * game status is false.
     */
    public void exit() {
        if (!this.inGame) {
            throw new IllegalStateException(
                "The player is already out of the cave.");
        }
        if (this.choice == PlayerChoice.EXIT) {
            this.inGame = false;
        }
    }

    /**
     * Resets to true the player's game status.
     */
    public void resetStatus() {
        this.inGame = true;
    }

    /**
     * Resets the player's sack gems to zero and
     * their game status to true.
     */
    public void resetRoundPlayer() {
        resetSack();
        resetStatus();
    }
}
