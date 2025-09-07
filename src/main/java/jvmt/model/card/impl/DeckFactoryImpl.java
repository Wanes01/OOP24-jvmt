package jvmt.model.card.impl;

import java.util.Map;

import jvmt.model.card.api.Deck;
import jvmt.model.card.api.DeckBuilder;
import jvmt.model.card.api.DeckFactory;
import jvmt.model.card.api.TypeTrapCard;

/**
 * The implementation of the DeckFactory interface.
 * 
 * @author Andrea La Tosa
 */
public final class DeckFactoryImpl implements DeckFactory {

    /** Default constructor. */
    public DeckFactoryImpl() {
        // this constructor is empty on purpose.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Deck standardDeck() {
        // In this map, the key represent the gems of the card to be created
        // and the values indicate the number of cards to create.
        final Map<Integer, Integer> treasureStandardDeck = Map.ofEntries(
                Map.entry(1, 1),
                Map.entry(2, 1),
                Map.entry(3, 1),
                Map.entry(4, 1),
                Map.entry(5, 2),
                Map.entry(7, 2),
                Map.entry(9, 1),
                Map.entry(11, 2),
                Map.entry(13, 1),
                Map.entry(14, 1),
                Map.entry(15, 1),
                Map.entry(17, 1));
        // the number of cards per trap type in the standard deck.
        final int numberPerTrapStandardDeck = 3;
        // the number of relic in the standard deck.
        final int numberRelicStandardDeck = 5;

        final DeckBuilder deckBuilder = new DeckBuilderImpl();

        // adds treasure cards to the deck.
        for (final Map.Entry<Integer, Integer> entry : treasureStandardDeck.entrySet()) {
            if (entry.getValue() == 1) {
                deckBuilder.addTreasure(entry.getKey());
            } else {
                deckBuilder.addMultipleTreasure(entry.getKey(), entry.getValue());
            }
        }

        // adds trap cards to the deck.
        for (final TypeTrapCard typeTrap : TypeTrapCard.values()) {
            deckBuilder.addMultipleTrap(typeTrap, numberPerTrapStandardDeck);
        }

        // adds relic cards to the deck.
        deckBuilder.addMultipleRelic(numberRelicStandardDeck);

        // shuffle the cards in the deck.
        deckBuilder.shuffle();

        return deckBuilder.build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Deck specialDeck() {
        throw new UnsupportedOperationException("Unimplemented method 'specialDeck'");
    }

}
