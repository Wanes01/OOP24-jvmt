package model.card.api;

/**
 * Contains methods for adding various types of cards to the deck.
 * <p>There are two methods for each type of card: </p>
 * <ul>
 * <li>addX, which adds a single card to the deck</li>
 * <li>addMultipleX, which adds more than one card</li>
 * </ul>
 * 
 * @author Andrea La Tosa
 */
public interface DeckBuilder {

    /**
     * Constructs and returns a {@link Deck} object, representing a complete deck 
     * with methods for using it and related statistics.
     * 
     * @return a Deck implementation
     * 
     * @author Andrea La Tosa
     */
    Deck build();

    /**
     * Adds a single trap to the deck of the specified type.
     * 
     * @param type the type of trap to add to the deck
     * 
     * @return DeckBuilder to follow the builder pattern
     * 
     * @throws NullPointerException if {@code typeTrap} is null
     */
    DeckBuilder addTrap(TypeTrapCard type);
    /**
     * Adds the number of trap cards to the deck, 
     * all of the same type, according to the input provided.
     * 
     * @param typeTrap the type of trap cards to create
     * @param numTrap the number of trap cards to create
     * 
     * @return DeckBuilder to follow the builder pattern
     * 
     * @throws NullPointerException if {@code typeTrap} is null
     * @throws IllegalArgumentException if numtrap is <= 0
     */
    DeckBuilder addMultipleTrap(TypeTrapCard typeTrap, int numTrap);

    /**
     * Adds a single treasure card to the deck with the number of gems specified in the input.
     * 
     * @param gemValue the number of gems to assign to the card before adding it to the deck
     * 
     * @return DeckBuilder to follow the builder pattern
     * 
     * @throws IllegalArgumentException if gemValue is <= 0
     */
    DeckBuilder addTreasure(int gemValue);
    /**
     * Adds the number of treasure cards specified in the input to the deck,
     * all with the same gem value.
     * 
     * @param gemValue the number of gems to assign to the card before adding it to the deck
     * @param numTreasure the number of treasure cards to add to the deck
     * 
     * @return DeckBuilder to follow the builder pattern
     * 
     * @throws IllegalArgumentException if gemValue or numTreasure are <= 0
     */
    DeckBuilder addMultipleTreasure(int gemValue, int numTreasure);

    /**
     * Adds a single relic card to the deck.
     * 
     * @return DeckBuilder to follow the builder pattern
     */
    DeckBuilder addRelic();
    /**
     * Adds the number of relic card specified in input to the deck.
     * 
     * @param numRelic the number of relic cards to add to the deck
     * 
     * @return DeckBuilder to follow the builder pattern
     * 
     * @throws IllegalArgumentException if numRelic is <= 0
     */
    DeckBuilder addMultipleRelicCard(int numRelic);

    /**
     * Shuffle the cards in the deck.
     * 
     * @return DeckBuilder to follow the builder pattern
     */
    DeckBuilder shuffle();

}
