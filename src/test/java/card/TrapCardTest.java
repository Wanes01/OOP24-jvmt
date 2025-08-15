package card;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.card.api.Card;
import model.card.api.TypeCard;
import model.card.api.TypeTrapCard;
import model.card.impl.TrapCard;

/**
 * Trap card test class.
 * 
 * @author Andrea La Tosa
 */
class TrapCardTest {

    @BeforeEach
    void setUp() { }

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

    // Check each trap card to see if the corresponding image is present.
    @Test 
    void correctPathTrapCard() {
        assertDoesNotThrow(() -> new TrapCard("Battering ram", TypeTrapCard.BATTERING_RAM),
            "Image associated with trap card BATTERING RAM not found");

        assertDoesNotThrow(() -> new TrapCard("Boulder", TypeTrapCard.BOULDER),
            "Image associated with trap card BOULDER not found");

        assertDoesNotThrow(() -> new TrapCard("Lava", TypeTrapCard.LAVA),
            "Image associated with trap card LAVA not found");

        assertDoesNotThrow(() -> new TrapCard("Snake", TypeTrapCard.SNAKE),
            "Image associated with trap card SNAKE not found");

        assertDoesNotThrow(() -> new TrapCard("Spider", TypeTrapCard.SPIDER),
            "Image associated with trap card SPIDER not found");
    }

    // Check that the equals function for TrapCard is working correctly
    @Test
    void equalsTrapCard() {
        final Card trapCard = new TrapCard("trap 1", TypeTrapCard.SNAKE);
        final Card trapCard2 = new TrapCard("trap 1", TypeTrapCard.SNAKE);
        final Card trapCard3 = new TrapCard("trap 1", TypeTrapCard.LAVA);
        assertEquals(trapCard, trapCard2);
        assertNotEquals(trapCard, trapCard3);
    }
}
