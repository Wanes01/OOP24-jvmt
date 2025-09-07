package jvmt.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvmt.model.card.api.Card;
import jvmt.model.card.api.TypeCard;
import jvmt.model.card.api.TypeTrapCard;
import jvmt.model.card.impl.TrapCard;

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
        assertEquals(
            trapCard.getImagePath(),
            Card.class.getResource(imagePathBoulder));
    }

    // Check each trap card to see if the corresponding image is present.
    @Test
    void correctPathTrapCard() {
        final String sourceImagePath = "/imageCard/trap/";
        final List<String> trapCardImage = List.of(
            "Battering_Ram.png",
            "Boulder.png",
            "Lava.png",
            "Snake.png",
            "Spider.png");
        final List<TrapCard> trapCard = List.of(
            new TrapCard("Battering_Ram", TypeTrapCard.BATTERING_RAM),
            new TrapCard("Boulder", TypeTrapCard.BOULDER),
            new TrapCard("Lava", TypeTrapCard.LAVA),
            new TrapCard("Snake", TypeTrapCard.SNAKE),
            new TrapCard("Spider", TypeTrapCard.SPIDER));

        assertEquals(trapCard.size(), trapCard.size());

        for (int i = 0; i < trapCard.size(); i++) {
            assertEquals(
                trapCard.get(i).getImagePath(),
                Card.class.getResource(sourceImagePath + trapCardImage.get(i)));
        }
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
