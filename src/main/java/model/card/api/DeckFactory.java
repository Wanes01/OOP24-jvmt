package model.card.api;

/**
 * Represents all types of decks that can be built.
 * 
 * @author Andrea La Tosa
 */
public interface DeckFactory {

    /**
     * Create the standard configuration of the deck.
     * <p>The standard configuration includes:</p>
     * <ul>
     * <li> 15 trap cards (3 per type)</li>
     * <li> 5 relic cards</li>
     * <li> 15 treasure cards</li>
     * </ul>
     * 
     * @return a new instance of DeckImpl
     */
    Deck standardDeck();

    /**
     * Unimplemented method 'specialDeck'.
     * 
     * @return should return Deck, but since it has not yet been implemented,
     * it returns UnsupportedOperationException
     * 
     * @throws UnsupportedOperationException because it has not yet been implemented
     */
    Deck specialDeck();

}
