package impl.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import api.card.Card;
import api.card.Deck;
import api.card.TypeDeck;
import api.card.TypeTrapCard;

/**
 * Concrete representation of the Deck interface.
 * 
 * @see DeckBuilder
 * @see Card
 * @see RelicCard
 * @see TrapCard
 * @see TreasureCard
 * 
 * @author Andrea La Tosa
 */
public class DeckImpl implements Deck {

    // Represents the deck of cards for a round
    private final List<Card> deck;
    // Useful for providing information about the cards in the deck
    private final DeckFactory deckFactory;

    /**
     * Create the deck for the round.
     * 
     * @param typeDeck the type of deck you need to create
     */
    public DeckImpl(final TypeDeck typeDeck) {
        this.deckFactory = new DeckFactory(typeDeck);
        this.deck = deckFactory.getDeck();
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
        this.deck.remove(numberOfRemainingCards() - 1);
        return drawn;
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
        return this.deck.get(numberOfRemainingCards() - 1);
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
        return this.deckFactory.deckSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int totRelicCardsInDeck() {
        return this.deckFactory.getTotRelic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int totTreasureCardsInDeck() {
        return this.deckFactory.getTotTreasure();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int totTrapCardsInDeck() {
        return this.deckFactory.getTotTrap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int totTrapCardTypesInDeck() {
        return this.deckFactory.getTotTrapCardTypes();
    }

    private static final class DeckFactory {

        // the number of cards per trap type in the standard deck.
        private static final int NUMBER_PER_TRAP_STANDARD_DECK = 3;
        // the number of relic in the standard deck.
        private static final int NUMBER_RELIC_STANDARD_DECK = 5;
        // the number of trap types used in the standard deck.
        private static final int NUMBER_TYPE_TRAP_STANDARD_DECK = TypeTrapCard.values().length;

        // In this map, the key represent the gems of the card to be created 
        // and the values indicate the number of cards to create.
        private static final Map<Integer, Integer> TREASURE_STANDARD_DECK = Map.ofEntries(
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
            Map.entry(17, 1)
        ); 

        // Contains the deck created for the round.
        private final List<Card> deck;

        // Information on the number of different types of cards in the deck.
        private int totRelic;
        private int totTreasure;
        private int totTrap;
        // The number of trap types included in the deck.
        private int totTrapCardTypes;

        /**
         * Create the deck for the round.
         * 
         * @param typeDeck the type of deck to create.
         * 
         * @see TypeDeck
         */
        private DeckFactory(final TypeDeck typeDeck) {
            this.deck = new ArrayList<>();
            createDeck(typeDeck);
        }

        /**
         * Create the deck and set the information on the newly created deck.
         * 
         * @param typeDeck the type of deck to be created
         * 
         * @see TypeDeck
         */
        private void createDeck(final TypeDeck typeDeck) {
            if (typeDeck == TypeDeck.STANDARD) {
                this.totRelic = NUMBER_RELIC_STANDARD_DECK;
                this.totTrap = NUMBER_PER_TRAP_STANDARD_DECK * NUMBER_TYPE_TRAP_STANDARD_DECK;
                this.totTreasure = calculateNumberTreasureCards(TREASURE_STANDARD_DECK);
                this.totTrapCardTypes = NUMBER_TYPE_TRAP_STANDARD_DECK;
                createStandardDeck();
            } else {
                throw new UnsupportedOperationException("the SPECIAL DECK has not been programmed yet");
            }
            deckShuffle();
        }

        /**
         * Calculate the number of treasure cards to add to the deck.
         * 
         * @param treasures a list containing the gems that the card must have 
         * and the number of cards with those gems to be created.
         * 
         * @return the number of treasure cards in the deck
         */
        private int calculateNumberTreasureCards(final Map<Integer, Integer> treasures) {
            int nTreasures = 0;
            for (final int values : treasures.values()) {
                nTreasures += values;
            }
            return nTreasures;
        }

        /**
         * Adds treasure, trap, and relic cards to the deck 
         * for standard deck configuration.
         */
        private void createStandardDeck() {
            this.deck.addAll(addTrapsDeckStandard());
            this.deck.addAll(addTreasuresDeckStandard());
            this.deck.addAll(addRelicDeckStandard());
        }

        /**
         * @return the deck created.
         */
        private List<Card> getDeck() {
            return this.deck;
        }

        /**
         * @return the number of cards in the deck.
         */
        private int deckSize() {
            return this.deck.size();
        }

        /**
         * @return the number of relic cards in the deck.
         */
        public int getTotRelic() {
            return this.totRelic;
        }

        /**
         * @return the number of treasure cards in the deck.
         */
        public int getTotTreasure() {
            return this.totTreasure;
        }

        /**
         * @return the number of trap cards in the deck
         */
        public int getTotTrap() {
            return this.totTrap;
        }

        /**
         * @return the number of trap card types in the deck
         */
        public int getTotTrapCardTypes() {
            return this.totTrapCardTypes;
        }

        /**
         * Shuffle the cards in the deck.
         * 
         * @see Collections
         */
        private void deckShuffle() {
            Collections.shuffle(this.deck);
        }

        /**
         * Create a list of all trap card types and their quantities for the standard deck.
         * 
         * @return a list of TrapCard
         * 
         * @see TrapCard
         */
        private List<TrapCard> addTrapsDeckStandard() {
            final List<TrapCard> traps = new ArrayList<>();
            for (final TypeTrapCard typeTrap : TypeTrapCard.values()) {
                traps.addAll(addMultipleTrap(typeTrap, NUMBER_PER_TRAP_STANDARD_DECK));
            }
            return traps;
        }

        /**
         * Create a list of a specific type of trap card.
         * 
         * @param typeTrap the type of trap cards to be created
         * @param numTrap the number of that type of trap card to be created
         * 
         * @return a list of trap cards
         */
        private List<TrapCard> addMultipleTrap(final TypeTrapCard typeTrap, final int numTrap) {
            final List<TrapCard> traps = new ArrayList<>();
            for (int i = 0; i < numTrap; i++) {
                traps.add(addTrap(typeTrap, i));
            }
            return traps;
        }

        /**
         * Creates a trap card of the type provided in the input.
         * 
         * @param typeTrap the type of trap cards to be created
         * @param i used for the name of the card
         * 
         * @return new trap card
         */
        private TrapCard addTrap(final TypeTrapCard typeTrap, final int i) {
            return new TrapCard(typeTrap.toString() + " " + Integer.toString(i), typeTrap);
        }


        /**
         * Create a list of treasure cards to add to the standard deck.
         * 
         * @return a list of treasure cards
         */
        private List<TreasureCard> addTreasuresDeckStandard() {
            return addTreasuresDeck(TREASURE_STANDARD_DECK);
        }

        /**
         * Create a list of treasure cards based on the map provided as input.
         * 
         * @param treasuresMap the input map for creating the treasure card list
         * 
         * @return a list of treasure cards
         */
        private List<TreasureCard> addTreasuresDeck(final Map<Integer, Integer> treasuresMap) {

            // Useful for the name of the treasure card.
            final int singleTreasure = 1;

            final List<TreasureCard> treasures = new ArrayList<>();

            for (final Map.Entry<Integer, Integer> entry : treasuresMap.entrySet()) {
                if (entry.getValue() == 1) {
                    treasures.add(addTreasure(entry.getKey(), singleTreasure));
                } else {
                    treasures.addAll(addMultipleTreasure(entry.getKey(), entry.getValue()));
                }
            }

            return treasures;
        }

        /**
         * Create a list of treasure cards all having the same gem value.
         * 
         * @param gemValue the number of gems on the cards to be created
         * @param numTreasure the number of cards to be created
         * 
         * @return a list of treasure cards
         */
        private List<TreasureCard> addMultipleTreasure(final int gemValue, final int numTreasure) {
            final List<TreasureCard> treasures = new ArrayList<>();
            for (int i = 0; i < numTreasure; i++) {
                treasures.add(addTreasure(gemValue, i));
            }
            return treasures;
        }

        /**
         * Create a new treasure card.
         * 
         * @param gemValue the gem value of the card to be created
         * @param i used to generate the card name
         * 
         * @return new treasure card
         */
        private TreasureCard addTreasure(final int gemValue, final int i) {
            return new TreasureCard("Treasure " + Integer.toString(gemValue) + " gems " 
                + Integer.toString(i), gemValue);
        }

        /**
         * @return a list of relic cards for the standard deck
         */
        private List<RelicCard> addRelicDeckStandard() {
            return addMultipleRelicCard(NUMBER_RELIC_STANDARD_DECK);
        }

        /**
         * Create a list of relic cards.
         * 
         * @param numRelic number of relic cards to be created.
         * 
         * @return a list of relic cards
         */
        private List<RelicCard> addMultipleRelicCard(final int numRelic) {
            final List<RelicCard> relics = new ArrayList<>();
            for (int i = 0; i < numRelic; i++) {
                relics.add(addRelic(i));
            }
            return relics;
        }

        /**
         * Create a new relic card.
         * 
         * @param i used to generate the card name
         * 
         * @return a new relic card
         */
        private RelicCard addRelic(final int i) {
            return new RelicCard("Relic " + Integer.toString(i));
        }
    }

}
