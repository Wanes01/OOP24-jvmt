package impl.card;

import java.util.List;
import java.util.Random;

import api.card.CardWithGem;
import api.card.TypeCard;

/**
 * Represents a relic card.
 * When this card is created, the value of its gems is determined by a random value from a list.
 * 
 * @author Andrea La Tosa
 */
public final class RelicCard extends CardWithGem {

    //Is a list of possible gem values that can be associated with the relic card.
    private static final List<Integer> POSSIBLE_RELIC_GEM = List.of(5, 7, 8, 10, 12);
    // Is the path for the image of the relic cards.
    private static final String IMAGE_RELIC_PATH = "relic/Relic.png";
    /* This variable indicates the state of the card.
     * if redeemed == true, it signifies that the card has already been redeemed by one player.
     * if redeemed == false, it signifies that no player has redeemed it yet.*/
    private boolean redeemed;

    /*
    public RelicCard(final String name, final TypeCard type, final String imagePath) {
        super(name, type, imagePath);
        setGemValue(generateGemValue());
    }
    */

    /**
     * Creates a new relic card with a random gem value from a predefined list.
     * 
     * @param name the name of the card
     * 
     * @see TypeCard
     */
    public RelicCard(final String name) {
        super(name, TypeCard.RELIC, IMAGE_RELIC_PATH);
        setGemValue(generateGemValue());
    }

    /**
     * @return a random gem value from the predefined list.
     * 
     * @see Random
     */
    private int generateGemValue() {
        final Random rnd = new Random();
        final int indexEl = rnd.nextInt(POSSIBLE_RELIC_GEM.size());
        return POSSIBLE_RELIC_GEM.get(indexEl);
    }

    /**
     * 
     * @return Returns a string representation of the relic card including:
     * the name, type, gem value of the card and the path to the card image.
     */
    @Override
    public String toString() {
        return "Relic " + super.toString();
    }

    /**
     * @return the status of the relic card.
     * True if it has already been redeemed by a player, false otherwise.
     */
    public boolean isRedeemed() {
        return this.redeemed;
    }

    /**
     * Redeem the card for a player.
     */
    public void redeemCard() {
        this.redeemed = true;
    }

}
