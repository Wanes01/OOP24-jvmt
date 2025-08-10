package api.card;

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
    private int gemValue;

    /**
     * Builds a new card using the name, type, and path of the image.
     * 
     * @see TypeCard
     * 
     * @param name the name of the card
     * @param type the type of the card
     * @param imagePath the path used to associate the card with the image
     */
    protected CardWithGem(final String name, final TypeCard type, final String imagePath) {
        super(name, type, imagePath);
    }

    /**
     * Set the value of the gem on the card.
     * 
     * @param val the gem value assigned to the card
     * 
     * @throws IllegalStateException if a change to the value of the gems is requested
     * after it has already been set previously
     */
    protected final void setGemValue(final int val) {
        if (this.gemValue == 0) {
            this.gemValue = val;
        } else {
            throw new IllegalStateException("Cannot modify gemValue once it has been set.");
        }
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
