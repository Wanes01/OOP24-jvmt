package model.card.api;

import model.card.impl.DeckFactoryImpl;

/**
 * Represents the types of deck that can be used.
 * 
 * @author Andrea La Tosa
 */
public enum TypeDeck {
    STANDARD, SPECIAL;

    private static final DeckFactory FACTORY = new DeckFactoryImpl();

    public Deck getDeck() {
        switch (this) {
            case STANDARD:
                return FACTORY.standardDeck();
            case SPECIAL:
                return FACTORY.specialDeck();
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
