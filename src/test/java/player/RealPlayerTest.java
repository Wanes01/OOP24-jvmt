package player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.player.PlayerChoice;
import impl.player.RealPlayer;

public class RealPlayerTest {

    RealPlayer test;
    private static final String PLAYER_NAME = "TestReal";
    private static final String PLAYER_DIFF_NAME = "TestDiffReal";

    @BeforeEach
    void setUp() {
        test = new RealPlayer(PLAYER_NAME);
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
    void ExitTest() {
        test.exit();
        assertEquals(PlayerChoice.EXIT, test.getChoice());
    }

    @Test
    void impossibleExitTest() {
        test.choose(PlayerChoice.EXIT);
        assertThrows(IllegalStateException.class, () -> test.exit());
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
        final RealPlayer other = new RealPlayer(PLAYER_NAME);
        assertEquals(test.hashCode(), other.hashCode());
    }
    
    @Test
    void hashCodeDiffNameTest() {
        final RealPlayer other = new RealPlayer(PLAYER_DIFF_NAME);
        assertNotEquals(test.hashCode(), other.hashCode());
    }

    @Test
    void hashCodeDiffChestTest() {
        final int gemsToAddToTest = 5;
        final int gemsToAddToOther = 10;
        final RealPlayer other = new RealPlayer(PLAYER_NAME);
        test.addSackGems(gemsToAddToTest);
        test.addSackToChest();
        other.addSackGems(gemsToAddToOther);
        other.addSackToChest();
        assertNotEquals(test.hashCode(), other.hashCode());
    }

    @Test
    void hashCodeDiffSackTest() {
        final int gemsToAddToTest = 5;
        final int gemsToAddToOther = 10;
        final RealPlayer other = new RealPlayer(PLAYER_NAME);
        test.addSackGems(gemsToAddToTest);
        other.addSackGems(gemsToAddToOther);
        assertNotEquals(test.hashCode(), other.hashCode());
    }

    @Test
    void hashCodeDiffChoiceTest() {
        final RealPlayer other = new RealPlayer(PLAYER_NAME);
        test.choose(PlayerChoice.STAY);
        other.choose(PlayerChoice.EXIT);
        assertNotEquals(test.hashCode(), other.hashCode());
    }

    @Test
    void equalsTest() {
        final RealPlayer other = new RealPlayer(PLAYER_NAME);
        assertTrue(test.equals(other));
    }
    
    @Test
    void equalsDiffNameTest() {
        final RealPlayer other = new RealPlayer(PLAYER_DIFF_NAME);
        assertFalse(test.equals(other));
    }

    @Test
    void equalsDiffChestTest() {
        final int gemsToAddToTest = 5;
        final int gemsToAddToOther = 10;
        final RealPlayer other = new RealPlayer(PLAYER_NAME);
        test.addSackGems(gemsToAddToTest);
        test.addSackToChest();
        other.addSackGems(gemsToAddToOther);
        other.addSackToChest();
        assertFalse(test.equals(other));
    }

    @Test
    void equalsDiffSackTest() {
        final int gemsToAddToTest = 5;
        final int gemsToAddToOther = 10;
        final RealPlayer other = new RealPlayer(PLAYER_NAME);
        test.addSackGems(gemsToAddToTest);
        other.addSackGems(gemsToAddToOther);
        assertFalse(test.equals(other));
    }

    @Test
    void equalsDiffChoiceTest() {
        final RealPlayer other = new RealPlayer(PLAYER_NAME);
        test.choose(PlayerChoice.STAY);
        other.choose(PlayerChoice.EXIT);
        assertFalse(test.equals(other));
    }

    @Test
    void equalsNullTest() {
        final RealPlayer other = null;
        assertFalse(test.equals(other));
    }

    @Test
    void equalsDiffClassTest() {
        final Object other = new Object();
        assertFalse(test.equals(other));
    }
}
