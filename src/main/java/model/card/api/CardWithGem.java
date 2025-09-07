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

    //The number of gems associated with this card
    private final int gemValue;

    /**
     * Builds a new card using the name, type, path of the image and gemValue.
     * 
     * @see TypeCard
     * 
     * @param name      the name of the card
     * @param type      the type of the card
     * @param imagePath the path used to associate the card with the image
     * @param gemValue  the gem values of the card
     * 
     * @throws NullPointerException if {@code name} or {@code type} of the card is null or
     * if the {@code imagePath} does not point to a valid path
     */
    protected CardWithGem(final String name, final TypeCard type, final String imagePath, final int gemValue) {
        super(name, type, imagePath);
        this.gemValue = gemValue;
    }

    /**
     * Returns the gem value of the card.
     * 
     * @return the gem value of the card.
     */
    public int getGemValue() {
        return this.gemValue;
    }

    /**
     * Returns a string representation of the CardWithGem including:
     * the name, type, gem value of the card and the path to the card image.
     * 
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return " [getName()=" + getName() + ", getType()=" + getType() + ", getGemValue()=" + getGemValue()
                + ", getImagePath()=" + getImagePath() + "]";
    }

}
