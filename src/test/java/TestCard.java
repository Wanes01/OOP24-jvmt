import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.card.Card;
import api.card.Deck;
import api.card.TypeCard;
import api.card.TypeDeck;
import api.card.TypeTrapCard;
import impl.card.DeckImpl;
import impl.card.RelicCard;
import impl.card.TrapCard;
import impl.card.TreasureCard;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestCard {

    private Deck deck;

    @BeforeEach
    void setUp() {
       deck = new DeckImpl(TypeDeck.STANDARD);
    }

    // number of cards in a standard deck
    @Test
    void numberCardsInStandardDeck() {
        final int nCardStandard = 35;
        assertEquals(nCardStandard, deck.deckSize());
    }

    @Test
    void numberTrapCardsInStandardDeck() {
        final int nTrapCards = 15;
        assertEquals(nTrapCards, deck.totTrapCardsInDeck());
    }

    @Test
    void numberRelicCardsInStandardDeck() {
        final int nRelicCards = 5;
        assertEquals(nRelicCards, deck.totRelicCardsInDeck());
    }

    @Test
    void numberTreasureCardsInStandardDeck() {
        final int nTreasureCard = 15;
        assertEquals(nTreasureCard, deck.totTreasureCardsInDeck());
    }

    // Check that peekCard does not return a null element 
    // and that it does not remove it from the deck.
    @Test
    void peekCard() {
        final int deckSize = deck.deckSize();
        Card card = deck.peekCard();
        assertNotNull(card, "The card must not be null.");
        assertEquals(deckSize, deck.deckSize());
    }

    // Check that the card returned from the deck is not null
    // and that the card has been removed from the deck.
    @Test
    void nextCardNotNull() {
        final int deckSize = deck.numberOfRemainingCards();
        Card nextCard = deck.next();
        assertNotNull(nextCard, "The card drawn must not be null.");
        assertEquals(deck.numberOfRemainingCards() ,deckSize - 1);
    }

    @Test
    void numberTrapTypesStandardDeck() {
        final int nTrapTypes = 5;
        assertEquals(nTrapTypes, deck.totTrapCardTypesInDeck());
    }

    // Verification of getter methods for trap cards.
    @Test
    void correctGetterTrapCard() {
        final TrapCard trapCard = new TrapCard("Boulder", TypeTrapCard.BOULDER);
        final String imagePathBoulder = "/imageCard/trap/Boulder.png";

        assertEquals("Boulder", trapCard.getName());
        assertEquals(TypeCard.TRAP, trapCard.getType());
        assertEquals(TypeTrapCard.BOULDER, trapCard.getTypeTrap());
        assertEquals(imagePathBoulder, trapCard.getImagePath());
    }

    // Verification of getter methods for treasure cards.
    @Test
    void correctGetterTreasureCard() {
        final TreasureCard treasureCard = new TreasureCard("5 gems", 5);
        final String imagePath5gem = "/imageCard/treasure/5_Gem.png";
        final int expectedGems = 5;

        assertEquals("5 gems", treasureCard.getName());
        assertEquals(TypeCard.TREASURE, treasureCard.getType());
        assertEquals(expectedGems, treasureCard.getGemValue());
        assertEquals(imagePath5gem, treasureCard.getImagePath());
    }

    // Verification of getter methods for relic cards.
    @Test
    void correctGetterRelicCard() {
        final RelicCard relicCard = new RelicCard("Relic");
        final String imagePathRelic = "/imageCard/relic/Relic.png";
        final List<Integer> possibleValues = List.of(5, 7, 8, 10, 12);

        assertEquals("Relic", relicCard.getName());
        assertTrue(possibleValues.contains(relicCard.getGemValue()));
        assertEquals(TypeCard.RELIC, relicCard.getType());
        assertEquals(imagePathRelic, relicCard.getImagePath());
        assertFalse(relicCard.isRedeemed());
        relicCard.redeemCard();
        assertTrue(relicCard.isRedeemed());
    }

    // Check each trap card to see if the corresponding image is present.
    @Test 
    void correctPathTrapCard() {

        assertDoesNotThrow(() -> {
            Card trapBatteringRam = new TrapCard("Battering ram", TypeTrapCard.BATTERING_RAM);
        }, "Image associated with trap card BATTERING RAM not found");

        assertDoesNotThrow(() -> {
            Card trapBoulder = new TrapCard("Boulder", TypeTrapCard.BOULDER);
        }, "Image associated with trap card BOULDER not found");

        assertDoesNotThrow(() -> {
            Card trapLava = new TrapCard("Lava", TypeTrapCard.LAVA);
        }, "Image associated with trap card LAVA not found");

        assertDoesNotThrow(() -> {
            Card trapSnake= new TrapCard("Snake", TypeTrapCard.SNAKE);
        }, "Image associated with trap card SNAKE not found");

        assertDoesNotThrow(() -> {
            Card trapSpider = new TrapCard("Spider", TypeTrapCard.SPIDER);
        }, "Image associated with trap card SPIDER not found");
    }

    @Test
    void correctPathTreasureCard() {
        final Set<Integer> POSSIBLE_GEM_VALUES =
            Set.of(1, 2, 3, 4, 5, 7, 9, 11, 13, 14, 15, 17);
        Card treasureCard; 
        for(final int gemValue : POSSIBLE_GEM_VALUES) {
            treasureCard = new TreasureCard(
                "Treasure" + Integer.toString(gemValue) + " gems", gemValue);
        }
        assertThrows(IllegalArgumentException.class, () -> {
            Card treasure = new TreasureCard("Treasure", 100);
        });
    
    }

    @Test
    void equalsTreasureCard() {
        Card treasureCard = new TreasureCard("treasure 1", 1);
        Card treasureCard2 = new TreasureCard("treasure 1", 1);
        Card treasureCard3 = new TreasureCard("treasure 1", 2);
        assertTrue(treasureCard.equals(treasureCard2));
        assertFalse(treasureCard.equals(treasureCard3));
    }

    @Test
    void equalsTrapCard() {
        Card trapCard = new TrapCard("trap 1", TypeTrapCard.SNAKE);
        Card trapCard2 = new TrapCard("trap 1", TypeTrapCard.SNAKE);
        Card trapCard3 = new TrapCard("trap 1", TypeTrapCard.LAVA);
        assertTrue(trapCard.equals(trapCard2));
        assertFalse(trapCard.equals(trapCard3));
    }

    @Test
    void equalsRelicCard() {
        Card relicCard = new RelicCard("relic 1");
        Card relicCard2 = new RelicCard("relic 1");
        Card trapCard = new TrapCard("relic 1", TypeTrapCard.LAVA);
        assertTrue(relicCard.equals(relicCard2));
        assertFalse(relicCard.equals(trapCard));

    }

    @Test
    void DrawnWithEmptyDeck() {
        while(deck.hasNext()) {
            deck.next();
        }
        assertThrows(NoSuchElementException.class, () -> { deck.next(); });
    }
}
