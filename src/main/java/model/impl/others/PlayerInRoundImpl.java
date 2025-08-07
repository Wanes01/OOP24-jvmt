package model.impl.others;

import model.api.others.PlayerInRound;

public class PlayerInRoundImpl implements PlayerInRound {

    private final String name;
    private boolean left = false;

    public PlayerInRoundImpl(final String name) {
        this.name = name;
    }

    @Override
    public void leave() {
        this.left = true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerInRound)) {
            return false;
        }
        final var other = (PlayerInRound) o;
        return this.name.equals(other.getName()) && this.hasLeft() == other.hasLeft();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + Boolean.hashCode(left);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();
        b.append("[PLAYER | name: ");
        b.append(this.name);
        b.append(" | left: ");
        b.append(this.left);
        b.append("]");
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
        return 0;
    }

    @Override
    public int getSackGems() {
        return 0;
    }

    @Override
    public void addSackGems(int gems) {
    }

    @Override
    public void addSackToChest() {
    }

    @Override
    public void resetSack() {
    }

    @Override
    public void resetRoundPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetRoundPlayer'");
    }

}
