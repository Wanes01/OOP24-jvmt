package jvmt.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvmt.model.player.api.Player;
import jvmt.model.player.api.PlayerChoice;
import jvmt.model.player.impl.PlayerInRound;

/**
 * Plyer in round test class.
 * 
 * @author Filippo Gaggi
 */
class PlayerInRoundTest {

    private static final String PLAYER_NAME = "TestReal";
    private static final String PLAYER_DIFF_NAME = "TestDiffReal";
    private Player test;

    @BeforeEach
    void setUp() {
        test = new PlayerInRound(PLAYER_NAME);
    }

    // -- Testing the initial values --
    @Test
    void testInitialValues() {
        assertEquals(PLAYER_NAME, test.getName());
        assertEquals(0, test.getChestGems());
        assertEquals(0, test.getSackGems());
        assertEquals(PlayerChoice.STAY, test.getChoice());
    }

    // -- Testing methods for modifying the sack's and chest's gems --
    @Test
    void addSackGemsPositiveTest() {
        final int gemsToAdd = 5;
        test.addSackGems(gemsToAdd);
        assertEquals(gemsToAdd, test.getSackGems());
    }

    @Test
    void addSackGemsNegativeTest() {
        final int gemsToAdd = -5;
        assertThrows(IllegalArgumentException.class, () -> test.addSackGems(gemsToAdd));
    }

    @Test
    void subSackGemsPositiveTest() {
        final int initialGems = 10;
        final int gemsToSubtract = 5;
        test.addSackGems(initialGems);
        test.subSackGems(gemsToSubtract);
        assertEquals(initialGems - gemsToSubtract, test.getSackGems());
    }

    @Test
    void subSackGemsExceedingTest() {
        final int initialGems = 5;
        final int gemsToSubtract = 10;
        test.addSackGems(initialGems);
        test.subSackGems(gemsToSubtract);
        assertEquals(0, test.getSackGems());
    }

    @Test
    void subSackGemsNegativeTest() {
        final int initialGems = 10;
        final int gemsToSubtract = -5;
        test.addSackGems(initialGems);
        assertThrows(IllegalArgumentException.class, () -> test.subSackGems(gemsToSubtract));
    }

    @Test
    void addSackToChestTest() {
        final int gemsInSack = 5;
        test.addSackGems(gemsInSack);
        test.addSackToChest();
        assertEquals(gemsInSack, test.getChestGems());
        assertEquals(0, test.getSackGems());
    }

    @Test
    void subChestGemsPositiveTest() {
        final int initialGems = 10;
        final int gemsToSubtract = 5;
        test.addSackGems(initialGems);
        test.addSackToChest();
        test.subChestGems(gemsToSubtract);
        assertEquals(initialGems - gemsToSubtract, test.getChestGems());
    }

    @Test
    void subChestGemsExceedingTest() {
        final int initialGems = 5;
        final int gemsToSubtract = 10;
        test.addSackGems(initialGems);
        test.addSackToChest();
        test.subChestGems(gemsToSubtract);
        assertEquals(0, test.getChestGems());
    }

    @Test
    void subChestGemsNegativeTest() {
        final int initialGems = 10;
        final int gemsToSubtract = -5;
        test.addSackGems(initialGems);
        test.addSackToChest();
        assertThrows(IllegalArgumentException.class, () -> test.subChestGems(gemsToSubtract));
    }

    // -- Testing methods for managing the player choice --
    @Test
    void chooseTest() {
        test.choose(PlayerChoice.EXIT);
        assertEquals(PlayerChoice.EXIT, test.getChoice());
    }

    @Test
    void exitTest() {
        test.exit();
        assertEquals(PlayerChoice.EXIT, test.getChoice());
    }

    @Test
    void impossibleExitTest() {
        test.choose(PlayerChoice.EXIT);
        assertThrows(IllegalStateException.class, test::exit);
    }

    // -- Testing methods for reset --
    @Test
    void resetSackTest() {
        final int gemsToAdd = 5;
        test.addSackGems(gemsToAdd);
        test.resetSack();
        assertEquals(0, test.getSackGems());
    }

    @Test
    void resetChoiceTest() {
        test.choose(PlayerChoice.EXIT);
        test.resetChoice();
        assertEquals(PlayerChoice.STAY, test.getChoice());
    }

    @Test
    void resetRoundPlayerTest() {
        final int gemsToAdd = 5;
        test.addSackGems(gemsToAdd);
        test.choose(PlayerChoice.EXIT);
        test.resetRoundPlayer();
        assertEquals(0, test.getSackGems());
        assertEquals(PlayerChoice.STAY, test.getChoice());
    }

    // -- Testing for hashCode() and equals() methods --
    @Test
    void hashCodeTest() {
        final PlayerInRound other = new PlayerInRound(PLAYER_NAME);
        assertEquals(test.hashCode(), other.hashCode());
    }

    @Test
    void hashCodeDiffNameTest() {
        final PlayerInRound other = new PlayerInRound(PLAYER_DIFF_NAME);
        assertNotEquals(test.hashCode(), other.hashCode());
    }

    @Test
    void equalsTest() {
        final PlayerInRound other = new PlayerInRound(PLAYER_NAME);
        assertEquals(test, other);
    }

    @Test
    void equalsDiffNameTest() {
        final PlayerInRound other = new PlayerInRound(PLAYER_DIFF_NAME);
        assertNotEquals(test, other);
    }

    @Test
    void equalsNullTest() {
        assertNotNull(test);
    }

    @Test
    void equalsDiffClassTest() {
        final Object other = new Object();
        assertNotEquals(test, other);
    }
}
