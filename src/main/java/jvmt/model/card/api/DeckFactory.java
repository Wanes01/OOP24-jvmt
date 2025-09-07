package jvmt.model.card.api;

/**
 * Represents all types of decks that can be built.
 * 
 * @author Andrea La Tosa
 */
public interface DeckFactory {

    /**
     * Create the standard configuration of the deck.
     * <p>
     * The standard configuration includes:
     * </p>
     * <ul>
     * <li>15 trap cards (3 per type)</li>
     * <li>5 relic cards</li>
     * <li>15 treasure cards</li>
     * </ul>
     * 
     * @return a standard deck
     */
    Deck standardDeck();

    /**
     * Create the special configuration of the deck.
     * This configuration uses special card.
     * 
     * @return a special deck
     */
    Deck specialDeck();

}
