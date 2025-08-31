package model.card.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import model.card.api.Card;
import model.card.api.Deck;
import model.card.api.DeckBuilder;
import model.card.api.TypeTrapCard;

/**
 * The implementation of the DeckBuilder interface.
 * 
 * @author Andrea La Tosa
 */
public class DeckBuilderImpl implements DeckBuilder {
 
    private final List<Card> deck = new ArrayList<>();
    //This variable prevents changes from being made after the build() call
    private boolean isBuilt;

    /**
     * {@inheritDoc}
     */
    @Override
    public Deck build() {
        checkNotBuilt();
        this.isBuilt = true;
        return new DeckImpl(this.deck);
    }

    // Verify that no changes are requested after calling build()
    private void checkNotBuilt() {
        if (isBuilt) {
            throw new IllegalStateException("build() has already been called.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeckBuilder addTrap(final TypeTrapCard typeTrap) {
        checkNotBuilt();
        Objects.requireNonNull(typeTrap, "typeTrap cannot be null.");
        deck.add(new TrapCard(typeTrap.toString(), typeTrap));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeckBuilder addMultipleTrap(final TypeTrapCard typeTrap, final int numTrap) {
        checkNotBuilt();

        Objects.requireNonNull(typeTrap, "typeTrap cannot be null.");
        if (numTrap <= 0) {
            throw new IllegalArgumentException("numTrap must be greater than zero.");
        }

        for (int i = 1; i <= numTrap; i++) {
            deck.add(new TrapCard(typeTrap.toString() + " " + Integer.toString(i), typeTrap));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeckBuilder addTreasure(final int gemValue) {
        checkNotBuilt();

        if (gemValue <= 0) {
            throw new IllegalArgumentException("gemValue must be greater than zero.");
        }

        deck.add(new TreasureCard(gemValue + " gems", gemValue));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeckBuilder addMultipleTreasure(final int gemValue, final int numTreasure) {
        checkNotBuilt();

        if (gemValue <= 0) {
            throw new IllegalArgumentException("gemValue must be greater than zero.");
        }
        if (numTreasure <= 0) {
            throw new IllegalArgumentException("numTreasure must be greater then zero.");
        }

        for (int i = 1; i <= numTreasure; i++) {
            deck.add(new TreasureCard("Treasure " + gemValue 
                + " gems" + " " + i, gemValue));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeckBuilder addRelic() {
        checkNotBuilt();
        deck.add(new RelicCard("Relic"));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeckBuilder addMultipleRelicCard(final int numRelic) {
        checkNotBuilt();

        if (numRelic <= 0) {
            throw new IllegalArgumentException("numRelic must be greater then zero.");
        }

        for (int i = 1; i <= numRelic; i++) {
            deck.add(new RelicCard("Relic " + Integer.toString(i)));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeckBuilder shuffle() {
        checkNotBuilt();
        Collections.shuffle(this.deck);
        return this;
    }

}
