package card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.card.Card;
import api.card.Deck;
import impl.card.DeckFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

/**
 * Deck test class.
 * 
 * @author Andrea La Tosa
 */
class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
       final DeckFactoryImpl deckFactory = new DeckFactoryImpl();
       this.deck = deckFactory.standardDeck();
    }

    // Verify that the number of cards in the deck 
    // for the standard configuration is correct.
    @Test
    void numberCardsInStandardDeck() {
        final int nCardStandard = 35;
        assertEquals(nCardStandard, deck.deckSize());
    }

    // Verify that the number of trap cards in the deck 
    // for the standard configuration is correct.
    @Test
    void numberTrapCardsInStandardDeck() {
        final int nTrapCards = 15;
        assertEquals(nTrapCards, deck.totTrapCardsInDeck());
    }

    // Verify that the number of relic cards in the deck 
    // for the standard configuration is correct.
    @Test
    void numberRelicCardsInStandardDeck() {
        final int nRelicCards = 5;
        assertEquals(nRelicCards, deck.totRelicCardsInDeck());
    }

    // Verify that the number of treasure cards in the deck 
    // for the standard configuration is correct.
    @Test
    void numberTreasureCardsInStandardDeck() {
        final int nTreasureCard = 15;
        assertEquals(nTreasureCard, deck.totTreasureCardsInDeck());
    }

    // Verify that the number of trap card types in the deck
     // for the standard configuration is correct.
    @Test
    void numberTrapTypesStandardDeck() {
        final int nTrapTypes = 5;
        assertEquals(nTrapTypes, deck.totTrapCardTypesInDeck());
    }

    // Check that peekCard does not return a null element 
    // and that it does not remove it from the deck.
    @Test
    void peekCard() {
        final int deckSize = deck.deckSize();
        final Card card = deck.peekCard();
        assertNotNull(card, "The card must not be null.");
        assertEquals(deckSize, deck.deckSize());
    }

    // Check that the card returned from the deck is not null
    // and that the card has been removed from the deck.
    @Test
    void nextCardNotNull() {
        final int deckSize = deck.numberOfRemainingCards();
        final Card nextCard = deck.next();
        assertNotNull(nextCard, "The card drawn must not be null.");
        assertEquals(deck.numberOfRemainingCards(), deckSize - 1);
    }

    // Check if the exception NoSuchElementException is thrown
    // if a card is requested from an empty deck
    @Test
    void drawnWithEmptyDeck() {
        while (deck.hasNext()) {
            deck.next();
        }
        assertThrows(NoSuchElementException.class, deck::next);
        assertThrows(NoSuchElementException.class, deck::peekCard);
    }
}
