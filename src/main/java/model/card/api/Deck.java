package model.card.api;

import java.util.Iterator;

/**
 * Represents the game deck,
 * it provides common methods that the deck must offer.
 * 
 * @author Andrea La Tosa
 */
public interface Deck extends Iterator<Card> {

    /**
     * @return the number of cards remaining in the deck.
     */
    int numberOfRemainingCards();

    /**
     * @return the next card to be drawn without removing it from the deck.
     */
    Card peekCard();

    /**
     * @return the next card that must be drawn by removing it from the deck.
     */
    @Override
    Card next();

    /**
     * @return true if the deck still has cards, false otherwise.
     */
    @Override
    boolean hasNext();

    /**
     * @return the number of cards initially present in the deck.
     */
    int deckSize();

    /**
     * @return the number of relics initially present in the deck.
     */
    int totRelicCardsInDeck();

    /**
     *  @return the number of treasures initially present in the deck.
     */
    int totTreasureCardsInDeck();

    /**
     * @return the number of traps initially present in the deck.
     */
    int totTrapCardsInDeck();

    /**
     * @return the number of trap types included in the deck.
     */
    int totTrapCardTypesInDeck();

    /**
     * @return the number of special card initially present in the deck.
     */
    int totSpecialCardInDeck();

    Deck getShuffledCopy();
}
