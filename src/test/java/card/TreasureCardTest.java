package card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.card.api.Card;
import model.card.api.TypeCard;
import model.card.impl.TreasureCard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    void setUp() { }

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

    @Test
    void correctPathTreasureCard() {
        final Set<Integer> possibleGemValue =
            Set.of(1, 2, 3, 4, 5, 7, 9, 11, 13, 14, 15, 17); 
        for (final int gemValue : possibleGemValue) {
            assertDoesNotThrow(() -> new TreasureCard("Treasure" + Integer.toString(gemValue) + " gems", gemValue));
        }
        assertThrows(IllegalArgumentException.class, () -> new TreasureCard("Treasure", 100));
    }

    @Test
    void equalsTreasureCard() {
        final Card treasureCard = new TreasureCard("treasure 1", 1);
        final  Card treasureCard2 = new TreasureCard("treasure 1", 1);
        final  Card treasureCard3 = new TreasureCard("treasure 1", 2);
        assertEquals(treasureCard, treasureCard2);
        assertNotEquals(treasureCard, treasureCard3);
    }
}
