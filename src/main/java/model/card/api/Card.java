package model.card.api;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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
    private final String imagePath;
    private final BufferedImage imageCard;

    /**
     * Builds a new card using the name, type, and path of the image.
     * The image is loaded from a file inside the imageCard folder.
     * 
     * @param name the name of the card
     * @param type the type of the card
     * @param imagePath the path used to associate the card with the image
     * 
     * @throws IllegalArgumentException if the path provided does not point to a valid image file.
     */ 
    protected Card(final String name, final TypeCard type, final String imagePath) {
        this.name = name;
        this.type = type;
        this.imagePath = "/imageCard/" + imagePath;

        final URL imageUrl = Card.class.getResource(this.imagePath);
        try {
            this.imageCard = ImageIO.read(imageUrl);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Image not available for the card ["
                + "name: " + name + ", type card: " + type + ", image path: " + imagePath + "]", e);
        }
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
     * @return the relative path of the image.
     */
    public String getImagePath() {
        return this.imagePath;
    }

    /**
     * @return the image of the card.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
    justification = "The image is considered immutable and will not be modified" 
        + " by callers, so it is safe to return it directly.")
    public BufferedImage getImageCard() {
        return this.imageCard;
    }

    /**
     * @return a string representation of the card including: the name, type, and path of the card image
     */
    @Override
    public String toString() {
        return "Card [getName()=" + getName() + ", getType()=" + getType() + ", getImagePath()=" + getImagePath() + "]";
    }

}
