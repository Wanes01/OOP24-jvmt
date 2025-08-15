package model.card.api;

/**
 * Represents a generic card that uses gems.
 * Is the basis for all cards in the game that use gems.
 * 
 * @see TypeCard
 * 
 * @author Andrea La Tosa
 */

public class CardWithGem extends Card {

    /**
     * The number of gems associated with this card.
    */
    private final int gemValue;

    /**
     * Builds a new card using the name, type, and path of the image.
     * 
     * @see TypeCard
     * 
     * @param name the name of the card
     * @param type the type of the card
     * @param imagePath the path used to associate the card with the image
     * @param gemValue the gem values of the card
     */
    protected CardWithGem(final String name, final TypeCard type, final String imagePath, final int gemValue) {
        super(name, type, imagePath);
        this.gemValue = gemValue;
    }

    /**
     * @return the gem value the card.
     */
    public int getGemValue() {
        return this.gemValue;
    }

    /**
     * @return Returns a string representation of the CardWithGem including:
     * the name, type, gem value of the card and the path to the card image.
     */
    @Override
    public String toString() {

        return " [getName()=" + getName() + ", getType()=" + getType() + ", getGemValue()=" + getGemValue()
            + ", getImagePath()=" + getImagePath() + "]";
    }

}
