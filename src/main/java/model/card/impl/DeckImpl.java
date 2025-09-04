package model.card.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import model.card.api.Card;
import model.card.api.Deck;
import model.card.api.TypeTrapCard;

/**
 * The implementation of the Deck interface.
 * 
 * @author Andrea La Tosa
 */
public final class DeckImpl implements Deck {

    private final List<Card> deck;

    //Deck statistics
    private int totRelic;
    private int totTreasure;
    private int totTrap;
    private int totSpecial;
    private final Set<TypeTrapCard> totTrapTypes;
    private final int initialDeckSize;

    /**
     * Creates the deck for the round and sets its statistics.
     * 
     * @param deck the deck to use
     * 
     * @throws NullPointerException if null is passed to the deck parameter
     */
    public DeckImpl(final List<Card> deck) {
        this.deck = new ArrayList<>(
            Objects.requireNonNull(deck, "Deck must not be null"));
        this.initialDeckSize = deck.size();
        totTrapTypes = EnumSet.noneOf(TypeTrapCard.class);
        calculateStatistics();
    }

    // This method calculates statistics for the deck
    private void calculateStatistics() {
        for (final Card card : deck) {
            switch (card.getType()) {
                case TREASURE:
                    this.totTreasure++;
                    break;
                case TRAP:
                    // Used to eliminate the warning of an unsafe cast
                    assert card instanceof TrapCard : "Expected TrapCard instance for TRAP type";

                    this.totTrapTypes.add(((TrapCard) card).getTypeTrap());
                    this.totTrap++;
                    break;
                case RELIC:
                    this.totRelic++;
                    break;
                case SPECIAL:
                    this.totSpecial++;
                    break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int numberOfRemainingCards() {
        return this.deck.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Card peekCard() {
        if (!hasNext()) {
            throw new NoSuchElementException("A card is requested, but the deck has no cards.");
        }
        return this.deck.get(this.deck.size() - 1);
    }

    /**
     * @throws NoSuchElementException if a card is requested but the deck has no cards. 
     * 
     * @return the next card that must be drawn by removing it from the deck.
     */
    @Override
    public Card next() {
        if (!hasNext()) {
            throw new NoSuchElementException("A card is requested, but the deck has no cards.");
        }
        final Card drawn = peekCard();
        this.deck.remove(this.deck.size() - 1);
        return drawn;
    }

    /**
     * @return true if the deck still has cards, false otherwise.
     */
    @Override
    public boolean hasNext() {
        return !this.deck.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deckSize() {
        return this.initialDeckSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int totRelicCardsInDeck() {
        return this.totRelic;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int totTreasureCardsInDeck() {
        return this.totTreasure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int totTrapCardsInDeck() {
        return this.totTrap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int totTrapCardTypesInDeck() {
        return this.totTrapTypes.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int totSpecialCardInDeck() {
        return this.totSpecial;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Deck getShuffledCopy() {
        final List<Card> cards = new ArrayList<>(this.deck);
        Collections.shuffle(cards);
        return new DeckImpl(cards);
    }

}
