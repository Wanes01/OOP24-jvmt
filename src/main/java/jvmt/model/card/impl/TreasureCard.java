package jvmt.model.card.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jvmt.model.card.api.CardWithGem;
import jvmt.model.card.api.TypeCard;

/**
 * Represents a treasure card.
 * 
 * @see TypeCard
 * 
 * @author Andrea La Tosa
 */
public final class TreasureCard extends CardWithGem {

    // represents the folder containing images of the various treasures
    private static final String TREASURE_PATH_IMAGE = "treasure/";
    // map that associates each type of treasures with the corresponding image
    private static final Map<Integer, String> PATH_IMAGE;
    // a set containing all possible gem values for the treasure card.
    private static final Set<Integer> POSSIBLE_GEM_VALUES = Set.of(1, 2, 3, 4, 5, 7, 9, 11, 13, 14, 15, 17);

    private static final int HASHCODE_BASE = 23;

    static {
        final Map<Integer, String> tempMap = new HashMap<>();
        for (final int gemValue : POSSIBLE_GEM_VALUES) {
            tempMap.put(gemValue,
                    TREASURE_PATH_IMAGE + Integer.toString(gemValue) + "_Gem.png");
        }
        // makes the list immutable by blocking both the modification of the reference
        // and the possibility of adding elements
        PATH_IMAGE = Map.copyOf(tempMap);
    }

    /**
     * Creates a new Treasure card.
     * The image path is automatically derived from the number of gems.
     * 
     * @param name     the name of the card
     * @param gemValue the gem value of the card
     */
    public TreasureCard(final String name, final int gemValue) {
        super(name, TypeCard.TREASURE, validateGemValueAndGetPath(gemValue), gemValue);
    }

    /**
     * Checks that the number of gems to be associated with the card is present in
     * the set and, if so,
     * returns the path for the image relating to the card; otherwise, it throws an
     * exception.
     * 
     * @param gemValue the value of the gems to be associated with the card
     * 
     * @throws IllegalArgumentException if the value of the gems is not present
     *                                  in the set of acceptable gem values.
     * 
     * @return the path of the image of the card
     */
    private static String validateGemValueAndGetPath(final int gemValue) {
        if (!POSSIBLE_GEM_VALUES.contains(gemValue)) {
            throw new IllegalArgumentException("Invalid gem value for treasure card: " + gemValue);
        }
        return PATH_IMAGE.get(gemValue);
    }

    /**
     * Compare this item with the specified item to verify that they are the same.
     * Two TreasureCards are considered the same if they have the same gem value and
     * type.
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
        final TreasureCard other = (TreasureCard) obj;
        return getGemValue() == other.getGemValue()
                && getType() == other.getType();
    }

    /**
     * @return the hash code for the treasure card uses getGemValue() and getType().
     */
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = HASHCODE_BASE;
        result = prime * result + getGemValue();
        result = prime * result + (getType() == null ? 0 : getType().hashCode());
        return result;
    }

    /**
     * @return Returns a string representation of the Treasure including:
     *         the name, type, gem value of the card and the path to the card image.
     */
    @Override
    public String toString() {
        return this.getName() + super.toString();
    }
}
