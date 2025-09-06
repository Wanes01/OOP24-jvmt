package jvmt.model.card.api;

import jvmt.model.card.impl.DeckFactoryImpl;

/**
 * Represents the types of deck that can be used.
 * 
 * @author Andrea La Tosa
 */
public enum TypeDeck {
    /** Represents a standard deck of cards and includes 35 cards. */
    STANDARD,
    /** Represents a special deck of cards. */
    SPECIAL;

    private static final DeckFactory FACTORY = new DeckFactoryImpl();

    /**
     * Creates and returns a new {@link Deck} based on this deck type.
     * This method delegates the creation of a deck to {@link DeckFactory}.
     * 
     * @return a new {@code Deck} instance corresponding to the selected type
     */
    public Deck getDeck() {
        return switch (this) {
            case STANDARD -> FACTORY.standardDeck();
            case SPECIAL -> FACTORY.specialDeck();
        };
    }
}
