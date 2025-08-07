package impl.others;

import java.util.Random;

import api.others.Card;
import api.others.TreasureCard;

public class TreasureCardImpl implements TreasureCard {

    private final int gems = this.computeGems();

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
