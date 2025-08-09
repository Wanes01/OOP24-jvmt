package api.player;

import impl.player.PlayerCpu;
import impl.player.RealPlayer;

/**
 * Represents a generic player during a round.
 * <p>
 * This class implements {@link Player},
 * it provides various informations regarding a player during
 * a round, the number of gems inside their sack and their choice
 * at the end of the turn.
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

    private static final int HASHCODE_BASE = 19;
    private final String name;
    private int chestGems;
    private int sackGems;
    private PlayerChoice choice;

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
     * @return the player's informations
     * in a text string.
     */
    @Override
    public String toString() {
        return "PlayerInRound{"
            + "name ='" + getName()
            + ", chestGems =" + getChestGems()
            + ", sackGems =" + getSackGems()
            + ", choice =" + getChoice()
            + '}';
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
        } else {
            this.sackGems -= gems;
        }
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
     * @throws IllegalArgumentException if the amount of gems
     * to substract from the chest is negative.
     * 
     * @param gems the number of gems to substract from the player's
     *             chest.
     */
    public void subChestGems(final int gems) {
        if (gems < 0) {
            throw new IllegalArgumentException(
                "The amount of gems can't be negative.");
        }
        if (this.chestGems < gems) {
            this.chestGems = 0;
        } else {
            this.chestGems -= gems;
        }
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
     * Updates the player's choice as EXIT.
     * 
     * @throws IllegalStateException if the player's
     * choice is already EXIT.
     */
    public void exit() {
        if (this.choice == PlayerChoice.EXIT) {
            throw new IllegalStateException(
                "The player is already out of the cave.");
        }
        this.choice = PlayerChoice.EXIT;
    }

    /**
     * Resets to true the player's choice.
     */
    public void resetChoice() {
        this.choice = PlayerChoice.STAY;
    }

    /**
     * Resets the player's sack gems to zero and
     * their choice.
     */
    public void resetRoundPlayer() {
        resetSack();
        resetChoice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = HASHCODE_BASE;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PlayerInRound other = (PlayerInRound) obj;
        if (name == null) {
            return other.name == null;
        } else {
            return name.equals(other.name);
        }
    }
}
