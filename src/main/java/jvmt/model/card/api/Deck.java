package jvmt.model.card.api;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents the game deck,
 * it provides common methods that the deck must offer.
 * 
 * @author Andrea La Tosa
 */
public interface Deck extends Iterator<Card> {

    /**
     * Returns the number of cards remaining in the deck.
     * 
     * @return the number of cards remaining in the deck.
     */
    int numberOfRemainingCards();

    /**
     * Returns the next card to be drawn without removing it from the deck.
     * 
     * @return the next card to be drawn.
     * 
     * @throws NoSuchElementException if a card is requested, but the deck has no
     *                                cards.
     */
    Card peekCard();

    /**
     * Returns the next card to be drawn by removing it from the deck.
     * 
     * @return the next card to be drawn.
     * 
     * @throws NoSuchElementException if a card is requested but the deck has no
     *                                cards.
     */
    @Override
    Card next();

    /**
     * Check if there are still cards in the deck.
     * 
     * @return true if the deck still has cards, false otherwise.
     */
    @Override
    boolean hasNext();

    /**
     * Returns the total number of cards that originally added to the deck.
     * 
     * @return the number of cards initially present in the deck.
     */
    int deckSize();

    /**
     * Returns the number of relic cards originally included in the deck at
     * creation.
     * 
     * @return the number of relics initially present in the deck.
     */
    int totRelicCardsInDeck();

    /**
     * Returns the number of treasure cards originally included in the deck at
     * creation.
     * 
     * @return the number of treasures initially present in the deck.
     */
    int totTreasureCardsInDeck();

    /**
     * Returns the number of trap cards originally included in the deck at creation.
     * 
     * @return the number of traps initially present in the deck.
     */
    int totTrapCardsInDeck();

    /**
     * Returns the number of distinct types of trap cards originally included in the
     * deck at creation.
     * 
     * @return the number of trap types included in the deck.
     */
    int totTrapCardTypesInDeck();

    /**
     * Returns the number of special cards originally included in the deck at
     * creation.
     * 
     * @return the number of special card initially present in the deck.
     */
    int totSpecialCardInDeck();

    /**
     * Returns a new {@code Deck} instance containing the same cards as this deck
     * but in a random order.
     * The original deck remains unmodified.
     *
     * @return a shuffled copy of this deck
     */
    Deck getShuffledCopy();
}
