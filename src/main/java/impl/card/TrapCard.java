package impl.card;

import java.util.Map;

import api.card.Card;
import api.card.TypeCard;
import api.card.TypeTrapCard;

/**
 * Represents a trap card.
 * 
 * @see TypeTrapCard
 * @see TypeCard
 * 
 * @author Andrea La Tosa
 */
public final class TrapCard extends Card {

    // represents the folder containing images of the various traps
    private static final String TRAP_PATH_IMAGE = "trap/";
    // map that associates each type of trap with the corresponding image
    private static final Map<TypeTrapCard, String> PATH_IMAGE = Map.of(
        TypeTrapCard.SNAKE, TRAP_PATH_IMAGE + "Snake.png",
        TypeTrapCard.LAVA, TRAP_PATH_IMAGE + "Lava.png",
        TypeTrapCard.SPIDER, TRAP_PATH_IMAGE + "Spider.png",
        TypeTrapCard.BATTERING_RAM, TRAP_PATH_IMAGE + "Battering_Ram.png",
        TypeTrapCard.BOULDER, TRAP_PATH_IMAGE + "Boulder.png"
    );
    private final TypeTrapCard typeTrap;

    /*
    public TrapCard(final String name, final TypeCard type, final String imagePath, final TypeTrapCard typeTrap) {
        super(name, type, imagePath);
        this.typeTrap = typeTrap;
    }
    */

    /**
     * Creates a new Trap card.
     * The image path is automatically derived from the trap type.
     * 
     * @param name the name of the card
     * @param typeTrap the type of trap card to create
     */
    public TrapCard(final String name, final TypeTrapCard typeTrap) {
        super(name, TypeCard.TRAP, PATH_IMAGE.get(typeTrap));
        this.typeTrap = typeTrap;
    }

    /**
     * @return the type of trap associated with this card
     */
    public TypeTrapCard getTypeTrap() {
        return this.typeTrap;
    }

    /**
     * @return Returns a representation of the trap card including:
     * the name, card type, trap type, and path to the card image.
     */
    @Override
    public String toString() {
        return "Trap Card [getName()=" + getName() + ", getType()=" + getType() 
            + ", getTypeTrap()=" + getTypeTrap() + ", getImagePath()=" + getImagePath() + "]";
    }

}
