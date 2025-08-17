package model.card.impl;

import java.util.List;
import java.util.Random;

import model.card.api.CardWithGem;
import model.card.api.TypeCard;

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

    private static final Random RND = new Random();

    private static final int HASHCODE_BASE = 17;

    /* This variable indicates the state of the card.
     * if redeemed == true, it signifies that the card has already been redeemed by one player.
     * if redeemed == false, it signifies that no player has redeemed it yet.*/
    private boolean redeemed;

    /**
     * Creates a new relic card with a random gem value from a predefined list.
     * 
     * @param name the name of the card
     * 
     * @see TypeCard
     */
    public RelicCard(final String name) {
        // The vaue of gems gets randomly chosen from the values inside the list
        super(
            name,
            TypeCard.RELIC,
            IMAGE_RELIC_PATH, 
            POSSIBLE_RELIC_GEM.get(RND.nextInt(POSSIBLE_RELIC_GEM.size())));
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

    /**
     * Compare this item with the specified item to verify that they are the same.
     * Two RelicCards are considered the same if they have the same type.
     * 
     * @param obj the object to compare with this one
     * 
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final RelicCard other = (RelicCard) obj;
        return getType() == other.getType();
    }

    /**
     * @return the hash code for the treasure card uses getType().
     */
    @Override
    public int hashCode() {
        final int prime = 19;
        int result = HASHCODE_BASE;
        result = prime * result + (getType() == null ? 0 : getType().hashCode());
        return result;
    }

    /**
     * 
     * @return Returns a string representation of the relic card including:
     * the name, type, gem value of the card and the path to the card image.
     */
    @Override
    public String toString() {
        return this.getName() + super.toString();
    }

}
