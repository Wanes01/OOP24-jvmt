package jvmt.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvmt.model.card.api.Card;
import jvmt.model.card.api.TypeCard;
import jvmt.model.card.impl.RelicCard;
import jvmt.model.card.impl.TreasureCard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

/**
 * Treasure card test class.
 * 
 * @author Andrea La Tosa
 */
class TreasureCardTest {

    @BeforeEach
    void setUp() {
    }

    // Verification of getter methods for treasure cards.
    @Test
    void correctGetterTreasureCard() {
        final int expectedGems = 5;
        final TreasureCard treasureCard = new TreasureCard("5 gems", expectedGems);

        assertEquals("5 gems", treasureCard.getName());
        assertEquals(TypeCard.TREASURE, treasureCard.getType());
        assertEquals(expectedGems, treasureCard.getGemValue());
    }

    // Check that the paths for the treasure card images are correct and
    // that if the creation of a treasure card with an unexpected number of gems is
    // requested,
    // an exception is thrown.
    @Test
    void correctPathTreasureCardAndGemValue() {
        final String sourceImagePath = "/imageCard/treasure/";
        final Set<Integer> possibleGemValue = Set.of(1, 2, 3, 4, 5, 7, 9, 11, 13, 14, 15, 17);
        for (final int gemValue : possibleGemValue) {
            final TreasureCard treasure = new TreasureCard("Treasure", gemValue);
            assertEquals(
                    treasure.getImagePath(),
                    Card.class.getResource(sourceImagePath + gemValue + "_Gem.png"));
        }
        assertThrows(IllegalArgumentException.class,
                () -> new TreasureCard("Treasure", 100));
    }

    // Check that the equals function for treasure card is working correctly
    @Test
    void equalsTreasureCard() {
        final Card treasureCard = new TreasureCard("treasure 1", 1);
        final Card treasureCard2 = new TreasureCard("treasure 1", 1);
        final Card treasureCard3 = new TreasureCard("treasure 1", 2);
        final Card relicCard = new RelicCard("Relic");
        assertEquals(treasureCard, treasureCard2);
        assertNotEquals(treasureCard, treasureCard3);
        assertNotEquals(treasureCard, relicCard);
    }
}
