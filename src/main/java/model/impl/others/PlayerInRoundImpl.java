package model.impl.others;

import model.api.others.PlayerInRound;

public class PlayerInRoundImpl implements PlayerInRound {

    private final String name;
    private boolean left = false;
    private int sack;
    private int chest;

    public PlayerInRoundImpl(final String name) {
        this.name = name;
    }

    @Override
    public void leave() {
        this.left = true;
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();
        b.append("[PLAYER | name: ");
        b.append(this.name);
        b.append(" | left: ");
        b.append(this.left);
        b.append(" | sack: ");
        b.append(this.sack);
        b.append(" | chest: ");
        b.append(this.chest);
        b.append("]\n");
        return b.toString();
    }

    @Override
    public boolean hasLeft() {
        return this.left;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isCPU() {
        return false;
    }

    @Override
    public int getChestGems() {
        return this.chest;
    }

    @Override
    public int getSackGems() {
        return this.sack;
    }

    @Override
    public void addSackGems(int gems) {
        this.sack += gems;
    }

    @Override
    public void addSackToChest() {
        this.chest += this.sack;
        this.resetSack();
    }

    @Override
    public void resetSack() {
        this.sack = 0;
    }

    @Override
    public void resetRoundPlayer() {
        resetSack();
        this.left = false;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PlayerInRound other = (PlayerInRound) obj;
        return this.name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
}
