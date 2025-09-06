package jvmt.model.card.api;

import java.net.URL;
import java.util.Objects;

/**
 * Is the basis for all cards in the game.
 * It contains elements common to all cards.
 * 
 * @see TypeCard
 * 
 * @author Andrea La Tosa
 */
public class Card {

    private final String name;
    private final TypeCard type;
    private final URL imageUrl;

    /**
     * Builds a new card using the name, type, and path of the image.
     * The image is loaded from a file inside the imageCard folder.
     * 
     * @param name      the name of the card
     * @param type      the type of the card
     * @param imagePath the path used to associate the card with the image
     * 
     * @throws IllegalArgumentException if the path provided does not point to a
     *                                  valid image file
     * @throws NullPointerException     if the name or type of the card is null or
     *                                  if the image URL does not point to a valid
     *                                  path
     */
    protected Card(final String name, final TypeCard type, final String imagePath) {
        this.name = Objects.requireNonNull(name, "The name cannot be null.");
        this.type = Objects.requireNonNull(type, "The card type cannot be null.");
        final String sourceImagePath = "/imageCard/" + imagePath;
        this.imageUrl = Objects.requireNonNull(
                Card.class.getResource(sourceImagePath),
                "Image resource not found at path: " + sourceImagePath);
    }

    /**
     * @return the name of the card.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the type of the card.
     * 
     * @see TypeCard
     */
    public TypeCard getType() {
        return this.type;
    }

    /**
     * @return the URL of the image resource associated with the card.
     */
    public URL getImagePath() {
        return this.imageUrl;
    }

    /**
     * @return a string representation of the card including: the name, type, and
     *         path of the card image
     */
    @Override
    public String toString() {
        return "Card [getName()=" + getName() + ", getType()=" + getType() + ", getImagePath()=" + getImagePath() + "]";
    }

}
