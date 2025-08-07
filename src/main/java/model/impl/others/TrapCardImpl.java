package model.impl.others;

import model.api.others.TrapCard;

public class TrapCardImpl implements TrapCard {

    private final String name;

    public TrapCardImpl(String name) {
        this.name = name;
    }

    @Override
    public CardType getType() {
        return CardType.TRAP;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TrapCard)) {
            return false;
        }

        final TrapCard other = (TrapCard) obj;

        return this.getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        final int factor = 31;
        int result = 17;
        result = result * factor + this.getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "[TRAP: " + this.getName() + "]";
    }

}