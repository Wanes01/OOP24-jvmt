package jvmt.model.player.impl;

import java.util.Objects;

import jvmt.model.player.api.Player;
import jvmt.model.player.api.PlayerChoice;

/**
 * The implementation of the {@link Player} interface.
 * It provides various informations regarding a player during
 * a round, such as the number of gems inside their sack and
 * their choice at the end of the turn.
 * This class also provides methods for modifying and resetting
 * a player's informations.
 * 
 * @see Player
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
     * @throws NullPointerException if {@link name} is null.
     * 
     * @param name a string representing the player's name
     */
    public PlayerInRound(final String name) {
        Objects.requireNonNull(name);
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
     * {@inheritDoc}
     */
    @Override
    public int getSackGems() {
        return this.sackGems;
    }

    /**
     * @return the player's informations in a text string.
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
     * {@inheritDoc}
     */
    @Override
    public PlayerChoice getChoice() {
        return this.choice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSackGems(final int gems) {
        if (gems < 0) {
            throw new IllegalArgumentException(
                    "The amount of gems can't be negative.");
        }
        this.sackGems += gems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public void resetSack() {
        this.sackGems = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSackToChest() {
        this.chestGems += this.sackGems;
        resetSack();
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public void choose(final PlayerChoice choice) {
        Objects.requireNonNull(choice);
        this.choice = choice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        if (this.choice == PlayerChoice.EXIT) {
            throw new IllegalStateException(
                    "The player is already out of the cave.");
        }
        this.choice = PlayerChoice.EXIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetChoice() {
        this.choice = PlayerChoice.STAY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
