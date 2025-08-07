package impl.others;

import java.util.Random;

import api.others.RelicCard;

public class RelicCardImpl implements RelicCard {

    private boolean taken;

    private int gems = this.computeGems();

    @Override
    public int getGems() {
        return this.gems;
    }

    private int computeGems() {
        final Random rand = new Random();
        final int min = 1;
        final int max = 10;

        final int gems = rand.nextInt(max - min + 1) + min;
        return gems;
    }

    @Override
    public CardType getType() {
        return CardType.RELIC;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public boolean isAlreadyTaken() {
        return this.taken;
    }

    @Override
    public void setAsTaken() {
        this.taken = true;
    }

    @Override
    public void setAsAvailable() {
        this.taken = false;
    }

    @Override
    public String toString() {
        return "[RELIC: " + this.gems + " gems]";
    }

}
