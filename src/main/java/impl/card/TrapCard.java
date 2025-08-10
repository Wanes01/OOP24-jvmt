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

    private static final int HASHCODE_BASE = 29;

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
     * Compare this item with the specified item to verify that they are the same.
     * Two TrapeCards are considered the same if they have the type value and type trap card.
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
        final TrapCard other = (TrapCard) obj;
        return getType() == other.getType() 
            && getTypeTrap() == other.getTypeTrap();
    }

    /**
    * @return the hash code for the trap card uses getType() and getTypeTrap().
    */
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = HASHCODE_BASE;
        result = prime * result + (getType() == null ? 0 : getType().hashCode());
        result = prime * result + (getTypeTrap() == null ? 0 : getTypeTrap().hashCode());
        return result;
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
