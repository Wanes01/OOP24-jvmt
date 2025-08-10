package model.others.impl;

import java.util.Random;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import model.others.api.Card;
import model.others.api.TreasureCard;

public class TreasureCardImpl implements TreasureCard {

    private final int gems = this.computeGems();

    @Override
    public int getGems() {
        return this.gems;
    }

    @SuppressFBWarnings(value = "DMI_RANDOM_USED_ONLY_ONCE", justification = "...")
    private int computeGems() {
        final Random rand = new Random();
        final int min = 1;
        final int max = 10;

        final int gems = rand.nextInt(max - min + 1) + min;
        return gems;
    }

    @Override
    public CardType getType() {
        return Card.CardType.TREASURE;
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
    public String toString() {
        return "[TREASURE: " + this.gems + " gems]";
    }

}
