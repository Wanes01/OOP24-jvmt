package jvmt.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvmt.model.card.api.Card;
import jvmt.model.card.api.TypeCard;
import jvmt.model.card.api.TypeTrapCard;
import jvmt.model.card.impl.RelicCard;
import jvmt.model.card.impl.TrapCard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.List;

/**
 * Relic card test class.
 * 
 * @author Andrea La Tosa
 */
class RelicCardTest {

    @BeforeEach
    void setUp() {
    }

    // Check that if the card name is null, it throws an exception
    @Test
    void inputCardNameNull() {
        assertThrows(NullPointerException.class, () -> new RelicCard(null));
    }

    // Verification of getter methods for relic cards
    @Test
    void correctGetterRelicCard() {
        final RelicCard relicCard = new RelicCard("Relic");
        final String imagePathRelic = "/imageCard/relic/Relic.png";
        final URL imageUrlRelic = Card.class.getResource(imagePathRelic);
        final List<Integer> possibleValues = List.of(5, 7, 8, 10, 12);

        assertEquals("Relic", relicCard.getName());
        assertTrue(possibleValues.contains(relicCard.getGemValue()));
        assertEquals(TypeCard.RELIC, relicCard.getType());
        assertEquals(imageUrlRelic, relicCard.getImagePath());
        assertFalse(relicCard.isRedeemed());
        relicCard.redeemCard();
        assertTrue(relicCard.isRedeemed());
    }

    // Check that the equals function for RelicCard is working correctly
    @Test
    void equalsRelicCard() {
        final Card relicCard = new RelicCard("relic 1");
        final Card relicCard2 = new RelicCard("relic 1");
        final Card trapCard = new TrapCard("relic 1", TypeTrapCard.LAVA);
        assertEquals(relicCard, relicCard2);
        assertNotEquals(relicCard, trapCard);
    }
}
